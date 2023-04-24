/*
 * Copyright (c) 2012-2016 Arne Schwabe
 * Distributed under the GNU GPL v2 with additional terms. For full terms see the file doc/LICENSE.txt
 */
package de.blinkt.openvpn.core;

import android.content.Context;
import android.os.Build;
import android.os.HandlerThread;
import android.os.Message;

import com.leopard.vpn.R;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Vector;

public class VpnStatus {
    final static java.lang.Object readFileLock = new Object();
    static final int MAXLOGENTRIES = 1000;
    // keytool -printcert -jarfile de.blinkt.openvpn85.apk
    static final byte[] officalkey = {-58, -42, -44, -106, 90, -88, -87, -88, -52, -124, 84, 117, 66, 79, -112, -111, -46, 86, -37, 109};
    static final byte[] officaldebugkey = {-99, -69, 45, 71, 114, -116, 82, 66, -99, -122, 50, -70, -56, -111, 98, -35, -65, 105, 82, 43};
    static final byte[] amazonkey = {-116, -115, -118, -89, -116, -112, 120, 55, 79, -8, -119, -23, 106, -114, -85, -56, -4, 105, 26, -57};
    static final byte[] fdroidkey = {-92, 111, -42, -46, 123, -96, -60, 79, -27, -31, 49, 103, 11, -54, -68, -27, 17, 2, 121, 104};
    private static final LinkedList<LogItem> logbuffer;
    public static TrafficHistory trafficHistory;
    static boolean readFileLog = false;
    private static Vector<LogListener> logListener;
    private static Vector<StateListener> stateListener;
    private static Vector<ByteCountListener> byteCountListener;
    private static String mLaststatemsg = "";
    private static String mLaststate = "NOPROCESS";
    private static int mLastStateresid = R.string.statenoprocess;
    private static HandlerThread mHandlerThread;
    private static String mLastConnectedVPNUUID;
    private static ConnectionStatus mLastLevel = ConnectionStatus.LEVELNOTCONNECTED;
    private static LogFileHandler mLogFileHandler;

    static {
        logbuffer = new LinkedList<>();
        logListener = new Vector<>();
        stateListener = new Vector<>();
        byteCountListener = new Vector<>();
        trafficHistory = new TrafficHistory();
        logInformation();
    }

    public static void logException(LogLevel ll, String context, Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        LogItem li;
        if (context != null) {
            li = new LogItem(ll, R.string.unhandledexceptioncontext, e.getMessage(), sw.toString(), context);
        } else {
            li = new LogItem(ll, R.string.unhandledexception, e.getMessage(), sw.toString());
        }
        newLogItem(li);
    }

    public static void logException(Exception e) {
        logException(LogLevel.ERROR, null, e);
    }

    public static void logException(String context, Exception e) {
        logException(LogLevel.ERROR, context, e);
    }

    public static boolean isVPNActive() {
        return mLastLevel != ConnectionStatus.LEVELAUTHFAILED && !(mLastLevel == ConnectionStatus.LEVELNOTCONNECTED);
    }

    public static String getLastCleanLogMessage(Context c) {
        String message = mLaststatemsg;
        switch (mLastLevel) {
            case LEVELCONNECTED:
                String[] parts = mLaststatemsg.split(",");
                /*
                   (a) the integer unix date/time,
                   (b) the state name,
                   0 (c) optional descriptive string (used mostly on RECONNECTING
                    and EXITING to show the reason for the disconnect),
                    1 (d) optional TUN/TAP local IPv4 address
                   2 (e) optional address of remote server,
                   3 (f) optional port of remote server,
                   4 (g) optional local address,
                   5 (h) optional local port, and
                   6 (i) optional TUN/TAP local IPv6 address.
*/
                // Return only the assigned IP addresses in the UI
                if (parts.length >= 7) message = String.format(Locale.US, "%s %s", parts[1], parts[6]);
                break;
        }
        while (message.endsWith(",")) message = message.substring(0, message.length() - 1);
        String status = mLaststate;
        if (status.equals("NOPROCESS")) return message;
        if (mLastStateresid == R.string.statewaitconnectretry) {
            return c.getString(R.string.statewaitconnectretry, mLaststatemsg);
        }
        String prefix = c.getString(mLastStateresid);
        if (mLastStateresid == R.string.unknownstate) message = status + message;
        if (message.length() > 0) prefix += ": ";
        return prefix + message;
    }

    public static void initLogCache(File cacheDir) {
        mHandlerThread = new HandlerThread("LogFileWriter", Thread.MINPRIORITY);
        mHandlerThread.start();
        mLogFileHandler = new LogFileHandler(mHandlerThread.getLooper());
        Message m = mLogFileHandler.obtainMessage(LogFileHandler.LOGINIT, cacheDir);
        mLogFileHandler.sendMessage(m);
    }

    public static void flushLog() {
        if (mLogFileHandler != null) mLogFileHandler.sendEmptyMessage(LogFileHandler.FLUSHTODISK);
    }

    public static void setConnectedVPNProfile(String uuid) {
        mLastConnectedVPNUUID = uuid;
        for (StateListener sl : stateListener)
            sl.setConnectedVPN(uuid);
    }

    public static String getLastConnectedVPNProfile() {
        return mLastConnectedVPNUUID;
    }

    public static void setTrafficHistory(TrafficHistory trafficHistory) {
        VpnStatus.trafficHistory = trafficHistory;
    }

    public synchronized static void logMessage(LogLevel level, String prefix, String message) {
        newLogItem(new LogItem(level, prefix + message));
    }

    public synchronized static void clearLog() {
        logbuffer.clear();
        logInformation();
        if (mLogFileHandler != null) mLogFileHandler.sendEmptyMessage(LogFileHandler.TRIMLOGFILE);
    }

    private static void logInformation() {
        String nativeAPI;
        try {
            nativeAPI = NativeUtils.getNativeAPI();
        } catch (UnsatisfiedLinkError ignore) {
            nativeAPI = "error";
        }
        logInfo(R.string.mobileinfo, Build.MODEL, Build.BOARD, Build.BRAND, Build.VERSION.SDK_INT, nativeAPI, Build.VERSION.RELEASE, Build.ID, Build.FINGERPRINT, "", "");
    }

    public synchronized static void addLogListener(LogListener ll) {
        logListener.add(ll);
    }

    public synchronized static void removeLogListener(LogListener ll) {
        logListener.remove(ll);
    }

    public synchronized static void addByteCountListener(ByteCountListener bcl) {
        TrafficHistory.LastDiff diff = trafficHistory.getLastDiff(null);
        bcl.updateByteCount(diff.getIn(), diff.getOut(), diff.getDiffIn(), diff.getDiffOut());
        byteCountListener.add(bcl);
    }

    public synchronized static void removeByteCountListener(ByteCountListener bcl) {
        byteCountListener.remove(bcl);
    }

    public synchronized static void addStateListener(StateListener sl) {
        if (!stateListener.contains(sl)) {
            stateListener.add(sl);
            if (mLaststate != null) sl.updateState(mLaststate, mLaststatemsg, mLastStateresid, mLastLevel);
        }
    }

    private static int getLocalizedState(String state) {
        switch (state) {
            case "CONNECTING":
                return R.string.stateconnecting;
            case "WAIT":
                return R.string.statewait;
            case "AUTH":
                return R.string.stateauth;
            case "GETCONFIG":
                return R.string.stategetconfig;
            case "ASSIGNIP":
                return R.string.stateassignip;
            case "ADDROUTES":
                return R.string.stateaddroutes;
            case "CONNECTED":
                return R.string.stateconnected;
            case "DISCONNECTED":
                return R.string.statedisconnected;
            case "RECONNECTING":
                return R.string.statereconnecting;
            case "EXITING":
                return R.string.stateexiting;
            case "RESOLVE":
                return R.string.stateresolve;
            case "TCPCONNECT":
                return R.string.statetcpconnect;
            default:
                return R.string.unknownstate;
        }
    }

    public static void updateStatePause(OpenVPNManagement.pauseReason pauseReason) {
        switch (pauseReason) {
            case noNetwork:
                VpnStatus.updateStateString("NONETWORK", "", R.string.statenonetwork, ConnectionStatus.LEVELNONETWORK);
                break;
            case screenOff:
                VpnStatus.updateStateString("SCREENOFF", "", R.string.statescreenoff, ConnectionStatus.LEVELVPNPAUSED);
                break;
            case userPause:
                VpnStatus.updateStateString("USERPAUSE", "", R.string.stateuserpause, ConnectionStatus.LEVELVPNPAUSED);
                break;
        }
    }

    private static ConnectionStatus getLevel(String state) {
        String[] noreplyet = {"CONNECTING", "WAIT", "RECONNECTING", "RESOLVE", "TCPCONNECT"};
        String[] reply = {"AUTH", "GETCONFIG", "ASSIGNIP", "ADDROUTES"};
        String[] connected = {"CONNECTED"};
        String[] notconnected = {"DISCONNECTED", "EXITING"};
        for (String x : noreplyet)
            if (state.equals(x)) return ConnectionStatus.LEVELCONNECTINGNOSERVERREPLYYET;
        for (String x : reply)
            if (state.equals(x)) return ConnectionStatus.LEVELCONNECTINGSERVERREPLIED;
        for (String x : connected)
            if (state.equals(x)) return ConnectionStatus.LEVELCONNECTED;
        for (String x : notconnected)
            if (state.equals(x)) return ConnectionStatus.LEVELNOTCONNECTED;
        return ConnectionStatus.UNKNOWNLEVEL;
    }

    public synchronized static void removeStateListener(StateListener sl) {
        stateListener.remove(sl);
    }

    synchronized public static LogItem[] getlogbuffer() {
        // The stoned way of java to return an array from a vector
        // brought to you by eclipse auto complete
        return logbuffer.toArray(new LogItem[logbuffer.size()]);
    }

    static void updateStateString(String state, String msg) {
        int rid = getLocalizedState(state);
        ConnectionStatus level = getLevel(state);
        updateStateString(state, msg, rid, level);
    }

    public synchronized static void updateStateString(String state, String msg, int resid, ConnectionStatus level) {
        // Workound for OpenVPN doing AUTH and wait and being connected
        // Simply ignore these state
        if (mLastLevel == ConnectionStatus.LEVELCONNECTED && (state.equals("WAIT") || state.equals("AUTH"))) {
            newLogItem(new LogItem((LogLevel.DEBUG), String.format("Ignoring OpenVPN Status in CONNECTED state (%s->%s): %s", state, level.toString(), msg)));
            return;
        }
        mLaststate = state;
        mLaststatemsg = msg;
        mLastStateresid = resid;
        mLastLevel = level;
        for (StateListener sl : stateListener) {
            sl.updateState(state, msg, resid, level);
        }
        newLogItem(new LogItem((LogLevel.DEBUG), String.format("New OpenVPN Status (%s->%s): %s", state, level.toString(), msg)));
    }

    public static void logInfo(String message) {
        newLogItem(new LogItem(LogLevel.INFO, message));
    }

    public static void logDebug(String message) {
        newLogItem(new LogItem(LogLevel.DEBUG, message));
    }

    public static void logInfo(int resourceId, Object... args) {
        newLogItem(new LogItem(LogLevel.INFO, resourceId, args));
    }

    public static void logDebug(int resourceId, Object... args) {
        newLogItem(new LogItem(LogLevel.DEBUG, resourceId, args));
    }

    static void newLogItem(LogItem logItem) {
        newLogItem(logItem, false);
    }

    synchronized static void newLogItem(LogItem logItem, boolean cachedLine) {
        if (cachedLine) {
            logbuffer.addFirst(logItem);
        } else {
            logbuffer.addLast(logItem);
            if (mLogFileHandler != null) {
                Message m = mLogFileHandler.obtainMessage(LogFileHandler.LOGMESSAGE, logItem);
                mLogFileHandler.sendMessage(m);
            }
        }
        if (logbuffer.size() > MAXLOGENTRIES + MAXLOGENTRIES / 2) {
            while (logbuffer.size() > MAXLOGENTRIES) logbuffer.removeFirst();
            if (mLogFileHandler != null) mLogFileHandler.sendMessage(mLogFileHandler.obtainMessage(LogFileHandler.TRIMLOGFILE));
        }
        //if (BuildConfig.DEBUG && !cachedLine && !BuildConfig.FLAVOR.equals("test"))
        //    Log.d("OpenVPN", logItem.getString(null));
        for (LogListener ll : logListener) {
            ll.newLog(logItem);
        }
    }

    public static void logError(String msg) {
        newLogItem(new LogItem(LogLevel.ERROR, msg));
    }

    public static void logWarning(int resourceId, Object... args) {
        newLogItem(new LogItem(LogLevel.WARNING, resourceId, args));
    }

    public static void logWarning(String msg) {
        newLogItem(new LogItem(LogLevel.WARNING, msg));
    }

    public static void logError(int resourceId) {
        newLogItem(new LogItem(LogLevel.ERROR, resourceId));
    }

    public static void logError(int resourceId, Object... args) {
        newLogItem(new LogItem(LogLevel.ERROR, resourceId, args));
    }

    public static void logMessageOpenVPN(LogLevel level, int ovpnlevel, String message) {
        newLogItem(new LogItem(level, ovpnlevel, message));
    }

    public static synchronized void updateByteCount(long in, long out) {
        TrafficHistory.LastDiff diff = trafficHistory.add(in, out);
        for (ByteCountListener bcl : byteCountListener) {
            bcl.updateByteCount(in, out, diff.getDiffIn(), diff.getDiffOut());
        }
    }

    public enum LogLevel {
        INFO(2), ERROR(-2), WARNING(1), VERBOSE(3), DEBUG(4);
        protected int mValue;

        LogLevel(int value) {
            mValue = value;
        }

        public static LogLevel getEnumByValue(int value) {
            switch (value) {
                case 2:
                    return INFO;
                case -2:
                    return ERROR;
                case 1:
                    return WARNING;
                case 3:
                    return VERBOSE;
                case 4:
                    return DEBUG;
                default:
                    return null;
            }
        }

        public int getInt() {
            return mValue;
        }
    }

    public interface LogListener {
        void newLog(LogItem logItem);
    }

    public interface StateListener {
        void updateState(String state, String logmessage, int localizedResId, ConnectionStatus level);

        void setConnectedVPN(String uuid);
    }

    public interface ByteCountListener {
        void updateByteCount(long in, long out, long diffIn, long diffOut);
    }
}
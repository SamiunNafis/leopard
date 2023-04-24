package com.leopard.vpn;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

public class SamiunApplication extends Application {

    private static Context mApplicationContext;
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

    public static Context getContext() {
        return mApplicationContext;
    }

    @Override
    public void onCreate() {
        mApplicationContext = getApplicationContext();
        this.uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

        Thread.setDefaultUncaughtExceptionHandler(
            new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread thread, Throwable throwable) {
                    Intent intent = new Intent(getApplicationContext(), DebugActivity.class);
                    intent.setFlags(Intent.FLAGACTIVITYCLEARTASK);
                    intent.putExtra("error", Log.getStackTraceString(throwable));

                    PendingIntent pendingIntent =
                        PendingIntent.getActivity(
                            getApplicationContext(),
                            11111,
                            intent,
                            PendingIntent.FLAGONESHOT
                        );

                    AlarmManager am = (AlarmManager) getSystemService(Context.ALARMSERVICE);
                    am.set(AlarmManager.ELAPSEDREALTIMEWAKEUP, 1000, pendingIntent);

                  //  SketchLogger.broadcastLog(Log.getStackTraceString(throwable));
                    Process.killProcess(Process.myPid());
                    System.exit(1);

                    uncaughtExceptionHandler.uncaughtException(thread, throwable);
                }
            });
       // SketchLogger.startLogging();
        super.onCreate();
    }
}
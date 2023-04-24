package com.leopard.vpn;

import android.Manifest;
import android.animation.*;
import android.animation.ObjectAnimator;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.os.Bundle;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.webkit.*;
import android.widget.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.firebase.FirebaseApp;
import com.sanojpunchihewa.glowbutton.*;
import de.hdodenhof.circleimageview.*;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import junit.*;
import org.json.*;
import de.blinkt.openvpn.LaunchVPN;
import de.blinkt.openvpn.VpnProfile;
import de.blinkt.openvpn.core.App;
import de.blinkt.openvpn.core.ConnectionStatus;
import de.blinkt.openvpn.core.IOpenVPNServiceInternal;
import de.blinkt.openvpn.core.OpenVPNManagement;
import de.blinkt.openvpn.core.OpenVPNService;
import de.blinkt.openvpn.core.ProfileManager;
import de.blinkt.openvpn.core.VpnStatus;

import static de.blinkt.openvpn.core.OpenVPNService.humanReadableByteCount;

import android.util.TypedValue;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.content.BroadcastReceiver;


public class HomeActivity extends AppCompatActivity implements VpnStatus.StateListener {

    private Timer timer = new Timer();

    private int colorFrom = Color.parseColor("#E0E0E0");
    private int colorTo = Color.parseColor("#00E676");
    public final int pickServerREQ = 55;
    private IOpenVPNServiceInternal mService;
    private ProfileAsync profileAsync;
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {

            mService = IOpenVPNServiceInternal.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mService = null;
        }
    };
    private String FNAME = "";
    private String URL = "";
    private double n = 0;
    private  AlertDialog dialog;
    private boolean stilConnecting = false;
    private boolean connected = false;
    private String ICON = "";
    private String name = "";
    private boolean isEmpty = false;
    private boolean runningTimer = false;
    private double stopwatch2 = 0;
    private double stopwatch3 = 0;
    private double stopwatch4 = 0;
    private double stopwatch = 0;
    private double time = 0;

    private ArrayList<HashMap<String, Object>> listmap1 = new ArrayList<>();

    private LinearLayout linear4;
    private ScrollView vscroll3;
    private LinearLayout servcon;
    private ImageView imageview2;
    private TextView textview4;
    private LinearLayout base;
    private LinearLayout linear1;
    private LinearLayout linear2;
    private LinearLayout linear3;
    private LinearLayout ggcontanholdee;
    private LinearLayout linear10;
    private TextView ip;
    private LinearLayout connectcontainer;
    private TextView timer;
    private TextView textview7;
    private TextView textview1;
    private TextView textview8;
    private LinearLayout server;
    private Button button1;
    private LinearLayout gcontain;
    private FrameLayout gift;
    private TextView offertimeleft;
    private ImageView imageview6;
    private TextView textview10;
    private ImageView imageview5;
    private TextView textview9;
    private RelativeLayout l1;
    private RelativeLayout l2;
    private LinearLayout linear9;
    private GlowButton circlecontainer1;
    private LinearLayout linear6;
    private ImageView imageview3;
    private GlowButton circlecontainer2;
    private LinearLayout linear8;
    private ImageView imageview4;
    private LinearLayout btnc;
    private TextView textview3;
    private CircleImageView circleimageview1;
    private TextView textview2;
    private ImageView imageview1;
    private AdView adview2;
    private LinearLayout line;

    private ObjectAnimator colorChange = new ObjectAnimator();
    private SharedPreferences ConnectionManagement;
    private Intent intent = new Intent();
    private TimerTask t;
    private RequestNetwork iprequest;
    private RequestNetwork.RequestListener iprequestrequestlistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        initialize(savedInstanceState);
        FirebaseApp.initializeApp(this);
        MobileAds.initialize(this);

        List<String> testDeviceIds = Arrays.asList("874A04C22612BAC3077E65563760EC57");
        MobileAds.setRequestConfiguration(new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build());

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READEXTERNALSTORAGE) == PackageManager.PERMISSIONDENIED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READEXTERNALSTORAGE}, 1000);
        } else {
            initializeLogic();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            initializeLogic();
        }
    }

    private void initialize(Bundle savedInstanceState) {
        linear4 = findViewById(R.id.linear4);
        vscroll3 = findViewById(R.id.vscroll3);
        servcon = findViewById(R.id.servcon);
        imageview2 = findViewById(R.id.imageview2);
        textview4 = findViewById(R.id.textview4);
        base = findViewById(R.id.base);
        linear1 = findViewById(R.id.linear1);
        linear2 = findViewById(R.id.linear2);
        linear3 = findViewById(R.id.linear3);
        ggcontanholdee = findViewById(R.id.ggcontanholdee);
        linear10 = findViewById(R.id.linear10);
        ip = findViewById(R.id.ip);
        connectcontainer = findViewById(R.id.connectcontainer);
        timer = findViewById(R.id.timer);
        textview7 = findViewById(R.id.textview7);
        textview1 = findViewById(R.id.textview1);
        textview8 = findViewById(R.id.textview8);
        server = findViewById(R.id.server);
        button1 = findViewById(R.id.button1);
        gcontain = findViewById(R.id.gcontain);
        gift = findViewById(R.id.gift);
        offertimeleft = findViewById(R.id.offertimeleft);
        imageview6 = findViewById(R.id.imageview6);
        textview10 = findViewById(R.id.textview10);
        imageview5 = findViewById(R.id.imageview5);
        textview9 = findViewById(R.id.textview9);
        l1 = findViewById(R.id.l1);
        l2 = findViewById(R.id.l2);
        linear9 = findViewById(R.id.linear9);
        circlecontainer1 = findViewById(R.id.circlecontainer1);
        linear6 = findViewById(R.id.linear6);
        imageview3 = findViewById(R.id.imageview3);
        circlecontainer2 = findViewById(R.id.circlecontainer2);
        linear8 = findViewById(R.id.linear8);
        imageview4 = findViewById(R.id.imageview4);
        btnc = findViewById(R.id.btnc);
        textview3 = findViewById(R.id.textview3);
        circleimageview1 = findViewById(R.id.circleimageview1);
        textview2 = findViewById(R.id.textview2);
        imageview1 = findViewById(R.id.imageview1);
        adview2 = findViewById(R.id.adview2);
        line = findViewById(R.id.line);
        ConnectionManagement = getSharedPreferences("connectionManagement", Activity.MODE_PRIVATE);
        iprequest = new RequestNetwork(this);

        linear10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent.setClass(getApplicationContext(), PurchaseActivity.class);
                    startActivity(intent);
                }
            });

        server.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (stilConnecting || connected) {
                        SamiunUtil.showMessage(getApplicationContext(), "Please disconnect first!");
                    }
                    else {
                        Intent clientStartIntent = new Intent(HomeActivity.this, ServersActivity.class);
                        startActivityForResult(clientStartIntent, pickServerREQ);
                        cleanup();

                    }
                }
            });

        button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    time++;
                    SamiunUtil.showMessage(getApplicationContext(), "1 hour added");
                }
            });

        gcontain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent.setClass(getApplicationContext(), PurchaseActivity.class);
                    startActivity(intent);
                }
            });

        circlecontainer1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    circlecontainer2.performClick();
                }
            });

        circlecontainer2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        stopwatch = 0;
                        stopwatch2 = 0;
                        stopwatch3 = 0;
                        stopwatch4 = 0;
                    }catch(Exception e){

                    }
                    try{
                        if (!networkAvailable()) {
                            SamiunUtil.showMessage(getApplicationContext(), "Please enable internet connection!");
                        }
                        else {
                            if (isEmpty) {
                                SamiunUtil.showMessage(getApplicationContext(), "Please select a server!");
                            }
                            else {
                                Runnable r = new Runnable() {
                                    @Override
                                    public void run() {
                                        if (!App.isStart) {
                                            try{

                                            }catch(Exception e){

                                            }
                                            startVPN();
                                            stilConnecting = true;
                                            App.isStart = true;
                                            animateBackground(colorFrom, colorTo);
                                            textview3.setText("Connecting");
                                        }
                                        else {
                                            stopVPN();
                                            stilConnecting = false;
                                            App.isStart = false;
                                            animateBackground(colorTo, colorFrom);
                                            textview3.setText("DISCONNECTED");

                                            textview3.setTextColor(0xFF757575);
                                            try{
                                                onVpnConnected();
                                            }catch(Exception e){

                                            }
                                        }
                                    }
                                };
                                r.run();
                            }
                        }
                    }catch(Exception e){
                        showMessage(e.toString());
                    }
                }
            });

        btnc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    circlecontainer2.performClick();
                }
            });

        iprequestrequestlistener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String param1, String param2, HashMap<String, Object> param3) {
                final String tag = param1;
                final String response = param2;
                final HashMap<String, Object> responseHeaders = param3;
                if (connected) {
                    ip.setText("IP : ".concat(response));
                    ip.setVisibility(View.VISIBLE);
                }
                else {
                    ip.setVisibility(View.GONE);
                }
                try{
                    TransitionManager(base, 500);
                }catch(Exception e){

                }
            }

            @Override
            public void onErrorResponse(String param1, String param2) {
                final String tag = param1;
                final String message = param2;

            }
        };
    }

    private void initializeLogic() {
        NavStatusBarColor("#FFFFFFFF", "#FFFFFFFF");
        DARKICONS();


        ip.setVisibility(View.GONE);
        button1.setVisibility(View.GONE);
        l2.setVisibility(View.GONE);
        //timer.getBackground().setAlpha(0);
        timer.setAlpha((float)(0));
        linear10.setVisibility(View.VISIBLE);
        servcon.setBackgroundColor(Color.TRANSPARENT);
        btnc.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)96, (int)2, 0xFF757575, Color.TRANSPARENT));
        time = 1;
        n = 0;
        stopwatch = 0;
        stopwatch2 = 0;
        stopwatch3 = 0;
        stopwatch4 = 0;
        try{
            IconColour(imageview3, "#ffffff");
            IconColour(imageview5, "#ffffff");
        }catch(Exception e){

        }
        try{
            IconColour(imageview4, "#ffffff");
        }catch(Exception e){

        }
        onCreate();




        {
            android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
            int d = (int) getApplicationContext().getResources().getDisplayMetrics().density;
            int clrs [] = {0xFF19376D,0xFF19376D};
            SketchUi= new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.TOPBOTTOM, clrs);
            SketchUi.setCornerRadius(d*96);
            SketchUi.setStroke(d*1, 0xFFFFFFFF);
            linear10.setElevation(d*0);
            android.graphics.drawable.RippleDrawable SketchUiRD = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{0xFF9E9E9E}), SketchUi, null);
            linear10.setBackground(SketchUiRD);
            linear10.setClickable(true);
        }
        {
            android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
            int d = (int) getApplicationContext().getResources().getDisplayMetrics().density;
            int clrs [] = {0xFFFFFFFF,0xFFFFFFFF};
            SketchUi= new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.LEFTRIGHT, clrs);
            SketchUi.setCornerRadius(d*96);
            SketchUi.setStroke(d*1, 0xFF757575);
            btnc.setElevation(d*0);
            android.graphics.drawable.RippleDrawable SketchUiRD = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{0xFF757575}), SketchUi, null);
            btnc.setBackground(SketchUiRD);
            btnc.setClickable(true);
        }
        {
            android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
            int d = (int) getApplicationContext().getResources().getDisplayMetrics().density;
            int clrs [] = {0xFFFFFFFF,0xFFFFFFFF};
            SketchUi= new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.LEFTRIGHT, clrs);
            SketchUi.setCornerRadius(d*14);
            SketchUi.setStroke(d*2, 0xFF757575);
            server.setElevation(d*2);
            android.graphics.drawable.RippleDrawable SketchUiRD = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{0xFF757575}), SketchUi, null);
            server.setBackground(SketchUiRD);
            server.setClickable(true);
        }
        {
            android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
            int d = (int) getApplicationContext().getResources().getDisplayMetrics().density;
            int clrs [] = {0xFF8BC34A,0xFF8BC34A};
            SketchUi= new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.LEFTRIGHT, clrs);
            SketchUi.setCornerRadius(d*96);
            SketchUi.setStroke(d*0, 0xFFFFFFFF);
            offertimeleft.setElevation(d*0);
            android.graphics.drawable.RippleDrawable SketchUiRD = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.TRANSPARENT}), SketchUi, null);
            offertimeleft.setBackground(SketchUiRD);
            offertimeleft.setClickable(false);
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("connectionState")); 

        {
            AdRequest adRequest = new AdRequest.Builder().build();
            adview2.loadAd(adRequest);
        }

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
         // If we're running on Honeycomb or newer, then we can use the Theme's
         // selectableItemBackground to ensure that the View has a pressed state
         TypedValue outValue = new TypedValue();
         this.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
         textview3.setBackgroundResource(outValue.resourceId);
         }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((resultCode == Activity.RESULTOK) && (requestCode == pickServerREQ)) {
            isEmpty = false;
            ProfileManager.setConntectedVpnProfileDisconnected(HomeActivity.this);
            try {
                ProfileManager pm = ProfileManager.getInstance(HomeActivity.this);
                VpnProfile vp = pm.getProfileByName(Build.MODEL);
                pm.removeProfile(HomeActivity.this, vp);
            } catch (Exception e) {

            }
            getServerData(data.getStringExtra("title"), data.getStringExtra("url"), data.getStringExtra("icon"));
        }
        switch (requestCode) {

            default:
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        VpnStatus.addStateListener(this);

        Intent intent = new Intent(this, OpenVPNService.class);
        intent.setAction(OpenVPNService.STARTSERVICE);
        bindService(intent, mConnection, Context.BINDAUTOCREATE);
    }

    @Override
    public void onPause() {
        super.onPause();
        unbindService(mConnection);
    }

    @Override
    public void onStop() {
        super.onStop();
        VpnStatus.removeStateListener(this);

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (adview2 != null) {
            adview2.destroy();
        }
    }
    public void onCreate() {
        designs();
        if (ConnectionManagement.getString("name", "").equals("")) {
            isEmpty = true;
            textview2.setText("NO SERVER SELECTED");
            circleimageview1.setImageResource(R.drawable.iconbackground);
        }
        else {
            isEmpty = false;
            textview2.setText(ConnectionManagement.getString("name", ""));
            Glide.with(getApplicationContext()).load(Uri.parse(ConnectionManagement.getString("icon", ""))).into(circleimageview1);
        }
    }


    public void textview(final TextView t, final String s) {
        FNAME = "fonts/".concat(s.concat(".otf"));
        try{
            t.setTypeface(Typeface.createFromAsset(getAssets(), FNAME), 0);
        }catch(Exception e){
            SamiunUtil.showMessage(getApplicationContext(), "Error!");
        }
    }


    public void designs() {
        textview(textview2, "manropesemibold");
        textview(timer, "manropemedium");
        textview(ip, "manropemedium");
        textview(textview3, "manropemedium");
        textview3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/josefinsanslight.ttf"), 0);

        textview2.setSelected(true); 
        textview2.setSingleLine(true);
    }


    public void more() {
    }
    public void animateBackground(int colorFrom,int colorTo){

        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(250); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animator) {

                }
            });
        colorAnimation.start();
    }


    public void startVPNConnection(VpnProfile vp) {
        Intent intent = new Intent(getApplicationContext(), LaunchVPN.class);
        intent.putExtra(LaunchVPN.EXTRAKEY, vp.getUUID().toString());
        intent.setAction(Intent.ACTIONMAIN);
        startActivity(intent);
    }
    public void stopVPNConnection() {
        ProfileManager.setConntectedVpnProfileDisconnected(HomeActivity.this);
        if (mService != null) {
            try {
                mService.stopVPN(false);
                //ProfileManager pm = ProfileManager.getInstance(HomeActivity.this);
                //VpnProfile vp = pm.getProfileByName(Build.MODEL);
                //pm.removeProfile(HomeActivity.this, vp);
            } catch (RemoteException e) {

            }
        }
    }
    void setConnected() {
        connected = true;
        textview3.setText("CONNECTED");
        textview3.setTextColor(0xFFFFFFFF);
        {
            android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
            int d = (int) getApplicationContext().getResources().getDisplayMetrics().density;
            int clrs [] = {0xFF0B2447,0xFF19376D};
            SketchUi= new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.LEFTRIGHT, clrs);
            SketchUi.setCornerRadius(d*96);
            SketchUi.setStroke(d*0,0xFFFFFFFF);
            btnc.setElevation(d*7);
            android.graphics.drawable.RippleDrawable SketchUiRD = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{0xFF9E9E9E}), SketchUi, null);
            btnc.setBackground(SketchUiRD);
            btnc.setClickable(true);
        }
        setConnectedMore();
    }
    void changeStateButton(Boolean state) {
        if (state) {
            connected = state;
            textview3.setText("CONNECTED");
            textview3.setTextColor(0xFFFFFFFF);
            {
                android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
                int d = (int) getApplicationContext().getResources().getDisplayMetrics().density;
                int clrs [] = {0xFF0B2447,0xFF19376D};
                SketchUi= new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.LEFTRIGHT, clrs);
                SketchUi.setCornerRadius(d*96);
                SketchUi.setStroke(d*0,0xFFFFFFFF);
                btnc.setElevation(d*7);
                android.graphics.drawable.RippleDrawable SketchUiRD = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{0xFF9E9E9E}), SketchUi, null);
                btnc.setBackground(SketchUiRD);
                btnc.setClickable(true);
            }
        } else {
            connected = state;
            textview3.setText("DISCONNECTED");
            textview3.setTextColor(0xFF757575);
            {
                android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
                int d = (int) getApplicationContext().getResources().getDisplayMetrics().density;
                int clrs [] = {Color.TRANSPARENT,Color.TRANSPARENT};
                SketchUi= new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.TOPBOTTOM, clrs);
                SketchUi.setCornerRadius(d*96);
                SketchUi.setStroke(d*1,0xFF757575);
                btnc.setElevation(d*0);
                android.graphics.drawable.RippleDrawable SketchUiRD = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{0xFF9E9E9E}), SketchUi, null);
                btnc.setBackground(SketchUiRD);
                btnc.setClickable(true);
            }
        }
    }
    public void updateState(final String state, String logmessage, int localizedResId, ConnectionStatus level) {
        runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (state.equals("CONNECTED")) {
                        App.isStart = true;
                        setConnected();
                    } else {
                    }
                    if (state.equals("AUTHFAILED")) {
                        Toast.makeText(getApplicationContext(), "Wrong Username or Password!", Toast.LENGTH_SHORT).show();
                        changeStateButton(false);
                    }
                }
            });
    }
    public void setConnectedVPN(String uuid) {
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                //  Log.d(TAG, "onReceive: " + intent.getStringExtra("state"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                String duration = intent.getStringExtra("duration");
                String lastPacketReceive = intent.getStringExtra("lastPacketReceive");
                String byteIn = intent.getStringExtra("byteIn");
                String byteOut = intent.getStringExtra("byteOut");

                if (duration.equals(null)) duration = "00:00:00";
                if (lastPacketReceive.equals(null)) lastPacketReceive = "0";
                if (byteIn.equals(null)) byteIn = " ";
                if (byteOut.equals(null)) byteOut = " ";

                String bndl = "";
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    for (String key : bundle.keySet()) {
                        //  Log.e(TAG, key + " : " + (bundle.get(key) != null ? bundle.get(key) : "NULL"));
                        bndl += key + " : " + (bundle.get(key) != null ? bundle.get(key) : "NULL");
                    }
                }

                //Toast.makeText(context, bndl, 0).show();



                //  Log.d(TAG, "onReceive: " + duration + " " + lastPacketReceive + " " + byteIn + " " + byteOut);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    public void cleanup() {
        if (profileAsync != null && !profileAsync.isCancelled()) {
            profileAsync.cancel(true);
        }



    }


    public void startVPNFromURL(final String url) {
        if (!App.isStart) {
            DataCleanManager.cleanCache(HomeActivity.this);
            progressDialog(true);
            profileAsync = new ProfileAsync(this, new ProfileAsync.OnProfileLoadListener() {
                    @Override
                    public void onProfileLoadSuccess() {
                        progressDialog(false);
                    }
                    @Override
                    public void onProfileLoadFailed(String msg) {
                        showMessage("Failed");
                        progressDialog(false);
                    }
                },url);
            profileAsync.execute();
        }
    }


    public void startVPN() {
        try {

            ProfileManager pm = ProfileManager.getInstance(HomeActivity.this);
            VpnProfile profile = pm.getProfileByName(Build.MODEL);
            startVPNConnection(profile);

        } catch (Exception ex) {

            App.isStart = false;

        }
    }


    public void stopVPN() {
        try{
            stopVPNConnection();
            connected = false;
        }catch(Exception e){
            connected = false;
        }
    }


    public void setConnectedMore() {
        ip.setVisibility(View.GONE);
        onVpnConnected();
        URL = ConnectionManagement.getString("link", "");
        ICON = ConnectionManagement.getString("icon", "");
        name = ConnectionManagement.getString("name", "");
        textview2.setText(name);
        Glide.with(getApplicationContext()).load(Uri.parse(ICON)).into(circleimageview1);
    }


    public void getServerData(final String name, final String link, final String icon) {
        startVPNFromURL(link);
        textview2.setText(name);
        Glide.with(getApplicationContext()).load(Uri.parse(icon)).into(circleimageview1);
    }


    public void progressDialog(final boolean t) {
        if (t) {
            dialog = new AlertDialog.Builder(HomeActivity.this).create();
            LayoutInflater inflater = getLayoutInflater();

            View convertView = (View) inflater.inflate(R.layout.loadingcus, null);
            dialog.setView(convertView);

            dialog.requestWindowFeature(Window.FEATURENOTITLE);  dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));


            LinearLayout l1 = (LinearLayout) convertView.findViewById(R.id.base);


            ProgressBar p1 = (ProgressBar) convertView.findViewById(R.id.progressbar1);

            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }
        else {
            dialog.dismiss();
        }
    }


    public boolean networkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) HomeActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }


    public void onVpnConnected() {
        if (connected) {
            if (!(time == 0)) {
                ip.setVisibility(View.GONE);
                iprequest.startRequestNetwork(RequestNetworkController.GET, "https://api.ipify.org/?format=txt", "SamiunNafis", iprequestrequestlistener);
                try{
                    l1.setVisibility(View.GONE);
                }catch(Exception e){

                }
                try{
                    l2.setVisibility(View.VISIBLE);
                }catch(Exception e){

                }
                button1.setVisibility(View.VISIBLE);
                //timer.getBackground().setAlpha(255);
                timer.setAlpha((float)(1));
                try{
                    IconColour(imageview3, "#ffffff");
                }catch(Exception e){

                }
                try{
                    TransitionManager(base, 1000);
                }catch(Exception e){

                }
            }
            else {
                time = 0;
            }
        }
        else {
            button1.setVisibility(View.GONE);
            try{
                l1.setVisibility(View.VISIBLE);
            }catch(Exception e){

            }
            try{
                l2.setVisibility(View.GONE);
            }catch(Exception e){

            }
            ip.setVisibility(View.GONE);
            //timer.getBackground().setAlpha(0);
            timer.setAlpha((float)(0));
            try{
                IconColour(imageview3, "#ffffff");
            }catch(Exception e){

            }
            try{
                TransitionManager(base, 1000);
            }catch(Exception e){

            }
        }
        if (!runningTimer) {
            if (connected) {
                runningTimer = true;
                t = new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    stopwatch2++;
                                    if (stopwatch3 == 60) {
                                        stopwatch4++;
                                        stopwatch3 = 0;
                                    }
                                    if (stopwatch2 == 60) {
                                        stopwatch++;
                                        stopwatch2 = 0;
                                    }
                                    if (stopwatch == 60) {
                                        stopwatch3++;
                                        stopwatch = 0;
                                    }
                                    textview1.setText("0".concat(String.valueOf((long)(stopwatch))));
                                    textview7.setText("0".concat(String.valueOf((long)(stopwatch2))));
                                    textview8.setText("0".concat(String.valueOf((long)(stopwatch3))));
                                    textview8.setText("0".concat(String.valueOf((long)(stopwatch4))));
                                    if (9 < stopwatch) {
                                        textview1.setText(String.valueOf((long)(stopwatch)));
                                    }
                                    if (9 < stopwatch2) {
                                        textview7.setText(String.valueOf((long)(stopwatch2)));
                                    }
                                    if (9 < stopwatch3) {
                                        textview8.setText(String.valueOf((long)(stopwatch3)));
                                    }
                                    if (9 < stopwatch4) {
                                        textview8.setText(String.valueOf((long)(stopwatch4)));
                                    }
                                    timer.setText(textview8.getText().toString().concat(".".concat(textview1.getText().toString().concat(".".concat(textview7.getText().toString())))));
                                    if ((Double.parseDouble(textview7.getText().toString()) == 5) || (Double.parseDouble(textview7.getText().toString()) > 5)) {
                                        if (textview8.getText().toString().endsWith(String.valueOf((long)(time))) || (Double.parseDouble(textview8.getText().toString()) > time)) {
                                            SamiunUtil.showMessage(getApplicationContext(), "Time has been outed");
                                            stopVPN();
                                            stilConnecting = false;
                                            App.isStart = false;
                                            animateBackground(colorTo, colorFrom);
                                            textview3.setText("DISCONNECTED");

                                            textview3.setTextColor(0xFF757575);
                                            runningTimer = false;
                                            try{
                                                onVpnConnected();
                                            }catch(Exception e){

                                            }
                                        }
                                        else {

                                        }
                                    }
                                }
                            });
                    }
                };
                timer.scheduleAtFixedRate(t, (int)(1000), (int)(1000));
            }
            else {
                stopwatch = 0;
                stopwatch2 = 0;
                stopwatch3 = 0;
                stopwatch4 = 0;
                try{
                    t.cancel();
                }catch(Exception e){

                }
            }
        }
    }


    public void NavStatusBarColor(final String color1, final String color2) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Window w = this.getWindow();    w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);   w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BARBACKGROUNDS);
            w.setStatusBarColor(Color.parseColor("#" + color1.replace("#", "")));  w.setNavigationBarColor(Color.parseColor("#" + color2.replace("#", "")));
        }
    }


    public void DARKICONS() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEMUIFLAGLIGHTSTATUSBAR);
        base.setSystemUiVisibility(View.SYSTEMUIFLAGLIGHTNAVIGATIONBAR);
    }


    public void IconColour(final ImageView iconview, final String colour) {
        iconview.getDrawable().setColorFilter(Color.parseColor(colour), PorterDuff.Mode.SRCIN);
    }


    public void TransitionManager(final View view, final double duration) {
        LinearLayout viewgroup =(LinearLayout) view;

        android.transition.AutoTransition autoTransition = new android.transition.AutoTransition(); autoTransition.setDuration((long)duration); android.transition.TransitionManager.beginDelayedTransition(viewgroup, autoTransition);
    }


    public void showNotification(final String title, final String text, final int icon) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            makeNotificationChannel("leovpn", "Leo Channel", NotificationManager.IMPORTANCE_DEFAULT);
        }

        Notification.Builder notificationBuilder = new Notification.Builder(this, "leovpn");
        notificationBuilder
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(text)
            .setNumber(3);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATIONSERVICE);

        notificationManager.notify(1, notificationBuilder.build());
    }

    private void makeNotificationChannel(String channelId,String channelName, int importance) {

        NotificationChannel channel= new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATIONSERVICE);
        notificationManager.createNotificationChannel(channel);

    }


    @Deprecated
    public void showMessage(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Deprecated
    public int getLocationX(View v) {
        int location[] = new int[2];
        v.getLocationInWindow(location);
        return location[0];
    }

    @Deprecated
    public int getLocationY(View v) {
        int location[] = new int[2];
        v.getLocationInWindow(location);
        return location[1];
    }

    @Deprecated
    public int getRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    @Deprecated
    public ArrayList<Double> getCheckedItemPositionsToArray(ListView list) {
        ArrayList<Double> result = new ArrayList<Double>();
        SparseBooleanArray arr = list.getCheckedItemPositions();
        for (int iIdx = 0; iIdx < arr.size(); iIdx++) {
            if (arr.valueAt(iIdx))
                result.add((double)arr.keyAt(iIdx));
        }
        return result;
    }

    @Deprecated
    public float getDip(int input) {
        return TypedValue.applyDimension(TypedValue.COMPLEXUNITDIP, input, getResources().getDisplayMetrics());
    }

    @Deprecated
    public int getDisplayWidthPixels() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    @Deprecated
    public int getDisplayHeightPixels() {
        return getResources().getDisplayMetrics().heightPixels;
    }
}
package com.leopard.vpn;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.Intent;
import android.content.res.*;
import android.graphics.*;
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
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.firebase.FirebaseApp;
import com.sanojpunchihewa.glowbutton.*;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import junit.*;
import org.json.*;

public class MainActivity extends AppCompatActivity {

    private Timer _timer = new Timer();

    private LinearLayout base;
    private ImageView imageview1;

    private Intent intent = new Intent();
    private TimerTask timer;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.main);
        initialize(_savedInstanceState);
        FirebaseApp.initializeApp(this);
        MobileAds.initialize(this);

        List<String> testDeviceIds = Arrays.asList("874A04C22612BAC3077E65563760EC57");
        MobileAds.setRequestConfiguration(new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build());
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {
        base = findViewById(R.id.base);
        imageview1 = findViewById(R.id.imageview1);
    }

    private void initializeLogic() {
        _NavStatusBarColor("#FFFFFFFF", "#FFFFFFFF");
        _DARK_ICONS();
        if (_isNetworkAvailableAndConnected()) {
            android.view.animation.Animation animation = new android.view.animation.AlphaAnimation(0, 1);
            animation.setDuration(600);
            imageview1.setAnimation(animation);
            timer = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                intent.setClass(getApplicationContext(), HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_SINGLE_TOP); 

                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                startActivity(intent);
                                finish();
                            }
                        });
                }
            };
            _timer.schedule(timer, (int)(1000));
        }
        else {
            imageview1.setImageResource(R.drawable.image_no_connection);
        }
    }

    public boolean _isNetworkAvailableAndConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }


    public void _NavStatusBarColor(final String _color1, final String _color2) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Window w = this.getWindow();    w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);   w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.setStatusBarColor(Color.parseColor("#" + _color1.replace("#", "")));  w.setNavigationBarColor(Color.parseColor("#" + _color2.replace("#", "")));
        }
    }


    public void _DARK_ICONS() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        base.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
    }

}


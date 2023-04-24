package com.leopard.vpn;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.firebase.FirebaseApp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Timer timer = new Timer();

    private LinearLayout base;
    private ImageView imageview1;

    private Intent intent = new Intent();
    private TimerTask timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initialize(savedInstanceState);
        FirebaseApp.initializeApp(this);
        MobileAds.initialize(this);

        List<String> testDeviceIds = Arrays.asList("874A04C22612BAC3077E65563760EC57");
        MobileAds.setRequestConfiguration(new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build());
        initializeLogic();
    }

    private void initialize(Bundle savedInstanceState) {
        base = findViewById(R.id.base);
        imageview1 = findViewById(R.id.imageview1);
    }

    private void initializeLogic() {
        NavStatusBarColor("#FFFFFFFF", "#FFFFFFFF");
        DARKICONS();
        if (isNetworkAvailableAndConnected()) {
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

                                overridePendingTransition(android.R.anim.fadein, android.R.anim.fadeout);
                                startActivity(intent);
                                finish();
                            }
                        });
                }
            };
            timer.schedule(timer, (int)(1000));
        }
        else {
            imageview1.setImageResource(R.drawable.imagenoconnection);
        }
    }

    public boolean isNetworkAvailableAndConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }


    public void NavStatusBarColor(final String color1, final String color2) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Window w = this.getWindow();    w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);   w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.setStatusBarColor(Color.parseColor("#" + color1.replace("#", "")));  w.setNavigationBarColor(Color.parseColor("#" + color2.replace("#", "")));
        }
    }


    public void DARKICONS() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        base.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
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
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, input, getResources().getDisplayMetrics());
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

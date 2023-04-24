package com.leopard.vpn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ServersActivity extends AppCompatActivity {

    private double n = 0;
    private String serverurl = "";
    private String FNAME = "";

    private ArrayList<HashMap<String, Object>> listmap1 = new ArrayList<>();

    private LinearLayout base;
    private SwipeRefreshLayout swiperefreshlayout1;
    private LinearLayout linear2;
    private ListView listview1;
    private LinearLayout toolbar;
    private TabLayout tablayout2;
    private ViewPager viewpager1;
    private ImageView imageview1;
    private TextView textview1;

    private Intent intent = new Intent();
    private SharedPreferences ConnectionManagement;
    private FragAdapterFragmentAdapter fragAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.servers);
        initialize(savedInstanceState);
        FirebaseApp.initializeApp(this);
        MobileAds.initialize(this);

        List<String> testDeviceIds = Arrays.asList("874A04C22612BAC3077E65563760EC57");
        MobileAds.setRequestConfiguration(new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build());
        initializeLogic();
    }

    private void initialize(Bundle savedInstanceState) {
        base = findViewById(R.id.base);
        swiperefreshlayout1 = findViewById(R.id.swiperefreshlayout1);
        linear2 = findViewById(R.id.linear2);
        listview1 = findViewById(R.id.listview1);
        toolbar = findViewById(R.id.toolbar);
        tablayout2 = findViewById(R.id.tablayout2);
        viewpager1 = findViewById(R.id.viewpager1);
        imageview1 = findViewById(R.id.imageview1);
        textview1 = findViewById(R.id.textview1);
        ConnectionManagement = getSharedPreferences("connectionManagement", Activity.MODE_PRIVATE);
        fragAdapter = new FragAdapterFragmentAdapter(getApplicationContext(), getSupportFragmentManager());

        swiperefreshlayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swiperefreshlayout1.setRefreshing(false);
                    listview1.setAdapter(new Listview1Adapter(listmap1));
                    ((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
                }
            });
    }

    private void initializeLogic() {
        try{
            removeScollBar(listview1);
            setListviewSelector(listview1);
            /*getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0B2447")));
             toolbar.setTitleTextColor(Color.WHITE);
             getSupportActionBar().setHomeAsUpIndicator(R.drawable.icbackwhite); 
             for(int i = 0; i < toolbar.getChildCount(); i++)     
             { View view = toolbar.getChildAt(i);

             if(view instanceof TextView) {
             TextView textView = (TextView) view;

             Typeface myCustomFont=Typeface.createFromAsset(getAssets(),"fonts/manropebold.otf");
             textView.setTypeface(myCustomFont); 
             }

             }
             setTitle("SERVERS");
             */
            fragAdapter.setTabCount(2);
            viewpager1.setAdapter(fragAdapter);
            tablayout2.setupWithViewPager(viewpager1);
            tablayout2.setInlineLabel(true);
            tablayout2.setTabTextColors(0xFFFFFFFF, 0xFFFFFFFF);
            tablayout2.setTabRippleColor(new android.content.res.ColorStateList(new int[][]{new int[]{android.R.attr.statepressed}}, 

                                                                                new int[] {0xFF9E9E9E}));
            tablayout2.setSelectedTabIndicatorColor(0xFFFFFFFF);
            textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/manropebold.otf"), 1);
        }catch(Exception e){

        }
    }

    public class FragAdapterFragmentAdapter extends FragmentStatePagerAdapter {
        // This class is deprecated, you should migrate to ViewPager2:
        // https://developer.android.com/reference/androidx/viewpager2/widget/ViewPager2
        Context context;
        int tabCount;

        public FragAdapterFragmentAdapter(Context context, FragmentManager manager) {
            super(manager);
            this.context = context;
        }

        public void setTabCount(int tabCount) {
            this.tabCount = tabCount;
        }

        @Override
        public int getCount() {
            return tabCount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "FREE";
            }
            if (position == 1) {
                return "VIP";
            }
            return null;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new FreeFragmentActivity();
            }
            if (position == 1) {
                return new ProFragmentActivity();
            }
            return null;
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


    public void removeScollBar(final View view) {
        view.setVerticalScrollBarEnabled(false);
    }


    public void setListviewSelector(final ListView l) {
        l.setSelector(android.R.color.transparent);
    }


    public void transferDataToHone(final String name, final String url, final String icon) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("title", name);
        returnIntent.putExtra("icon", icon);
        returnIntent.putExtra("url", url);
        setResult(RESULTOK, returnIntent);
        finish();
        ConnectionManagement.edit().putString("name", name).commit();
        ConnectionManagement.edit().putString("link", url).commit();
        ConnectionManagement.edit().putString("icon", icon).commit();
    }


    public void NavStatusBarColor(final String color1, final String color2) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Window w = this.getWindow();    w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);   w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BARBACKGROUNDS);
            w.setStatusBarColor(Color.parseColor("#" + color1.replace("#", "")));  w.setNavigationBarColor(Color.parseColor("#" + color2.replace("#", "")));
        }
    }


    public void DARKICONS() {
        /*getWindow().getDecorView().setSystemUiVisibility(View.SYSTEMUIFLAGDARKSTATUSBAR);
         base.setSystemUiVisibility(View.SYSTEMUIFLAGDARKNAVIGATIONBAR);*/
    }

    public class Listview1Adapter extends BaseAdapter {

        ArrayList<HashMap<String, Object>> data;

        public Listview1Adapter(ArrayList<HashMap<String, Object>> arr) {
            data = arr;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public HashMap<String, Object> getItem(int index) {
            return data.get(index);
        }

        @Override
        public long getItemId(int index) {
            return index;
        }

        @Override
        public View getView(final int position, View v, ViewGroup container) {
            LayoutInflater inflater = getLayoutInflater();
            View view = v;
            if (view == null) {
                view = inflater.inflate(R.layout.datacus, null);
            }

            final LinearLayout container = view.findViewById(R.id.container);
            final de.hdodenhof.circleimageview.CircleImageView servericon = view.findViewById(R.id.servericon);
            final TextView title = view.findViewById(R.id.title);
            final ImageView imageview1 = view.findViewById(R.id.imageview1);
            final ImageView condition = view.findViewById(R.id.condition);

            if (data.get((int)position).containsKey("title")) {
                title.setText(data.get((int)position).get("title").toString());
            }
            if (data.get((int)position).containsKey("countryicon")) {
                Glide.with(getApplicationContext()).load(Uri.parse(data.get((int)position).get("countryicon").toString())).into(servericon);
            }
            if (data.get((int)position).containsKey("url")) {
                serverurl = data.get((int)position).get("url").toString();
            }
            if (data.get((int)position).containsKey("condition")) {
                if (data.get((int)position).get("condition").toString().equals("true")) {
                    condition.setImageResource(R.drawable.networkmonitor1);
                }
                else {
                    condition.setImageResource(R.drawable.networkmonitor2);
                }
            }
            container.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        transferDataToHone(data.get((int)position).get("title").toString(), data.get((int)position).get("url").toString(), data.get((int)position).get("countryicon").toString());
                    }
                });
            textview(title, "manropesemibold");

            android.view.animation.Animation animation = new android.view.animation.AlphaAnimation(0, 1);
            animation.setDuration(1500);
            view.setAnimation(animation);

            return view;
        }
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
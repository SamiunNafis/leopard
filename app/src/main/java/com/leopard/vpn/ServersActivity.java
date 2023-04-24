package com.leopard.vpn;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnAdapterChangeListener;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.google.firebase.FirebaseApp;
import com.sanojpunchihewa.glowbutton.*;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import junit.*;
import org.json.*;

public class ServersActivity extends AppCompatActivity {

    private double n = 0;
    private String server_url = "";
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
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.servers);
        initialize(_savedInstanceState);
        FirebaseApp.initializeApp(this);
        MobileAds.initialize(this);

        List<String> testDeviceIds = Arrays.asList("874A04C22612BAC3077E65563760EC57");
        MobileAds.setRequestConfiguration(new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build());
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {
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
            _removeScollBar(listview1);
            _setListviewSelector(listview1);
            /*getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0B2447")));
             _toolbar.setTitleTextColor(Color.WHITE);
             getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_white); 
             for(int i = 0; i < _toolbar.getChildCount(); i++)     
             { View view = _toolbar.getChildAt(i);

             if(view instanceof TextView) {
             TextView textView = (TextView) view;

             Typeface myCustomFont=Typeface.createFromAsset(getAssets(),"fonts/manrope_bold.otf");
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
            tablayout2.setTabRippleColor(new android.content.res.ColorStateList(new int[][]{new int[]{android.R.attr.state_pressed}}, 

                                                                                new int[] {0xFF9E9E9E}));
            tablayout2.setSelectedTabIndicatorColor(0xFFFFFFFF);
            textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/manrope_bold.otf"), 1);
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
        public CharSequence getPageTitle(int _position) {
            if (_position == 0) {
                return "FREE";
            }
            if (_position == 1) {
                return "VIP";
            }
            return null;
        }

        @Override
        public Fragment getItem(int _position) {
            if (_position == 0) {
                return new FreeFragmentActivity();
            }
            if (_position == 1) {
                return new ProFragmentActivity();
            }
            return null;
        }
    }

    public void _textview(final TextView _t, final String _s) {
        FNAME = "fonts/".concat(_s.concat(".otf"));
        try{
            _t.setTypeface(Typeface.createFromAsset(getAssets(), FNAME), 0);
        }catch(Exception e){
            SamiunUtil.showMessage(getApplicationContext(), "Error!");
        }
    }


    public void _removeScollBar(final View _view) {
        _view.setVerticalScrollBarEnabled(false);
    }


    public void _setListviewSelector(final ListView _l) {
        _l.setSelector(android.R.color.transparent);
    }


    public void _transferDataToHone(final String _name, final String _url, final String _icon) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("title", _name);
        returnIntent.putExtra("icon", _icon);
        returnIntent.putExtra("url", _url);
        setResult(RESULT_OK, returnIntent);
        finish();
        ConnectionManagement.edit().putString("name", _name).commit();
        ConnectionManagement.edit().putString("link", _url).commit();
        ConnectionManagement.edit().putString("icon", _icon).commit();
    }


    public void _NavStatusBarColor(final String _color1, final String _color2) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Window w = this.getWindow();    w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);   w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.setStatusBarColor(Color.parseColor("#" + _color1.replace("#", "")));  w.setNavigationBarColor(Color.parseColor("#" + _color2.replace("#", "")));
        }
    }


    public void _DARK_ICONS() {
        /*getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_DARK_STATUS_BAR);
         base.setSystemUiVisibility(View.SYSTEM_UI_FLAG_DARK_NAVIGATION_BAR);*/
    }

    public class Listview1Adapter extends BaseAdapter {

        ArrayList<HashMap<String, Object>> _data;

        public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }

        @Override
        public int getCount() {
            return _data.size();
        }

        @Override
        public HashMap<String, Object> getItem(int _index) {
            return _data.get(_index);
        }

        @Override
        public long getItemId(int _index) {
            return _index;
        }

        @Override
        public View getView(final int _position, View _v, ViewGroup _container) {
            LayoutInflater _inflater = getLayoutInflater();
            View _view = _v;
            if (_view == null) {
                _view = _inflater.inflate(R.layout.data_cus, null);
            }

            final LinearLayout container = _view.findViewById(R.id.container);
            final de.hdodenhof.circleimageview.CircleImageView server_icon = _view.findViewById(R.id.server_icon);
            final TextView title = _view.findViewById(R.id.title);
            final ImageView imageview1 = _view.findViewById(R.id.imageview1);
            final ImageView condition = _view.findViewById(R.id.condition);

            if (_data.get((int)_position).containsKey("title")) {
                title.setText(_data.get((int)_position).get("title").toString());
            }
            if (_data.get((int)_position).containsKey("country_icon")) {
                Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("country_icon").toString())).into(server_icon);
            }
            if (_data.get((int)_position).containsKey("url")) {
                server_url = _data.get((int)_position).get("url").toString();
            }
            if (_data.get((int)_position).containsKey("condition")) {
                if (_data.get((int)_position).get("condition").toString().equals("true")) {
                    condition.setImageResource(R.drawable.network_monitor_1);
                }
                else {
                    condition.setImageResource(R.drawable.network_monitor_2);
                }
            }
            container.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View _view){
                        _transferDataToHone(_data.get((int)_position).get("title").toString(), _data.get((int)_position).get("url").toString(), _data.get((int)_position).get("country_icon").toString());
                    }
                });
            _textview(title, "manrope_semibold");

            android.view.animation.Animation animation = new android.view.animation.AlphaAnimation(0, 1);
            animation.setDuration(1500);
            _view.setAnimation(animation);

            return _view;
        }
    }
}


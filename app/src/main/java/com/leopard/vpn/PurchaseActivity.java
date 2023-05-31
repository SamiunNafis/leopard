package com.leopard.vpn;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.Intent;
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
import com.airbnb.lottie.*;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.sanojpunchihewa.glowbutton.*;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import junit.extensions.*;
import junit.framework.*;
import junit.runner.*;
import junit.textui.*;
import org.json.*;
import android.database.DataSetObserver;


public class PurchaseActivity extends AppCompatActivity {

    private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();

    private double number = 0;
    private HashMap<String, Object> mapp = new HashMap<>();
    private HashMap<String, Object> mm2 = new HashMap<>();

    private ArrayList<HashMap<String, Object>> lmap = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> lmap2 = new ArrayList<>();

    private LinearLayout linear1;
    private LinearLayout RelativeLayout;
    private LinearLayout layout1;
    private LinearLayout layout2;
    private LinearLayout layout3;
    private ListView listview2;
    private LottieAnimationView lottie1;
    private ImageView imageview1;
    private LinearLayout linear6;
    private LinearLayout linear7;
    private TextView textview5;
    private ListView listview1;

    private Intent intent = new Intent();
    private DatabaseReference fdb = _firebase.getReference("offers/features");
    private ChildEventListener _fdb_child_listener;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.purchase);
        initialize(_savedInstanceState);
        FirebaseApp.initializeApp(this);
        MobileAds.initialize(this);

        List<String> testDeviceIds = Arrays.asList("874A04C22612BAC3077E65563760EC57");
        MobileAds.setRequestConfiguration(new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build());
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {
        linear1 = findViewById(R.id.linear1);
        RelativeLayout = findViewById(R.id.RelativeLayout);
        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        layout3 = findViewById(R.id.layout3);
        listview2 = findViewById(R.id.listview2);
        lottie1 = findViewById(R.id.lottie1);
        imageview1 = findViewById(R.id.imageview1);
        linear6 = findViewById(R.id.linear6);
        linear7 = findViewById(R.id.linear7);
        textview5 = findViewById(R.id.textview5);
        listview1 = findViewById(R.id.listview1);

        imageview1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View _view) {
                    finish();
                }
            });

        _fdb_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                fdb.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot _dataSnapshot) {
                            lmap = new ArrayList<>();
                            try {
                                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                                for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                    HashMap<String, Object> _map = _data.getValue(_ind);
                                    lmap.add(_map);
                                }
                            }
                            catch (Exception _e) {
                                _e.printStackTrace();
                            }
                            listview1.setAdapter(new Listview1Adapter(lmap));
                            ((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
                        }
                        @Override
                        public void onCancelled(DatabaseError _databaseError) {
                        }
                    });
            }

            @Override
            public void onChildChanged(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                fdb.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot _dataSnapshot) {
                            lmap = new ArrayList<>();
                            try {
                                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                                for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                    HashMap<String, Object> _map = _data.getValue(_ind);
                                    lmap.add(_map);
                                }
                            }
                            catch (Exception _e) {
                                _e.printStackTrace();
                            }
                            listview1.setAdapter(new Listview1Adapter(lmap));
                            ((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
                        }
                        @Override
                        public void onCancelled(DatabaseError _databaseError) {
                        }
                    });
            }

            @Override
            public void onChildMoved(DataSnapshot _param1, String _param2) {

            }

            @Override
            public void onChildRemoved(DataSnapshot _param1) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);

            }

            @Override
            public void onCancelled(DatabaseError _param1) {
                final int _errorCode = _param1.getCode();
                final String _errorMessage = _param1.getMessage();

            }
        };
        fdb.addChildEventListener(_fdb_child_listener);
    }

    private void initializeLogic() {
        _NavStatusBarColor("#FFFFFFFF", "#FFFFFFFF");
        _DARK_ICONS();
        _shape(14, 14, 0, 0, "#FFFFFF", "#FFFFFF", 0, layout3);
        for(int _repeat40 = 0; _repeat40 < (int)(3); _repeat40++) {
            {
                HashMap<String, Object> _item = new HashMap<>();
                _item.put("price", "Monthly ");
                lmap2.add(_item);
            }

        }
        listview2.setAdapter(new Listview2Adapter(lmap2));
        listview1.setVerticalScrollBarEnabled(false);
        listview2.setVerticalScrollBarEnabled(false);
        try{
            textview5.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/josefinsanslight.ttf"), 1);
        }catch(Exception e){

        }
    }

    public void _NavStatusBarColor(final String _color1, final String _color2) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Window w = this.getWindow();    w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);   w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.setStatusBarColor(Color.parseColor("#" + _color1.replace("#", "")));  w.setNavigationBarColor(Color.parseColor("#" + _color2.replace("#", "")));
        }
    }


    public void _DARK_ICONS() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        linear1.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
    }


    public void _shape(final double _top1, final double _top2, final double _bottom2, final double _bottom1, final String _inside_color, final String _side_color, final double _side_size, final View _view) {
        Double tlr = _top1;
        Double trr = _top2;
        Double blr = _bottom2;
        Double brr = _bottom1;
        Double sw = _side_size;
        android.graphics.drawable.GradientDrawable s = new android.graphics.drawable.GradientDrawable();
        s.setShape(android.graphics.drawable.GradientDrawable.RECTANGLE);
        s.setCornerRadii(new float[] {tlr.floatValue(),tlr.floatValue(), trr.floatValue(),trr.floatValue(), blr.floatValue(),blr.floatValue(), brr.floatValue(),brr.floatValue()}); 

        s.setColor(Color.parseColor(_inside_color));
        s.setStroke(sw.intValue(), Color.parseColor(_side_color));
        _view.setBackground(s);
    }


    public void _Icon_Colour(final ImageView _iconview, final String _colour) {
        _iconview.getDrawable().setColorFilter(Color.parseColor(_colour), PorterDuff.Mode.SRC_IN);
    }

    public class Listview2Adapter extends BaseAdapter {

        ArrayList<HashMap<String, Object>> _data;

        public Listview2Adapter(ArrayList<HashMap<String, Object>> _arr) {
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
                _view = _inflater.inflate(R.layout.offerbt_cus, null);
            }

            final LinearLayout linear1 = _view.findViewById(R.id.linear1);
            final ImageView imageview1 = _view.findViewById(R.id.imageview1);
            final TextView textview1 = _view.findViewById(R.id.textview1);
            final LinearLayout linear2 = _view.findViewById(R.id.linear2);
            final TextView textview2 = _view.findViewById(R.id.textview2);

            /*

             */

            {
                android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
                int d = (int) getApplicationContext().getResources().getDisplayMetrics().density;
                int clrs [] = {0xFF0B2447,0xFF576CBC};
                SketchUi= new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.LEFT_RIGHT, clrs);
                SketchUi.setCornerRadius(d*25);
                SketchUi.setStroke(d*0,0xFFFFFFFF);
                linear1.setElevation(d*3);
                android.graphics.drawable.RippleDrawable SketchUiRD = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{0xFF9E9E9E}), SketchUi, null);
                linear1.setBackground(SketchUiRD);
                linear1.setClickable(true);
            }
            textview1.setText(lmap2.get((int)_position).get("price").toString());

            return _view;
        }
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
                _view = _inflater.inflate(R.layout.offer_cus, null);
            }

            final LinearLayout linear1 = _view.findViewById(R.id.linear1);
            final LinearLayout linear2 = _view.findViewById(R.id.linear2);
            final ImageView imageview1 = _view.findViewById(R.id.imageview1);
            final TextView textview1 = _view.findViewById(R.id.textview1);

            try{
                textview1.setText(lmap.get((int)_position).get("title").toString());
                textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/josefinsanslight.ttf"), 1);
                _Icon_Colour(imageview1, "#0B2447");
            }catch(Exception e){

            }

            return _view;
        }
    }
}


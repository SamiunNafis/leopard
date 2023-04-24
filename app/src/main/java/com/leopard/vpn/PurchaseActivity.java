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
import junit.*;
import org.json.*;
import android.database.DataSetObserver;

public class PurchaseActivity extends AppCompatActivity {

    private FirebaseDatabase firebase = FirebaseDatabase.getInstance();

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
    private DatabaseReference fdb = firebase.getReference("offers/features");
    private ChildEventListener fdbchildlistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase);
        initialize(savedInstanceState);
        FirebaseApp.initializeApp(this);
        MobileAds.initialize(this);

        List<String> testDeviceIds = Arrays.asList("874A04C22612BAC3077E65563760EC57");
        MobileAds.setRequestConfiguration(new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build());
        initializeLogic();
    }

    private void initialize(Bundle savedInstanceState) {
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
                public void onClick(View view) {
                    finish();
                }
            });

        fdbchildlistener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot param1, String param2) {
                GenericTypeIndicator<HashMap<String, Object>> ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                final String childKey = param1.getKey();
                final HashMap<String, Object> childValue = param1.getValue(ind);
                fdb.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            lmap = new ArrayList<>();
                            try {
                                GenericTypeIndicator<HashMap<String, Object>> ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    HashMap<String, Object> map = data.getValue(ind);
                                    lmap.add(map);
                                }
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            listview1.setAdapter(new Listview1Adapter(lmap));
                            ((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
            }

            @Override
            public void onChildChanged(DataSnapshot param1, String param2) {
                GenericTypeIndicator<HashMap<String, Object>> ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                final String childKey = param1.getKey();
                final HashMap<String, Object> childValue = param1.getValue(ind);
                fdb.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            lmap = new ArrayList<>();
                            try {
                                GenericTypeIndicator<HashMap<String, Object>> ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    HashMap<String, Object> map = data.getValue(ind);
                                    lmap.add(map);
                                }
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            listview1.setAdapter(new Listview1Adapter(lmap));
                            ((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
            }

            @Override
            public void onChildMoved(DataSnapshot param1, String param2) {

            }

            @Override
            public void onChildRemoved(DataSnapshot param1) {
                GenericTypeIndicator<HashMap<String, Object>> ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                final String childKey = param1.getKey();
                final HashMap<String, Object> childValue = param1.getValue(ind);

            }

            @Override
            public void onCancelled(DatabaseError param1) {
                final int errorCode = param1.getCode();
                final String errorMessage = param1.getMessage();

            }
        };
        fdb.addChildEventListener(fdbchildlistener);
    }

    private void initializeLogic() {
        NavStatusBarColor("#FFFFFFFF", "#FFFFFFFF");
        DARKICONS();
        shape(14, 14, 0, 0, "#FFFFFF", "#FFFFFF", 0, layout3);
        for(int repeat40 = 0; repeat40 < (int)(3); repeat40++) {
            {
                HashMap<String, Object> item = new HashMap<>();
                item.put("price", "Monthly ");
                lmap2.add(item);
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

    public void NavStatusBarColor(final String color1, final String color2) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Window w = this.getWindow();    w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);   w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BARBACKGROUNDS);
            w.setStatusBarColor(Color.parseColor("#" + color1.replace("#", "")));  w.setNavigationBarColor(Color.parseColor("#" + color2.replace("#", "")));
        }
    }


    public void DARKICONS() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEMUIFLAGLIGHTSTATUSBAR);
        linear1.setSystemUiVisibility(View.SYSTEMUIFLAGLIGHTNAVIGATIONBAR);
    }


    public void shape(final double top1, final double top2, final double bottom2, final double bottom1, final String insidecolor, final String sidecolor, final double sidesize, final View view) {
        Double tlr = top1;
        Double trr = top2;
        Double blr = bottom2;
        Double brr = bottom1;
        Double sw = sidesize;
        android.graphics.drawable.GradientDrawable s = new android.graphics.drawable.GradientDrawable();
        s.setShape(android.graphics.drawable.GradientDrawable.RECTANGLE);
        s.setCornerRadii(new float[] {tlr.floatValue(),tlr.floatValue(), trr.floatValue(),trr.floatValue(), blr.floatValue(),blr.floatValue(), brr.floatValue(),brr.floatValue()}); 

        s.setColor(Color.parseColor(insidecolor));
        s.setStroke(sw.intValue(), Color.parseColor(sidecolor));
        view.setBackground(s);
    }


    public void IconColour(final ImageView iconview, final String colour) {
        iconview.getDrawable().setColorFilter(Color.parseColor(colour), PorterDuff.Mode.SRCIN);
    }

    public class Listview2Adapter extends BaseAdapter {

        ArrayList<HashMap<String, Object>> data;

        public Listview2Adapter(ArrayList<HashMap<String, Object>> arr) {
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
                view = inflater.inflate(R.layout.offerbtcus, null);
            }

            final LinearLayout linear1 = view.findViewById(R.id.linear1);
            final ImageView imageview1 = view.findViewById(R.id.imageview1);
            final TextView textview1 = view.findViewById(R.id.textview1);
            final LinearLayout linear2 = view.findViewById(R.id.linear2);
            final TextView textview2 = view.findViewById(R.id.textview2);

            /*

             */

            {
                android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
                int d = (int) getApplicationContext().getResources().getDisplayMetrics().density;
                int clrs [] = {0xFF0B2447,0xFF576CBC};
                SketchUi= new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.LEFTRIGHT, clrs);
                SketchUi.setCornerRadius(d*25);
                SketchUi.setStroke(d*0,0xFFFFFFFF);
                linear1.setElevation(d*3);
                android.graphics.drawable.RippleDrawable SketchUiRD = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{0xFF9E9E9E}), SketchUi, null);
                linear1.setBackground(SketchUiRD);
                linear1.setClickable(true);
            }
            textview1.setText(lmap2.get((int)position).get("price").toString());

            return view;
        }
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
                view = inflater.inflate(R.layout.offercus, null);
            }

            final LinearLayout linear1 = view.findViewById(R.id.linear1);
            final LinearLayout linear2 = view.findViewById(R.id.linear2);
            final ImageView imageview1 = view.findViewById(R.id.imageview1);
            final TextView textview1 = view.findViewById(R.id.textview1);

            try{
                textview1.setText(lmap.get((int)position).get("title").toString());
                textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/josefinsanslight.ttf"), 1);
                IconColour(imageview1, "#0B2447");
            }catch(Exception e){

            }

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
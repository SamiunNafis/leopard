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
import android.widget.ListView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import com.bumptech.glide.Glide;


public class FreeFragmentActivity extends Fragment {

    private FirebaseDatabase firebase = FirebaseDatabase.getInstance();

    private String serverurl = "";
    private static final int RESULTOK = -1;

    private ArrayList<HashMap<String, Object>> lmap = new ArrayList<>();

    private ListView listview1;

    private DatabaseReference firedb = firebase.getReference("configData");
    private ChildEventListener firedbchildlistener;
    private SharedPreferences ConnectionManagement;
    private Intent returnIntent = new Intent();

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.freefragment, container, false);
        initialize(savedInstanceState, view);
        FirebaseApp.initializeApp(getContext());
        initializeLogic();
        return view;
    }

    private void initialize(Bundle savedInstanceState, View view) {
        listview1 = view.findViewById(R.id.listview1);
        ConnectionManagement = getContext().getSharedPreferences("connectionManagement", Activity.MODE_PRIVATE);

        firedbchildlistener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot param1, String param2) {
                GenericTypeIndicator<HashMap<String, Object>> ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                final String childKey = param1.getKey();
                final HashMap<String, Object> childValue = param1.getValue(ind);
                firedb.addListenerForSingleValueEvent(new ValueEventListener() {
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
                firedb.addListenerForSingleValueEvent(new ValueEventListener() {
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
        firedb.addChildEventListener(firedbchildlistener);
    }

    private void initializeLogic() {
    }

    public void more() {
    }

    private class Listview1Adapter extends BaseAdapter {

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
            LayoutInflater inflater = getActivity().getLayoutInflater();
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
                Glide.with(getContext().getApplicationContext()).load(Uri.parse(data.get((int)position).get("countryicon").toString())).into(servericon);
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

            return view;
        }
    }

    {
    }


    public void transferDataToHone(final String name, final String url, final String icon) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("title", name);
        returnIntent.putExtra("icon", icon);
        returnIntent.putExtra("url", url);
        getActivity().setResult(RESULTOK, returnIntent);
        getActivity().finish();
        ConnectionManagement.edit().putString("name", name).commit();
        ConnectionManagement.edit().putString("link", url).commit();
        ConnectionManagement.edit().putString("icon", icon).commit();
    }

}
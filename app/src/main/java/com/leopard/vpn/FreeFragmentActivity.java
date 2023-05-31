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
import junit.extensions.*;
import junit.framework.*;
import junit.runner.*;
import junit.textui.*;
import org.json.*;
import com.bumptech.glide.Glide;


public class FreeFragmentActivity extends Fragment {

    private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();

    private String server_url = "";
    private static final int RESULT_OK = -1;

    private ArrayList<HashMap<String, Object>> lmap = new ArrayList<>();

    private ListView listview1;

    private DatabaseReference firedb = _firebase.getReference("configData");
    private ChildEventListener _firedb_child_listener;
    private SharedPreferences ConnectionManagement;
    private Intent returnIntent = new Intent();

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
        View _view = _inflater.inflate(R.layout.free_fragment, _container, false);
        initialize(_savedInstanceState, _view);
        FirebaseApp.initializeApp(getContext());
        initializeLogic();
        return _view;
    }

    private void initialize(Bundle _savedInstanceState, View _view) {
        listview1 = _view.findViewById(R.id.listview1);
        ConnectionManagement = getContext().getSharedPreferences("connectionManagement", Activity.MODE_PRIVATE);

        _firedb_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                firedb.addListenerForSingleValueEvent(new ValueEventListener() {
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
                firedb.addListenerForSingleValueEvent(new ValueEventListener() {
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
        firedb.addChildEventListener(_firedb_child_listener);
    }

    private void initializeLogic() {
    }

    public void _more() {
    }

    private class Listview1Adapter extends BaseAdapter {

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
            LayoutInflater _inflater = getActivity().getLayoutInflater();
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
                Glide.with(getContext().getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("country_icon").toString())).into(server_icon);
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

            return _view;
        }
    }

    {
    }


    public void _transferDataToHone(final String _name, final String _url, final String _icon) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("title", _name);
        returnIntent.putExtra("icon", _icon);
        returnIntent.putExtra("url", _url);
        getActivity().setResult(RESULT_OK, returnIntent);
        getActivity().finish();
        ConnectionManagement.edit().putString("name", _name).commit();
        ConnectionManagement.edit().putString("link", _url).commit();
        ConnectionManagement.edit().putString("icon", _icon).commit();
    }

}


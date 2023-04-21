package com.zerox.vpn;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
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

public class ServersActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private double n = 0;
	private String server_url = "";
	private String FNAME = "";
	
	private ArrayList<HashMap<String, Object>> listmap1 = new ArrayList<>();
	
	private LinearLayout base;
	private SwipeRefreshLayout swiperefreshlayout1;
	private ListView listview1;
	
	private DatabaseReference filesData = _firebase.getReference("configData");
	private ChildEventListener _filesData_child_listener;
	private Intent intent = new Intent();
	private SharedPreferences ConnectionManagement;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.servers);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		_app_bar = findViewById(R.id._app_bar);
		_coordinator = findViewById(R.id._coordinator);
		_toolbar = findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		base = findViewById(R.id.base);
		swiperefreshlayout1 = findViewById(R.id.swiperefreshlayout1);
		listview1 = findViewById(R.id.listview1);
		ConnectionManagement = getSharedPreferences("connectionManagement", Activity.MODE_PRIVATE);
		
		swiperefreshlayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				swiperefreshlayout1.setRefreshing(false);
				listview1.setAdapter(new Listview1Adapter(listmap1));
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			}
		});
		
		_filesData_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				listmap1.add((int)0, _childValue);
				listview1.setAdapter(new Listview1Adapter(listmap1));
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				filesData.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						listmap1 = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								listmap1.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview1.setAdapter(new Listview1Adapter(listmap1));
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
				n = 0;
				for(int _repeat15 = 0; _repeat15 < (int)(listmap1.size()); _repeat15++) {
					if (listmap1.get((int)n).get("key").toString().equals(_childKey)) {
						listmap1.remove((int)(n));
					}
					n++;
				}
				SketchwareUtil.sortListMap(listmap1, "title", false, true);
				listview1.setAdapter(new Listview1Adapter(listmap1));
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		filesData.addChildEventListener(_filesData_child_listener);
	}
	
	private void initializeLogic() {
		_NavStatusBarColor("#FFFFFFFF", "#FFFFFFFF");
		_DARK_ICONS();
		setTitle("SERVERS");
		_removeScollBar(listview1);
		_setListviewSelector(listview1);
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
		_toolbar.setTitleTextColor(Color.BLACK);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_grey); 
		for(int i = 0; i < _toolbar.getChildCount(); i++)     
		 { View view = _toolbar.getChildAt(i);
			
			    if(view instanceof TextView) {
				        TextView textView = (TextView) view;
				
				        Typeface myCustomFont=Typeface.createFromAsset(getAssets(),"fonts/manrope_bold.otf");
				        textView.setTypeface(myCustomFont); 
					}
			
			 }
	}
	
	public void _textview(final TextView _t, final String _s) {
		FNAME = "fonts/".concat(_s.concat(".otf"));
		try{
			_t.setTypeface(Typeface.createFromAsset(getAssets(), FNAME), 0);
		}catch(Exception e){
			SketchwareUtil.showMessage(getApplicationContext(), "Error!");
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
			Window w = this.getWindow();	w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);	w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			w.setStatusBarColor(Color.parseColor("#" + _color1.replace("#", "")));	w.setNavigationBarColor(Color.parseColor("#" + _color2.replace("#", "")));
		}
	}
	
	
	public void _DARK_ICONS() {
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		base.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
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
				title.setText("Server- ".concat(_data.get((int)_position).get("title").toString()));
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
					_transferDataToHone("Server- ".concat(_data.get((int)_position).get("title").toString()), _data.get((int)_position).get("url").toString(), _data.get((int)_position).get("country_icon").toString());
				}
			});
			_textview(title, "manrope_semibold");
			
			android.view.animation.Animation animation = new android.view.animation.AlphaAnimation(0, 1);
			animation.setDuration(1500);
			_view.setAnimation(animation);
			
			return _view;
		}
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
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
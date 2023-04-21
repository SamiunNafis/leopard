package com.zerox.vpn;

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
import com.google.android.material.button.*;
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

public class HomeActivity extends AppCompatActivity implements VpnStatus.StateListener {
	
	private Timer _timer = new Timer();
	
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
	
	private ScrollView vscroll3;
	private LinearLayout base;
	private LinearLayout linear1;
	private LinearLayout linear3;
	private LinearLayout linear2;
	private MaterialButton materialbutton1;
	private LinearLayout linear4;
	private ImageView imageview2;
	private TextView textview4;
	private TextView textview5;
	private TextView ip;
	private RelativeLayout l1;
	private RelativeLayout l2;
	private TextView timer;
	private TextView textview1;
	private TextView textview7;
	private TextView textview8;
	private Button button1;
	private LinearLayout server;
	private GlowButton circle_container1;
	private LinearLayout linear6;
	private ImageView imageview3;
	private TextView textview3;
	private GlowButton circle_container2;
	private LinearLayout linear8;
	private ImageView imageview4;
	private TextView textview9;
	private CircleImageView circleimageview1;
	private TextView textview2;
	private ImageView imageview1;
	
	private ObjectAnimator colorChange = new ObjectAnimator();
	private SharedPreferences ConnectionManagement;
	private Intent intent = new Intent();
	private TimerTask t;
	private RequestNetwork ip_request;
	private RequestNetwork.RequestListener _ip_request_request_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.home);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
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
	
	private void initialize(Bundle _savedInstanceState) {
		vscroll3 = findViewById(R.id.vscroll3);
		base = findViewById(R.id.base);
		linear1 = findViewById(R.id.linear1);
		linear3 = findViewById(R.id.linear3);
		linear2 = findViewById(R.id.linear2);
		materialbutton1 = findViewById(R.id.materialbutton1);
		linear4 = findViewById(R.id.linear4);
		imageview2 = findViewById(R.id.imageview2);
		textview4 = findViewById(R.id.textview4);
		textview5 = findViewById(R.id.textview5);
		ip = findViewById(R.id.ip);
		l1 = findViewById(R.id.l1);
		l2 = findViewById(R.id.l2);
		timer = findViewById(R.id.timer);
		textview1 = findViewById(R.id.textview1);
		textview7 = findViewById(R.id.textview7);
		textview8 = findViewById(R.id.textview8);
		button1 = findViewById(R.id.button1);
		server = findViewById(R.id.server);
		circle_container1 = findViewById(R.id.circle_container1);
		linear6 = findViewById(R.id.linear6);
		imageview3 = findViewById(R.id.imageview3);
		textview3 = findViewById(R.id.textview3);
		circle_container2 = findViewById(R.id.circle_container2);
		linear8 = findViewById(R.id.linear8);
		imageview4 = findViewById(R.id.imageview4);
		textview9 = findViewById(R.id.textview9);
		circleimageview1 = findViewById(R.id.circleimageview1);
		textview2 = findViewById(R.id.textview2);
		imageview1 = findViewById(R.id.imageview1);
		ConnectionManagement = getSharedPreferences("connectionManagement", Activity.MODE_PRIVATE);
		ip_request = new RequestNetwork(this);
		
		materialbutton1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), PurchaseActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				time++;
				SketchwareUtil.showMessage(getApplicationContext(), "1 hour added");
			}
		});
		
		server.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (stilConnecting || connected) {
					SketchwareUtil.showMessage(getApplicationContext(), "Please disconnect first!");
				}
				else {
					Intent clientStartIntent = new Intent(HomeActivity.this, ServersActivity.class);
					startActivityForResult(clientStartIntent, pickServerREQ);
					cleanup();
					
				}
			}
		});
		
		circle_container1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				circle_container2.performClick();
			}
		});
		
		circle_container2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				try{
					stopwatch = 0;
					stopwatch2 = 0;
					stopwatch3 = 0;
					stopwatch4 = 0;
				}catch(Exception e){
					 
				}
				try{
					if (!_networkAvailable()) {
						SketchwareUtil.showMessage(getApplicationContext(), "Please enable internet connection!");
					}
					else {
						if (isEmpty) {
							SketchwareUtil.showMessage(getApplicationContext(), "Please select a server!");
						}
						else {
							Runnable r = new Runnable() {
								@Override
								public void run() {
									if (!App.isStart) {
										try{
											 
										}catch(Exception e){
											 
										}
										_startVPN();
										stilConnecting = true;
										App.isStart = true;
										animateBackground(colorFrom, colorTo);
										textview3.setText("Connecting");
									}
									else {
										_stopVPN();
										stilConnecting = false;
										App.isStart = false;
										animateBackground(colorTo, colorFrom);
										textview3.setText("Connect");
										try{
											_onVpnConnected();
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
		
		_ip_request_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				if (connected) {
					ip.setText("IP : ".concat(_response));
					ip.setVisibility(View.VISIBLE);
				}
				else {
					ip.setVisibility(View.GONE);
				}
				try{
					_TransitionManager(base, 500);
				}catch(Exception e){
					 
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
	}
	
	private void initializeLogic() {
		_NavStatusBarColor("#FFFFFFFF", "#FFFFFFFF");
		_DARK_ICONS();
		vscroll3.setFillViewport(true);
		vscroll3.setVerticalScrollBarEnabled(false); vscroll3.setHorizontalScrollBarEnabled(false);
		textview5.setVisibility(View.GONE);
		ip.setVisibility(View.GONE);
		button1.setVisibility(View.GONE);
		l2.setVisibility(View.GONE);
		timer.setVisibility(View.GONE);
		materialbutton1.setVisibility(View.VISIBLE);
		time = 1;
		n = 0;
		stopwatch = 0;
		stopwatch2 = 0;
		stopwatch3 = 0;
		stopwatch4 = 0;
		try{
			_Icon_Colour(imageview3, "#ffffff");
		}catch(Exception e){
			 
		}
		try{
			_Icon_Colour(imageview4, "#ffffff");
		}catch(Exception e){
			 
		}
		_onCreate_();
		
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		if ((_resultCode == Activity.RESULT_OK) && (_requestCode == pickServerREQ)) {
				isEmpty = false;
				ProfileManager.setConntectedVpnProfileDisconnected(HomeActivity.this);
				try {
						ProfileManager pm = ProfileManager.getInstance(HomeActivity.this);
						VpnProfile vp = pm.getProfileByName(Build.MODEL);
						pm.removeProfile(HomeActivity.this, vp);
						showMessage("Done");
				} catch (Exception e) {
						
				}
				_getServerData(_data.getStringExtra("title"), _data.getStringExtra("url"), _data.getStringExtra("icon"));
		}
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		VpnStatus.addStateListener(this);
		
		Intent intent = new Intent(this, OpenVPNService.class);
		intent.setAction(OpenVPNService.START_SERVICE);
		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
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
	public void _onCreate_() {
		_designs();
		if (ConnectionManagement.getString("name", "").equals("")) {
			isEmpty = true;
			textview2.setText("NO SERVER SELECTED");
			circleimageview1.setImageResource(R.drawable.icon_background);
		}
		else {
			isEmpty = false;
			textview2.setText(ConnectionManagement.getString("name", ""));
			Glide.with(getApplicationContext()).load(Uri.parse(ConnectionManagement.getString("icon", ""))).into(circleimageview1);
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
	
	
	public void _designs() {
		_textview(textview2, "manrope_semibold");
		_textview(timer, "manrope_medium");
		_textview(textview5, "manrope_medium");
		_textview(ip, "manrope_medium");
		_textview(textview3, "manrope_medium");
		textview3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/josefinsanslight.ttf"), 0);
		
		textview2.setSelected(true); 
		textview2.setSingleLine(true);
	}
	
	
	public void _more() {
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
		intent.putExtra(LaunchVPN.EXTRA_KEY, vp.getUUID().toString());
		intent.setAction(Intent.ACTION_MAIN);
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
		textview3.setText("Disconnect");
		_setConnectedMore();
	}
	void changeStateButton(Boolean state) {
		if (state) {
			connected = state;
			textview3.setText("Disconnect");
		} else {
			connected = state;
			textview3.setText("Connect");
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
				if (state.equals("AUTH_FAILED")) {
					Toast.makeText(getApplicationContext(), "Wrong Username or Password!", Toast.LENGTH_SHORT).show();
					changeStateButton(false);
				}
			}
		});
	}
	public void setConnectedVPN(String uuid) {
	}
	public void cleanup() {
		if (profileAsync != null && !profileAsync.isCancelled()) {
			profileAsync.cancel(true);
		}
	}
	
	
	public void _startVPNFromURL(final String _url) {
		if (!App.isStart) {
			DataCleanManager.cleanCache(HomeActivity.this);
			_progressDialog(true);
			profileAsync = new ProfileAsync(this, new ProfileAsync.OnProfileLoadListener() {
				@Override
				public void onProfileLoadSuccess() {
					_progressDialog(false);
				}
				@Override
				public void onProfileLoadFailed(String msg) {
					showMessage("Failed");
					_progressDialog(false);
				}
			},_url);
			profileAsync.execute();
		}
	}
	
	
	public void _startVPN() {
		try {
			
			ProfileManager pm = ProfileManager.getInstance(HomeActivity.this);
			VpnProfile profile = pm.getProfileByName(Build.MODEL);
			startVPNConnection(profile);
			
		} catch (Exception ex) {
			
			App.isStart = false;
			
		}
	}
	
	
	public void _stopVPN() {
		try{
			stopVPNConnection();
			connected = false;
		}catch(Exception e){
			connected = false;
		}
	}
	
	
	public void _setConnectedMore() {
		ip.setVisibility(View.GONE);
		_onVpnConnected();
		URL = ConnectionManagement.getString("link", "");
		ICON = ConnectionManagement.getString("icon", "");
		name = ConnectionManagement.getString("name", "");
		textview2.setText(name);
		Glide.with(getApplicationContext()).load(Uri.parse(ICON)).into(circleimageview1);
	}
	
	
	public void _getServerData(final String _name, final String _link, final String _icon) {
		_startVPNFromURL(_link);
		textview2.setText(_name);
		Glide.with(getApplicationContext()).load(Uri.parse(_icon)).into(circleimageview1);
	}
	
	
	public void _progressDialog(final boolean _t) {
		if (_t) {
			dialog = new AlertDialog.Builder(HomeActivity.this).create();
			LayoutInflater inflater = getLayoutInflater();
			
			View convertView = (View) inflater.inflate(R.layout.loading_cus, null);
			dialog.setView(convertView);
			
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
			
			
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
	
	
	public boolean _networkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) HomeActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		
		return networkInfo != null && networkInfo.isConnected();
	}
	
	
	public void _onVpnConnected() {
		if (connected) {
			if (!(time == 0)) {
				ip.setVisibility(View.GONE);
				ip_request.startRequestNetwork(RequestNetworkController.GET, "https://api.ipify.org/?format=txt", "TechnoSahil", _ip_request_request_listener);
				try{
					l1.setVisibility(View.GONE);
				}catch(Exception e){
					 
				}
				try{
					l2.setVisibility(View.VISIBLE);
				}catch(Exception e){
					 
				}
				button1.setVisibility(View.VISIBLE);
				timer.setVisibility(View.VISIBLE);
				textview5.setVisibility(View.VISIBLE);
				try{
					_Icon_Colour(imageview3, "#ffffff");
				}catch(Exception e){
					 
				}
				try{
					_TransitionManager(base, 1000);
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
			timer.setVisibility(View.GONE);
			textview5.setVisibility(View.GONE);
			ip.setVisibility(View.GONE);
			try{
				_Icon_Colour(imageview3, "#ffffff");
			}catch(Exception e){
				 
			}
			try{
				_TransitionManager(base, 1000);
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
								if ((Double.parseDouble(textview8.getText().toString()) == 1) || (Double.parseDouble(textview8.getText().toString()) > 1)) {
									if (textview8.getText().toString().endsWith(String.valueOf((long)(time))) || (Double.parseDouble(textview8.getText().toString()) > time)) {
										SketchwareUtil.showMessage(getApplicationContext(), "Time has been outed");
										_stopVPN();
										stilConnecting = false;
										App.isStart = false;
										animateBackground(colorTo, colorFrom);
										textview3.setText("Connect");
										try{
											_onVpnConnected();
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
				_timer.scheduleAtFixedRate(t, (int)(1000), (int)(1000));
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
	
	
	public void _Icon_Colour(final ImageView _iconview, final String _colour) {
		_iconview.getDrawable().setColorFilter(Color.parseColor(_colour), PorterDuff.Mode.SRC_IN);
	}
	
	
	public void _TransitionManager(final View _view, final double _duration) {
		LinearLayout viewgroup =(LinearLayout) _view;
		
		android.transition.AutoTransition autoTransition = new android.transition.AutoTransition(); autoTransition.setDuration((long)_duration); android.transition.TransitionManager.beginDelayedTransition(viewgroup, autoTransition);
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
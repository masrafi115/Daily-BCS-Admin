package com.studyaid.dailybcsaid.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import java.util.HashMap;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import android.widget.EditText;
import android.widget.ImageView;
import com.google.android.material.button.*;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.bumptech.glide.Glide;
import android.graphics.Typeface;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class ViewPostActivity extends  AppCompatActivity  { 
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private HashMap<String, Object> map = new HashMap<>();
	private String ref = "";
	private HashMap<String, Object> data = new HashMap<>();
	private String key = "";
	
	private ScrollView vscroll1;
	private LinearLayout linear4;
	private TextView Title_txt;
	private CardView cardview1;
	private LinearLayout linear1;
	private EditText article_txt;
	private TextView time_txt;
	private LinearLayout linear_all_store_mail;
	private ImageView banner_img;
	private LinearLayout linear3;
	private LinearLayout linear2;
	private TextView textview1;
	private TextView textview2;
	private LinearLayout linear5;
	private TextView textview4;
	private TextView textview3;
	private MaterialButton materialbutton1;
	private MaterialButton materialbutton3;
	
	private DatabaseReference post = _firebase.getReference("post");
	private ChildEventListener _post_child_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.view_post);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		_app_bar = (AppBarLayout) findViewById(R.id._app_bar);
		_coordinator = (CoordinatorLayout) findViewById(R.id._coordinator);
		_toolbar = (Toolbar) findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		Title_txt = (TextView) findViewById(R.id.Title_txt);
		cardview1 = (CardView) findViewById(R.id.cardview1);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		article_txt = (EditText) findViewById(R.id.article_txt);
		time_txt = (TextView) findViewById(R.id.time_txt);
		linear_all_store_mail = (LinearLayout) findViewById(R.id.linear_all_store_mail);
		banner_img = (ImageView) findViewById(R.id.banner_img);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		textview1 = (TextView) findViewById(R.id.textview1);
		textview2 = (TextView) findViewById(R.id.textview2);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		textview4 = (TextView) findViewById(R.id.textview4);
		textview3 = (TextView) findViewById(R.id.textview3);
		materialbutton1 = (MaterialButton) findViewById(R.id.materialbutton1);
		materialbutton3 = (MaterialButton) findViewById(R.id.materialbutton3);
		
		materialbutton1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				map = new HashMap<>();
				map.put("status", "verified");
				map.put("long", article_txt.getText().toString());
				post.child(key).updateChildren(map);
				SketchwareUtil.showMessage(getApplicationContext(), "Verified");
				finish();
			}
		});
		
		materialbutton3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				post.child(key).removeValue();
			}
		});
		
		_post_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
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
		post.addChildEventListener(_post_child_listener);
	}
	
	private void initializeLogic() {
		com.google.android.material.appbar.AppBarLayout appBarLayout = (com.google.android.material.appbar.AppBarLayout) _toolbar.getParent();
		appBarLayout.setStateListAnimator(null);
		data = new Gson().fromJson(getIntent().getStringExtra("key"), new TypeToken<HashMap<String, Object>>(){}.getType());
		Title_txt.setText(data.get("title").toString());
		article_txt.setText(data.get("long").toString());
		time_txt.setText(_millisToDate(Double.parseDouble(data.get("time").toString())));
		Glide.with(getApplicationContext()).load(Uri.parse(data.get("img").toString())).into(banner_img);
		cardview1.setCardBackgroundColor(0xFFFFFFFF);
		cardview1.setRadius((float)15);
		cardview1.setCardElevation((float)0);
		Title_txt.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/manrope_bold.ttf"), 0);
		article_txt.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/opensansregular.ttf"), 0);
		time_txt.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/opensansregular.ttf"), 1);
		
		key = data.get("key").toString();
		_StatusBarTrans();
		textview1.setText(data.get("author").toString().substring((int)(0), (int)(1)).toUpperCase());
		textview2.setText(data.get("author").toString());
		textview3.setText(data.get("category").toString());
		textview4.setText(_millisToDate(Double.parseDouble(data.get("time").toString())));
		_shapeRadius(linear3, "#2196F3", 90);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _StatusBarTrans () {
		if (Build.VERSION.SDK_INT >= 23) {
			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		}
		else {
			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
				Window w =ViewPostActivity.this.getWindow();
				w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
				w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(0xFF9E9E9E);
			}
		}
	}
	
	
	public String _millisToDate (final double _millis) {
		return DateFormat.getDateInstance(DateFormat.LONG).format(_millis);
		    //You can use DateFormat.LONG instead of SHORT
		
	}
	
	
	public void _shapeRadius (final View _v, final String _color, final double _radius) {
		android.graphics.drawable.GradientDrawable shape = new android.graphics.drawable.GradientDrawable();
		  shape.setShape(android.graphics.drawable.GradientDrawable.RECTANGLE);
		
		shape.setCornerRadius((int)_radius);
		
		shape.setColor(Color.parseColor(_color));
		_v.setBackgroundDrawable(shape);
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
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}
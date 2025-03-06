package com.studyaid.dailybcsaid.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import android.widget.LinearLayout;
import com.google.android.material.textfield.*;
import android.widget.TextView;
import android.widget.EditText;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import com.google.gson.Gson;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class AddQuestionsActivity extends  AppCompatActivity  { 
	
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private FloatingActionButton _fab;
	private HashMap<String, Object> map = new HashMap<>();
	private String label = "";
	private String value = "";
	private String key = "";
	private String ans = "";
	
	private LinearLayout linear3;
	private LinearLayout linear5;
	private TextInputLayout textinputlayout6;
	private RadioGroup radiogroup3;
	private TextView textview2;
	private RadioGroup radiogroup1;
	private TextInputLayout textinputlayout5;
	private EditText edittext10;
	private LinearLayout linear9;
	private LinearLayout linear8;
	private LinearLayout linear7;
	private LinearLayout linear6;
	private RadioButton radiobutton7;
	private EditText edittext9;
	private RadioButton radiobutton6;
	private EditText edittext8;
	private RadioButton radiobutton5;
	private EditText edittext7;
	private RadioButton radiobutton1;
	private EditText edittext6;
	private RadioButton radiobutton8;
	private RadioButton radiobutton9;
	private RadioButton radiobutton10;
	private RadioButton radiobutton11;
	private EditText edittext5;
	
	private SharedPreferences questions;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.add_questions);
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
		_fab = (FloatingActionButton) findViewById(R.id._fab);
		
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		textinputlayout6 = (TextInputLayout) findViewById(R.id.textinputlayout6);
		radiogroup3 = (RadioGroup) findViewById(R.id.radiogroup3);
		textview2 = (TextView) findViewById(R.id.textview2);
		radiogroup1 = (RadioGroup) findViewById(R.id.radiogroup1);
		textinputlayout5 = (TextInputLayout) findViewById(R.id.textinputlayout5);
		edittext10 = (EditText) findViewById(R.id.edittext10);
		linear9 = (LinearLayout) findViewById(R.id.linear9);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		radiobutton7 = (RadioButton) findViewById(R.id.radiobutton7);
		edittext9 = (EditText) findViewById(R.id.edittext9);
		radiobutton6 = (RadioButton) findViewById(R.id.radiobutton6);
		edittext8 = (EditText) findViewById(R.id.edittext8);
		radiobutton5 = (RadioButton) findViewById(R.id.radiobutton5);
		edittext7 = (EditText) findViewById(R.id.edittext7);
		radiobutton1 = (RadioButton) findViewById(R.id.radiobutton1);
		edittext6 = (EditText) findViewById(R.id.edittext6);
		radiobutton8 = (RadioButton) findViewById(R.id.radiobutton8);
		radiobutton9 = (RadioButton) findViewById(R.id.radiobutton9);
		radiobutton10 = (RadioButton) findViewById(R.id.radiobutton10);
		radiobutton11 = (RadioButton) findViewById(R.id.radiobutton11);
		edittext5 = (EditText) findViewById(R.id.edittext5);
		questions = getSharedPreferences("questions", Activity.MODE_PRIVATE);
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				map = new HashMap<>();
				map.put("ques", edittext10.getText().toString());
				map.put("a", edittext9.getText().toString());
				map.put("b", edittext8.getText().toString());
				map.put("c", edittext7.getText().toString());
				map.put("d", edittext6.getText().toString());
				map.put("ans", ans);
				map.put("def", edittext5.getText().toString());
				questions.edit().putString("key", new Gson().toJson(map)).commit();
				finish();
			}
		});
	}
	
	private void initializeLogic() {
		_shapeRadius(linear5, "#FFFFFF", 15);
		radiobutton1.setEnabled(false);
		radiobutton5.setEnabled(false);
		radiobutton6.setEnabled(false);
		radiobutton7.setEnabled(false);
		radiogroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
		    {
			        public void onCheckedChanged(RadioGroup group, int checkedId) {
				            // checkedId is the RadioButton selected
				            //RadioButton rb=(RadioButton)findViewById(checkedId);
				
							if (checkedId == R.id.radiobutton8){
									
					ans = "0";
				} else if (checkedId == R.id.radiobutton9){
					ans = "1";
				} else if (checkedId == R.id.radiobutton10){
					ans = "2";
				} else if (checkedId == R.id.radiobutton11){
					ans = "3";
				}
			}
			    });
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
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
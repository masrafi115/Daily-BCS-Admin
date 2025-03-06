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
import java.util.ArrayList;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import com.google.android.material.button.*;
import com.google.android.material.textfield.*;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.content.Intent;
import android.net.Uri;
import android.app.Activity;
import android.content.SharedPreferences;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class QuestionsActivity extends  AppCompatActivity  { 
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private FloatingActionButton _fab;
	private HashMap<String, Object> map = new HashMap<>();
	private HashMap<String, Object> data = new HashMap<>();
	private HashMap<String, Object> mapdata = new HashMap<>();
	private double day = 0;
	private double mon = 0;
	private double year = 0;
	private boolean isPreview = false;
	
	private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
	
	private ScrollView vscroll1;
	private LinearLayout linear23;
	private LinearLayout linear19;
	private LinearLayout linear22;
	private LinearLayout linear21;
	private MaterialButton materialbutton1;
	private TextInputLayout textinputlayout5;
	private TextInputLayout textinputlayout4;
	private TextInputLayout textinputlayout6;
	private EditText edittext1;
	private EditText edittext2;
	private EditText edittext3;
	private ListView listview1;
	private LinearLayout linear1;
	private MaterialButton materialbutton2;
	
	private Intent i = new Intent();
	private SharedPreferences questions;
	private Calendar c = Calendar.getInstance();
	private DatabaseReference Questions = _firebase.getReference("/");
	private ChildEventListener _Questions_child_listener;
	private AlertDialog.Builder d;
	private SharedPreferences datas;
	private Calendar calendar = Calendar.getInstance();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.questions);
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
		
		vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
		linear23 = (LinearLayout) findViewById(R.id.linear23);
		linear19 = (LinearLayout) findViewById(R.id.linear19);
		linear22 = (LinearLayout) findViewById(R.id.linear22);
		linear21 = (LinearLayout) findViewById(R.id.linear21);
		materialbutton1 = (MaterialButton) findViewById(R.id.materialbutton1);
		textinputlayout5 = (TextInputLayout) findViewById(R.id.textinputlayout5);
		textinputlayout4 = (TextInputLayout) findViewById(R.id.textinputlayout4);
		textinputlayout6 = (TextInputLayout) findViewById(R.id.textinputlayout6);
		edittext1 = (EditText) findViewById(R.id.edittext1);
		edittext2 = (EditText) findViewById(R.id.edittext2);
		edittext3 = (EditText) findViewById(R.id.edittext3);
		listview1 = (ListView) findViewById(R.id.listview1);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		materialbutton2 = (MaterialButton) findViewById(R.id.materialbutton2);
		questions = getSharedPreferences("questions", Activity.MODE_PRIVATE);
		d = new AlertDialog.Builder(this);
		datas = getSharedPreferences("datas", Activity.MODE_PRIVATE);
		
		materialbutton1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_datePickerDialog(d, c);
			}
		});
		
		materialbutton2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (materialbutton1.getText().toString().equals("Set Date")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Please Set Date Perfectly");
				}
				else {
					data = new HashMap<>();
					data.put("title", edittext1.getText().toString());
					_ConvertTime(materialbutton1.getText().toString(), c);
					data.put("date", String.valueOf((long)(c.getTimeInMillis())));
					data.put("material", edittext2.getText().toString());
					data.put("points", String.valueOf((long)(list.size())));
					data.put("syllabus", edittext3.getText().toString());
					data.put("questions", "\"".concat(new Gson().toJson(list).concat("\"")));
					if (isPreview) {
						data.put("participants", mapdata.get("participants").toString());
					}
					else {
						data.put("participants", "0");
					}
					Questions.child("Questions/".concat(getIntent().getStringExtra("key").concat("/")).concat(_DateTimeFormat(c.getTimeInMillis() - 86400000, "d-M-yy"))).updateChildren(data);
					SketchwareUtil.showMessage(getApplicationContext(), "Added");
					finish();
				}
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), AddQuestionsActivity.class);
				startActivity(i);
			}
		});
		
		_Questions_child_listener = new ChildEventListener() {
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
		Questions.addChildEventListener(_Questions_child_listener);
	}
	
	private void initializeLogic() {
		isPreview = getIntent().hasExtra("data");
		if (isPreview) {
			mapdata = new Gson().fromJson(getIntent().getStringExtra("data"), new TypeToken<HashMap<String, Object>>(){}.getType());
			edittext1.setText(mapdata.get("title").toString());
			edittext2.setText(mapdata.get("material").toString());
			edittext3.setText(mapdata.get("syllabus").toString());
			materialbutton1.setText(_DateTimeFormat(Double.parseDouble(mapdata.get("date").toString()) - 86400000, "d-M-yy"));
			list = new Gson().fromJson(mapdata.get("questions").toString().substring((int)(1), (int)(mapdata.get("questions").toString().length() - 1)), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
			listview1.setAdapter(new Listview1Adapter(list));
			((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			_setListViewHeightBasedOnChildren(listview1);
			/*
*/
		}
		_shapeRadius(linear19, "#FFFFFF", 15);
		_shapeRadius(linear22, "#FFFFFF", 15);
		calendar = Calendar.getInstance();
		day = calendar.get(Calendar.DATE);
		mon = calendar.get(Calendar.MONTH);
		year = calendar.get(Calendar.YEAR);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (!questions.getString("key", "").equals("")) {
			map = new Gson().fromJson(questions.getString("key", ""), new TypeToken<HashMap<String, Object>>(){}.getType());
			list.add(map);
			listview1.setAdapter(new Listview1Adapter(list));
			((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			_setListViewHeightBasedOnChildren(listview1);
			questions.edit().remove("key").commit();
		}
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}
	public void _setBackground (final View _view, final double _radius, final double _shadow, final String _color, final boolean _ripple) {
		if (_ripple) {
			android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
			gd.setColor(Color.parseColor(_color));
			gd.setCornerRadius((int)_radius);
			_view.setElevation((int)_shadow);
			android.content.res.ColorStateList clrb = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor("#9e9e9e")});
			android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrb , gd, null);
			_view.setClickable(true);
			_view.setBackground(ripdrb);
		}
		else {
			android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
			gd.setColor(Color.parseColor(_color));
			gd.setCornerRadius((int)_radius);
			_view.setBackground(gd);
			_view.setElevation((int)_shadow);
		}
	}
	
	
	public String _millisToDate (final double _millis) {
		return DateFormat.getDateInstance(DateFormat.SHORT).format(_millis);
		    //You can use DateFormat.LONG instead of SHORT
		
	}
	
	
	public void _setListViewHeightBasedOnChildren (final ListView _listView) {
		ListAdapter listAdapter = _listView.getAdapter();
		if (listAdapter == null) {
				return;
		}
		
		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
				View listItem = listAdapter.getView(i, null, _listView);
				listItem.measure(0, 0); // 计算子项View 的宽高
				totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}
		
		ViewGroup.LayoutParams params = _listView.getLayoutParams();
		params.height = totalHeight
		+ (_listView.getDividerHeight() * (listAdapter.getCount() - 1))+80;
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		_listView.setLayoutParams(params);
		
	}
	
	
	public void _shapeRadius (final View _v, final String _color, final double _radius) {
		android.graphics.drawable.GradientDrawable shape = new android.graphics.drawable.GradientDrawable();
		  shape.setShape(android.graphics.drawable.GradientDrawable.RECTANGLE);
		
		shape.setCornerRadius((int)_radius);
		
		shape.setColor(Color.parseColor(_color));
		_v.setBackgroundDrawable(shape);
	}
	
	
	public void _datePickerDialog (final AlertDialog.Builder _d, final Calendar _calendar) {
		 LinearLayout mylayout = new LinearLayout(this); 
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT); 
		mylayout.setLayoutParams(params);
		mylayout.setOrientation(LinearLayout.VERTICAL);
		 DatePicker dp=new DatePicker (QuestionsActivity.this);
		final EditText myedittext = new EditText(this);  
		mylayout.addView(dp);
		mylayout.setBackgroundColor(0xffffffff); 
		dp.init(_calendar.get(Calendar.YEAR), _calendar.get(Calendar.MONTH),_calendar.get(Calendar.DAY_OF_MONTH),new DatePicker.OnDateChangedListener(){
				
				@Override public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
						day = datePicker.getDayOfMonth(); 
						mon = datePicker.getMonth()+1;
						year = datePicker.getYear(); 
				} });
		
		_d.setView(mylayout);
		_d.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				materialbutton1.setText(String.valueOf((long)(day)).concat("-".concat(String.valueOf((long)(mon)).concat("-".concat(String.valueOf((long)(year)).substring((int)(2), (int)(4)))))));
			}
		});
		_d.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				
			}
		});
		_d.create().show();
	}
	
	
	public void _ConvertTime (final String _time, final Calendar _cal) {
		try
		{
				Date date2 =new SimpleDateFormat("d-M-yy").parse(_time);
				
					date2.setDate(date2.getDate()+1);
					_cal.setTime(date2);
				
		}
		catch (java.text.ParseException execption)
		{
				showMessage("Error Setting Time");
		}
		
	}
	
	
	public void _getData () {
		list.clear();
		if (!datas.getString("key", "").equals("")) {
			list = new Gson().fromJson(datas.getString("key", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		}
		listview1.setAdapter(new Listview1Adapter(list));
		((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
	}
	
	
	public String _DateTimeFormat (final double _millisecond, final String _format) {
		return new SimpleDateFormat(_format).format(_millisecond);
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
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.custom, null);
			}
			
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final LinearLayout linear2 = (LinearLayout) _view.findViewById(R.id.linear2);
			final RadioGroup radiogroup1 = (RadioGroup) _view.findViewById(R.id.radiogroup1);
			final Button button1 = (Button) _view.findViewById(R.id.button1);
			final TextView textview1 = (TextView) _view.findViewById(R.id.textview1);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			final RadioButton radiobutton1 = (RadioButton) _view.findViewById(R.id.radiobutton1);
			final RadioButton radiobutton2 = (RadioButton) _view.findViewById(R.id.radiobutton2);
			final RadioButton radiobutton3 = (RadioButton) _view.findViewById(R.id.radiobutton3);
			final RadioButton radiobutton4 = (RadioButton) _view.findViewById(R.id.radiobutton4);
			
			textview1.setText(list.get((int)_position).get("ques").toString());
			radiobutton1.setText(list.get((int)_position).get("a").toString());
			radiobutton2.setText(list.get((int)_position).get("b").toString());
			radiobutton3.setText(list.get((int)_position).get("c").toString());
			radiobutton4.setText(list.get((int)_position).get("d").toString());
			button1.setVisibility(View.GONE);
			radiobutton1.setEnabled(false);
			radiobutton2.setEnabled(false);
			radiobutton3.setEnabled(false);
			radiobutton4.setEnabled(false);
			_setBackground(linear1, 5, 5, "#FFFFFF", false);
			imageview1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					list.remove((int)(_position));
					datas.edit().putString("key", new Gson().toJson(list)).commit();
					_getData();
				}
			});
			
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
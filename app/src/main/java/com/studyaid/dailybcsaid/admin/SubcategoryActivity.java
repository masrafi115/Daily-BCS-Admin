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
import java.util.ArrayList;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.Button;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class SubcategoryActivity extends  AppCompatActivity  { 
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private HashMap<String, Object> map = new HashMap<>();
	private String ref = "";
	private boolean isInstanced = false;
	
	private ArrayList<String> tags = new ArrayList<>();
	private ArrayList<Double> tmp = new ArrayList<>();
	private ArrayList<String> value = new ArrayList<>();
	private ArrayList<String> key = new ArrayList<>();
	
	private ListView listview1;
	private LinearLayout linear1;
	private EditText edittext1;
	private Button button1;
	
	private DatabaseReference Subcategory = _firebase.getReference("/");
	private ChildEventListener _Subcategory_child_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.subcategory);
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
		listview1 = (ListView) findViewById(R.id.listview1);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		edittext1 = (EditText) findViewById(R.id.edittext1);
		button1 = (Button) findViewById(R.id.button1);
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (!getIntent().getStringExtra("key").equals("null")) {
					getAllNumKeysFromMap(map,tmp);
					map.put(String.valueOf((long)(Collections.max(tmp)+1)), edittext1.getText().toString());
				}
				else {
					if (isInstanced) {
						getAllNumKeysFromMap(map,tmp);
						map.put(String.valueOf((long)(Collections.max(tmp)+1)), edittext1.getText().toString());
					}
					else {
						map = new HashMap<>();
						map.put("\"1\"", edittext1.getText().toString());
						isInstanced = true;
					}
				}
				getAllValuesFromMap(map, tags);
				//Subcategory.child("sub").setValue(map);
				Subcategory.child(ref.concat("/sub")).updateChildren(map);
				edittext1.setText("");
				listview1.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, tags));
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			}
		});
		
		_Subcategory_child_listener = new ChildEventListener() {
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
		Subcategory.addChildEventListener(_Subcategory_child_listener);
	}
	
	private void initializeLogic() {
		_ButtonColorFilter(button1, "#3F67B2");
		ref = getIntent().getStringExtra("path");
		/*
Subcategory.removeEventListener(_Subcategory_child_listener);
Subcategory =
_firebase.getReference(ref);

Subcategory.addChildEventListener(_Subcategory_child_listener);
*/
		map = new Gson().fromJson(getIntent().getStringExtra("key"), new TypeToken<HashMap<String, Object>>(){}.getType());
		getAllValuesFromMap(map, tags);
		SketchwareUtil.getAllKeysFromMap(map, key);
		listview1.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, tags));
		((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _getAllValuesFromMap () {
	} public static void getAllValuesFromMap(Map<String, Object> map, ArrayList<String> output) {
						if (output == null) return;
						output.clear();
		
						if (map == null || map.size() <= 0) return;
		
						Iterator itr = map.entrySet().iterator();
						while (itr.hasNext()) {
									Map.Entry<String, String> entry = (Map.Entry) itr.next();
									output.add(entry.getValue());
						}
	}
	
	
	public void _ButtonColorFilter (final View _view, final String _color) {
		_view.getBackground().setColorFilter(Color.parseColor(_color), PorterDuff.Mode.MULTIPLY);
	}
	
	
	public void _getAllNumKeysFromMap () {
	} public static void getAllNumKeysFromMap(Map<String, Object> map, ArrayList<Double> output) {
		        if (output == null) return;
		        output.clear();
		
		        if (map == null || map.size() <= 0) return;
		
		        Iterator itr = map.entrySet().iterator();
		        while (itr.hasNext()) {
			            Map.Entry<String, String> entry = (Map.Entry) itr.next();
			            output.add(Double.parseDouble( entry.getKey().toString().replace("\"", "")));
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
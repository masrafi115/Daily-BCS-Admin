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
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import com.google.gson.Gson;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class RoutinesActivity extends  AppCompatActivity  { 
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private FloatingActionButton _fab;
	private String ref = "";
	private String timeStr = "";
	private String key = "";
	
	private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
	private ArrayList<String> keys = new ArrayList<>();
	
	private ListView listview1;
	
	private DatabaseReference Routines = _firebase.getReference("+data+");
	private ChildEventListener _Routines_child_listener;
	private Intent i = new Intent();
	private Intent in = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.routines);
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
		
		listview1 = (ListView) findViewById(R.id.listview1);
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				in.setClass(getApplicationContext(), QuestionsActivity.class);
				in.putExtra("key", key);
				startActivity(in);
			}
		});
		
		_Routines_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				list.add(_childValue);
				listview1.setAdapter(new Listview1Adapter(list));
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				Routines.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						list = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								list.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview1.setAdapter(new Listview1Adapter(list));
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
				SketchwareUtil.showMessage(getApplicationContext(), "Deleted");
				Routines.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						list = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								list.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview1.setAdapter(new Listview1Adapter(list));
						((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		Routines.addChildEventListener(_Routines_child_listener);
	}
	
	private void initializeLogic() {
		key = getIntent().getStringExtra("key");
		Routines.removeEventListener(_Routines_child_listener);
		ref = "Questions/".concat(key);
		Routines =
		_firebase.getReference(ref);
		
		Routines.addChildEventListener(_Routines_child_listener);
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
	public void onBackPressed() {
		finish();
	}
	public String _millisToDate (final double _millis) {
		return DateFormat.getDateInstance(DateFormat.LONG).format(_millis);
		    //You can use DateFormat.LONG instead of SHORT
		
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
				_view = _inflater.inflate(R.layout.routine, null);
			}
			
			final androidx.cardview.widget.CardView cardview1 = (androidx.cardview.widget.CardView) _view.findViewById(R.id.cardview1);
			final LinearLayout linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final LinearLayout linear4 = (LinearLayout) _view.findViewById(R.id.linear4);
			final LinearLayout linear2 = (LinearLayout) _view.findViewById(R.id.linear2);
			final TextView textview1 = (TextView) _view.findViewById(R.id.textview1);
			final ImageView imageview5 = (ImageView) _view.findViewById(R.id.imageview5);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			final TextView textview2 = (TextView) _view.findViewById(R.id.textview2);
			final ImageView imageview2 = (ImageView) _view.findViewById(R.id.imageview2);
			final TextView textview3 = (TextView) _view.findViewById(R.id.textview3);
			final ImageView imageview3 = (ImageView) _view.findViewById(R.id.imageview3);
			final TextView textview4 = (TextView) _view.findViewById(R.id.textview4);
			
			textview1.setText(list.get((int)_position).get("title").toString());
			textview2.setText(list.get((int)_position).get("participants").toString());
			textview3.setText(list.get((int)_position).get("points").toString());
			textview4.setText(_millisToDate(Double.parseDouble(list.get((int)_position).get("date").toString())));
			cardview1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					i.setClass(getApplicationContext(), QuestionsActivity.class);
					i.putExtra("data", new Gson().toJson(list.get((int)(_position))));
					i.putExtra("key", key);
					startActivity(i);
				}
			});
			imageview5.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					Routines.child(_DateTimeFormat(Double.parseDouble(list.get((int)_position).get("date").toString()) - 86400000, "d-M-yy")).removeValue();
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
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
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class SubCategoriasActivity extends  AppCompatActivity  { 
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private FloatingActionButton _fab;
	private String label = "";
	private String value = "";
	private String ref = "";
	private HashMap<String, Object> map = new HashMap<>();
	private String key = "";
	private String type = "";
	
	private ArrayList<String> keys = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
	private ArrayList<String> types = new ArrayList<>();
	
	private RecyclerView recyclerview1;
	
	private AlertDialog.Builder dialog_d;
	private DatabaseReference Subcategory = _firebase.getReference("+data+");
	private ChildEventListener _Subcategory_child_listener;
	private Intent i = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.sub_categorias);
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
		
		recyclerview1 = (RecyclerView) findViewById(R.id.recyclerview1);
		dialog_d = new AlertDialog.Builder(this);
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				dialog_d.setTitle("Add Category");
				final EditText dialog_edittext1 = new EditText(SubCategoriasActivity.this);
				dialog_edittext1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
				dialog_edittext1.setHint("Name");
				
				final EditText dialog_edittext2 = new EditText(SubCategoriasActivity.this);
				dialog_edittext2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
				dialog_edittext2.setHint("Link");
				
				final Spinner dialog_spinner = new Spinner(SubCategoriasActivity.this);
				dialog_spinner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
				dialog_spinner.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, types));
				((ArrayAdapter)dialog_spinner.getAdapter()).notifyDataSetChanged();
					
				
				View inflated_view = getLayoutInflater().inflate(R.layout.dialog, null);
				
				LinearLayout dialog_linear1 = inflated_view.findViewById(R.id.linear2);
				LinearLayout dialog_linear2 = inflated_view.findViewById(R.id.linear3);
				LinearLayout dialog_linear3 = inflated_view.findViewById(R.id.linear4);
				dialog_linear3.addView(dialog_spinner);
				dialog_linear1.addView(dialog_edittext1);
				dialog_linear2.addView(dialog_edittext2);
				dialog_d.setView(inflated_view);
				dialog_d.setPositiveButton("Add", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						label = dialog_edittext1.getText().toString();
						value = dialog_edittext2.getText().toString();
						if (dialog_spinner.getSelectedItemPosition() == 0) {
								type = "question";
						}
						else {
								type = "article";
						}
						
						map = new HashMap<>();
						map.put("name", label);
						map.put("img", value);
						map.put("type", type);
						Subcategory.child(label).updateChildren(map);
					}
				});
				dialog_d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				dialog_d.create().show();
			}
		});
		
		_Subcategory_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				keys.add(_childKey);
				list.add(_childValue);
				recyclerview1.setAdapter(new Recyclerview1Adapter(list));
				recyclerview1.setLayoutManager(new GridLayoutManager(SubCategoriasActivity.this, 2));
				//recyclerview1.addItemDecoration(new SpacesItemDecoration(getDip(6)));
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				Subcategory.addListenerForSingleValueEvent(new ValueEventListener() {
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
						recyclerview1.setAdapter(new Recyclerview1Adapter(list));
						recyclerview1.setLayoutManager(new GridLayoutManager(SubCategoriasActivity.this, 2));
						//recyclerview1.addItemDecoration(new SpacesItemDecoration(getDip(6)));
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
				Subcategory.addListenerForSingleValueEvent(new ValueEventListener() {
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
						recyclerview1.setAdapter(new Recyclerview1Adapter(list));
						recyclerview1.setLayoutManager(new GridLayoutManager(SubCategoriasActivity.this, 2));
						//recyclerview1.addItemDecoration(new SpacesItemDecoration(10));
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
		Subcategory.addChildEventListener(_Subcategory_child_listener);
	}
	
	private void initializeLogic() {
		types.add("Question");
		types.add("Article");
		if (getIntent().getStringExtra("option").equals("edit")) {
			_fab.setVisibility(View.VISIBLE);
		}
		else {
			_fab.setVisibility(View.GONE);
		}
		Subcategory.removeEventListener(_Subcategory_child_listener);
		ref = "categories/".concat(getIntent().getStringExtra("key")).concat("/sub");
		Subcategory =
		_firebase.getReference(ref);
		
		Subcategory.addChildEventListener(_Subcategory_child_listener);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _extra () {
	} public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
		
		    private int halfSpace;
		
		    public SpacesItemDecoration(int space) {
			        this.halfSpace = space / 2;
			    }
		
		    @Override
		    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
			
			        if (parent.getPaddingLeft() != halfSpace) {
				            parent.setPadding(halfSpace, halfSpace, halfSpace, halfSpace);
				            parent.setClipToPadding(false);
				        }
			
			        outRect.top = halfSpace;
			        outRect.bottom = halfSpace;
			        outRect.left = halfSpace;
			        outRect.right = halfSpace;
			    }
		
	}
	
	
	public HashMap<String, Object> _get (final double _position, final String _key, final ArrayList<HashMap<String, Object>> _list) {
		return (HashMap<String, Object>) _list.get((int)_position).get(_key);
	}
	
	
	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _inflater.inflate(R.layout.category, null);
RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final androidx.cardview.widget.CardView cardview1 = (androidx.cardview.widget.CardView) _view.findViewById(R.id.cardview1);
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			final TextView textview1 = (TextView) _view.findViewById(R.id.textview1);
			
			Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("img").toString())).into(imageview1);
			textview1.setText(_data.get((int)_position).get("name").toString());
			cardview1.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View _view) {
					if (getIntent().getStringExtra("option").equals("edit")) {
						dialog_d.setTitle("Edit Category");
						final EditText dialog_edittext1 = new EditText(SubCategoriasActivity.this);
						dialog_edittext1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
						dialog_edittext1.setHint("Name");
						dialog_edittext1.setText(_data.get((int)_position).get("name").toString());
						
						final EditText dialog_edittext2 = new EditText(SubCategoriasActivity.this);
						dialog_edittext2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
						dialog_edittext2.setHint("Link");
						dialog_edittext2.setText(_data.get((int)_position).get("img").toString());
						
						final Spinner dialog_spinner = new Spinner(SubCategoriasActivity.this);
						dialog_spinner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
						dialog_spinner.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, types));
						((ArrayAdapter)dialog_spinner.getAdapter()).notifyDataSetChanged();
							
						View inflated_view = getLayoutInflater().inflate(R.layout.dialog, null);
						
						LinearLayout dialog_linear1 = inflated_view.findViewById(R.id.linear2);
						LinearLayout dialog_linear2 = inflated_view.findViewById(R.id.linear3);
						LinearLayout dialog_linear3 = inflated_view.findViewById(R.id.linear4);
						dialog_linear3.addView(dialog_spinner);
						dialog_linear1.addView(dialog_edittext1);
						dialog_linear2.addView(dialog_edittext2);
						dialog_d.setView(inflated_view);
						dialog_d.setPositiveButton("Add", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								label = dialog_edittext1.getText().toString();
								value = dialog_edittext2.getText().toString();
								if (dialog_spinner.getSelectedItemPosition() == 0) {
										type = "question";
								}
								else {
										type = "article";
								}
								
								map = new HashMap<>();
								map.put("name", label);
								map.put("img", value);
								map.put("type", type);
								Subcategory.child(label).updateChildren(map);
							}
						});
						dialog_d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								
							}
						});
						dialog_d.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								Subcategory.child(_data.get((int)_position).get("name").toString()).removeValue();
							}
						});
						dialog_d.create().show();
					}
					else {
						i.setClass(getApplicationContext(), RoutinesActivity.class);
						i.putExtra("key", _data.get((int)_position).get("name").toString());
						startActivity(i);
						finish();
					}
					return true;
				}
			});
			cardview1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					if (getIntent().getStringExtra("option").equals("edit")) {
						i.setClass(getApplicationContext(), SubcategoryActivity.class);
						i.putExtra("key", new Gson().toJson(_get(_position, "sub", _data)));
						i.putExtra("path", ref.concat("/".concat(_data.get((int)_position).get("name").toString())));
						startActivity(i);
						finish();
					}
					else {
						i.setClass(getApplicationContext(), RoutinesActivity.class);
						i.putExtra("key", _data.get((int)_position).get("name").toString());
						startActivity(i);
						finish();
					}
				}
			});
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder{
			public ViewHolder(View v){
				super(v);
			}
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
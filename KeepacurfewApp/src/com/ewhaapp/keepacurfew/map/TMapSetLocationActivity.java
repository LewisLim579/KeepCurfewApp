package com.ewhaapp.keepacurfew.map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ewhaapp.keepacurfew.R;
import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapData.FindAllPOIListenerCallback;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapView;
import com.skp.Tmap.TMapView.OnLongClickListenerCallback;

public class TMapSetLocationActivity extends ActionBarActivity {
	TMapView mapView;
	LocationManager mLM;
	ArrayAdapter<POIItem> mAdapter;
	TextView tvAddress;
	TextView tvPhone;
	String myhome;
	double myhomelong;
	double myhomelat;
	EditText keywordView;
	boolean startPositionMode = false;
	private static final String API_KEY = "458a10f5-c07e-34b5-b2bd-4a891e024c2a";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tmap_set_location);
		startPositionMode = getIntent().getBooleanExtra("startmode", false);

		keywordView = (EditText) findViewById(R.id.editText_forDestination);
		tvAddress = (TextView) findViewById(R.id.textView_address);
		tvPhone = (TextView) findViewById(R.id.textView_phone);
		mAdapter = new ArrayAdapter<POIItem>(this,
				android.R.layout.simple_list_item_1);
		mapView = (TMapView) findViewById(R.id.mapView);
		mLM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		new Thread(new Runnable() {

			@Override
			public void run() {
				mapView.setSKPMapApiKey(API_KEY);
				mapView.setLanguage(TMapView.LANGUAGE_KOREAN);
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						setupMap();
					}
				});
			}
		}).start();

		mapView.setOnLongClickListenerCallback(new OnLongClickListenerCallback() {

			@Override
			public void onLongPressEvent(ArrayList<TMapMarkerItem> arg0,
					ArrayList<TMapPOIItem> arg1, TMapPoint arg2) {
				// TODO Auto-generated method stub
				// Toast.makeText(TMapSetLocationActivity.this,
				// arg2.toString()+"",0).show();
				Geocoder d = new Geocoder(TMapSetLocationActivity.this);

				try {
					final List<Address> tmp = d.getFromLocation(
							arg2.getLatitude(), arg2.getLongitude(), 1);
					myhome = tmp.get(0).getAddressLine(0);
					myhomelat = arg2.getLatitude();
					myhomelong = arg2.getLongitude();
					tvAddress.setText(myhome);
					// Toast.makeText(TMapSetLocationActivity.this,
					// tmp.get(0).getAddressLine(0), 0).show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		Button btn = (Button) findViewById(R.id.button_search);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					TMapData data = new TMapData();
					data.findAllPOI(keyword, new FindAllPOIListenerCallback() {

						@Override
						public void onFindAllPOI(
								final ArrayList<TMapPOIItem> poilist) {
							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									mAdapter.clear();
									for (TMapPOIItem poi : poilist) {
										POIItem pi = new POIItem();

										pi.poi = poi;
										mAdapter.add(pi);

										// TMapPoint point =
										// mapView.getCenterPoint();
										TMapPoint pp = poi.getPOIPoint();
										POIItem item = (POIItem) mAdapter
												.getItem(0);

										mapView.setCenterPoint(item.poi
												.getPOIPoint().getLongitude(),
												item.poi.getPOIPoint()
														.getLatitude());

									}
								}
							});
						}
					});
				}
			}
		});

		btn = (Button) findViewById(R.id.button_save);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent.putExtra("myhome", myhome);
				intent.putExtra("myhomelong", myhomelong);
				intent.putExtra("myhomelat", myhomelat);
				setResult(Activity.RESULT_OK, intent);
				finish();

			}
		});

	}

	boolean isInitialized = false;

	private void setupMap() {
		isInitialized = true;
		mapView.setMapType(TMapView.MAPTYPE_STANDARD);

	}

	int count = 0;
	LocationListener mListener = new LocationListener() {

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onLocationChanged(Location location) {
			if (isInitialized) {
				moveMap(location);
				setMyLocation(location);
			} else {
				cacheLocation = location;
			}
			mLM.removeUpdates(this);
		}
	};

	private void moveMap(Location location) {
		mapView.setCenterPoint(location.getLongitude(), location.getLatitude());
	}

	private void setMyLocation(Location location) {
		mapView.setLocationPoint(location.getLongitude(),
				location.getLatitude());

		// Bitmap icon = ((BitmapDrawable) getResources().getDrawable(
		// R.drawable.mymarker)).getBitmap();
		// mapView.setIcon(icon);
		// mapView.setIconVisibility(true); //지금내위치알려줄 마커시사용
	}

	Location cacheLocation = null;
	String mProvider;

	@Override
	protected void onStart() {
		super.onStart();
		mProvider = LocationManager.NETWORK_PROVIDER;
		Location location = mLM.getLastKnownLocation(mProvider);
		if (location != null) {
			mListener.onLocationChanged(location);
		}
		mLM.requestLocationUpdates(mProvider, 0, 0, mListener);
	}

	@Override
	protected void onStop() {
		super.onStop();
		mLM.removeUpdates(mListener);
	}
	//
	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.tmap_set_location, menu);
	// return true;
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// // Handle action bar item clicks here. The action bar will
	// // automatically handle clicks on the Home/Up button, so long
	// // as you specify a parent activity in AndroidManifest.xml.
	// int id = item.getItemId();
	// if (id == R.id.action_settings) {
	// return true;
	// }
	// return super.onOptionsItemSelected(item);
	// }
}

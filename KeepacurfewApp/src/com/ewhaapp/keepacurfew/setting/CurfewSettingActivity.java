package com.ewhaapp.keepacurfew.setting;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ewhaapp.keepacurfew.BasicActivity;
import com.ewhaapp.keepacurfew.MyUserManager;
import com.ewhaapp.keepacurfew.PropertyManager;
import com.ewhaapp.keepacurfew.R;
import com.ewhaapp.keepacurfew.map.TMapSetLocationActivity;

public class CurfewSettingActivity extends ActionBarActivity implements
		OnClickListener {

	TextView tv_startTime;
	TextView tv_acurfewTime;
	TextView tv_nowLocation;
	TextView tv_homeLocation;
	TextView tv_way;
	TextView tv_distance;
	ImageButton imgbuttonGetLocation;
	ImageButton imgbuttonCurfewTime;
	ImageButton imgbuttonGetPath;
	String nowLocation;
	Date curfewTime;
	double mynowLongitude;
	double mynowLatitude;
	final static int REQUESTCODE_FIXUSERNOWLOCATION = 103;
	final static int REQUESTCODE_FIXCURFEWTIME = 104;
	private int myHour, myMinute;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_curfew_setting);
		tv_startTime = (TextView) findViewById(R.id.textView_starttime);
		tv_acurfewTime = (TextView) findViewById(R.id.textView_finishtime);
		tv_nowLocation = (TextView) findViewById(R.id.textView_nowlocation);
		tv_homeLocation = (TextView) findViewById(R.id.textView_myhomeaddress);
		tv_way = (TextView) findViewById(R.id.textView_howtoway);
		tv_distance = (TextView) findViewById(R.id.textView_distance);
		imgbuttonGetLocation = (ImageButton) findViewById(R.id.imageButton_getlocation);
		imgbuttonCurfewTime = (ImageButton) findViewById(R.id.imageButton_getcurfewTime);
		imgbuttonGetPath = (ImageButton) findViewById(R.id.imageButton_getPath);
		setNowTime();
		tv_homeLocation.setText(PropertyManager.getInstance().getUserAddress());
		imgbuttonCurfewTime.setOnClickListener(this);
		imgbuttonGetPath.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(
						CurfewSettingActivity.this,
						"경로세팅\n 내집"
								+ MyUserManager.getInstance().myHomeData
										.getLatitude()
								+ ","
								+ MyUserManager.getInstance().myHomeData
										.getLongitude()
								+ "\n 지금위치"
								+ MyUserManager.getInstance().nowLocationData
										.getLatitude()
								+ ","
								+ MyUserManager.getInstance().nowLocationData
										.getLongitude() + "\n지금시간"
								+ MyUserManager.getInstance().startTimeHour
								+ "\n도착시간"
								+ MyUserManager.getInstance().curfewTimeHour, 0)
						.show();
				// 여기부분에서 로케이션 경로 관련된걸 띄워서 해주면될것 같아 위에서 세팅한 정보 기반으로 ..
				// 목적지(내집)
				// MyUserManager.getInstance().myHomeData.getLatitude();
				// MyUserManager.getInstance().myHomeData.getLongitude();
				// //내 지금 위치
				// MyUserManager.getInstance().nowLocationData.getLatitude();
				// MyUserManager.getInstance().nowLocationData.getLongitude();
				// //지금시간
				// MyUserManager.getInstance().startTimeHour;
				// MyUserManager.getInstance().startTimeMin;
				// //도착해야 하는 시간
				// MyUserManager.getInstance().curfewTimeHour;
				// MyUserManager.getInstance().curfewTimeMin;

			}
		});
		imgbuttonGetLocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent getmyLocationIntent = new Intent(
						CurfewSettingActivity.this,
						TMapSetLocationActivity.class);
				startActivityForResult(getmyLocationIntent,
						REQUESTCODE_FIXUSERNOWLOCATION);
			}
		});

		Button btn = (Button) findViewById(R.id.button_save);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent gobasicIntent = new Intent(CurfewSettingActivity.this,
						BasicActivity.class);
				gobasicIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(gobasicIntent);
			}
		});

	}

	private void setNowTime() {
		// TODO Auto-generated method stub
		long now;

		Date date;

		now = System.currentTimeMillis();
		date = new Date(now);
		SimpleDateFormat sdfnow = new SimpleDateFormat("HH:mm");
		String strnow = sdfnow.format(date);
		MyUserManager.getInstance().startTimeHour = date.getHours();
		MyUserManager.getInstance().startTimeMin = date.getMinutes();
		tv_startTime.setText(strnow);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		if (requestCode == REQUESTCODE_FIXUSERNOWLOCATION
				&& resultCode == Activity.RESULT_OK) {
			nowLocation = data.getStringExtra("myhome");
			mynowLatitude = data.getDoubleExtra("myhomelat", 0);
			mynowLongitude = data.getDoubleExtra("myhomelong", 0);

			MyUserManager.getInstance().nowLocationData.setAddress(nowLocation);
			MyUserManager.getInstance().nowLocationData
					.setLatitude(mynowLatitude);
			MyUserManager.getInstance().nowLocationData
					.setLongitude(mynowLongitude);

			tv_nowLocation.setText(nowLocation);
		} else if (requestCode == REQUESTCODE_FIXCURFEWTIME
				&& resultCode == Activity.RESULT_OK) {
			// nowLocation = data.getStringExtra("myhome");
			tv_acurfewTime.setText(nowLocation);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private TimePickerDialog.OnTimeSetListener myTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			String time = "Hour: " + String.valueOf(hourOfDay) + "\n"
					+ "Minute: " + String.valueOf(minute);
			MyUserManager.getInstance().curfewTimeHour = hourOfDay;
			MyUserManager.getInstance().curfewTimeMin = minute;
			SimpleDateFormat sdfnow = new SimpleDateFormat("HH:mm");
			String strnow = sdfnow.format(curfewTime);
			tv_acurfewTime.setText(hourOfDay + ":" + minute);
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		final Calendar c = Calendar.getInstance();
		if (v.getId() == R.id.imageButton_getcurfewTime) {
			myHour = c.get(Calendar.HOUR_OF_DAY);
			myMinute = c.get(Calendar.MINUTE);
			curfewTime = new Date();
			curfewTime = c.getTime();
			Dialog dlgTime = new TimePickerDialog(this, myTimeSetListener,
					myHour, myMinute, false);
			dlgTime.show();
		}
	}

}

package com.ewhaapp.keepacurfew.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ewhaapp.keepacurfew.MyUserManager;
import com.ewhaapp.keepacurfew.PropertyManager;
import com.ewhaapp.keepacurfew.R;
import com.ewhaapp.keepacurfew.map.TMapSetLocationActivity;

public class SettingActivity extends ActionBarActivity {
	Button button_save;
	LocationManager mLM;
	ImageButton imgbuttonGetLocation;
	ImageButton imgbuttonGetPhoneNumber;
	TextView tv_address;
	TextView tv_forsms;
	String people_Name;
	String people_Number;
	String messageToAuto;
	String myHome;
	EditText et_message;
	double myhomeLongitude;
	double myhomeLatitude;
	final static int REQUESTCODE_FIXUSERHOME = 101;
	final static int REQUESTCODE_FIXOTHERONEFORSMS = 102;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		et_message = (EditText) findViewById(R.id.editText_requestmessage);
		tv_address = (TextView) findViewById(R.id.textView_homeaddress);
		tv_forsms = (TextView) findViewById(R.id.textView_phone);
		mLM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		imgbuttonGetPhoneNumber = (ImageButton) findViewById(R.id.imageButton_getphone);
		imgbuttonGetPhoneNumber.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent contact_picker = new Intent(Intent.ACTION_PICK);
				contact_picker
						.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
				startActivityForResult(contact_picker,
						REQUESTCODE_FIXOTHERONEFORSMS);
			}
		});
		imgbuttonGetLocation = (ImageButton) findViewById(R.id.imageButton_getlocation);
		imgbuttonGetLocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent getmyLocationIntent = new Intent(SettingActivity.this,
						TMapSetLocationActivity.class);
				startActivityForResult(getmyLocationIntent,
						REQUESTCODE_FIXUSERHOME);
			}
		});

		button_save = (Button) findViewById(R.id.button_save);

		button_save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String message = et_message.getText().toString();
				PropertyManager.getInstance().setUserMessageContent(message);

				PropertyManager.getInstance().setUserToSms(people_Number);
				PropertyManager.getInstance().setUserAddress(myHome);
				MyUserManager.getInstance().myHomeData.setAddress(myHome);
				MyUserManager.getInstance().myHomeData
						.setLatitude(myhomeLatitude);
				MyUserManager.getInstance().myHomeData
						.setLongitude(myhomeLongitude);

				Toast.makeText(SettingActivity.this, "저장 되었습니다", 0).show();
				Intent next = new Intent(SettingActivity.this,
						CurfewSettingActivity.class);
				next.putExtra("startmode", true);

				startActivity(next);

			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		if (requestCode == REQUESTCODE_FIXUSERHOME
				&& resultCode == Activity.RESULT_OK) {
			myHome = data.getStringExtra("myhome");
			myhomeLatitude = data.getDoubleExtra("myhomelat", 0);
			myhomeLongitude = data.getDoubleExtra("myhomelong", 0);
			tv_address.setText(myHome);
		} else if (requestCode == REQUESTCODE_FIXOTHERONEFORSMS
				&& resultCode == RESULT_OK) {
			Cursor cursor = getContentResolver()
					.query(data.getData(),
							new String[] {
									ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
									ContactsContract.CommonDataKinds.Phone.NUMBER },
							null, null, null);
			cursor.moveToFirst();
			people_Name = cursor.getString(0); // 0은 이름을 얻어옵니다.
			people_Number = cursor.getString(1); // 1은 번호를 받아옵니다.
			cursor.close();

			tv_forsms.setText(people_Name + " " + people_Number);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}

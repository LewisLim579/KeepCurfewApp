package com.ewhaapp.keepacurfew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.ewhaapp.keepacurfew.setting.CurfewSettingActivity;
import com.ewhaapp.keepacurfew.setting.SettingActivity;

public class BasicActivity extends ActionBarActivity {
	ImageButton btnBasicSetting;
	ImageButton btnCurfewSetting;
	ImageButton btnGearSetting;
	ImageButton btnCheckPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basic);
		btnBasicSetting = (ImageButton) findViewById(R.id.imageButton_basicsetting);
		btnCurfewSetting = (ImageButton) findViewById(R.id.imageButton_curfewsetting);
		btnBasicSetting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(BasicActivity.this,
						SettingActivity.class);
				startActivity(intent);
			}
		});
		btnCurfewSetting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.basic, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

package com.example.password;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends Activity {

	EditText password;
	Button watchpassword;
	ImageView clearpassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		password = (EditText) findViewById(R.id.login_password);
		watchpassword = (Button) findViewById(R.id.watchpassword);
		clearpassword = (ImageView) findViewById(R.id.clear);
		
		clearpassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				password.setText("");
			}
		});

		watchpassword.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int e = event.getAction();
				if (e == MotionEvent.ACTION_DOWN) {
					password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				}
				if (e == MotionEvent.ACTION_UP) {
					password.setTransformationMethod(PasswordTransformationMethod.getInstance());
				}
				return false;
			}
		});

		password.addTextChangedListener(new TextWatcher() {

			@SuppressLint("NewApi")
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

				if (!TextUtils.isEmpty(s)) {
					if (watchpassword.getVisibility() == View.GONE) {
						PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 0, 1f);
						PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 0, 1f);
						ObjectAnimator.ofPropertyValuesHolder(watchpassword, pvhY, pvhZ).setDuration(1000).start();
						
						
						ObjectAnimator.ofFloat(clearpassword, "rotation", 0.0F, 360.0F).setDuration(500).start();
					}
					watchpassword.setVisibility(View.VISIBLE);
					clearpassword.setVisibility(View.VISIBLE);
				} else {
					if (watchpassword.getVisibility() == View.VISIBLE) {
						PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f, 0);
						PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f, 0);
						ObjectAnimator.ofPropertyValuesHolder(watchpassword, pvhY, pvhZ).setDuration(1000).start();
						new Handler().postDelayed(new Runnable() {

							@Override
							public void run() {
								watchpassword.setVisibility(View.GONE);
							}
						}, 1000);
						
					}
					clearpassword.setVisibility(View.INVISIBLE);
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

package com.Ichif1205.gridreflexes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;

import com.Ichif1205.gridreflexes.common.ui.FontTextView;
import com.Ichif1205.gridreflexes.numbers.NumbersActivity;
import com.Ichif1205.gridreflexes.reverse.ReverseActivity;

import java.util.concurrent.TimeUnit;

public class SplashActivity extends Activity {
	private static final String TAG = SplashActivity.class.getSimpleName();
	private CountDownTimer mTimer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_activity);
		countDown();
	}

	private void countDown() {
		mTimer = new CountDownTimer(3200, 1000) {
			final FontTextView count_timer = (FontTextView) findViewById(R.id.countDownTxt);

			public void onTick(long millisUntilFinished) {
                count_timer.setText(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)));
			}

			public void onFinish() {
				final Intent intent = getIntent();
				final String gameMode = intent.getStringExtra(Construct.EXTRA_MODE);

				// 選択されたモードによってゲーム画面を切り替える
				if (TextUtils.equals(gameMode, Construct.NUMBERS_MODE)) {
					intent.setClass(SplashActivity.this, NumbersActivity.class);
				} else {
					intent.setClass(SplashActivity.this, ReverseActivity.class);
				}

				startActivity(intent);
				finish();
			}
		}.start();
	}

	// 戻るボタンを押したときにカウントダウンストップする
	@Override
	public void onDestroy() {
		super.onDestroy();
		mTimer.cancel();
	}

    public static void startActivity(Activity activity, String mode) {
        Intent intent = new Intent(activity, SplashActivity.class);
		intent.putExtra(Construct.EXTRA_MODE, mode);
        activity.startActivity(intent);
    }
}
package com.Ichif1205.gridreflexes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.Ichif1205.gridreflexes.ad.BeadWrapper;
import com.Ichif1205.gridreflexes.utils.FontUtil;
import com.Ichif1205.gridreflexes.utils.SharedPrefHelper;

import net.app_c.cloud.sdk.AppCCloud;

import jp.beyond.sdk.Bead;

public class ResultActivity extends Activity implements OnClickListener {
    @SuppressWarnings("unused")
	private static final String TAG = ResultActivity.class.getSimpleName();

    /**
     * Bead広告
     */
    private Bead mBead = null;

    /**
     * AppC広告
     */
    private AppCCloud mAppCCloud = null;

    /**
     *
     */
    private float mScore;

    /**
     * {@link com.Ichif1205.gridreflexes.Construct#NUMBERS_MODE}
     * {@link com.Ichif1205.gridreflexes.Construct#REVERSE_MODE}
     */
	private String mGameMode;

    private SharedPrefHelper mHelper;

    private AppCCloud mAppC;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_activity);

        mHelper = SharedPrefHelper.getInstance(this);

        showBeadAd();

        // appC cloud生成
        mAppC = new AppCCloud(this).start();

        showAppCAd();

        parseIntent();

        setView();

        saveScore();
	}

    @Override
    protected void onResume() {
        super.onResume();

        mBead.showAd(this);
        mAppC.Ad.initCutin();
    }

    /**
     * Bead広告を表示
     */
    private void showBeadAd() {
        mBead = BeadWrapper.createInstance();
        mBead.requestAd(this);
    }

    /**
     * インテントをパース
     */
    private void parseIntent() {
        final Intent intent = getIntent();
        mScore = intent.getFloatExtra(Construct.EXTRA_SCORE, 0);
        mGameMode = intent.getStringExtra(Construct.EXTRA_MODE);
    }

    /**
     * Viewをセット
     */
    private void setView() {
        if (isNewScore()) {
            findViewById(R.id.new_score).setVisibility(View.VISIBLE);
            saveScore();
        }

        final Typeface txtFont = FontUtil.getTextFonts(getApplicationContext());

        final Button retryButton = (Button) findViewById(R.id.retryButton);
        retryButton.setTypeface(txtFont);
        retryButton.setOnClickListener(this);

        final Button topButton = (Button) findViewById(R.id.topButton);
        topButton.setTypeface(txtFont);
        topButton.setOnClickListener(this);

        final TextView scoreTxt = (TextView) findViewById(R.id.scoreTxt);
        scoreTxt.setText(String.valueOf(mScore));
    }

    /**
     * AppCの広告表示
     */
    private void showAppCAd() {
        mAppCCloud = new AppCCloud(this).start();
        FrameLayout appCLayout = (FrameLayout) findViewById(R.id.appc_ad);
        appCLayout.addView(mAppCCloud.Ad.loadSimpleView("green", "white"));

    }

    /**
     * @return true highScore, false lowScore
     */
    private boolean isNewScore() {
        float highScore;
        if (TextUtils.equals(mGameMode, Construct.NUMBERS_MODE)) {
            highScore = mHelper.getScoreNumbers();
        } else {
            highScore = mHelper.getScoreReverse();
        }

        if (highScore == 0) {
            return true;
        } else {
            return highScore > mScore;
        }
    }

    /**
     * スコアをDBに保存
     */
    private void saveScore() {
        if (TextUtils.equals(mGameMode, Construct.NUMBERS_MODE)) {
            mHelper.setScoreNumbers(mScore);
        } else {
            mHelper.setScoreReverse(mScore);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mBead != null) {
            mBead.endAd();
            mBead = null;
        }

        if (mAppCCloud != null) {
            mAppCCloud.finish();
            mAppCCloud = null;
        }
    }

    @Override
	public void onClick(View v) {
		if (v.getId() == R.id.retryButton) {
            SplashActivity.startActivity(ResultActivity.this, mGameMode);
		} else {
            MainActivity.startActivity(ResultActivity.this);
		}
		
		finish();
	}


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // カットイン終了タイプ(アプリが終了します)
            mAppC.Ad.callCutinFinish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 結果画面起動
     * @param activity
     * @param mode
     * @param finishTime
     */
    public static void startActivity(Activity activity, String mode, float finishTime) {
        final Intent intent = new Intent(activity, ResultActivity.class);
        intent.putExtra(Construct.EXTRA_SCORE, finishTime);
        intent.putExtra(Construct.EXTRA_MODE, mode);
        activity.startActivity(intent);

    }
}

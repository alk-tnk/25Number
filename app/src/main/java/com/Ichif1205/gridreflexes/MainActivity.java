package com.Ichif1205.gridreflexes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.Ichif1205.gridreflexes.common.ui.FontTextView;
import com.Ichif1205.gridreflexes.utils.FontUtil;
import com.Ichif1205.gridreflexes.utils.SharedPrefHelper;

import jp.maru.mrd.IconCell;
import jp.maru.mrd.IconLoader;

public class MainActivity extends Activity implements OnClickListener {

    private static final String SCORE_FORMAT = "Score: %.3f";

    private SharedPrefHelper mHelper;

    private IconLoader<Integer> mIconLoader = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

        mHelper = SharedPrefHelper.getInstance(this);

        setView();

        setAd();

	}

    private void setAd() {

        if (mIconLoader != null) {
            return;
        }

        mIconLoader = new IconLoader<Integer>("ast00675o196zs1zas1u", this);
        ((IconCell)findViewById(R.id.myCell1)).addToIconLoader(mIconLoader);// 2.
        ((IconCell)findViewById(R.id.myCell2)).addToIconLoader(mIconLoader);
        ((IconCell)findViewById(R.id.myCell3)).addToIconLoader(mIconLoader);
        ((IconCell)findViewById(R.id.myCell4)).addToIconLoader(mIconLoader);
        ((IconCell)findViewById(R.id.myCell5)).addToIconLoader(mIconLoader);
        mIconLoader.setRefreshInterval(60); // 3.

    }

    @Override
    protected void onResume() {
        super.onResume();
        mIconLoader.startLoading();

        final FontTextView numberScore = (FontTextView) findViewById(R.id.number_highScore_view);
        numberScore.setText(String.format(SCORE_FORMAT, mHelper.getScoreNumbers()));

        final FontTextView reverseScore = (FontTextView) findViewById(R.id.reverse_highScore_view);
        reverseScore.setText(String.format(SCORE_FORMAT, mHelper.getScoreReverse()));
    }

    /**
     * レイアウトの設定
     */
    private void setView() {
        final Button gridButton = (Button) findViewById(R.id.gridButton);
        gridButton.setTypeface(FontUtil.getTextFonts(getApplicationContext()));
        gridButton.setOnClickListener(this);

        final Button numbersButton = (Button) findViewById(R.id.numbersButton);
        numbersButton.setTypeface(FontUtil
                .getTextFonts(getApplicationContext()));
        numbersButton.setOnClickListener(this);

    }



    @Override
    protected void onPause() {
        if (mIconLoader != null) {
            mIconLoader.stopLoading();
        }
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.gridButton) {
                SplashActivity.startActivity(this, Construct.REVERSE_MODE);
        } else if (id == R.id.numbersButton) {
            SplashActivity.startActivity(this, Construct.NUMBERS_MODE);
        }
    }

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        activity.startActivity(intent);
    }
}
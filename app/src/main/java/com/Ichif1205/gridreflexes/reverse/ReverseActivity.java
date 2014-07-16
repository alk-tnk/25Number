package com.Ichif1205.gridreflexes.reverse;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.Ichif1205.gridreflexes.Construct;
import com.Ichif1205.gridreflexes.R;
import com.Ichif1205.gridreflexes.ResultActivity;
import com.Ichif1205.gridreflexes.utils.FontUtil;

import net.app_c.cloud.sdk.AppCCloud;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReverseActivity extends Activity implements View.OnClickListener {
    @SuppressWarnings("unused")
    private static final String TAG = ReverseActivity.class.getSimpleName();
    private static int[] TARGET_RES_ID = {R.id.target1, R.id.target2,
            R.id.target3, R.id.target4, R.id.target5, R.id.target6,
            R.id.target7, R.id.target8, R.id.target9, R.id.target10,
            R.id.target11, R.id.target12, R.id.target13, R.id.target14,
            R.id.target15, R.id.target16, R.id.target17, R.id.target18,
            R.id.target19, R.id.target20, R.id.target21, R.id.target22,
            R.id.target23, R.id.target24, R.id.target25};

    private static final int MAX_TARGET = TARGET_RES_ID.length;
    private int nextTargetNum = TARGET_RES_ID.length-1;
    private List<Integer> mTargetLists = new ArrayList<Integer>();
    private long mStartTime;
    private TextView mNextButton;


    private Handler myHandler = new Handler();
    private TextView mTextTimer;
    private float mTimer = 0;

    private AppCCloud mAppC = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.numbers_activity);

        mAppC = new AppCCloud(this).start();

        shuffleNumbers();

        for (int i = 0; i < MAX_TARGET; i++) {
            final int firstTargetRes = TARGET_RES_ID[mTargetLists.get(i)];
            final Button targetButton = (Button) findViewById(firstTargetRes);
            targetButton.setVisibility(View.VISIBLE);
            targetButton.setText(String.valueOf(i + 1));
            targetButton.setTypeface(FontUtil
                    .getNumberFonts(getApplicationContext()));
            targetButton.setOnClickListener(this);
        }

        mNextButton = (TextView) findViewById(R.id.nextNumber);
        mNextButton.setText(String.valueOf(nextTargetNum + 1));

        mTextTimer = (TextView) findViewById(R.id.time);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mAppC.Ad.initCutin();
        mStartTime = System.currentTimeMillis();
        myHandler.postDelayed(updateTimerMethod, 0);
    }

    @Override
    public void onClick(View v) {
        final int currentTargetId = TARGET_RES_ID[mTargetLists.get(nextTargetNum)];
        if (v.getId() != currentTargetId) {
            return;
        }


        switchTargetBackground(currentTargetId);

        if (nextTargetNum == 0) {
            // 終了
            // Stop Hanlder
            myHandler.removeCallbacks(updateTimerMethod);

            ResultActivity.startActivity(ReverseActivity.this, Construct.REVERSE_MODE, mTimer);
            finish();
            return;
        }

        // 次のボタンをVISIBLE
        nextTargetNum--;
        mNextButton.setText(String.valueOf(nextTargetNum + 1));
    }

    private Runnable updateTimerMethod = new Runnable() {

        public void run() {
            mTimer = (float) (System.currentTimeMillis() - mStartTime) / 1000;
            mTextTimer.setText(String.format("%.3f", mTimer));
            myHandler.postDelayed(this, 10);
        }

    };

    /**
     * targetIdで指定されたViewの背景色を変更する
     *
     * @param targetId
     */
    private void switchTargetBackground(int targetId) {
        findViewById(targetId).setBackgroundColor(
                Color.rgb(22, 160, 133));
    }

    /**
     * 順番をシャッフル
     */
    private void shuffleNumbers() {
        mTargetLists = new ArrayList<Integer>();

        for (int i = 0; i < MAX_TARGET; i++) {
            mTargetLists.add(i);
        }
        Collections.shuffle(mTargetLists);
    }

    private boolean isBack = false;

    @Override
    public void onBackPressed() {
        if (isBack) {
            super.onBackPressed();
        } else {
            isBack = true;
            mAppC.Ad.callCutin();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mAppC != null) {
            mAppC.finish();
        }
    }
}

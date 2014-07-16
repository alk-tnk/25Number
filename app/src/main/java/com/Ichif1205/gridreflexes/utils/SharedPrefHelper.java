package com.Ichif1205.gridreflexes.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by tanakatatsuya on 2014/07/14.
 */
public class SharedPrefHelper {
    private static final String PREF_NAME = SharedPrefHelper.class.getSimpleName();


    private static SharedPrefHelper sInstance;
    private final SharedPreferences mPref;


    private SharedPrefHelper(Context context) {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPrefHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SharedPrefHelper(context);
        }

        return sInstance;
    }

    public void setScoreNumbers(float score) {
        mPref.edit().putFloat(PrefKeys.KEY_SCORE_Numbers, score).commit();
    }

    public float getScoreNumbers() {
        return mPref.getFloat(PrefKeys.KEY_SCORE_Numbers, 0);
    }

    public void setScoreReverse(float score) {
        mPref.edit().putFloat(PrefKeys.KEY_SCORE_REVERSE, score).commit();
    }

    public float getScoreReverse() {
        return mPref.getFloat(PrefKeys.KEY_SCORE_REVERSE, 0);
    }

    class PrefKeys {
        static final String KEY_SCORE_REVERSE = "pref_score_reverse";
        static final String KEY_SCORE_Numbers = "pref_score_numbers";
    }

}

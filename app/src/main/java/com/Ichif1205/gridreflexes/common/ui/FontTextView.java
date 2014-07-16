package com.Ichif1205.gridreflexes.common.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.Ichif1205.gridreflexes.R;

/**
 * フォントを指定できるTextView
 *
 * <p/>
 * <p/>
 * Created by tanakatatsuya on 2014/05/04.
 */
public class FontTextView extends TextView {
    private static final String TAG = FontTextView.class.getSimpleName();

    private final Context mContext;
    private final TypedArray mType;

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        mType = context.obtainStyledAttributes(attrs, R.styleable.FontTextView);
        setFont();
    }

    /**
     * フォントを設定する
     * <p>
     *
     * </p>
     */
    private void setFont() {
        final String font = mType.getString(0);

        if (TextUtils.isEmpty(font)) {
            return;
        }

        try {
            final Typeface face = Typeface.createFromAsset(mContext.getAssets(), String.format("fonts/%s.ttf", font));
            setTypeface(face);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }
}

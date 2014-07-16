package com.Ichif1205.gridreflexes.utils;

import android.content.Context;
import android.graphics.Typeface;

public class FontUtil {
	/**
	 * 文字用のフォントを取得
	 * @param context
	 * @return
	 */
	public static Typeface getTextFonts(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Bla.ttf");
	}
	
	/**
	 * 数字用のフォントを取得
	 * @param context
	 * @return
	 */
	public static Typeface getNumberFonts(Context context) {
		return Typeface.createFromAsset(context.getAssets(), "fonts/DIN_Medium.ttf");
	}

}

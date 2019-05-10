package com.nec.baselib.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @author Elijah <a href="https://github.com/liuzhonghu">Contact me.</a>
 * @desc
 * @since 2019/5/10
 */
public class SizeUtil {
  private static float sPixelDensity = -1;

  public static void init(Context context) {
    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
    sPixelDensity = metrics.density;
  }

  public static int getFitPxFromDp(float dp) {
    return (int) (dp * sPixelDensity + 0.5);
  }

  public static float dpToPixel(float dp) {
    return sPixelDensity * dp;
  }

  public static int px2sp(Context context, float pxValue) {
    return (int) (pxValue / context.getResources().getDisplayMetrics().scaledDensity + 0.5f);
  }
}

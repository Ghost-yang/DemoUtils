package com.example.raymond.demoutils.intent;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

public class CompatScreenUtils {
    /**
     * 今日头条团队：https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA
     */

    private static  float sNoncompatDensity;

    private static  float sNoncompatScaledDensity;

    /**
     * 以宽度为基准进行适配 https://www.jianshu.com/p/1eeb0d8d1c86
     * @param activity
     * @param application
     */
    public static  void setCustomDensity(Activity activity, final Application application) {
        final DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        if(sNoncompatDensity == 0) {
            sNoncompatDensity = displayMetrics.density;
            sNoncompatScaledDensity = displayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if(newConfig != null && newConfig.fontScale >0) {
                        sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }

        final float targetDensity = displayMetrics.widthPixels / 360;
        final float targetScaleDensity = targetDensity *(sNoncompatDensity /sNoncompatScaledDensity);
        final int targetDpi = (int)(targetDensity * 160);

        displayMetrics.density = targetDensity;
        displayMetrics.scaledDensity = targetScaleDensity;
        displayMetrics.densityDpi = targetDpi;

        final DisplayMetrics activityDisplayMetric = activity.getResources().getDisplayMetrics();
        activityDisplayMetric.density = targetDensity;
        activityDisplayMetric.scaledDensity = targetScaleDensity;
        activityDisplayMetric.densityDpi = targetDpi;
    }
}

package br.com.ezeqlabs.selltimer.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.test.rule.ActivityTestRule;

public class PreparaTela{
    public static void rotaciona(Context context, ActivityTestRule<?> activityTestRule){
        int orientation = context.getResources().getConfiguration().orientation;

        Activity activity = activityTestRule.getActivity();
        activity.setRequestedOrientation(
                (orientation == Configuration.ORIENTATION_PORTRAIT) ?
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


}

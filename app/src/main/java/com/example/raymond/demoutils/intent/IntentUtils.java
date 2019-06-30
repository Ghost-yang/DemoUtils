package com.example.raymond.demoutils.intent;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Raymond on 2019/6/30.
 */

public class IntentUtils {

    public static void startAppByPackage(Context context,String pkgname) {
        PackageManager manager = context.getPackageManager();
        Intent intent = manager.getLaunchIntentForPackage(pkgname);
        if(intent == null) {
            Toast.makeText(context,"未找到指定apk",Toast.LENGTH_LONG).show();
        } else {
            context.startActivity(intent);
        }
    }

    public static String getLauncherActivityNameByPackageName(Context context, String packageName) {
        String className = null;
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);//android.intent.action.MAIN
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);//android.intent.category.LAUNCHER
        resolveIntent.setPackage(packageName);
        List<ResolveInfo> resolveinfoList = context.getPackageManager().queryIntentActivities(resolveIntent, 0);
        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            className = resolveinfo.activityInfo.name;
        }
        return className;
    }

    public static  void StartActivityNameByPackageName(Context context, String packageName) {
        String pkgclassname = getLauncherActivityNameByPackageName(context,packageName);
        if(pkgclassname != null) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName componentName = new ComponentName(packageName,pkgclassname);
            intent.setComponent(componentName);
            context.startActivity(intent);
        } else {
            Toast.makeText(context,"未找到指定apk",Toast.LENGTH_LONG).show();
        }
    }
}

package net.assemble.emailnotify;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * 設定管理
 */
public class EmailNotifyPreferences
{
    public static final String PREF_KEY_ENABLE = "enable";
    public static final boolean PREF_ENABLE_DEFAULT = true;

    public static final String PREF_KEY_NOTIFY = "notify";
    public static final boolean PREF_NOTIFY_DEFAULT = true;

    public static final String PREF_KEY_LAUNCH = "launch";
    public static final boolean PREF_LAUNCH_DEFAULT = false;

    public static final String PREF_KEY_LAUNCH_APP_PACKAGE = "launch_app_package_name";
    public static final String PREF_KEY_LAUNCH_APP_CLASS = "launch_app_class_name";

    public static boolean getEnable(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(
                EmailNotifyPreferences.PREF_KEY_ENABLE,
                EmailNotifyPreferences.PREF_ENABLE_DEFAULT);
    }

    public static void setEnable(Context ctx, boolean val) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(ctx).edit();
        e.putBoolean(EmailNotifyPreferences.PREF_KEY_ENABLE, val);
        e.commit();
    }

    public static boolean getNotify(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(
                EmailNotifyPreferences.PREF_KEY_NOTIFY,
                EmailNotifyPreferences.PREF_NOTIFY_DEFAULT);
    }

    public static boolean getLaunch(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(
                EmailNotifyPreferences.PREF_KEY_LAUNCH,
                EmailNotifyPreferences.PREF_LAUNCH_DEFAULT);
    }

    public static ComponentName getComponent(Context ctx) {
        String packageName = PreferenceManager.getDefaultSharedPreferences(ctx).getString(
                EmailNotifyPreferences.PREF_KEY_LAUNCH_APP_PACKAGE, null);
        String className = PreferenceManager.getDefaultSharedPreferences(ctx).getString(
                EmailNotifyPreferences.PREF_KEY_LAUNCH_APP_CLASS, null);
        if (packageName == null || className == null) {
            return null;
        }
        return new ComponentName(packageName, className);
    }

}
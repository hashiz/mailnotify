package net.assemble.emailnotify;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

/**
 * 設定画面
 */
public class EmailNotifyPreferencesActivity extends PreferenceActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener
{
    private Preference mPrefLaunchApp;
    private SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        mPrefLaunchApp = findPreference("launch_app");
        mPref = PreferenceManager.getDefaultSharedPreferences(this);

        updateSummary();
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
            Preference preference) {
        if (preference == mPrefLaunchApp) {
            Intent intent = new Intent().setClass(this, EmailNotifyPreferencesLaunchAppActivity.class);
            startActivityForResult(intent, 1/*TODO*/);
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1/*TODO*/) {
            if (resultCode == RESULT_OK) {
                String package_name = data.getStringExtra("package_name");
                String class_name = data.getStringExtra("class_name");

                Editor e = mPref.edit();
                e.putString(EmailNotifyPreferences.PREF_KEY_LAUNCH_APP_PACKAGE, package_name);
                e.putString(EmailNotifyPreferences.PREF_KEY_LAUNCH_APP_CLASS, class_name);
                e.commit();

                updateSummary();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void updateSummary() {
        ComponentName component = EmailNotifyPreferences.getComponent(this);
        if (component != null) {
            mPrefLaunchApp.setSummary(
                    getResources().getString(R.string.pref_launch_app_is) + "\n" +
                    component.getClassName());
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updateSummary();
    }

}

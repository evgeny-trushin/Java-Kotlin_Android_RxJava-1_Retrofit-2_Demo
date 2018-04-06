package com.example.assignment.helpers.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.assignment.helpers.AppApplication;
import com.example.assignment.helpers.CrashlyticsProxy;

/**
 * Simplify the usage of preferences
 *
 * @param <PREFERENCES> - The type of the preference
 */
public class PreferencesManager<PREFERENCES extends PreferencesManagerOps> {

    private static final String TAG = PreferencesManager.class.getSimpleName();

    private PreferencesManager() {
    }

    private volatile static PreferencesManager mInstance;

    @SuppressWarnings("unchecked")
    public static <PREFERENCES extends PreferencesManagerOps> PreferencesManager<PREFERENCES> getInstance() {
        if (null == mInstance) {
            synchronized (PreferencesManager.class) {
                if (null == mInstance) {
                    mInstance = new PreferencesManager<PREFERENCES>();
                }
            }
        }
        return mInstance;
    }

    public void setLongValue(PREFERENCES preference, Long value) {
        SharedPreferences.Editor editor = getPreferences(preference).edit();
        if (null == value) {
            editor.remove(preference.toString());
        } else {
            editor.putLong(preference.toString(), value);
        }
        preference.setValue(value);
        editor.apply();
    }

    public void setStringValue(PREFERENCES preference, String value) {
        SharedPreferences.Editor editor = getPreferences(preference).edit();
        if (null == value) {
            editor.remove(preference.toString());
        } else {
            editor.putString(preference.toString(), value);
        }
        preference.setValue(value);
        editor.apply();
    }

    public void setBooleanValue(PREFERENCES preference, Boolean value) {
        SharedPreferences.Editor editor = getPreferences(preference).edit();
        if (null == value) {
            editor.remove(preference.toString());
        } else {
            editor.putBoolean(preference.toString(), value);
        }
        preference.setValue(value);
        editor.apply();
    }

    public Long getValueAsLong(PREFERENCES cachedPreference) {
        if (null != cachedPreference.getValue()) {
            return (Long) cachedPreference.getValue();
        }
        long value = getPreferences(cachedPreference).getLong(cachedPreference.toString(), 0);
        cachedPreference.setValue(value);
        return value;
    }

    public Boolean getValueAsBoolean(PREFERENCES cachedPreference) {
        if (null != cachedPreference.getValue()) {
            return (Boolean) cachedPreference.getValue();
        }
        boolean value = getPreferences(cachedPreference).getBoolean(cachedPreference.toString(), false);
        cachedPreference.setValue(value);
        return value;
    }

    public String getValueAsString(PREFERENCES cachedPreference) {
        if (null != cachedPreference.getValue()) {
            return (String) cachedPreference.getValue();
        }
        String value = getPreferences(cachedPreference).getString(cachedPreference.toString(), null);
        cachedPreference.setValue(value);
        return value;
    }

    private SharedPreferences getPreferences(PREFERENCES preference) {
        return AppApplication.getAppContext().getSharedPreferences(
            preference.getClass().toString(), Context.MODE_PRIVATE);
    }
}

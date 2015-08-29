package io.github.jaisonoh.hackatseoul.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jaisonoh on 2015. 8. 14..
 */
public class Preference {

    private final static String pref = "beacon";

    private Context context;
    private SharedPreferences preferences;

    public Preference(Context context) {
        this.context = context;
    }

    public String getPreferenceString(String key) {
        preferences = context.getSharedPreferences(pref, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public int getPreferenceInt(String key) {
        preferences = context.getSharedPreferences(pref, Context.MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }

    public void putPreference(String key, String id) {
        preferences = context.getSharedPreferences(pref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, id);
        editor.commit();
    }

    public void putPreference(String key, int id) {
        preferences = context.getSharedPreferences(pref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, id);
        editor.commit();
    }

    public void removePreference(String key) {
        preferences = context.getSharedPreferences(pref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.commit();
    }
}

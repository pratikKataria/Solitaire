/**
 * Created by Pratik Katariya on 23-09-2020
 */
package com.stetig.solitaire.utils;

/**Created by Pratik Katariya on 23-09-2020 */
import android.content.Context;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

public class UpcommingNotificationPrefs {
    public static final String IS_RECOD = "isRecord";
    public static final String RECORED_FILE_PATH = "file_path";
    public static final String ALL_CALL_RECORDS = "all_call_records";
    private static final String PREF_APP = "upcomming_rec_prefs";

    private UpcommingNotificationPrefs() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }

    /**
     * Gets boolean data.
     *
     * @param context the context
     * @param key     the key
     * @return the boolean data
     */
    static public boolean getBooleanData(Context context, String key) {

        return context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getBoolean(key, false);
    }

    /**
     * Gets int data.
     *
     * @param context the context
     * @param key     the key
     * @return the int data
     */
    static public int getIntData(Context context, String key) {
        return context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getInt(key, 0);
    }

    /**
     * Gets string data.
     *
     * @param context the context
     * @param key     the key
     * @return the string data
     */
    // Get Data
    static public String getStringData(Context context, String key) {
        return context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getString(key, "");
    }

    /**
     * Save data.
     *
     * @param context the context
     * @param key     the key
     * @param val     the val
     */
    // Save Data
    static public void saveData(Context context, String key, String val) {
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().putString(key, val).apply();
    }

    /**
     * Save data.
     *
     * @param context the context
     * @param key     the key
     * @param val     the val
     */
    static public void saveData(Context context, String key, int val) {
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().putInt(key, val).apply();
    }

    /**
     * Save data.
     *
     * @param context the context
     * @param key     the key
     * @param val     the val
     */
    static public void saveData(Context context, String key, boolean val) {
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(key, val)
                .apply();
    }

    static public Editor getSharedPrefEditor(Context context, String pref) {
        return context.getSharedPreferences(pref, Context.MODE_PRIVATE).edit();
    }

    static public void saveData(Editor editor) {
        editor.apply();
    }

    public static HashMap<String, String> getAllData(Context context) {
        return (HashMap<String, String>) context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getAll();
    }


    static public void removeData(Context context, String key) {
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().remove(key).apply();
    }
}
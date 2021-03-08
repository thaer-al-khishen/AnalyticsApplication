package com.example.analyticsapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.analyticsapplication.BuildConfig;

public class Preferences {
    private static final String PREF_USER_ID = "pref_user_id";
    private static final String PREF_FIRST_NAME = "pref_first_name";
    private static final String PREF_LAST_NAME = "pref_last_name";
    private static final String PREF_SELECTED_LANGUAGE = "pref_selected_language";
    private static final String PREF_NETWORK_LANGUAGE = "pref_network_language";
    private static Preferences instance = null;
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    private static Context context;

    private static final String TOKEN = "com.DatabaseUtils.TOKEN";
    public static final String PUBLIC_TOKEN = "LoFWPO93nTKSt";



    public Preferences() {
    }

    public static Preferences getInstance(Context mContext){
        if (instance == null) {
            context = mContext;
            instance = new Preferences();
            preferences = mContext.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
            editor = preferences.edit();
        }
        return instance;
    }

    public int getUserId(){
        return preferences.getInt(PREF_USER_ID, 0);
    }

    public String getUserFullName(){
        return String.format("%s %s", preferences.getString(PREF_FIRST_NAME,""),
                preferences.getString(PREF_LAST_NAME, ""));
    }

    public String getToken  ( final Context context ) {
        SharedPreferences language = context.getSharedPreferences ( TOKEN , Context.MODE_PRIVATE );
        return language.getString ( TOKEN , PUBLIC_TOKEN );
    }

    public void setToken ( final Context context , final String reg ) {
        SharedPreferences sharedPref = context.getSharedPreferences ( TOKEN , Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPref.edit ();
        editor.putString ( TOKEN , reg );
        editor.apply ();
    }

    public String getAppSelectedLanguage(String defaultLanguage){
        return preferences.getString(PREF_SELECTED_LANGUAGE, defaultLanguage);
    }

    public void setAppSelectedLangauge(String language){
        editor.putString(PREF_SELECTED_LANGUAGE, language).apply();
    }

    public void setNetworkAppLanguage(String language){
        editor.putString(PREF_NETWORK_LANGUAGE, language).apply();
    }

    public String getNetworkAppLanguage(String defLang){
        return preferences.getString(PREF_NETWORK_LANGUAGE, defLang);
    }
}

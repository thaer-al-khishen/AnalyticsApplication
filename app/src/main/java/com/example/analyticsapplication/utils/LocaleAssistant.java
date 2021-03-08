package com.example.analyticsapplication.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;

import com.example.analyticsapplication.R;

import java.util.Locale;

public class LocaleAssistant {
    public static final String ENGLISH = "english";
    public static final String ARABIC = "arabic";


    public static Context onAttach(Context context) {
        String lang = getPersistedData(context, Locale.getDefault().getLanguage());
        String networkLang = getPersistNetworkLang(context, Locale.getDefault().getLanguage());
        return setLocale(context, lang, networkLang);
    }

    public static Context onAttach(Context context, String defaultLang) {
        String lang = getPersistedData(context, defaultLang);
        String networkLang = getPersistNetworkLang(context, defaultLang);
        return setLocale(context, lang, networkLang);
    }

    public static Context setLocale(Context context, String language, String networkLang) {
        persist(context, language);
        persistNetworkLang(context, networkLang);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, language);
        }

        return updateResourcesLegacy(context, language);
    }

    public static String getPersistedData(Context context, String defaultLanguage) {
        Preferences preferences = Preferences.getInstance(context);
        return preferences.getAppSelectedLanguage(defaultLanguage);
    }

    private static void persist(Context context, String language) {
        Preferences preferences = Preferences.getInstance(context);
        preferences.setAppSelectedLangauge(language);
    }

    private static void persistNetworkLang(Context context, String lang){
        Preferences preferences = Preferences.getInstance(context);
        preferences.setNetworkAppLanguage(lang);
    }

    public static String getPersistNetworkLang(Context context, String lang){
        Preferences preferences = Preferences.getInstance(context);
        return preferences.getNetworkAppLanguage(lang);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);
        return context.createConfigurationContext(configuration);
    }

    @SuppressWarnings("deprecation")
    private static Context updateResourcesLegacy(Context context, String language){
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        return context;
    }

    public static String getPhoneLocale(){
        return Locale.getDefault().getLanguage();
    }
}

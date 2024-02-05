package com.roydon.niuyin.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.roydon.niuyin.other.CommonConstants;

public class TokenManager {

    private static final String PREF_NAME = "TokenPrefs";
    private static final String KEY_TOKEN = "token";

    public static void saveToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }

    public static String getToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sp.getString(KEY_TOKEN, null);
    }

    public boolean hasToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sp.contains(KEY_TOKEN);
    }

    public static void clearToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(KEY_TOKEN);
        editor.commit();
    }
}

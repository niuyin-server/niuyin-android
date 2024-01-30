package com.roydon.niuyin.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.roydon.niuyin.other.CommonConstants;

public class TokenManager {

    private static final String PREF_NAME = "TokenPrefs";
    private static final String KEY_TOKEN = "token";

    private SharedPreferences sharedPreferences;

    public TokenManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    public boolean hasToken() {
        return sharedPreferences.contains(KEY_TOKEN);
    }

    public void clearToken() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_TOKEN);
        editor.apply();
    }
}

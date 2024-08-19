package com.roydon.niuyin.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.roydon.niuyin.other.CommonConstants;

public class TokenManager {

    private Context context;

    private TokenManager(Context context) {
        this.context = context;
    }

    private volatile static TokenManager instance;

    public static synchronized TokenManager getInstance(Context context) {
        if (instance == null) {
            synchronized (TokenManager.class) {
                if (instance == null) {
                    instance = new TokenManager(context);
                }
            }
        }
        return instance;
    }

    private static final String PREF_NAME = "TokenPrefs";
    private static final String KEY_TOKEN = "token";

    public void saveToken(String token) {
        SharedPreferences sp = this.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }

    public String getToken() {
        SharedPreferences sp = this.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sp.getString(KEY_TOKEN, null);
    }

    public boolean hasToken() {
        SharedPreferences sp = this.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sp.contains(KEY_TOKEN);
    }

    public void clearToken() {
        SharedPreferences sp = this.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(KEY_TOKEN);
        editor.commit();
    }
}

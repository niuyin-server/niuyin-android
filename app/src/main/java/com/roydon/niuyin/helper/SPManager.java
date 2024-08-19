package com.roydon.niuyin.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author roydon
 * @date 2024/4/23 0:30
 * @description niuyin-android
 */
public class SPManager {

    public static final String USERID = "userId";
    public static final String USERNAME = "userName";
    public static final String AVATAR = "avatar";
    public static final String BACK_IMAGE = "backImage";

    private static final String NAME = "config";

    private Context context;

    private SPManager(Context context) {
        this.context = context;
    }

    private volatile static SPManager instance;

    public static synchronized SPManager getInstance(Context context) {
        if (instance == null) {
            synchronized (SPManager.class) {
                if (instance == null) {
                    instance = new SPManager(context);
                }
            }
        }
        return instance;
    }

    public boolean hasString(String key) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    public void putString(String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    public String getString(String key, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    public void clear() {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }
}

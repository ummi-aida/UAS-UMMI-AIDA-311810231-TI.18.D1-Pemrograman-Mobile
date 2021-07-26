package com.example.uas.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {

    static final String KEY_USER_EMAIL ="user_email", KEY_USER_ID ="user_id", KEY_USER_NAME="user_name";

    private static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setRegisteredUser(Context context, String username){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_USER_EMAIL, username);
        editor.apply();
    }

    public static String getRegisteredUser(Context context){
        return getSharedPreference(context).getString(KEY_USER_EMAIL,"");
    }

    public static void setRegisteredId(Context context, String password){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_USER_ID, password);
        editor.apply();
    }

    public static String getRegisteredId(Context context){
        return getSharedPreference(context).getString(KEY_USER_ID,"");
    }

    public static void setRegisteredNameUser(Context context, String nameUser) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_USER_NAME, nameUser);
        editor.apply();
    }

    public static String getRegisteredUserName(Context context){
        return getSharedPreference(context).getString(KEY_USER_NAME,"");
    }

    public static void clearLoggedInUser (Context context){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove(KEY_USER_EMAIL);
        editor.remove(KEY_USER_ID);
        editor.apply();
    }

}

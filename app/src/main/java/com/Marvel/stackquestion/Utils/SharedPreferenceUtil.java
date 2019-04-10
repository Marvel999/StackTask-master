package com.Marvel.stackquestion.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {

    private final String FIRSTTIMELOGIN = "FIRSTTIMELOGIN";
    private final String ACCESS_TOKEN = "ACCESS_TOKEN";

    private SharedPreferences sharedPreferences;

    public SharedPreferenceUtil(Context context) {
        final String SHARED_NAME = "STACKQUESTION";
        sharedPreferences = context.getSharedPreferences(SHARED_NAME, 0);

    }

    public void setFirstTimeLogin(boolean isFirstTime) {
        sharedPreferences.edit().putBoolean(FIRSTTIMELOGIN, isFirstTime).apply();
    }

    public boolean getFirstTimeLogin() {
        return sharedPreferences.getBoolean(FIRSTTIMELOGIN, false);
    }

    public void setAcessToken(String token) {

        sharedPreferences.edit().putString(ACCESS_TOKEN, token).apply();
    }

    public String getAccessToken() {
        return sharedPreferences.getString(ACCESS_TOKEN, "");
    }
}

package com.example.simpledatasharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreferences {
    private static final String PREF_NAME = "user_pref";

    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String AGE = "age";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String LOVE_MU = "love_mu";

    private final SharedPreferences sharedPreferences;

    UserPreferences(Context context){
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setUser(UserModel user){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NAME, user.name);
        editor.putString(EMAIL, user.email);
        editor.putInt(AGE, user.age);
        editor.putString(PHONE_NUMBER, user.phoneNumber);
        editor.putBoolean(LOVE_MU, user.isLove);
        editor.apply();
    }

    UserModel getUser(){
        UserModel userModel = new UserModel();
        userModel.setName(sharedPreferences.getString(NAME, ""));
        userModel.setEmail(sharedPreferences.getString(EMAIL, ""));
        userModel.setAge(sharedPreferences.getInt(AGE, 0));
        userModel.setPhoneNumber(sharedPreferences.getString(PHONE_NUMBER, ""));
        userModel.setLove(sharedPreferences.getBoolean(LOVE_MU, false));

        return userModel;
    }
}

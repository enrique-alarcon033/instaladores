package com.yonusa.instaladores_plus.utilities.helpers;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.Nullable;

import com.yonusa.instaladores_plus.ui.login.models.LogIn_response;
import com.yonusa.instaladores_plus.utilities.catalogs.SP_Dictionary;

import java.util.Set;

public class SP_Helper {

    public SP_Helper(){

    }
    public Boolean fillUserInfo(Context context, LogIn_response logInResponse){

        Boolean complete;

        try {

            String preferenceContainer = SP_Dictionary.USER_INFO;
            String userName = SP_Dictionary.USER_NAME;
            String userLastName = SP_Dictionary.USER_LASTNAME;
            String userEmail = SP_Dictionary.USER_EMAIL;
            String userId = SP_Dictionary.USER_ID;
            String mqttHost = SP_Dictionary.MQTT_SERVER;
            String mqttPort = SP_Dictionary.MQTT_PORT;
            String mqttUser = SP_Dictionary.MQTT_USER;
            String mqttPass = SP_Dictionary.MQTT_PASS;

            SharedPreferences.Editor editor = context.getSharedPreferences(preferenceContainer, MODE_PRIVATE).edit();

            editor.putString(userName, logInResponse.getNombre());
            editor.putString(userLastName, logInResponse.getApellido());
            editor.putString(userEmail, logInResponse.getEmail());
            editor.putString(userId, logInResponse.getUsuarioId());
            editor.putString(mqttHost, logInResponse.getMQTTSERVER());
            editor.putString(mqttPort, logInResponse.getMQTTPORT());
            editor.putString(mqttUser, logInResponse.getMQTTUSER());
            editor.putString(mqttPass, logInResponse.getMQTTPWD());

            editor.apply();
            complete = true;

        } catch (Exception e) {
            complete = false;
        }

        return complete;
    }


    public Boolean cleanUserInfo(Context context){

        Boolean complete;

        try {

            SharedPreferences.Editor editor = new SharedPreferences.Editor() {
                @Override
                public SharedPreferences.Editor putString(String s, @Nullable String s1) {
                    return null;
                }

                @Override
                public SharedPreferences.Editor putStringSet(String s, @Nullable Set<String> set) {
                    return null;
                }

                @Override
                public SharedPreferences.Editor putInt(String s, int i) {
                    return null;
                }

                @Override
                public SharedPreferences.Editor putLong(String s, long l) {
                    return null;
                }

                @Override
                public SharedPreferences.Editor putFloat(String s, float v) {
                    return null;
                }

                @Override
                public SharedPreferences.Editor putBoolean(String s, boolean b) {
                    return null;
                }

                @Override
                public SharedPreferences.Editor remove(String s) {
                    return null;
                }

                @Override
                public SharedPreferences.Editor clear() {
                    return null;
                }

                @Override
                public boolean commit() {
                    return false;
                }

                @Override
                public void apply() {

                }
            };

            editor.clear();
            editor.commit();
            editor.apply();
            Log.i("SP_Helper","Clear user info");
            complete = true;
        } catch (Exception e) {
            complete = false;
        }

        return complete;
    }

}

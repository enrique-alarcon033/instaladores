package com.yonusa.instaladores_plus.mqtt;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.yonusa.instaladores_plus.utilities.catalogs.Mqtt_CMD;
import com.yonusa.instaladores_plus.utilities.catalogs.SP_Dictionary;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Publisher {

    String clientId = MqttClient.generateClientId();
    MqttAndroidClient client;
    SharedPreferences prefs;

    public void SendMessage(Context context, final String msn, String mac){
        prefs = context.getSharedPreferences(SP_Dictionary.USER_INFO,Context.MODE_PRIVATE);
        String mqttHost = prefs.getString(SP_Dictionary.MQTT_SERVER, "No server defined");
        String mqttPort = prefs.getString(SP_Dictionary.MQTT_PORT, "No port defined");
        String mqttPasswrd = prefs.getString(SP_Dictionary.MQTT_PASS, "No password defined");
        String mqttUser = prefs.getString(SP_Dictionary.MQTT_USER, "No user defined");
        try {
            client = new MqttAndroidClient(context, "tcp://"+mqttHost+":"+mqttPort, clientId);
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setCleanSession(true);
            mqttConnectOptions.setPassword(mqttPasswrd.toCharArray());
            mqttConnectOptions.setUserName(mqttUser);
            client.connect(mqttConnectOptions).setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    try {
                        MqttMessage message = new MqttMessage(msn.getBytes());
                        Log.e("MQTT", message.toString());
                        message.setQos(1);
                        client.publish(Mqtt_CMD.GENERAL_TOPIC.concat(mac), message);
                        client.disconnect();
                    } catch (Exception ex) {
                        Log.e("MQTT", ex.getMessage());
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    exception.printStackTrace();
                }
            });
        } catch (MqttException e) {
            Log.e("Mqtt", e.getMessage());
        }
    }

}

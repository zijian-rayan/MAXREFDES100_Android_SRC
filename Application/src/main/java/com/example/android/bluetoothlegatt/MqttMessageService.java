package com.example.android.bluetoothlegatt;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttMessageService extends Service {

    private static final String TAG = "MqttMessageService";
    private MqttClient mqttClient;
    private MqttAndroidClient mqttAndroidClient;

    @Override
    public void onCreate() {
        super.onCreate();
        mqttClient = new MqttClient();
        mqttAndroidClient = mqttClient.getMqttClient(getApplicationContext(), Constants.MQTT_BROKER_URL, Constants.CLIENT_ID);

        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
            }

            @Override
            public void connectionLost(Throwable throwable) {
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                //Log.d("INFO:","messageArrived");
                String str = new String(mqttMessage.getPayload());
                System.out.println("Message poste est : " + str);
                //setMessageNotification(s, new String(mqttMessage.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}

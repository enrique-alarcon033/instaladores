package com.yonusa.instaladores_plus.utilities.firebaseService;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.yonusa.instaladores_plus.MainActivity;
import com.yonusa.instaladores_plus.R;
import com.yonusa.instaladores_plus.utilities.catalogs.Mqtt_CMD;

import java.util.Map;

public class FirebaseCloudMessagingService extends FirebaseMessagingService {

    private String TAG = "FirebaseCloudMessagingService";

    public static final String NOTIFICATION_CHANNEL_ID = "Hub002";
    public static final String NOTIFICATION_CHANNEL_NAME = "Notification Hubs Demo Channel";
    public static final String NOTIFICATION_CHANNEL_DESCRIPTION = "Notification Hubs Demo Channel";

    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    static Context ctx;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        Map<String, String> bundle = null;
        String nhMessage;
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        /*
        public string gcm = "{{" +
                "\"data\" : {{" +
                "\"comando\": \"{0}\"," +
                "\"aliasCerca\": \"{1}\"," +
                "\"textoMostrar\": \"{2}\"," +
                "\"notificationId\": \"{3}\"," +
                "\"cercaId\": \"{4}\"," +
                "\"fechaInicio\": \"{5}\"," +
                "\"fechaFin\": \"{6}\"" +
                "}}" +
                "}}";
        }
        */

        // Check if message contains a notification payload.
        //if (remoteMessage.getNotification() != null) {

            //Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            bundle = remoteMessage.getData();

            String Message = remoteMessage.getData().get("message");

            if (bundle.get(PushNotificationKeys.textoMostrar) == null ) return;

            nhMessage = bundle.get(PushNotificationKeys.textoMostrar) + " en " + bundle.get(PushNotificationKeys.aliasCerca);
            String command = bundle.get(PushNotificationKeys.comando);

            switch (command) {

                case Mqtt_CMD.ALARM_ON:
                    startSound(ctx, R.raw.sonido_alarma, 100);
                    sendNotification(nhMessage);
                    break;
                case "@PANICO_ON": // No se usa el de abajo por que Backend NO ENVÏA el comando en Inglés y lo envía en español.
                //case Mqtt_CMD.CMD_PANIC_ON:
                    startSound(ctx, R.raw.panico, 100);
                    sendNotification(nhMessage);
                    break;
                default:
                    startSound(ctx, R.raw.notification, 80);
                    sendNotification(nhMessage);
                    break;
            }

            //Metodo para mostrar las push default desde Firebase.
            //nhMessage = remoteMessage.getNotification().getBody();
            //Log.d("NOTIFICATIONS",  "Custom Message Received: "+ customMessage);


       // } else {
         //   nhMessage = remoteMessage.getData().values().iterator().next();
        //}

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        if (MainActivity.isVisible) {
            //MainActivity.mainActivity.ToastNotify(nhMessage);
        }
        //sendNotification(nhMessage);
    }

    private void sendNotification(String msg) {

        Intent intent = new Intent(ctx, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        mNotificationManager = (NotificationManager)
                ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,
                intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
                ctx,
                NOTIFICATION_CHANNEL_ID)
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL);

        notificationBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    public static void createChannelAndHandleNotifications(Context context) {
        ctx = context;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(NOTIFICATION_CHANNEL_DESCRIPTION);
            channel.setShowBadge(true);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    public static void startSound(final Context context, final int sound, final float volume){

        new Thread(() -> {
            MediaPlayer mp = MediaPlayer.create(context, sound);
            mp.setVolume(volume,volume);
            mp.start();
        }).start();

    }

    public  void startVibrator(final Context con){

        new Thread(() -> {
            Vibrator vibrador = (Vibrator) con.getSystemService(Context.VIBRATOR_SERVICE);
            long[] pattern = {0,1500,500,1500,500,1500,500,1500,500,1500,500,1500,500};
            vibrador.vibrate(pattern,-1);
        }).start();


    }

}

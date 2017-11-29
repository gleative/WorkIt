package com.example.gleative.workit;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

/**
 * Created by gleative on 29.11.2017.
 */

public class NotificationCreator extends ContextWrapper{
    public static final String channelID = "channelID";
    public static final String channel1Name = "Channel 1";

    private NotificationManager manager;

    public NotificationCreator(Context context){
        super(context);

        // If SDK is higher or equal to Oreo, create channels
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannels();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void createChannels(){
        NotificationChannel channel = new NotificationChannel(channelID, channel1Name, NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);
        channel.setLightColor(R.color.colorPrimary);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager(){
        if(manager == null){
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return manager;
    }

    public NotificationCompat.Builder getChannel1Notification(String title, String message){
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.ic_dumbbell);
    }

}

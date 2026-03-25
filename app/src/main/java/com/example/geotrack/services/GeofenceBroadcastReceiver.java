package com.example.geotrack.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.geotrack.R;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

/**
 * BroadcastReceiver that handles geofence transitions.
 * When a user enters a geofence, it triggers a system notification.
 */
public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "GeofenceReceiver";
    private static final String CHANNEL_ID = "GEOFENCE_CHANNEL";

    @Override
    public void onReceive(Context context, Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent == null || geofencingEvent.hasError()) {
            Log.e(TAG, "GeofencingEvent error: " + (geofencingEvent != null ? geofencingEvent.getErrorCode() : "null"));
            return;
        }

        // 1. Get the transition type
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        // 2. Check for "ENTER" or "DWELL" transitions
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            
            for (Geofence geofence : triggeringGeofences) {
                String geofenceId = geofence.getRequestId();
                Log.d(TAG, "Entered geofence: " + geofenceId);
                sendNotification(context, "Geofence Triggered!", "You have entered: " + geofenceId);
            }
        }
    }

    /**
     * Helper to show a notification when the geofence is triggered.
     */
    private void sendNotification(Context context, String title, String message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Create notification channel for Android O+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Geofence Alerts", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info) // System info icon
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }
}

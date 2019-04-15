package com.viktorija.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // Constant for Notification channel ID.
    // Every notification channel must be associated with an ID that is unique within your package.
    // This channel ID is used later to post your notifications.
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    // Constant for the notification ID
    // notification ID allows update or cancel the notification
    private static final int NOTIFICATION_ID = 0;

    // The Android system uses the NotificationManager class to deliver notifications to the user.
    // Creating a member variable to store the NotificationManager object
    private NotificationManager notificationManager;

    private Button button_notify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create Notification Channel (needed to create a basic notification)
        createNotificationChannel();

        // Button to send notification
        button_notify = findViewById(R.id.notify);
        button_notify.setOnClickListener(view -> sendNotification());
    }

    /**
     * Creates a Notification channel, for OREO and higher.
     */
    public void createNotificationChannel() {
        // Instantiating the NotificationManager object
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Condition to check for the device's API version as notification channels are only available in API 26 and higher.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // Creating the Notification Channel with all the parameters
            // PRIMARY_CHANNEL_ID is channel id; channel name is a name that is displayed under notification Categories
            // in the device's user-visible Settings app; Setting importance to high
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID, "Mascot Notification", NotificationManager.IMPORTANCE_HIGH);

            // Configuring the notificationChannel object's initial settings.
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Mascot");
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    /**
     * Helper method that builds the notification.
     * @return NotificationCompat.Builder: notification build with all the
     * parameters.
     */
    private NotificationCompat.Builder buildNotification() {

        // Updating the app so that when the user taps the notification,
        // your app sends a content intent that launches the MainActivity.
        // Creating an explicit intent method to launch the MainActivity:
        Intent notificationIntent = new Intent(this, MainActivity.class);

        // Using the getActivity() method to get a PendingIntent. Passing in the notification ID constant
        // for the requestCode and using the FLAG_UPDATE_CURRENT flag.
        // By using a PendingIntent to communicate with another app, we are telling that app
        // to execute some predefined code at some point in the future.
        PendingIntent notificationSelectedIntent = PendingIntent.getActivity
                (this, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Creating and instantiating the notification builder
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                // Adding title, text, and icon to the builder
                .setContentTitle("You've been notified!")
                .setContentText("This is your notification text.")
                .setSmallIcon(R.drawable.ic_android)
                // send this intent when user tapped on notification
                .setContentIntent(notificationSelectedIntent)
                // send this intent when user deleted notification
//                .setDeleteIntent(notificationDeleteIntent)
                .setAutoCancel(true)
                // setting priority of the notification to HIGH (for devices running Android 7.1 or lower)
                // For devices running Android 8.0 and higher, you use notification channels to add priority
                // and defaults to notifications
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                // setting sound, vibration, and LED-color pattern for your notification to the default value
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        return notificationBuilder;
    }

    /**
     * OnClick method for the "Notify Me!" button.
     * Creates and delivers a simple notification.
     */
    public void sendNotification() {

        // Build the notification with all of the parameters using helper method.
        NotificationCompat.Builder notificationBuilder = buildNotification();

        // Deliver the notification
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }
}
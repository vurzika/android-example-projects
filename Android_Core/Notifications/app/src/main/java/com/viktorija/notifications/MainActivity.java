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

    // Constant for the notification update action button
    // (make sure to prefix the variable value with your app's package name to ensure its uniqueness)
    private static final String ACTION_UPDATE_NOTIFICATION =
            "com.viktorija.notifications.ACTION_UPDATE_NOTIFICATION";

    // Constant for the notification update action button
    private static final String ACTION_DELETE_NOTIFICATION =
            "com.viktorija.notifications.ACTION_DELETE_NOTIFICATION";

    // The Android system uses the NotificationManager class to deliver notifications to the user.
    // Creating a member variable to store the NotificationManager object
    private NotificationManager notificationManager;

    private Button button_notify;
    private Button button_cancel;
    private Button button_update;

    // Creating a member variable for your receiver and initialize it using the default constructor.
    private NotificationReceiver receiver = new NotificationReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create Notification Channel (needed to create a basic notification)
        createNotificationChannel();

        // Registering broadcast receiver to receive the ACTION_UPDATE_NOTIFICATION and ACTION_DELETE_NOTIFICATION intent
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_UPDATE_NOTIFICATION);
        intentFilter.addAction(ACTION_DELETE_NOTIFICATION);
        registerReceiver(receiver, intentFilter);

        // Button to send notification
        button_notify = findViewById(R.id.notify);
        button_notify.setOnClickListener(view -> sendNotification());

        // Button to update notification
        button_update = findViewById(R.id.update);
        button_update.setOnClickListener(view -> updateNotification());
        // Button to cancel notification
        button_cancel = findViewById(R.id.cancel);
        button_cancel.setOnClickListener(view -> cancelNotification());

        // enabling and disabling the buttons depending on the state of the notification
        setNotificationButtonState(true, false, false);
    }

    /**
     * Unregisters the receiver when the app is being destroyed.
     */
    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
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

        // Creating an explicit intent method to delete the notification
        Intent deleteIntent = new Intent(ACTION_DELETE_NOTIFICATION);

        PendingIntent notificationDeleteIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Creating and instantiating the notification builder
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                // Adding title, text, and icon to the builder
                .setContentTitle("You've been notified!")
                .setContentText("This is your notification text.")
                .setSmallIcon(R.drawable.ic_android)
                // send this intent when user tapped on notification
                .setContentIntent(notificationSelectedIntent)
                // send this intent when user deleted notification
                .setDeleteIntent(notificationDeleteIntent)
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

        // Sets up the pending intent to update the notification.
        // Corresponds to a press of the Update Me! button.
        Intent updatePressedIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        // Using getBroadcast() to get a PendingIntent.
        // To make sure that this pending intent is sent and used only once, set FLAG_ONE_SHOT.
        PendingIntent updatePressedPendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, updatePressedIntent, PendingIntent.FLAG_ONE_SHOT);

        // Build the notification with all of the parameters using helper method.
        NotificationCompat.Builder notificationBuilder = buildNotification();

        // Add the action button using the pending intent.
        notificationBuilder.addAction(R.drawable.ic_update, "Update", updatePressedPendingIntent);

        // Deliver the notification
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());

        // enabling and disabling the buttons depending on the state of the notification
        setNotificationButtonState(false, true, true);
    }

    /**
     * OnClick method for the "Update Me!" button. Updates the existing
     * notification to show a picture.
     */
    public void updateNotification() {
        // converting drawable into a bitmap
        Bitmap androidImage = BitmapFactory
                .decodeResource(getResources(), R.drawable.mascot_1);

        // converting drawable into a bitmap
        Bitmap largeIcon = BitmapFactory
                .decodeResource(getResources(), R.drawable.ic_update);

        // Build the notification with all of the parameters using helper method
        NotificationCompat.Builder notificationBuilder = buildNotification();

        notificationBuilder
                //.setSmallIcon(R.drawable.ic_update)
                .setStyle(new NotificationCompat.InboxStyle()
                        .setBigContentTitle("Title")
                        .addLine("Here is the first line")
                        .addLine("This is the second line")
                        .addLine("Yay last one")
                        .setSummaryText(" + 3 more")
        );

        // Changing the style of the notification and setting the image and the title
        // BigPictureStyle is a subclass of NotificationCompat.Style which provides alternative layouts for notifications.

//        notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle()
//                .bigPicture(androidImage)
//                .setBigContentTitle("Notification Updated!"));

        // Sets up the pending intent to update the notification.
        // Corresponds to a press of the Update Me! button.
        Intent updatePressedIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        // Using getBroadcast() to get a PendingIntent.
        // To make sure that this pending intent is sent and used only once, set FLAG_ONE_SHOT.
        PendingIntent updatePressedPendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, updatePressedIntent, PendingIntent.FLAG_ONE_SHOT);

        // Add the action button using the pending intent.
        notificationBuilder.addAction(R.drawable.ic_update, "Update", updatePressedPendingIntent);


        // Deliver the notification.
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());

        // enabling and disabling the buttons depending on the state of the notification
        setNotificationButtonState(false, false, true);
    }

    /**
     * OnClick method for the "Cancel Me!" button. Cancels the notification.
     */
    public void cancelNotification() {
        notificationManager.cancel(NOTIFICATION_ID);
        // enabling and disabling the buttons depending on the state of the notification
        setNotificationButtonState(true, false, false);
    }

    /**
     * Helper method to enable/disable the buttons.
     *
     * @param isNotifyEnabled, boolean: true if notify button enabled
     * @param isUpdateEnabled, boolean: true if update button enabled
     * @param isCancelEnabled, boolean: true if cancel button enabled
     */
    void setNotificationButtonState(Boolean isNotifyEnabled, Boolean isUpdateEnabled, Boolean isCancelEnabled) {
        button_notify.setEnabled(isNotifyEnabled);
        button_update.setEnabled(isUpdateEnabled);
        button_cancel.setEnabled(isCancelEnabled);
    }

    /**
     * The broadcast receiver class for notifications.
     * Responds to the update notification pending intent action.
     */

    // Adding a subclass of BroadcastReceiver as an inner class.
    public class NotificationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String intentAction = intent.getAction();

            if (ACTION_UPDATE_NOTIFICATION.equals(intentAction)) {
                updateNotification();
            } else if (ACTION_DELETE_NOTIFICATION.equals(intentAction)){
                setNotificationButtonState(true, false, false);
            }
        }
    }
}
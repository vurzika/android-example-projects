package com.viktorija.jobscheduler;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

// create a new Java class that extends JobService
public class NotificationJobService extends JobService {

    // Declaring a member variable for the notification manager
    NotificationManager notificationManager;

    // constant for the notification channel ID
    public static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    // Constant for the notification ID
    // notification ID allows update or cancel the notification
    private static final int NOTIFICATION_ID = 0;

    // Define what should be done (in our case - show notification)
    @Override
    public boolean onStartJob(JobParameters params) {

        // Create the notification channel
        createNotificationChannel();

        // Create the notification
        NotificationCompat.Builder notificationBuilder = createNotification();

        // Deliver the notification
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        // onStopJob() returns true, because if the job fails, you want the job to be rescheduled instead of dropped.
        return true;
    }

    /**
     * Creates a Notification channel, for OREO and higher.
     */
    private void createNotificationChannel() {
        // Define notification manager object.
        notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            "Job Service notification",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription
                    ("Notifications from Job Service");

            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    // Creating Notification
    private NotificationCompat.Builder createNotification() {
        //Setting up the notification content intent to launch the app when clicked
        Intent contentIntent = new Intent(this, MainActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (this, NOTIFICATION_ID, contentIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("Job Service")
                .setContentText("Your Job is Running!")
                .setContentIntent(contentPendingIntent)
                .setSmallIcon(R.drawable.ic_job_running)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);
        return notificationBuilder;
    }
}

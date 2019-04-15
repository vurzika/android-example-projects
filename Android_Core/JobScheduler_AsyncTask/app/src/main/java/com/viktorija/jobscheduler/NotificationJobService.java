package com.viktorija.jobscheduler;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.lang.ref.WeakReference;

// create a new Java class that extends JobService
public class NotificationJobService extends JobService {

    // Declaring a member variable for the notification manager
    NotificationManager notificationManager;

    // constant for the notification channel ID
    public static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    // Constant for the notification ID
    // notification ID allows update or cancel the notification
    private static final int NOTIFICATION_ID = 0;

    private JobTask mTask;

    // Define what should be done (in our case - show notification)
    // The onStartJob() method returns a boolean value that indicates
    // whether the service needs to process the work in a separate thread
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        // execute job on background thread via task
        mTask = new JobTask(this);
        mTask.execute(jobParameters);

        // return true to indicate that job will continue working in background
        // in this case task must call jobFinished() when it will complete it's work
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        // this method was called because conditions are no longer met
        // if task is still running - cancel it

        if (mTask != null) {
            mTask.cancel(true);
            // and show toast message
            Toast.makeText(this, "The Job Failed", Toast.LENGTH_SHORT).show();
        }

        // onStopJob() returns true, because if the job fails, you want the job to be rescheduled instead of dropped.
        return true;
    }

    /**
     * Creates a Notification channel, for OREO and higher.
     */
    public void createNotificationChannel() {
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

    // Inner class that creates notification in background thread
    private static class JobTask extends AsyncTask<JobParameters, Void, JobParameters> {

        private final WeakReference<NotificationJobService> jobServiceReference;

        public JobTask(NotificationJobService jobServiceReference) {
            this.jobServiceReference = new WeakReference<>(jobServiceReference);
        }

        @Override
        protected JobParameters doInBackground(JobParameters... jobParameters) {
            // The AsyncTask sleeps for 5 seconds
            SystemClock.sleep(5000);

            NotificationJobService jobService = jobServiceReference.get();

            if (jobService != null) {
                // Create the notification channel
                jobService.createNotificationChannel();

                // Create the notification
                NotificationCompat.Builder notificationBuilder = jobService.createNotification();

                // Deliver the notification
                jobService.notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
            }

            return jobParameters[0];
        }

        @Override
        protected void onPostExecute(JobParameters jobParameters) {
            // notify the job that task has finished so job can be finished as well

            NotificationJobService jobService = jobServiceReference.get();
            if (jobService != null) {
                jobService.jobFinished(jobParameters, false);
            }
        }
    }
}

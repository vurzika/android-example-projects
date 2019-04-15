package com.viktorija.jobscheduler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // member constant for the JOB_ID (set it to 0)
    public static final int JOB_ID = 0;

    // member variable for the job scheduler
    private JobScheduler scheduler;

    //Switches for setting job options
    private Switch deviceIdleSwitch;
    private Switch deviceChargingSwitch;

    // The JobScheduler API includes the ability to set a hard deadline that overrides all previous constraints.
    // SeekBar allows the user to set a deadline between 0 and 100 seconds to execute your task.
    // The user sets the value by dragging the seek bar left or right.
    private SeekBar seekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deviceIdleSwitch = findViewById(R.id.idleSwitch);
        deviceChargingSwitch = findViewById(R.id.chargingSwitch);

        seekBar = findViewById(R.id.seekBar);
        // Creating and initializing a variable for the seek bar's progress TextView
        final TextView seekBarProgress = findViewById(R.id.seekBarProgress);

        // Calling setOnSeekBarChangeListener() on the seek bar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // If the value is greater than 0 (meaning a value has been set by the user),
                // set the seek bar's progress label to the integer value, followed by s to indicate seconds.
                // Otherwise, set the TextView to read "Not Set".
                if (progress > 0) {
                    seekBarProgress.setText(progress + "s");
                } else {
                    seekBarProgress.setText("Not Set");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    // This method is called when "Schedule job" button is pressed
    public void scheduleJob(View view) {
        //  initializing scheduler using getSystemService()
        // tIf there is no scheduler - ask android to provide it
        if (scheduler == null) {
            scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        }

        // Finding the RadioGroup by ID and saving it in an instance variable called radioGroup.
        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        // Getting the selected network ID and saving it in an integer variable.
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();

        // Creating an integer variable for the selected radio button.
        // Setting the variable to the default radio button, which is NETWORK_TYPE_NONE.
        int selectedRadioButton = JobInfo.NETWORK_TYPE_NONE;

        // Creating a switch statement with the selected radio button ID. Adding a case for each of the possible IDs
        // and assigning the selected radio button the appropriate JobInfo network constant, depending on the case
        switch (selectedRadioButtonId) {
            case R.id.noNetwork:
                selectedRadioButton = JobInfo.NETWORK_TYPE_NONE;
                break;
            case R.id.anyNetwork:
                selectedRadioButton = JobInfo.NETWORK_TYPE_ANY;
                break;
            case R.id.wifiNetwork:
                selectedRadioButton = JobInfo.NETWORK_TYPE_UNMETERED;
                break;
        }

        // int to store the seek bar's progress.
        int seekBarInteger = seekBar.getProgress();
        // boolean variable that's true if the seek bar has an integer value greater than 0
        boolean seekBarSet = seekBarInteger >0;


        // The ComponentName is used to associate the JobService with the JobInfo object.
        ComponentName serviceName = new ComponentName(getPackageName(), NotificationJobService.class.getName());

        // Creating JobInfo Builder object and setting constraints based on the user selection
        // JobScheduler requires at least one constraint to be set.
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, serviceName)
                // setting required network type
                .setRequiredNetworkType(selectedRadioButton)
                // setting constraint based on the user selection in the Switch views
                .setRequiresDeviceIdle(deviceIdleSwitch.isChecked())
                .setRequiresCharging(deviceChargingSwitch.isChecked());

        // if seekBarSet is true, call setOverrideDeadline() on the JobInfo.Builder.
        // Pass in the seek bar's integer value multiplied by 1000.
        // (The parameter is in milliseconds, and you want the user to set the deadline in seconds.)
        if (seekBarSet) {
            builder.setOverrideDeadline(seekBarInteger * 1000);
        }


        // This boolean tracks whether at least one constraint is set,
        // so that you can notify the user to set at least one constraint if they haven't already.
        boolean constraintSet = (selectedRadioButton
                != JobInfo.NETWORK_TYPE_NONE)
                || deviceChargingSwitch.isChecked()
                || deviceIdleSwitch.isChecked()
                || seekBarSet;
        if (constraintSet) {
            // JobInfo is a set of conditions that trigger the job to run
            JobInfo myJobInfo = builder.build();
            //Schedule the job and notify the user
            scheduler.schedule(myJobInfo);
            Toast.makeText(this, "Job Scheduled, job will run when " +
                    "the constraints are met.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please set at least one constraint", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelJobs(View view) {
        if (scheduler != null){
            // removing all pending jobs
            scheduler.cancelAll();
            // reset the JobScheduler to null
            scheduler = null;
            Toast.makeText(this, "Jobs cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}

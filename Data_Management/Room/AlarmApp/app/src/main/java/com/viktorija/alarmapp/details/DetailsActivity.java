package com.viktorija.alarmapp.details;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TimePicker;

import com.viktorija.alarmapp.R;
import com.viktorija.alarmapp.database.AlarmEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_ALARM_ID = "EXTRA_ALARM_ID";

    @BindView(R.id.time_picker)
    TimePicker mTimePicker;

    private DetailsViewModel mDetailsViewModel;

    private AlarmEntity mSelectedAlarm;

    // Adding member variables to the MainActivity class to hold the name of the shared preferences file,
    // and a reference to a SharedPreferences object
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // todo: how to center toolbar text?
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        TextView toolbarText = findViewById(R.id.toolbar_text);
//        getSupportActionBar().hide();
//        toolbarText.setText(getTitle());
//        setSupportActionBar(toolbar);



        mDetailsViewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);

        Bundle extras = getIntent().getExtras();
        if (extras == null || !extras.containsKey(EXTRA_ALARM_ID)) {
            setTitle("Add Alarm");
        } else {
            setTitle("Edit Alarm");
            int alarmId = extras.getInt(EXTRA_ALARM_ID);
            mDetailsViewModel.loadAlarmById(alarmId).observe(this, new Observer<AlarmEntity>() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onChanged(@Nullable AlarmEntity selectedAlarm) {
                    mSelectedAlarm = selectedAlarm;
                    updateUi();
                }
            });
        }

        //  Initializing the shared preferences
        // The getSharedPreferences() method opens the file at the given filename (sharedPrefFile) with the mode MODE_PRIVATE.
        mPreferences = android.support.v7.preference.PreferenceManager
                // getting the setting as a SharedPreferences object
                .getDefaultSharedPreferences(this);

        // changing picker based on selected preferences
        boolean isAmPmMode = mPreferences.getBoolean(getString(R.string.pref_key_time_mode_am_pm),
                getResources().getBoolean(R.bool.pref_key_time_mode_am_pm_default));

        mTimePicker.setIs24HourView(!isAmPmMode);


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void updateUi() {
        if (mSelectedAlarm != null) {
            mTimePicker.setHour(mSelectedAlarm.getHours());
            mTimePicker.setMinute(mSelectedAlarm.getMinutes());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            int valueHours = mTimePicker.getHour();
            int valueMinutes = mTimePicker.getMinute();

            AlarmEntity alarmEntity = new AlarmEntity(valueHours, valueMinutes);

            mDetailsViewModel.insert(alarmEntity);
        }
        finish();
        return true;
    }
}





package com.viktorija.alarmapp.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.viktorija.alarmapp.R;
import com.viktorija.alarmapp.SettingsActivity;
import com.viktorija.alarmapp.database.AlarmEntity;
import com.viktorija.alarmapp.details.DetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private AlarmRecyclerViewAdapter mAdapter;
    private List<AlarmEntity>mAlarmData = new ArrayList<>();
    private MainViewModel mViewModel;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    // Adding member variables to the MainActivity class to hold the name of the shared preferences file,
    // and a reference to a SharedPreferences object
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initViewModel();

        initRecyclerView();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                startActivity(intent);
            }
        });

        //  Initializing the shared preferences
        // The getSharedPreferences() method opens the file at the given filename (sharedPrefFile) with the mode MODE_PRIVATE.
        mPreferences = android.support.v7.preference.PreferenceManager
                // getting the setting as a SharedPreferences object
                .getDefaultSharedPreferences(this);
    }

    private void initViewModel() {
        // reference to ViewModel
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        //Adding sample data automatically
        mViewModel.getAlarmList().observe(MainActivity.this, new Observer<List<AlarmEntity>>() {
            @Override
            public void onChanged(@Nullable List<AlarmEntity> alarmEntities) {
                mAlarmData.clear();
                if (alarmEntities != null) {
                    mAlarmData.addAll(alarmEntities);
                }
                mAdapter.notifyDataSetChanged();
            }
        });

    }

    private void initRecyclerView(){
        // each item will have the same height
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new AlarmRecyclerViewAdapter(this, mAlarmData);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration divider = new DividerItemDecoration(
                mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(divider);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Change the label of the menu based on the state of the app.
        boolean isAmPmMode = mPreferences.getBoolean(getString(R.string.pref_key_time_mode_am_pm),
                getResources().getBoolean(R.bool.pref_key_time_mode_am_pm_default));

        if(isAmPmMode){
            menu.findItem(R.id.time_mode).setTitle("24 hour mode");
        } else{
            menu.findItem(R.id.time_mode).setTitle("AM/PM mode");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.time_mode) {
            changeTimeMode();
            // call this method to call onCreateOptionsMenu after time mode has been changed
            supportInvalidateOptionsMenu();
            return true;
        }
        if (id == R.id.settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeTimeMode() {
        // save preference
        // getting an editor for the SharedPreferences object
        // A shared preferences editor is required to write to the shared preferences object.
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();

        // Using the putInt() (if String - use putString()) method to put both the mCount and mColor integers
        // into the shared preferences with the appropriate keys:
        boolean isAmPmMode = mPreferences.getBoolean(getString(R.string.pref_key_time_mode_am_pm),
                getResources().getBoolean(R.bool.pref_key_time_mode_am_pm_default));

        preferencesEditor.putBoolean(getString(R.string.pref_key_time_mode_am_pm), !isAmPmMode);

        // Calling apply() to save the preferences
        // The apply() method saves the preferences asynchronously, off of the UI thread.
        preferencesEditor.apply();

        // refresh adapter
        mAdapter.notifyDataSetChanged();
    }
}

package com.viktorija.alarmapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {

    // Variable to hold the key for the value. Needed to read the changed settings value from shared preferences
    // Key for current count
    public static final String PREF_TIME_MODE_AM_PM = "PREF_TIME_MODE_AM_PM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
// todo:
//        setTheme(R.style.Preference_CheckBoxPreference);
    }
}

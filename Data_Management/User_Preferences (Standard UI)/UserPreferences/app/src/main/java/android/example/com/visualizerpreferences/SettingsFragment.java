package android.example.com.visualizerpreferences;

/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.widget.Toast;

// Implement OnSharedPreferenceChangeListener
public class SettingsFragment extends PreferenceFragmentCompat implements OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

        // Add visualizer preferences, defined in the XML file in res->xml->pref_visualizer
        addPreferencesFromResource(R.xml.pref_visualizer);

        // Get the preference screen, get the number of preferences and iterate through
        // all of the preferences if it is not a checkbox preference, call the setSummary method
        // passing in a preference and the value of the preference

        // Getting the preference screen
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen = getPreferenceScreen();

        //Getting the number of the preferences
        int count = preferenceScreen.getPreferenceCount();

        // Iterating through all of the preferences
        for (int i = 0; i < count; i++) {
            Preference p = preferenceScreen.getPreference(i);

            // Checking if a preference is a CheckBox preference
            if (!(p instanceof CheckBoxPreference)) {
                // If it is not CheckBox preference - get the value by
                // taking sharedPreferences  object and using GetString with the key.
                // Pass that value to setPreferenceSummary method
                String value = sharedPreferences.getString(p.getKey(), "");
                setPreferenceSummary(p, value);
            }
        }
    }

    // Override onSharedPreferenceChanged and, if it is not a checkbox preference,
    // call setPreferenceSummary on the changed preference
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // Finding a preference by its key
        Preference preference = findPreference(key);
        // If the preference is not null and is not the value of CheckBoxPreference - get the value
        // and call setPreferenceSummary
        if (null != preference) {
            if (!(preference instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(preference.getKey(),"");
                setPreferenceSummary(preference, value);
            }
        }
    }

    // Create a setPreferenceSummary which takes a Preference and String value as parameters.
    // This method should check if the preference is a ListPreference and, if so, find the label
    // associated with the value. You can do this by using the findIndexOfValue and getEntries methods
    // of Preference.
    // Helper method to set the correct preference summary
    private void setPreferenceSummary(Preference preference, String value) {

        // Checking if the preference is an instance of ListPreference
        if (preference instanceof ListPreference) {
            // If it is - cast it
            ListPreference listPreference = (ListPreference) preference;
            // Find the index ot the current preference that was selected
            int prefIndex = listPreference.findIndexOfValue(value);
            //Checking if the index is valid
            if (prefIndex >= 0) {
                //if it is - use setSummary method for listPreference (getting the label,
                // that is associated with the value)
                listPreference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }
    }

    // Register and unregister the OnSharedPreferenceChange listener (this class) in
    // onCreate and onDestroy respectively.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
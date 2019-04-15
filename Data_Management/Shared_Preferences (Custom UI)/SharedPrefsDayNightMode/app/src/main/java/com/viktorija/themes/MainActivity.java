package com.viktorija.themes;

import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Member variables for holding the score
    private int mScore1;
    private int mScore2;

    // Member variables for holding the score
    private TextView mScoreText1;
    private TextView mScoreText2;

    // tags that are used as the keys in onSaveInstanceState():
    static final String STATE_SCORE_1 = "Team 1 Score";
    static final String STATE_SCORE_2 = "Team 2 Score";

    // Adding member variables to the MainActivity class to hold the name of the shared preferences file,
    // and a reference to a SharedPreferences object
    private SharedPreferences mPreferences;
    private String sharedPrefFile =
            "com.viktorija.themes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find the TextViews by ID
        mScoreText1 = (TextView)findViewById(R.id.score_1);
        mScoreText2 = (TextView)findViewById(R.id.score_2);

        //  Initializing the shared preferences
        // The getSharedPreferences() method opens the file at the given filename (sharedPrefFile) with the mode MODE_PRIVATE.
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        // Restoring the shared preferences
        // getting the count from the preferences with the COUNT_KEY key and assign it to the mCount variable.
        mScore1 = mPreferences.getInt(STATE_SCORE_1, 0);
        // Updating the value of the main TextView with the new count.
        mScoreText1.setText(String.valueOf("" + mScore1));
        //Getting the color from the preferences with the COLOR_KEY key and assign it to the mColor variable.
        // the second argument to getInt() is the default value to use in case the key doesn't exist in
        // the shared preferences. In this case you can just reuse the value of mColor
        mScore2 = mPreferences.getInt(STATE_SCORE_2, 0);
        // Update the background color of the main text view
        mScoreText2.setText(String.valueOf("" + mScore2));
    }

    /**
     * Method that handles the onClick of both the decrement buttons
     * @param view The button view that was clicked
     */
    public void decreaseScore(View view) {
        // Get the ID of the button that was clicked
        int viewID = view.getId();
        switch (viewID){
            //If it was on Team 1
            case R.id.decreaseTeam1:
                //Decrement the score and update the TextView
                mScore1--;
                mScoreText1.setText(String.format("%s", mScore1));
                break;
            //If it was Team 2
            case R.id.decreaseTeam2:
                //Decrement the score and update the TextView
                mScore2--;
                mScoreText2.setText(String.format("%s", mScore2));
        }
    }

    /**
     * Method that handles the onClick of both the increment buttons
     * @param view The button view that was clicked
     */
    public void increaseScore(View view) {
        //Get the ID of the button that was clicked
        int viewID = view.getId();
        switch (viewID){
            //If it was on Team 1
            case R.id.increaseTeam1:
                //Increment the score and update the TextView
                mScore1++;
                mScoreText1.setText(String.valueOf(mScore1));
                break;
            //If it was Team 2
            case R.id.increaseTeam2:
                //Increment the score and update the TextView
                mScore2++;
                mScoreText2.setText(String.valueOf(mScore2));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // code to inflate the menu
        getMenuInflater().inflate(R.menu.main_menu, menu);
        // Change the label of the menu based on the state of the app.
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if(nightMode == AppCompatDelegate.MODE_NIGHT_YES){
            menu.findItem(R.id.night_mode).setTitle(R.string.day_mode);
        } else{
            menu.findItem(R.id.night_mode).setTitle(R.string.night_mode);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Check if the correct item was clicked
        if (item.getItemId() == R.id.night_mode) {
            // Get the night mode state of the app.
            int nightMode = AppCompatDelegate.getDefaultNightMode();
            //Set the theme mode for the restarted activity
            if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode
                        (AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                AppCompatDelegate.setDefaultNightMode
                        (AppCompatDelegate.MODE_NIGHT_YES);
            }
            // The theme can only change while the activity is being created,
            // so the code calls recreate() for the theme change to take effect.
            recreate();
        }
        return true;
    }

    // Adding the onPause() lifecycle method to MainActivity as For shared preferences
    // we save that data in the onPause() lifecycle callback
    @Override
    protected void onPause() {
        super.onPause();
        // getting an editor for the SharedPreferences object
        // A shared preferences editor is required to write to the shared preferences object.
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        // Using the putInt() (if String - use putString()) method to put both the mCount and mColor integers
        // into the shared preferences with the appropriate keys:
        preferencesEditor.putInt(STATE_SCORE_1, mScore1);
        preferencesEditor.putInt(STATE_SCORE_2, mScore2);
        // Calling apply() to save the preferences
        preferencesEditor.apply();
    }

    // The view (Button) that was clicked.
    public void reset(View view) {

        // Reset score1
        mScore1 = 0;
        mScoreText1.setText(String.format("%s", mScore1));

        // Reset score 2
        mScore2 = 0;

        mScoreText2.setText(String.format("%s",mScore2));

        // Clear Preferences
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.clear();
        preferencesEditor.apply();
    }
}
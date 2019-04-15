package com.viktorija.startactivityforresult;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import static com.viktorija.startactivityforresult.DetailsActivity.EXTRA_BUTTON_TITLE;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_SELECT_BUTTON = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonStartDetailsActivity = findViewById(R.id.button_start_activity);

        buttonStartDetailsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                // Request code:  If >= 0, this code will be returned in onActivityResult() when the activity exits.
                startActivityForResult(intent, REQUEST_CODE_SELECT_BUTTON);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_SELECT_BUTTON) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra(EXTRA_BUTTON_TITLE);
                showPressedButtonNumber(result);
            }
        }
    }



    // Show a dialog that tells the user which button was pressed
    private void showPressedButtonNumber(String buttonTitle) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive button on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You pressed button number " + buttonTitle);

        builder.setPositiveButton("Ok", null);

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

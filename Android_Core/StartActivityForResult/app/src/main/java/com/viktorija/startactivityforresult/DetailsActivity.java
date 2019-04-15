package com.viktorija.startactivityforresult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    // key to get intent with button id from MainActivity
    public static final String EXTRA_BUTTON_TITLE = "EXTRA_BUTTON_TITLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Button buttonOne = findViewById(R.id.button_one);
        buttonOne.setOnClickListener(this);

        Button buttonTwo = findViewById(R.id.button_two);
        buttonTwo.setOnClickListener(this);

        Button buttonThree = findViewById(R.id.button_three);
        buttonThree.setOnClickListener(this);

    }

    public void onClick(View view) {
        // Cast view to button, as we always assign listener to button(in this case)
        Button button = (Button) view;
        String buttonTitle = button.getText().toString();
        finishActivityWithButtonNumber(buttonTitle);
    }

    private void finishActivityWithButtonNumber(String buttonTitle) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(EXTRA_BUTTON_TITLE, buttonTitle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}

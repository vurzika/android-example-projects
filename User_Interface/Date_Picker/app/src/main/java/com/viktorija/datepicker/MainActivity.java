package com.viktorija.datepicker;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // when the button Date is pressed - show a date picker
    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    // Process the date picker result into strings that can be displayed in a Toast.
    public void processDatePickerResult(int year, int month, int day) {
        // The month integer returned by the date picker starts counting at 0 for January,
        // so you need to add 1 to it to show months starting at 1.
        String monthString = Integer.toString(month+1);
        String dayString = Integer.toString(day);
        String yearString = Integer.toString(year);
        String dateMessage = (monthString +
                "/" + dayString + "/" + yearString);

        Toast.makeText(this, "Date: " + dateMessage,
                Toast.LENGTH_SHORT).show();

    }
}

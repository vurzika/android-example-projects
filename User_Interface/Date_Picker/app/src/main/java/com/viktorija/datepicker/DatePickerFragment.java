package com.viktorija.datepicker;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

// The best practice to show a picker is to use an instance of DialogFragment, which is a subclass of Fragment.
// Expand app > java > com.example.android.pickerfordate and select MainActivity.
//Choose File > New > Fragment > Fragment (Blank), and name the fragment DatePickerFragment.
// Clear all three checkboxes so that you don't create a layout XML, include fragment factory methods, or include interface callbacks.
// You don't need to create a layout for a standard picker. Click Finish.

/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    @NonNull
    public Dialog onCreateDialog (Bundle savedInstanceState){
        // Use the current date as the default date in the picker.
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it.
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    // This method is called when the user sets the date.
    // Grabs the date and passes it to processDatePickerResult() method in MainActivity
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        MainActivity activity = (MainActivity) getActivity();
        activity.processDatePickerResult(year, month, day);
    }
}

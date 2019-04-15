package com.viktorija.permissions;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int PERMISSION_REQUEST_CODE = 1;

    private View layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.main_layout);

        Button buttonCall = findViewById(R.id.button_start_phone_call);
        buttonCall.setOnClickListener(this);

        Button buttonRequestPermission = findViewById(R.id.button_request_permission);
        buttonRequestPermission.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        int id = button.getId();

        switch (id) {
            // User pressed on button_check_permission
            case R.id.button_start_phone_call:
                // If permission is already allowed ((hasPermission() == true), start phone call
                if (hasPermission()) {
                    startPhoneCall();
                    //Snackbar.make(button, "Permission already granted", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(button, "Please request permission", Snackbar.LENGTH_LONG).show();
                }
                break;

            // User pressed on button_request_permission
            case R.id.button_request_permission:
                // If permission is not  allowed ((hasPermission() == false), request permission
                if (!hasPermission()) {
                    requestPermission();
                } else {
                    Snackbar.make(button, "Permission already granted", Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }

    // This method will return true if permission is already allowed otherwise it will return false.
    private boolean hasPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    // This method will make the compiler to request for runtime permission.
    // When the compiler executes the requestPermission() method,a pop up dialog will be shown to the user with allow and deny options.
    private void requestPermission() {
        // Check if we need to show the user the alert dialog to explain the user why we need the permission
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
            // If yes - show dialog
            Snackbar.make(layout, "You need to allow the permission in order to make phone calls",
                    Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
                        // If user agrees to ask for permission
                @Override
                public void onClick(View view) {
                    // Request the permission
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            PERMISSION_REQUEST_CODE);
                }
            }).show();
        } else {
            // Requesting the permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_CODE);
        }
    }

    // When the user selects any of the option (allow or deny), compiler will execute the onRequestPermissionsResult() method.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                // If permission granted - show snackbar "Permission granted successfully"
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission has been granted. Start call preview Activity.
                    Snackbar.make(layout, "Permission granted successfully", Snackbar.LENGTH_LONG).show();
                } else {
                    // Otherwise show snackbar "Permission request was denied"
                    Snackbar.make(layout, "Permission is denied!", Snackbar.LENGTH_LONG).show();
                }
        }
    }

    private void startPhoneCall() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        startActivity(intent);
    }
}

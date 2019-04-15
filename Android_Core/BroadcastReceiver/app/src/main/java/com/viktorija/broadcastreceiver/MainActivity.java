// We are interested in two system broadcasts, ACTION_POWER_CONNECTED and ACTION_POWER_DISCONNECTED.
// The Android system sends these broadcasts when the device's power is connected or disconnected.


// To create a new broadcast receiver, select the package name in the Android Project View
// and navigate to File > New > Other > Broadcast Receiver.
// Name the class CustomReceiver. Make sure that Java is selected as the source language,
// and that Exported and Enabled are selected.
// Exported allows your broadcast receiver to receive broadcasts from outside your app.
// Enabled allows the system to instantiate the receiver.

package com.viktorija.broadcastreceiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

import static com.viktorija.broadcastreceiver.CustomReceiver.EXTRA_RANDOM_NUMBER;

public class MainActivity extends AppCompatActivity {

    // String constant that defines the custom broadcast Action.
    private static final String ACTION_CUSTOM_BROADCAST =
            BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";


    // Creating and initializing custom receiver object
    private CustomReceiver mReceiver = new CustomReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Define the IntentFilter.
        // Intent filters specify the types of intents a component can receive.
        // They are used in filtering out the intents based on Intent values like action and category.
        IntentFilter filter = new IntentFilter();

        // Add system broadcast actions sent by the system when the power is
        // connected and disconnected.
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);

        // Add system broadcast actions sent by the system when the wired headset is connected.
        filter.addAction(Intent.ACTION_HEADSET_PLUG);

        // Register the power receiver using the activity context.
        this.registerReceiver(mReceiver, filter);

        // Custom Broadcast
        // Register Custom Receiver
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mReceiver, new IntentFilter(ACTION_CUSTOM_BROADCAST));

    }

    // To save system resources and avoid leaks, dynamic receivers must be unregistered
    // when they are no longer needed or before the corresponding activity or app is destroyed,
    // depending on the context used.
    @Override
    protected void onDestroy() {
        // unregister the power receiver
        this.unregisterReceiver(mReceiver);

        // unregister custom receiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    // Custom Broadcast
    // Click event handler for the button, that sends custom broadcast using the LocalBroadcastManager.
    public void sendCustomBroadcast(View view) {

        Random rand = new Random();
        // Obtain a number between [1 - 20].
        int n = rand.nextInt(20);

        TextView textView = findViewById(R.id.textView);
        // to convert int to String using "" + n
        textView.setText(""+n);

        // Create a new Intent, with your custom action string as the argument
        Intent customBroadcastIntent = new Intent(ACTION_CUSTOM_BROADCAST);
        customBroadcastIntent.putExtra(EXTRA_RANDOM_NUMBER, n);

        // Because this broadcast is meant to be used solely by your app, use LocalBroadcastManager to manage the broadcast
        LocalBroadcastManager.getInstance(this).sendBroadcast(customBroadcastIntent);
    }
}

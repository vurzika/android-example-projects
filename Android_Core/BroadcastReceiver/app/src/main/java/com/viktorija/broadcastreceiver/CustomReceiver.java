package com.viktorija.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

// This method is called when the BroadcastReceiver is receiving
// an Intent broadcast.
public class CustomReceiver extends BroadcastReceiver {

    // String constant that defines the custom broadcast Action.
    private static final String ACTION_CUSTOM_BROADCAST =
            BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";

    // Key to add extra number to the Intent
    public static final String EXTRA_RANDOM_NUMBER = "EXTRA_RANDOM_NUMBER";

    @Override
    public void onReceive(Context context, Intent intent) {

        // Get the Intent action from the intent parameter and store it in a String variable called intentAction.
        String intentAction = intent.getAction();

        // Create a switch statement with the intentAction string.
        // (Before using intentAction, do a null check on it.)
        // Display a different toast message for each action your receiver is registered for.
        if (intentAction != null) {
            String toastMessage = "unknown intent action";
            switch (intentAction) {
                case Intent.ACTION_POWER_CONNECTED:
                    toastMessage = "Power connected!";
                    break;

                case Intent.ACTION_POWER_DISCONNECTED:
                    toastMessage = "Power disconnected!";
                    break;

                    // Custom receiver
                case ACTION_CUSTOM_BROADCAST:
                    int n = intent.getIntExtra(EXTRA_RANDOM_NUMBER, 0);

                    toastMessage = "Custom Broadcast Received Square of the random number: "  + n*n;
                    break;

                case Intent.ACTION_HEADSET_PLUG:
                    toastMessage = "Headset is connected!";
                    break;
            }

            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
        }
    }
}

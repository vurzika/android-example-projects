package com.viktorija.intents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static com.viktorija.intents.MainActivity.EXTRA_REPLY;

// To create new activity:
// Click the app folder for your project and choose File > New > Activity > Empty Activity
// Android Studio adds both activity_second.xml, SecondActivity.java) to your project for the new Activity.
// It also updates the AndroidManifest.xml file to include the new Activity.

public class SecondActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    private EditText mReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        // Get the string containing the message from the Intent extras
        String message = intent.getStringExtra(EXTRA_MESSAGE);

        TextView textView = findViewById(R.id.text_message);

        // Setting the text of the TextView to the string from the Intent extra
        textView.setText(message);

        mReply = findViewById(R.id.editText_second);
    }

    /**
     * Handles the onClick for the "Reply" button. Gets the message from the
     * second EditText, creates an intent, and returns that message back to
     * the main activity.
     *
     * @param view The view (Button) that was clicked.
     */

    public void returnReply(View view) {
        String reply = mReply.getText().toString();
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLY, reply);
        // Set the result to RESULT_OK to indicate that the response was successful.
        // The Activity class defines the result codes, including RESULT_OK and RESULT_CANCELLED.
        setResult(RESULT_OK, replyIntent);
        // Call finish() to close the Activity and return to MainActivity
        finish();
    }
}

package com.viktorija.toasts;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toast)
    Button mToast;

    @BindView(R.id.custom_toast)
    Button mCustomToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // Toast
        mToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Toast toast = Toast.makeText(getApplicationContext(), "Toast button pressed", Toast.LENGTH_LONG);
            toast.show();
            toast.setGravity(Gravity.BOTTOM|Gravity.CENTER,0,0);
            }
        });

        // Custom Toast
        mCustomToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = getLayoutInflater();

                View toastLayout = inflater.inflate(R.layout.custom_toast,
                        (ViewGroup) findViewById(R.id.custom_toast_container));

                TextView header = toastLayout.findViewById(R.id.toast_header);
                header.setText("Message for you:");

                TextView body = toastLayout.findViewById(R.id.toast_body);
                body.setText("You have got mail!");

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(toastLayout);
                toast.show();
            }
        });

    }
}

package com.viktorija.localization;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.quantity)
    TextView mQuantity;

    @BindView(R.id.phrase)
    TextView mPhrase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // Pluralization
        // (1 book / 5 books)
        mQuantity.setText(getResources().getQuantityString(R.plurals.quantity,5,5));

        // Pluralization
        // Phrase (I have 1 book(5 books) on the shelf number 1(2,100 etc)
        mPhrase.setText(getResources().getQuantityString(R.plurals.phrase, 5, 5, 100));


    }
}

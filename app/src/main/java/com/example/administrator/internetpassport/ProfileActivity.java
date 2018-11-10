package com.example.administrator.internetpassport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String id = "WELCOME "+intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView textViewId = findViewById(R.id.textViewId);
        textViewId.setText(id);
    }
}

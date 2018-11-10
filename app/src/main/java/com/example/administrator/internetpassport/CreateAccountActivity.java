package com.example.administrator.internetpassport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText editTextNewId, editTextNewPassword, editTextConfirmPassword;
    private Button buttonCreateAccount;
    private TextView textViewPasswordSame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        editTextNewId = (EditText) findViewById(R.id.editTextNewId);
        editTextNewPassword = (EditText) findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);
        buttonCreateAccount = (Button) findViewById(R.id.buttonCreateAccount);
        textViewPasswordSame = (TextView) findViewById(R.id.textViewPasswordSame);
    }

    public void createAccount(View view) {

    }
}

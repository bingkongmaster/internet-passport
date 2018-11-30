package com.example.administrator.internetpassport;
//Added Login UI&UC
//added password variable on storage

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.provider.Settings;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity
{
    public static final String EXTRA_MESSAGE = "com.example.internetpassport.MESSAGE";

    private EditText editTextId;
    private EditText editTextPassword;
    private TextView textViewAnswer;
    private Button buttonLogin;
    private Button buttonCreateAccount;

    private static InfoStorage storage = new InfoStorage("kaist123","0000","Edward","Male","South Korea", "010-1234-5678", "KAIST","kaist123@kaist.ac.kr","19970301");
    private int nLoginFail;

    private BroadcastReceiver receiver;
    private BroadcastReceiver receiver_sms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            test_method();
            //Toast.makeText(getApplicationContext(), "Try clause", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            e.printStackTrace();
            //Toast.makeText(getApplicationContext(), "Catch clause", Toast.LENGTH_LONG).show();
        }

        editTextId = (EditText) findViewById(R.id.editTextId);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewAnswer = (TextView) findViewById(R.id.textViewAnswer);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonCreateAccount = (Button) findViewById(R.id.buttonSignup);

        //storage = new InfoStorage("kaist123","0000","ljw","Male","South Korea", "010-1234-5678", "KAIST");
        nLoginFail = 3;
        // AutofillManager afm = getSystemService(AutofillManager.class);
        Intent i = new Intent(Settings.ACTION_REQUEST_SET_AUTOFILL_SERVICE);
        i.setData(Uri.parse("package:com.example.administrator.internetpassport"));

        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(receiver, intentFilter);

        requestPermission();

        IntentFilter intentFilter2 = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        registerReceiver(receiver_sms, intentFilter2);

        startActivityForResult(i, 1);
    }

    public void test_method() throws SQLException {
//        DatabaseHelper helper = new DatabaseHelper(this);
//        Dao<InfoStorage, Long> infoDao = helper.getDao(InfoStorage.class);
        InfoStorage storage = new InfoStorage("kaist123","0000","ChulSoo","Male","South Korea", "010-1234-5678", "KAIST","kaist123@kaist.ac.kr","19970301");
//        infoDao.create(storage); // create문에서 SQLException 오류 발생
        Log.v("name", storage.getName());
        Log.v("id", storage.getId());
        Log.v("sex", storage.getSex());
        Log.v("nation", storage.getNationality());
        Log.v("num", storage.getPhone_number());
        Log.v("address", storage.getAddress());
//        helper.close();
    }

    //function when clicked login button
    public void login(View view) {
        String inputId = editTextId.getText().toString();
        String inputPassword = editTextPassword.getText().toString();

        if(inputId.equals(storage.getId()) && inputPassword.equals(storage.getPassword())){
            Toast.makeText(MainActivity.this,"Welcome"+inputId,Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra(EXTRA_MESSAGE, inputId);
            LoginInfo.getInstance().m_logined = true;
            startActivity(intent);
        }
        else{
            textViewAnswer.setText("Your password is incorrect: " + nLoginFail);
            nLoginFail--;
        }

        if(nLoginFail == -1) {
            buttonLogin.setEnabled(false);
        }
    }

    //function when clicked signup button
    public void signup(View view) {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    public static InfoStorage getStorage(){
        return storage;
    }
    
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, 1);
        } else {}
    }
}

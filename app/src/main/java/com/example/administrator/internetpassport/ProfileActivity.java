package com.example.administrator.internetpassport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {
    /*
    this.id = id;
    this.password = password;
    this.name = name;
    this.sex = sex;
    this.nationality = nationality;
    this.phone_number = phone_number;
    this.address = address;
    this.email = email;
    this.birthday = birthday;
     */
    private TextView textViewId;
    private Button buttonChangeProfile;
    private EditText editTextName;
    private EditText editTextSex;
    private EditText editTextNationality;
    private EditText editTextPhoneNumber;
    private EditText editTextAddress;
    private EditText editTextEmail;
    private EditText editTextBirthday;

    private InfoStorage storage;

    float x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        storage = MainActivity.getStorage();

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String id = "WELCOME "+intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        textViewId = findViewById(R.id.textViewId);
        buttonChangeProfile = findViewById(R.id.buttonChangeProfile);
        editTextName = findViewById(R.id.editTextName);
        editTextSex = findViewById(R.id.editTextSex);
        editTextNationality = findViewById(R.id.editTextNationality);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextBirthday = findViewById(R.id.editTextBirthday);

        textViewId.setText("Welcome "+storage.getName());
        editTextName.setText(storage.getName());
        editTextSex.setText(storage.getSex());
        editTextNationality.setText(storage.getNationality());
        editTextPhoneNumber.setText(storage.getPhone_number());
        editTextAddress.setText(storage.getAddress());
        editTextEmail.setText(storage.getEmail());
        editTextBirthday.setText(storage.getBirthday());

        buttonChangeProfile.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View view){
               storage.setName(editTextName.getText().toString());
               storage.setSex(editTextSex.getText().toString());
               storage.setNationality(editTextNationality.getText().toString());
               storage.setPhone_number(editTextPhoneNumber.getText().toString());
               storage.setAddress(editTextAddress.getText().toString());
               storage.setEmail(editTextEmail.getText().toString());
               storage.setBirthday(editTextBirthday.getText().toString());
               Toast.makeText(ProfileActivity.this,"Changed Profile",Toast.LENGTH_SHORT).show();
           }
        });
    }

    public boolean onTouchEvent(MotionEvent touchevent){
        switch (touchevent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchevent.getX();
                y1 = touchevent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchevent.getX();
                y2 = touchevent.getY();
                if(x1 > x2+100){
                    Toast.makeText(ProfileActivity.this,"Id Page",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ProfileActivity.this,IdActivity.class));
                }
                else if(y1-300 > y2){
                    Toast.makeText(ProfileActivity.this,"Log out",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ProfileActivity.this,MainActivity.class));
                }
                break;
        }
        return false;
    }

}
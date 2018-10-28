package com.example.administrator.internetpassport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            test_method();
            Toast.makeText(getApplicationContext(), "Try clause", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Catch clause", Toast.LENGTH_LONG).show();
        }
    }

    public void test_method() throws SQLException {
//        DatabaseHelper helper = new DatabaseHelper(this);
//        Dao<InfoStorage, Long> infoDao = helper.getDao(InfoStorage.class);
        InfoStorage storage = new InfoStorage("asd","ljw","Male","South Korea", "010-1234-5678", "KAIST");
//        infoDao.create(storage); // create문에서 SQLException 오류 발생
        Log.v("name", storage.getName());
        Log.v("id", storage.getId());
        Log.v("sex", storage.getSex());
        Log.v("nation", storage.getNationality());
        Log.v("num", storage.getPhone_number());
        Log.v("address", storage.getAddress());
//        helper.close();
    }
}

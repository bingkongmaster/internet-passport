package com.example.administrator.internetpassport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class IdActivity extends AppCompatActivity {

    float x1, x2, y1, y2;
    ListView listViewSites;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id);

        listViewSites = (ListView)findViewById(R.id.listViewSites);

        String[] astronomicalSigns = {"♈","♉","♊","♋","♌","♍","♎","♏","♐","♑","♒","♓"};
        String[] websites = {"google.com","yahoo.com","naver.com","daum.net"};
        addAstronomicalSigns(websites,astronomicalSigns);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, websites);
        listViewSites.setAdapter(adapter);
    }
    public void addAstronomicalSigns(String[] array, String[] astronomicalSigns){
        for(int i = 0; i < array.length; i++)
            array[i] = astronomicalSigns[i%astronomicalSigns.length] + " " + array[i];
        return;
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
                if(x1+100 < x2){
                    Toast.makeText(IdActivity.this,"Profile Page",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(IdActivity.this,ProfileActivity.class));
                }
                else if(y1-300 > y2){
                    Toast.makeText(IdActivity.this,"Log out",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(IdActivity.this,MainActivity.class));
                }
                break;
        }
        return false;
    }
}

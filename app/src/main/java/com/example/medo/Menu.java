package com.example.medo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Menu extends AppCompatActivity {
    Challenge challenge;
    Myprofile myprofile;
    Ranking ranking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ranking=new Ranking();
        challenge = new Challenge();
        myprofile = new Myprofile();


        getSupportFragmentManager().beginTransaction().replace(R.id.container, challenge).commit(); //처음화면
        BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);
        bottom_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.first_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, ranking).commit();
                        return true;
                   case R.id.second_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, challenge).commit();
                        return true;
                    case R.id.third_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, myprofile).commit();
                        return true;

                }
                return false;
            }
        });
    }
}
package com.example.medo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Menu extends AppCompatActivity {
    Ranking ranking;
    Myprofile myprofile;
    ItemFragment itemFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ranking = new Ranking();
        myprofile = new Myprofile();
        itemFragment=new ItemFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, ranking).commit(); //처음화면
        BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);
        bottom_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.first_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, ranking).commit();
                        return true;
                    case R.id.second_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, itemFragment).commit();
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
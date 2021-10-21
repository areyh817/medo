package com.example.medo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText joinName, joinPw, joinId;
    TextView joinLogin;
    Button joinBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        // findViewById
        joinName = findViewById(R.id.joinName);
        joinPw = findViewById(R.id.joinPw);
        joinId = findViewById(R.id.joinId);
        joinLogin = findViewById(R.id.joinLogin);
        joinBtn = findViewById(R.id.joinBtn);


        // 로그인을 클릭시 로그인화면으로 전환
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChallengeActivity.class);
                startActivity(intent);
            }
        });


    }
}
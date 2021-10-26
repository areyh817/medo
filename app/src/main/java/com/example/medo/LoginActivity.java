package com.example.medo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText loginId, loginPw;
    Button loginBtn;
    TextView loginJoin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // findViewById
        loginId = findViewById(R.id.loginId);
        loginPw = findViewById(R.id.loginPw);
        loginBtn = findViewById(R.id.loginBtn);
        loginJoin = findViewById(R.id.loginJoin);


        // 회원가입 클릭시 회원가입 화면으로 전환
        loginJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });


        // 로그인버튼 클릭시 로그인이 되면서 메인화면으로 이동 (로그인 정보 확인할 때 SQLite or FireBase 사용)
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 사용자로부터 입력받은 아이디, 비밀번호 값 저장
                String uid = loginId.getText().toString();
                String upw = loginPw.getText().toString();

                // 임시 아이디, 비밀번호를 통해 로그인이 되는지 확인
                if(uid.equals("mirim") && upw.equals("1111")){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    loginId.setText("");
                    loginPw.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    loginId.setText("");
                    loginPw.setText("");
                }

            }
        });

    }
}
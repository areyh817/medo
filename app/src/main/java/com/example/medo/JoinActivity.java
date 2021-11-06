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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JoinActivity extends AppCompatActivity {

    EditText edtId, edtPw, edtName;
    TextView joinLogin;
    Button btnReg;
    FirebaseDatabase db;            // 파이어베이스 객체
    DatabaseReference refUser;      // 데이터베이스 레퍼런스

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_join);

        // findViewById
        edtId = findViewById(R.id.edtID);
        edtName = findViewById(R.id.edtName);
        edtPw = findViewById(R.id.edtPW);
        joinLogin = findViewById(R.id.joinLogin);
        btnReg = findViewById(R.id.btnReg);

        db = FirebaseDatabase.getInstance();    // 읽고쓰기 데이터베이스 객체 가져오기
        refUser = db.getReference("user"); // User에 데이터를 읽고 쓰기위한 레퍼런스 객체


        // 가입버튼을 눌렀을 땐
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 입력받아오기
                String id = edtId.getText().toString();
                String pw = edtPw.getText().toString();
                String name = edtName.getText().toString();

                User user = new User(id, pw, name); // User객체 생성
                refUser.child(id).setValue(user);   // refUser에 id로 자식노드를 만들고, User객체 저장
                Toast.makeText(getApplicationContext(), "가입성공", Toast.LENGTH_SHORT).show();

                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                in.putExtra("id", id);
                setResult(RESULT_OK, in);

                finish();
            }
        });
        joinLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}
package com.example.medo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JoinActivity extends BaseActivity {

    private static final String TAG = "JoinActivity";
    EditText mEmailText, mPasswordText, mName;
    Button mregisterBtn;
    TextView joinLogin;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        //파이어베이스를 위한
        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");

        mEmailText = findViewById(R.id.edtID);
        mPasswordText = findViewById(R.id.edtPW);
        mName = findViewById(R.id.edtName);
        mregisterBtn = findViewById(R.id.btnReg);
        joinLogin = findViewById(R.id.joinLogin);

        //파이어베이스 user 로 접글

        //가입버튼 클릭리스너   -->  firebase에 데이터를 저장한다.
        mregisterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //String 변수에 내용을 넣어줌
                String strName = mName.getText().toString();
                String strPw = mPasswordText.getText().toString();
                String strEmail = mEmailText.getText().toString();
                //사용자 데이터베이스에 등록
                mAuth.createUserWithEmailAndPassword(strEmail, strPw).addOnCompleteListener(JoinActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //성공시
                        if (task.isSuccessful()) {
                            //사용자 정보 저장
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            UserData userData = new UserData();
                            userData.setIdToken(firebaseUser.getUid());
                            userData.setName(strName);
                            userData.setEmail(firebaseUser.getEmail());

                            mDatabaseRef.child("UserData").child(firebaseUser.getUid()).setValue(userData);
                            Toast.makeText(JoinActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                            startActivity(intent);

                        }
                        //실패시
                        else {
                            Toast.makeText(JoinActivity.this, "실패하셨습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        joinLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
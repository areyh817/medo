package com.example.medo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText edtId, edtPw;
    Button loginBtn;
    TextView loginJoin;

    FirebaseDatabase db;        // 파이어베이스 객체
    DatabaseReference refUser;  // 데이터베이스 레퍼런스

    String id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // findViewById
        edtId = findViewById(R.id.edtID);
        edtPw = findViewById(R.id.edtPW);
        loginBtn = findViewById(R.id.loginBtn);
        loginJoin = findViewById(R.id.loginJoin);

        db = FirebaseDatabase.getInstance();        // 읽고쓰기 데이터베이스 객체가져오기
        refUser = db.getReference("user");      // user에 데이터를 읽고 쓰기 위한 레퍼런스 객체


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refUser.addValueEventListener(new ValueEventListener() {    // 데이터를 검색하기 위한 리스너
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(edtId.getText().toString()).exists()){
                            User u = snapshot.child(edtId.getText().toString()).getValue(User.class);

                            if(u.getPw().equals(edtPw.getText().toString())){
                                Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), Menu.class);
                                i.putExtra("id", edtId.getText().toString());
                                startActivity(i);
                                edtId.setText("");
                                edtPw.setText("");
                            } else {
                                Toast.makeText(getApplicationContext(), "패스워드가 다릅니다.", Toast.LENGTH_SHORT).show();;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });
            }
        });


        // 회원가입 클릭시 회원가입 화면으로 전환
        loginJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            id = data.getStringExtra("id");
            edtId.setText(id);
        }
    }
}
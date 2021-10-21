package com.example.medo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ListActivity extends AppCompatActivity {
    ListView list;
    String[] title={"코딩테스트 1문제 풀기", "독서 1시간","기술블로그 작성","깃허브 커밋하기","아침 5시 기상","독서포트폴리오 작성","프로젝트 1시간 진행"};
    ArrayAdapter<String> ar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_list);

        list=findViewById(R.id.list);
        ar=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,title);
        list.setAdapter(ar);



    }
}
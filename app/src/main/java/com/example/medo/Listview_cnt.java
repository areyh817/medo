
package com.example.medo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medo.placeholder.ProgresCount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Listview_cnt extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> arr_room = new ArrayList<>();
    int rankingdata = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //파이어베이스를 위한
        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Challenge");
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        
        
        // Listview를 위한
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr_room);
        // ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);

        ListView listview = (ListView) findViewById(R.id.listView_cnt) ;
        listview.setAdapter(listViewAdapter) ;


        // ChallengeAdd에서 UserData에서 볼러온 name값을 대조해야함
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Rakingdata");
        mDatabaseRef.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listViewAdapter.clear();
                String rakingdata_name = dataSnapshot.child("data").getValue(String.class);
                for (DataSnapshot messageData : dataSnapshot.getChildren()){
                    // if문 조건 다시 줘야함
                    ChallengeData challengedata = messageData.getValue(ChallengeData.class);
                    listViewAdapter.add(challengedata.getTitle());

                    // notifyDataSetChanged를 안해주면 ListView 갱신이 안됨
                    listViewAdapter.notifyDataSetChanged();
                    // ListView 의 위치를 마지막으로 보내주기 위함
                    listview.setSelection(listViewAdapter.getCount() - 1);
                    rankingdata = listViewAdapter.getCount();
                    Log.d("mydata", String.valueOf(rankingdata));
                }


                CountData cdata = new CountData(rankingdata);
                cdata.setCnt(rankingdata);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


     }

}
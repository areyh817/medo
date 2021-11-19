package com.example.medo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Ranking extends Fragment {
    TextView txt_date;
    ArrayList<Rank> ranks;
    ListView listview_rank;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;
    private static CustomAdapter_ranking customAdapter;


    String get_name;
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy/M/d HH:mm");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //현재시간 설정
        View rootView = inflater.inflate(R.layout.fragment_ranking, container, false);
        txt_date = (TextView) rootView.findViewById(R.id.txt_date);
        Date date = new Date();
        String time = mFormat.format(date);
        txt_date.setText(time);
        //기본 데이터
        ranks = new ArrayList<>();



        //사용자 불러오기
        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Ranking");
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        mDatabaseRef.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            get_name = dataSnapshot.child("testdata").child("name").getValue(String.class);
                int get_cnt = dataSnapshot.child("testdata").child("cnt").getValue(int.class);
                // Toast.makeText(getContext(), get_name, Toast.LENGTH_SHORT).show();



/*

                for(int i=0; i<4; i++){

                   // ranks.add(new Rank("1위",get_name,"3개"));
                }
*/


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });




        ranks.add(new Rank("1위","테스트","3개"));




        listview_rank = (ListView) rootView.findViewById(R.id.listview_rank);
        customAdapter = new CustomAdapter_ranking(getContext(),ranks);
        listview_rank.setAdapter(customAdapter);


        return rootView;
    }
}


//data class
class Rank {
    private String rank, name;
    private String cnt;
    public Rank( String rank, String name,String cnt) {
        this.rank = rank;
        this.name = name;
        this.cnt = cnt;
    }

    public String getRank() {
        return rank;
    }

    public String getCnt() {
        return cnt;
    }
    public String getName() {
        return name;
    }

}
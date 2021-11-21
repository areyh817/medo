package com.example.medo;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.Transliterator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medo.placeholder.ProgresCount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Myprofile extends Fragment {
    private static final Object MODE_PRIVATE = "medo";
    Menu activity;
    TextView userId , txt_date, txt_challenging, txt_challengok;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;
    ArrayList<Profile> profile;
    private ArrayList<String> arr_room = new ArrayList<>();
    private ListView listView;
    Button logout;
    static int progres_cnt;
    int Okcnt=0;
    int success_cnt;
    int isuccess_cnt = 0;
    String ssuccess_cnt;
    RankingData rdata2;
    BasicData bdata2;
    final String[] user_name = new String[1];
    private SharedPreferences preferences;
    int ccc;
    CountData cdata;

    private static CustomAdapter_myprofile customAdapter;
    private DatabaseReference refdatabase;
    DatabaseReference postRef = FirebaseDatabase.getInstance().getReference("Ranking");


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // View view = inflater.inflate(R.layout.fragment_myprofile, container, false);
        View rootView = inflater.inflate(R.layout.fragment_myprofile, container, false);
        View cntView = inflater.inflate(R.layout.listview_cnt, container, false);
        listView = rootView.findViewById(R.id.listView_profile);
        preferences = this.getActivity().getSharedPreferences("medoCount", Context.MODE_PRIVATE);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arr_room);
        listView.setAdapter(listViewAdapter);


        //로그아웃기능
        logout = rootView.findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });


        //파이어베이스를 위한
        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");

        userId = rootView.findViewById(R.id.txtUser);
        FirebaseUser firebaseUser = mAuth.getCurrentUser();


        mDatabaseRef.child("UserData").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String get_name = dataSnapshot.child("name").getValue(String.class);
                if(get_name==""||get_name==null){
                    get_name = "미도";
                }
                TextView userId = rootView.findViewById(R.id.txtUser);
                userId.setText(get_name + "님");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });




        // 지금 현재 로그인 되어있는 사람의 이름 값을 UserData에서 가져와야함
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");

        UserData userdata = new UserData();

        mDatabaseRef.child("UserData").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String get_name = dataSnapshot.child("name").getValue(String.class);
                if(get_name==""||get_name==null){
                    get_name = "미도[테스트]";
                }

                // userdata.setName(get_name);
                user_name[0] = get_name;
                // Log를 찍었을 때 값이 잘 들어감.
                // Log.d("user_name", user_name[0]);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        // 현재순위, 도전진행, 도전성공을 프로필에 띄워주기
        txt_challenging = rootView.findViewById(R.id.txt_challenging);

        txt_challengok = rootView.findViewById(R.id.txt_challengok);


        // ChallengeAdd에서 UserData에서 볼러온 name값을 대조해야함
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ChallengeAdd");
        mDatabaseRef.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listViewAdapter.clear();
                String challengeadd_name = dataSnapshot.child("title").getValue(String.class);
                for (DataSnapshot messageData : dataSnapshot.getChildren()){
                    // if문 조건 다시 줘야함
                    ChallengeData challengedata = messageData.getValue(ChallengeData.class);
                    listViewAdapter.add(challengedata.getTitle());

                    // notifyDataSetChanged를 안해주면 ListView 갱신이 안됨
                    listViewAdapter.notifyDataSetChanged();
                    // ListView 의 위치를 마지막으로 보내주기 위함
                    listView.setSelection(listViewAdapter.getCount() - 1);
                    progres_cnt = listViewAdapter.getCount();
                    ProgresCount pcnt = new ProgresCount(progres_cnt);
                    txt_challenging.setText("도전진행\n"+(pcnt.getCnt())+"개");

                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







        //초기 랭킹리스트 카운트 불러오기
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("RankingList");
        mDatabaseRef.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String rakingdata_name = dataSnapshot.child("data").getValue(String.class);
                isuccess_cnt = 0;
                for (DataSnapshot messageData : dataSnapshot.getChildren()){
                    isuccess_cnt++;
                    Log.d("mydata", String.valueOf(isuccess_cnt));
                }
                cdata = new CountData();
                cdata.setCnt(isuccess_cnt);
                txt_challengok.setText("도전성공\n"+cdata.getCnt()+"개");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });





        //도전확인하기
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View diglogView = View.inflate(getActivity(), R.layout.dlg_challenge_check, null);
                String data = (String) parent.getItemAtPosition(position);
                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                dlg.setTitle("챌린지 확인");
                dlg.setView(diglogView);









                dlg.setPositiveButton("실천",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {



                


                                mDatabaseRef = FirebaseDatabase.getInstance().getReference("RankingList");
                                RankingData radata = new RankingData(user_name[0], firebaseUser.getUid(), data);
                                mDatabaseRef.child(firebaseUser.getUid()).push().setValue(radata);

                                // ChallengeAdd에서 UserData에서 볼러온 name값을 대조해야함
                                mDatabaseRef = FirebaseDatabase.getInstance().getReference("RankingList");
                                mDatabaseRef.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String rakingdata_name = dataSnapshot.child("data").getValue(String.class);
                                        isuccess_cnt = 0;
                                        for (DataSnapshot messageData : dataSnapshot.getChildren()){
                                            isuccess_cnt++;
                                            Log.d("mydata", String.valueOf(isuccess_cnt));
                                        }

                                         cdata = new CountData();
                                        cdata.setCnt(isuccess_cnt);
                                        ccc=cdata.getCnt();
                                        txt_challengok.setText("도전성공\n"+cdata.getCnt()+"개");

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }

                                });

                                Toast.makeText(getContext(), "실천 완료!", Toast.LENGTH_SHORT).show();
                                CountData cdata = new CountData();
                                cdata.setCnt(isuccess_cnt);
                                mDatabaseRef = FirebaseDatabase.getInstance().getReference("Ranking");
                                BasicData bdata = new BasicData(cdata.getCnt(), firebaseUser.getUid(), user_name[0]);
                                mDatabaseRef.child(firebaseUser.getUid()).child("testdata").setValue(bdata);
                                mDatabaseRef.push().setValue(bdata2);


                            }

                        }); dlg.setNegativeButton("취소", null);

                dlg.show();


            }
        });


        return rootView;

    }

    private void getPreferences(){
        //getString(KEY,KEY값이 없을때 대체)
        isuccess_cnt = preferences.getInt("cnt", success_cnt);
        Log.d("insuccess_cnt", String.valueOf(isuccess_cnt));
    }

}



//data class
class Profile {
    private String name;
    public Profile(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }


}
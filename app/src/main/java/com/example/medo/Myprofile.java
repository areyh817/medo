package com.example.medo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Myprofile extends Fragment {
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
    Transaction.Result success_cnt;
    int isuccess_cnt = 0;
    RankingData rdata2;

    private static CustomAdapter_myprofile customAdapter;




    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // View view = inflater.inflate(R.layout.fragment_myprofile, container, false);
        View rootView = inflater.inflate(R.layout.fragment_myprofile, container, false);
        listView = rootView.findViewById(R.id.listView_profile);

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
        //사용자


        //리스트뷰 값이 안들어간다 여기에 유저가 선택한 도전들 띄우게 해주쇼
        /*profile = new ArrayList<>();
        profile.add(new Profile("깃허브 커밋하기"));
        profile.add(new Profile("깃허브 커밋하기"));
        profile.add(new Profile("깃허브 커밋하기"));*/


        
        // 지금 현재 로그인 되어있는 사람의 이름 값을 UserData에서 가져와야함
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");
        final String[] user_name = new String[1];
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
                }


                ProgresCount pcnt = new ProgresCount(progres_cnt);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



       /* listView_profile = (ListView) view.findViewById(R.id.listView_profile);
        customAdapter = new CustomAdapter_myprofile(getContext(),profile);
        listView_profile.setAdapter(customAdapter);*/

        ProgresCount pcnt = new ProgresCount(progres_cnt);

        // 현재순위, 도전진행, 도전성공을 프로필에 띄워주기
        txt_challenging = rootView.findViewById(R.id.txt_challenging);
        txt_challenging.setText("도전진행\n"+pcnt.getCnt()+"개");
        txt_challengok = rootView.findViewById(R.id.txt_challengok);
        txt_challengok.setText("도전성공\n"+Okcnt+"개");

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

                                mDatabaseRef = FirebaseDatabase.getInstance().getReference("Ranking");

                                DatabaseReference upvotesRef = mDatabaseRef.child(firebaseUser.getUid()).child("testdata").child("cnt");
                                upvotesRef.runTransaction(new Transaction.Handler() {
                                    @Override
                                    public Transaction.Result doTransaction(MutableData mutableData) {
                                        Integer currentValue = mutableData.getValue(Integer.class);
                                        if (currentValue == null) {
                                            mutableData.setValue(1);
                                        } else {
                                            mutableData.setValue(currentValue + 1);
                                        }

                                        success_cnt = Transaction.success(mutableData);
                                        return Transaction.success(mutableData);
                                    }

                                    @Override
                                    public void onComplete(
                                            DatabaseError databaseError, boolean committed, DataSnapshot dataSnapshot) {
                                        System.out.println("Transaction completed");
                                    }
                                });


                                mDatabaseRef = FirebaseDatabase.getInstance().getReference("Ranking");
                                /*CountData cdata = new CountData();*/
                                RankingData rdata = new RankingData(user_name[0], success_cnt, firebaseUser.getUid());
                                mDatabaseRef.child(firebaseUser.getUid()).child("testdata").setValue(rdata);
                                mDatabaseRef.push().setValue(rdata2);


                                //값 삭제 못함
                                //mDatabaseRef.child(firebaseUser.getUid()).removeValue();
                                Toast.makeText(getContext(), "실천 완료!", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("취소", null);

                dlg.show();

            }
        });
        return rootView;

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
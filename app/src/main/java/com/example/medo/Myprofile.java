package com.example.medo;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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


        //?????? ????????? ????????????
  /*      Long time = new Date().getTime();
        Date today = new Date();
        Date tomorrow = new Date(time - time % (24 * 60 * 60 * 1000));
        String tt=today.toString();
        String dd=tomorrow.toString();*/

        //?????? ?????????
/*        Toast.makeText(getContext(), tt, Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), dd, Toast.LENGTH_SHORT).show();*/



        // View view = inflater.inflate(R.layout.fragment_myprofile, container, false);
        View rootView = inflater.inflate(R.layout.fragment_myprofile, container, false);
        View cntView = inflater.inflate(R.layout.listview_cnt, container, false);
        listView = rootView.findViewById(R.id.listView_profile);
        preferences = this.getActivity().getSharedPreferences("medoCount", Context.MODE_PRIVATE);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arr_room);
        listView.setAdapter(listViewAdapter);


        //??????????????????
        logout = rootView.findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });


        //????????????????????? ??????
        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");

        userId = rootView.findViewById(R.id.txtUser);
        FirebaseUser firebaseUser = mAuth.getCurrentUser();


        mDatabaseRef.child("UserData").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String get_name = dataSnapshot.child("name").getValue(String.class);
                if(get_name==""||get_name==null){
                    get_name = "??????";
                }
                TextView userId = rootView.findViewById(R.id.txtUser);
                userId.setText(get_name + "???");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });




        // ?????? ?????? ????????? ???????????? ????????? ?????? ?????? UserData?????? ???????????????
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");

        UserData userdata = new UserData();

        mDatabaseRef.child("UserData").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String get_name = dataSnapshot.child("name").getValue(String.class);
                if(get_name==""||get_name==null){
                    get_name = "??????[?????????]";
                }

                // userdata.setName(get_name);
                user_name[0] = get_name;
                // Log??? ????????? ??? ?????? ??? ?????????.
                // Log.d("user_name", user_name[0]);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        // ????????????, ????????????, ??????????????? ???????????? ????????????
        txt_challenging = rootView.findViewById(R.id.txt_challenging);

        txt_challengok = rootView.findViewById(R.id.txt_challengok);


        // ChallengeAdd?????? UserData?????? ????????? name?????? ???????????????
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ChallengeAdd");
        mDatabaseRef.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listViewAdapter.clear();
                String challengeadd_name = dataSnapshot.child("title").getValue(String.class);
                for (DataSnapshot messageData : dataSnapshot.getChildren()){
                    // if??? ?????? ?????? ?????????
                    ChallengeData challengedata = messageData.getValue(ChallengeData.class);
                    listViewAdapter.add(challengedata.getTitle());

                    // notifyDataSetChanged??? ???????????? ListView ????????? ??????
                    listViewAdapter.notifyDataSetChanged();
                    // ListView ??? ????????? ??????????????? ???????????? ??????
                    listView.setSelection(listViewAdapter.getCount() - 1);
                    progres_cnt = listViewAdapter.getCount();
                    ProgresCount pcnt = new ProgresCount(progres_cnt);


                    txt_challenging.setText("????????????\n"+(pcnt.getCnt())+"???");

                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







        //?????? ??????????????? ????????? ????????????
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
                CountData cdata = new CountData();
                cdata.setCnt(isuccess_cnt);
                txt_challengok.setText("????????????\n"+cdata.getCnt()+"???");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });




        //??????????????????
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View diglogView = View.inflate(getActivity(), R.layout.dlg_challenge_check, null);
                String data = (String) parent.getItemAtPosition(position);




                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                dlg.setTitle("????????? ??????");
                dlg.setView(diglogView);


                dlg.setPositiveButton("??????",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                             /*   if(tt==dd){
                                    mDatabaseRef.child(firebaseUser.getUid()).setValue(null);
                                }*/
                                //????????? ????????? ??????
                                mDatabaseRef = FirebaseDatabase.getInstance().getReference("ChallengeAdd");
                                mDatabaseRef.child(firebaseUser.getUid()).child(data).setValue(null);

                               // view.setBackgroundColor(Color.GREEN);
                                mDatabaseRef = FirebaseDatabase.getInstance().getReference("RankingList");
                                RankingData radata = new RankingData(user_name[0], firebaseUser.getUid(), data);
                                mDatabaseRef.child(firebaseUser.getUid()).push().setValue(radata);

                                // ChallengeAdd?????? UserData?????? ????????? name?????? ???????????????
                                mDatabaseRef = FirebaseDatabase.getInstance().getReference("RankingList");


                                //?????? ?????????
                               /* if(tt==dd){
                                    mDatabaseRef.child(firebaseUser.getUid()).setValue(null);
                                }*/
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
                                        txt_challengok.setText("????????????\n"+cdata.getCnt()+"???");

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }

                                });

                                Toast.makeText(getContext(), "?????? ??????!", Toast.LENGTH_SHORT).show();
                                CountData cdata = new CountData();
                                cdata.setCnt(isuccess_cnt);
                                mDatabaseRef = FirebaseDatabase.getInstance().getReference("Ranking");

                                BasicData bdata = new BasicData(cdata.getCnt(), firebaseUser.getUid(), user_name[0]);
                                //mDatabaseRef.child(firebaseUser.getUid()).child("rank").setValue(bdata);
                                mDatabaseRef.push().setValue(bdata);




                            }

                        }); dlg.setNegativeButton("??????", null);

                dlg.show();


            }
        });


        return rootView;

    }

    private void getPreferences(){
        //getString(KEY,KEY?????? ????????? ??????)
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
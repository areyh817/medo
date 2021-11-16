package com.example.medo;

import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Myprofile extends Fragment {
    Menu activity;
    TextView userId , txt_date;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;
    ArrayList<Profile> profile;
    private ArrayList<String> arr_room = new ArrayList<>();
    private ListView listView;
    Button logout;

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
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        // ChallengeAdd에서 UserData에서 볼러온 name값을 대조해야함
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ChallengeAdd");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listViewAdapter.clear();
                String challengeadd_name = dataSnapshot.child("name").getValue(String.class);
                for (DataSnapshot messageData : dataSnapshot.getChildren()){
                    // if문 조건 다시 줘야함
                    if (user_name[0] == challengeadd_name) {

                        ChallengeData challengedata = messageData.getValue(ChallengeData.class);
                        listViewAdapter.add(challengedata.getTitle());

                        // notifyDataSetChanged를 안해주면 ListView 갱신이 안됨
                        listViewAdapter.notifyDataSetChanged();
                        // ListView 의 위치를 마지막으로 보내주기 위함
                        listView.setSelection(listViewAdapter.getCount() - 1);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



       /* listView_profile = (ListView) view.findViewById(R.id.listView_profile);
        customAdapter = new CustomAdapter_myprofile(getContext(),profile);
        listView_profile.setAdapter(customAdapter);*/
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
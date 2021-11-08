package com.example.medo;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Myprofile extends Fragment {
    Menu activity;
    TextView userId;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_myprofile, container, false);
        View view = inflater.inflate(R.layout.fragment_myprofile, container, false);

        //파이어베이스를 위한
        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");

        userId = view.findViewById(R.id.txtUser);

        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        mDatabaseRef.child("UserData").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String get_name = dataSnapshot.child("name").getValue(String.class);
                if(get_name==""||get_name==null){
                    get_name = "미도";
                }
                TextView userId = view.findViewById(R.id.txtUser);
                userId.setText(get_name + "님");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        //사용자


        return view;
    }
}
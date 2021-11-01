package com.example.medo;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Myprofile extends Fragment {
    Menu activity;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_myprofile , container, false);
        ImageView profile_img=rootView.findViewById(R.id.profile_img);


        return rootView;


    }
}
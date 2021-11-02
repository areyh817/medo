package com.example.medo;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class Challenge extends Fragment {

    private static String[] title = new String[] {"홍길동", "이순신", "강감찬", "유관순", "김유신","을지문덕", "김춘추"};
    Button start_btn;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_challenge_list , container, false);
        Menu activity = (Menu) getActivity();
        start_btn = rootView.findViewById(R.id.start_btn);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1); //어댑터를 리스트 뷰에 적용 setListAdapter(adapter);
        //setListAdapter(adapter);


        return rootView;
    }




}
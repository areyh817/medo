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

import androidx.fragment.app.Fragment;


import java.util.ArrayList;

public class Ranking extends Fragment {

    ArrayList<Rank> ranks;
    ListView listview_rank;
    private static CustomAdapter_ranking customAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.fragment_ranking, container, false);


        //기본 데이터
        ranks = new ArrayList<>();
        ranks.add(new Rank("조조"));



        listview_rank = (ListView) rootView.findViewById(R.id.listview_rank);
        customAdapter = new CustomAdapter_ranking(getContext(),ranks);
        listview_rank.setAdapter(customAdapter);


        return rootView;
    }
}


//data class
class Rank {
    private String name;
    public Rank(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }


}
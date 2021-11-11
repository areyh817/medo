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


import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Ranking extends Fragment {
    TextView txt_date;
    ArrayList<Rank> ranks;
    ListView listview_rank;
    private static CustomAdapter_ranking customAdapter;

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
        ranks.add(new Rank("조조"));



        listview_rank = (ListView) rootView.findViewById(R.id.listview_rank);
        customAdapter = new CustomAdapter_ranking(getContext(),ranks);
        listview_rank.setAdapter(customAdapter);


        return rootView;
    }
}


//data class
class Rank {
    private String rank, name;
    private int cnt;
    public Rank(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }


}
package com.example.medo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Challenge extends Fragment {

    ArrayList<Actor> actors;
    ListView customListView;
    private static CustomAdapter_challenge customAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_challengeitem_list, container, false);

        //data를 가져와서 어답터와 연결해 준다. 서버에서 가져오는게 대부분 이다.
        actors = new ArrayList<>();
        actors.add(new Actor("기술블로그 작성"));
        actors.add(new Actor("깃허브 커밋하기"));
        actors.add(new Actor("독서 1시간 "));
        actors.add(new Actor("프로젝트 2시간 진행하기."));
        actors.add(new Actor("6시 기상하기"));
        actors.add(new Actor("전공 스터디 진행하기"));


        customListView = (ListView) rootView.findViewById(R.id.listView_custom);
        customAdapter = new CustomAdapter_challenge(getContext(),actors);
        customListView.setAdapter(customAdapter);
        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                //각 아이템을 분간 할 수 있는 position과 뷰
                String selectedItem = (String) view.findViewById(R.id.txt_challege).getTag().toString();
                Toast.makeText(getContext(), "Clicked: " + position +" " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}


//data class
class Actor {
    private String name;


    public Actor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
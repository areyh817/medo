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

public class Challenge extends Fragment {



    ArrayList<Actor> actors;
    ListView customListView;
    private static CustomAdapter_challenge customAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Button btnClick ,start_btn;
        TextView roomName, roomdesc;
        View diglogView, toastView;




        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_challengeitem_list, container, false);
        start_btn=rootView.findViewById(R.id.start_btn);
        roomName = rootView.findViewById(R.id.roomName);
        roomdesc = rootView.findViewById(R.id.roomdesc);
        btnClick = rootView.findViewById(R.id.btnClick);

        btnClick.setOnClickListener(new View.OnClickListener() {
            EditText edtName, edtDesc;
            @Override
            public void onClick(View view) {
                View diglogView = View.inflate(getActivity(), R.layout.dlg_challenge, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                dlg.setTitle("챌린지 방 개설하기");
                dlg.setView(diglogView);
                dlg.setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               edtName = diglogView.findViewById(R.id.edtName);
                                edtDesc = diglogView.findViewById(R.id.edtDesc);
                                /*roomName.setText(edtName.getText().toString());
                                roomdesc.setText(edtDesc.getText().toString());*/
                                    // 아이템 추가.
                                  actors.add(new Actor(edtName.getText().toString()));

                                Toast.makeText(getContext(),"추가 되었습니다", Toast.LENGTH_SHORT).show();
                            }
                        });
                dlg.setNegativeButton("취소", null);

                dlg.show();
            }
        });

        //기본 데이터
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
package com.example.medo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Challenge extends Fragment {

    // DB에 저장시킬 데이터를 입력받는 EditText
    private  EditText edtName, edtDesc;
    private TextView txtName, txtDesc;
    private ListView listView;


    Button btnClick;
    ChallengeData challengeData;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> arr_room = new ArrayList<>();
    ChallengeAdd cdata2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_challenge, container, false);
        listView = rootView.findViewById(R.id.listView_custom);

        btnClick = rootView.findViewById(R.id.btnClick);

        //파이어베이스를 위한
        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Challenge");
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arr_room);
        listView.setAdapter(listViewAdapter);


        // 데이터의 변화가 있을 때 출력해옴
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // 데이터를 읽어올 때 모든 데이터를 읽어오기때문에 List 를 초기화해주는 작업이 필요하다.
                listViewAdapter.clear();
                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    //String msg = messageData.getValue().toString();
                    ChallengeData challengedata = messageData.getValue(ChallengeData.class);
                    listViewAdapter.add(challengedata.getTitle());
                }
                // notifyDataSetChanged를 안해주면 ListView 갱신이 안됨
                listViewAdapter.notifyDataSetChanged();
                // ListView 의 위치를 마지막으로 보내주기 위함
                listView.setSelection(listViewAdapter.getCount() - 1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnClick.setOnClickListener(new View.OnClickListener() {


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

                                FirebaseUser firebaseUser = mAuth.getCurrentUser();

                                String edt_name = edtName.getText().toString();
                                String edt_desc = edtDesc.getText().toString();

                                ChallengeData challengedata = new ChallengeData(edt_name, edt_desc, firebaseUser.getUid());
                                mDatabaseRef.push().setValue(challengedata);

                                Toast.makeText(getContext(),"추가 되었습니다", Toast.LENGTH_SHORT).show();
                            }
                        });
                dlg.setNegativeButton("취소", null);

                dlg.show();
            }
        });


        //도전하기
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View diglogView = View.inflate(getActivity(), R.layout.dlg_challenge_add, null);
                txtName = diglogView.findViewById(R.id.txtName);
                txtDesc = diglogView.findViewById(R.id.txtDesc);
                String data = (String) parent.getItemAtPosition(position);

                //파이어베이스에서  설명 가져오세요!
                txtName.setText(data);

                FirebaseUser firebaseUser = mAuth.getCurrentUser();

                // String user_name;

                mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String get_desc = dataSnapshot.child("content").getValue(String.class);
                        if(get_desc==""||get_desc==null){
                            get_desc = "미림인 여러분 ! 성실히 챌린지에 임해주시길 바랍니다 !";
                        }
                        txtDesc.setText(get_desc);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

                final String[] user_name = new String[1];
                // 이름을 따로 불러옴
                mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");


                mDatabaseRef.child("UserData").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {


                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String get_name = dataSnapshot.child("name").getValue(String.class);
                        if(get_name==""||get_name==null){
                            get_name = "미도[테스트]";
                        }
                        user_name[0] = get_name;
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });


                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                dlg.setTitle("챌린지 도전");
                dlg.setView(diglogView);
                dlg.setPositiveButton("도전",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                mDatabaseRef = FirebaseDatabase.getInstance().getReference("ChallengeAdd");

                                mDatabaseRef.child(firebaseUser.getUid()).child(data).child("title").addListenerForSingleValueEvent(new ValueEventListener() {


                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String value = dataSnapshot.getValue(String.class);
                                            ChallengeAdd challengeAdd = new ChallengeAdd(user_name[0], firebaseUser.getUid(), data);
                                            mDatabaseRef.child(firebaseUser.getUid()).child(data).setValue(challengeAdd);
                                            Toast.makeText(getContext(), "챌린지 신청 완료! \n (전에 신청했던 방은 다시 재입장 처리됩니다.)", Toast.LENGTH_LONG).show();



                                        // mDatabaseRef.push().setValue(bdata2);
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                    }
                                });

                                // Toast.makeText(getContext(), "챌린지 신청 완료!", Toast.LENGTH_SHORT).show();
                            }
                        });
                dlg.setNegativeButton("취소", null);

                dlg.show();

            }
        });
        return rootView;
    }
}
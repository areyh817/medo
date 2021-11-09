package com.example.medo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter_ranking extends ArrayAdapter implements AdapterView.OnItemClickListener {

    private Context context;
    private List list;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
    }

    class ViewHolder {
        public TextView txt_rank;
        public TextView txt_name;
        public TextView txt_count;

    }

    public CustomAdapter_ranking(Context context, ArrayList list){
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.fragment_rankingitem, parent, false);
        }

        viewHolder = new ViewHolder();
        viewHolder.txt_rank = (TextView) convertView.findViewById(R.id.txt_rank);
        viewHolder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
        viewHolder.txt_count = (TextView) convertView.findViewById(R.id.txt_count);


        final Rank ranks = (Rank) list.get(position);
        //viewHolder.txt_rank.setText(ranks.getName());
       // viewHolder.txt_name.setText(ranks.getName());
        //viewHolder.txt_count.setText(ranks.getName());
        //viewHolder.tv_summary.setText(actor.getSummary());
/*        Glide
                .with(context)
                .load(actor.getThumb_url())
                .centerCrop()
                .apply(new RequestOptions().override(250, 350))
                .into(viewHolder.iv_thumb);
        viewHolder.tv_name.setTag(actor.getName());*/


//        //아이템 클릭 방법2 - 클릭시 아이템 반전 효과가 안 먹힘
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, " " + actor.getName(), Toast.LENGTH_SHORT).show();
//            }
//        });

        //Return the completed view to render on screen
        return convertView;
    }
}
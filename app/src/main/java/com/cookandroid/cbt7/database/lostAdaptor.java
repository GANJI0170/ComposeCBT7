package com.cookandroid.cbt7.database;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cookandroid.cbt7.LookupActivity;
import com.cookandroid.cbt7.R;

import java.util.ArrayList;

public class lostAdaptor extends RecyclerView.Adapter<lostAdaptor.CustomViewHolder> {
    private ArrayList<articlelostList> arrayList;
    private Context context;
    private int n;

    public lostAdaptor(ArrayList<articlelostList> arrayList, Context context, int n) {
        this.arrayList = arrayList;
        this.context = context;
        this.n = n;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.articlelist_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getLost_image())
                .into(holder.lost_image);
        holder.lost_title.setText(arrayList.get(position).getLost_title());
        holder.lost_title.setTextSize(Dimension.SP, 15);
        holder.lost_title.setTextColor(Color.parseColor("#000000"));
        holder.lost_title.setPaintFlags(holder.lost_title.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
        holder.lost_keyword.setText(arrayList.get(position).getLost_keyword());
        holder.lost_keyword.setTextSize(Dimension.SP, 15);
        holder.lost_keyword.setTextColor(Color.parseColor("#000000"));
        holder.lost_id.setText(arrayList.get(position).getLost_id());
        holder.lost_post_date.setText(arrayList.get(position).getLost_post_date());
        holder.lost_hits.setText(arrayList.get(position).getLost_hits());
        holder.lost_num.setText(arrayList.get(position).getLost_number());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String articleNum = holder.lost_num.getText().toString();

                Intent intent = new Intent(context, LookupActivity.class);
                intent.putExtra("boardType", "분실물 게시판");
                intent.putExtra("articleNum", articleNum);
                context.startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK));
            }
        });
        if(n==0) {
            holder.btnlatouy.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView lost_image;
        TextView lost_title;
        TextView lost_keyword;
        TextView lost_id;
        TextView lost_post_date;
        TextView lost_hits;
        TextView lost_num;
        Button articledelete, articleretouch;
        LinearLayout btnlatouy;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.lost_image = itemView.findViewById(R.id.board_image);
            this.lost_title = itemView.findViewById(R.id.board_title);
            this.lost_keyword = itemView.findViewById(R.id.board_keyword);
            this.lost_id = itemView.findViewById(R.id.board_id);
            this.lost_post_date = itemView.findViewById(R.id.board_post_date);
            this.lost_hits = itemView.findViewById(R.id.board_hits);
            this.lost_num = itemView.findViewById(R.id.board_Num);

            this.btnlatouy = itemView.findViewById(R.id.btnlayout);
            this.articledelete = itemView.findViewById(R.id.articledelete);
            this.articleretouch = itemView.findViewById(R.id.articleretouch);
        }
    }
}

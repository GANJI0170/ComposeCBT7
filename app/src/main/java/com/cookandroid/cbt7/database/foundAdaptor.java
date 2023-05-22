package com.cookandroid.cbt7.database;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cookandroid.cbt7.R;

import java.util.ArrayList;

public class foundAdaptor extends RecyclerView.Adapter<foundAdaptor.CustomViewHolder> {
    private ArrayList<articlefoundList> arrayList;
    private Context context;

    public foundAdaptor(ArrayList<articlefoundList> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
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
                .load(arrayList.get(position).getFound_image())
                .into(holder.found_image);
        holder.found_title.setText(arrayList.get(position).getFound_title());
        holder.found_title.setTextSize(Dimension.SP, 15);
        holder.found_title.setTextColor(Color.parseColor("#000000"));
        holder.found_title.setPaintFlags(holder.found_title.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
        holder.found_keyword.setText(arrayList.get(position).getFound_keyword());
        holder.found_keyword.setTextSize(Dimension.SP, 15);
        holder.found_keyword.setTextColor(Color.parseColor("#000000"));
        holder.found_id.setText(arrayList.get(position).getFound_id());
        holder.found_post_date.setText(arrayList.get(position).getFound_post_date());
        holder.found_hits.setText(arrayList.get(position).getFound_hits());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView found_image;
        TextView found_title;
        TextView found_keyword;
        TextView found_id;
        TextView found_post_date;
        TextView found_hits;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.found_image = itemView.findViewById(R.id.board_image);
            this.found_title = itemView.findViewById(R.id.board_title);
            this.found_keyword = itemView.findViewById(R.id.board_keyword);
            this.found_id = itemView.findViewById(R.id.board_id);
            this.found_post_date = itemView.findViewById(R.id.board_post_date);
            this.found_hits = itemView.findViewById(R.id.board_hits);
        }
    }
}

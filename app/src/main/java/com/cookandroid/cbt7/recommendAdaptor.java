package com.cookandroid.cbt7;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class recommendAdaptor extends RecyclerView.Adapter<recommendAdaptor.MyViewHolder> {

    private List<found_list> dataList;
    private Context context;
    private int n;

    public void setData(List<found_list> dataList) {
        this.dataList = dataList;
    }

    public recommendAdaptor(List<found_list> dataList, Context context, int n) {
        this.dataList = dataList;
        this.context = context;
        this.n = n;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.articlelist_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        found_list item = dataList.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getFound_image())
                .into(holder.imageView);
        holder.titleTextView.setText(item.getFound_title());
        holder.titleTextView.setTextSize(Dimension.SP, 15);
        holder.titleTextView.setTextColor(Color.parseColor("#000000"));
        holder.titleTextView.setPaintFlags(holder.titleTextView.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
        holder.keywordTextView.setText(item.getFound_keyword());
        holder.keywordTextView.setTextSize(Dimension.SP, 15);
        holder.keywordTextView.setTextColor(Color.parseColor("#000000"));
        holder.idTextView.setText(item.getFound_id());
        holder.postDateTextView.setText(item.getFound_post_date());
        holder.hitsTextView.setText(item.getFound_hits());
        holder.found_num.setText(item.getFound_number());


        // 이미지 설정
        // 예시: 이미지가 문자열 형태로 저장되어 있다고 가정하고 이미지 로딩 라이브러리를 사용하여 이미지 표시
        // Picasso 또는 Glide와 같은 이미지 로딩 라이브러리를 사용하면 편리합니다.
        // Picasso: implementation 'com.squareup.picasso:picasso:2.71828'
        // Glide: implementation 'com.github.bumptech.glide:glide:4.12.0'
        // 이미지 로딩 라이브러리를 사용하지 않고 직접 구현하는 경우에는 해당 부분을 적절히 수정하세요.
        // 예시 코드:
        // Picasso.get().load(item.getFound_image()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String articleNum = holder.found_num.getText().toString();

                Intent intent = new Intent(context, LookupActivity.class);
                intent.putExtra("boardType", "습득물 게시판");
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
        return dataList != null ? dataList.size() : 0;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView keywordTextView;
        TextView idTextView;
        TextView postDateTextView;
        TextView hitsTextView;
        ImageView imageView;
        TextView found_num;
        LinearLayout btnlatouy;


        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.board_title);
            keywordTextView = itemView.findViewById(R.id.board_keyword);
            idTextView = itemView.findViewById(R.id.board_id);
            postDateTextView = itemView.findViewById(R.id.board_post_date);
            hitsTextView = itemView.findViewById(R.id.board_hits);
            imageView = itemView.findViewById(R.id.board_image);
            found_num = itemView.findViewById(R.id.board_Num);
            btnlatouy = itemView.findViewById(R.id.btnlayout);


        }
    }
}


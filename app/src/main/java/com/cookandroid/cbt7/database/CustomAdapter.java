package com.cookandroid.cbt7.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cookandroid.cbt7.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<chatList> arrayList;
    private Context context;
    //어댑터에서 액티비티 액션을 가져올 때 context가 필요한데 어댑터에는 context가 없다.
    //선택한 액티비티에 대한 context를 가져올 때 필요하다.

    public CustomAdapter(ArrayList<chatList> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    //실제 리스트뷰가 어댑터에 연결된 다음에 뷰 홀더를 최초로 만들어낸다.
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatlist_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getName())
                .into(holder.ct_profile);
        holder.ct_name.setText(arrayList.get(position).getName());
        holder.ct_chat.setText(arrayList.get(position).getChatMessage());
        holder.ct_date.setText(arrayList.get(position).getChatDate());
    }

    @Override
    public int getItemCount() {
        // 삼항 연산자
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView ct_profile;
        TextView ct_name;
        TextView ct_did;
        TextView ct_chat;
        TextView ct_date;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ct_profile = itemView.findViewById(R.id.ct_profile);
            this.ct_name = itemView.findViewById(R.id.ct_name);
            this.ct_did = itemView.findViewById(R.id.ct_did);
            this.ct_chat = itemView.findViewById(R.id.ct_chat);
            this.ct_date = itemView.findViewById(R.id.ct_date);
        }
    }
}

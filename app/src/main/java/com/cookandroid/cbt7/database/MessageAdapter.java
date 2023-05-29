package com.cookandroid.cbt7.database;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.cbt7.R;

import java.util.List;

//public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
//    private List<Message> messages;
//
//    public MessageAdapter(List<Message> messages) {
//        this.messages = messages;
//    }
//
//    @NonNull
//    @Override
//    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
//        return new MessageViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
//        Message message = messages.get(position);
//        holder.messageTextView.setText(message.getContent());
//    }
//
//    @Override
//    public int getItemCount() {
//        return messages.size();
//    }
//    public class MessageViewHolder extends RecyclerView.ViewHolder {
//        public TextView messageTextView;
//
//        public MessageViewHolder(@NonNull View itemView) {
//            super(itemView);
//            messageTextView = itemView.findViewById(R.id.messageTextView);
//        }
//    }
//}
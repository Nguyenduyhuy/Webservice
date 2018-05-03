package com.example.ndh.webservice;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by NDH on 4/23/2018.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private ArrayList<Message> messageArrayList;
    private String nameReciver;
    private String mId;
    String idSend;


    public ChatAdapter(ArrayList<Message> messageArrayList, String nameReciver, String mId) {
        this.messageArrayList = messageArrayList;
        this.nameReciver = nameReciver;
        this.mId = mId;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if(mId) {
//            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_message_right, parent, false);
//            return new ChatViewHolder(v);
//        }
//        else{
//            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_message_left, parent, false);
//            return new ChatViewHolder(v);
//        }

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_message_left, parent, false);
        return new ChatViewHolder(v);


    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        Message message =messageArrayList.get(position);
         idSend = message.getIdSend();
        if(mId.equals(idSend)){
            holder.txtMessage.setBackgroundResource(R.drawable.custom_text_bg_white);
            holder.txtMessage.setTextColor(Color.BLACK);
            holder.txtMessName.setText("Ban");
        }else{
            holder.txtMessage.setBackgroundResource(R.drawable.custom_text_bg);
            holder.txtMessage.setTextColor(Color.WHITE);
            holder.txtMessName.setText(nameReciver);
        }
        holder.txtMessage.setText(message.getContent());

    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView txtMessage,txtMessName;
        public ChatViewHolder(View itemView) {
            super(itemView);
            txtMessage = itemView.findViewById(R.id.txtMessage);
            txtMessName = itemView.findViewById(R.id.txtMessName);
        }
    }

}

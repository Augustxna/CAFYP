package com.example.cafyp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafyp.R;
import com.example.cafyp.message.Chats2nd;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Message2ndAdapter extends RecyclerView.Adapter<Message2ndAdapter.ViewHolder> {

    private Context applicationcontext;
    private String myname;
    private List<Chats2nd> mChat2nd;

    public static final int MSG_TYPE_LEFT_2ND = 0;
    public static final int MSG_TYPE_RIGHT_2ND = 1;

    public Message2ndAdapter(Context applicationcontext, List<Chats2nd> mChat2nd, String myname) {
        this.applicationcontext = applicationcontext;
        this.mChat2nd = mChat2nd;
        this.myname = myname;
    }

    @NonNull
    @Override
    public Message2ndAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT_2ND) {

            View view = LayoutInflater.from(applicationcontext).inflate(R.layout.chat2nd_item_right, parent, false);
            return new Message2ndAdapter.ViewHolder(view);
        } else {

            View view = LayoutInflater.from(applicationcontext).inflate(R.layout.chat2nd_item_left, parent, false);
            return new Message2ndAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull Message2ndAdapter.ViewHolder holder, int position) {

        Chats2nd chats2nd = mChat2nd.get(position);
        holder.show_message2nd.setText(chats2nd.getMessage());
    }

    @Override
    public int getItemCount() {
        return mChat2nd.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView show_message2nd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            show_message2nd = itemView.findViewById(R.id.show_message2nd);
        }
    }

    public int getItemViewType(int position) {
        if (mChat2nd.get(position).getSender().equals(myname)) {
            return MSG_TYPE_RIGHT_2ND;
        } else {
            return MSG_TYPE_LEFT_2ND;
        }
    }
}

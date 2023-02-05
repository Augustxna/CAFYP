package com.example.cafyp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafyp.R;
import com.example.cafyp.message.Chats;

import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private Context applicationcontext;
    private String myname;
    private List<Chats> mChat;
    //SessionManager sessionManager = new SessionManager(context.getApplicationContext());

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    public MessageAdapter(Context applicationcontext, List<Chats> mChat, String myname) {
        this.applicationcontext = applicationcontext;
        this.mChat = mChat;
        this.myname = myname;
    }


    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {

            View view = LayoutInflater.from(applicationcontext).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);


        } else {

            View view = LayoutInflater.from(applicationcontext).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);


        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {

        Chats chats = mChat.get(position);

        holder.show_message.setText(chats.getMessage());
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView show_message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
        }
    }

    public int getItemViewType(int position) {


        //HashMap<String, String> usersDetails = sessionManager.getUserDataDetailFromSession();
        //String name = usersDetails.get(SessionManager.KEY_FULLNAME);

        // String name = "tester";

        if (mChat.get(position).getSender().equals(myname)) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }


    }

}

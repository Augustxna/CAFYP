package com.example.cafyp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafyp.Admin.RelatedMemberControl;
import com.example.cafyp.R;
import com.example.cafyp.domain.AllMemberData;
import com.example.cafyp.domain.ChatlistData;
import com.example.cafyp.message.MessageActivity2nd;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ChatlistAdapter extends RecyclerView.Adapter<ChatlistAdapter.ViewHolder> {

    Context context;
    List<ChatlistData> mChatlistList;
    List<ChatlistData> filteredChatlistList;

    public ChatlistAdapter(Context context, List<ChatlistData> mChatlistList) {
        this.context = context;
        this.mChatlistList = mChatlistList;
        this.filteredChatlistList = mChatlistList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.chatlist_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatlistAdapter.ViewHolder holder, int position) {

        ChatlistData chatlistData = filteredChatlistList.get(position);
        holder.relatedmembername.setText(chatlistData.getFullName());
        holder.relatedmemberphoneno.setText(chatlistData.getPhoneno());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MessageActivity2nd.class);
                intent.putExtra("memberid", filteredChatlistList.get(position).getPhoneno());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredChatlistList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView relatedmemberphoneno, relatedmembername;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            relatedmembername = itemView.findViewById(R.id.chatlist_name_text);
            relatedmemberphoneno = itemView.findViewById(R.id.chatlist_phoneno_text);
        }
    }

    public Filter getfilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String Key = constraint.toString();
                if (Key.isEmpty()) {
                    filteredChatlistList = mChatlistList;
                } else {
                    List<ChatlistData> lstFiltered = new ArrayList<>();
                    for (ChatlistData row : mChatlistList) {
                        if (row.getFullName().toLowerCase().contains(Key.toLowerCase())) {
                            lstFiltered.add(row);
                        }
                    }

                    filteredChatlistList = lstFiltered;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredChatlistList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                filteredChatlistList = (List<ChatlistData>) results.values;
                notifyDataSetChanged();

            }
        };
    }
}

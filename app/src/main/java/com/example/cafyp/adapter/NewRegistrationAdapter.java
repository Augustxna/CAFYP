package com.example.cafyp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafyp.R;
import com.example.cafyp.domain.NewRegistrationData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NewRegistrationAdapter extends RecyclerView.Adapter<NewRegistrationAdapter.ViewHolder> {

    Context context;
    List<NewRegistrationData> mNewRegisterList;
    RecyclerNewRegisterClickListener listener;

    public NewRegistrationAdapter(Context context, List<NewRegistrationData> mNewRegisterList, RecyclerNewRegisterClickListener listener) {
        this.context = context;
        this.mNewRegisterList = mNewRegisterList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.newregistrationitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        NewRegistrationData newRegistrationData = mNewRegisterList.get(position);
        holder.userphoneno.setText(newRegistrationData.getPhoneNo());
        holder.userfullname.setText(newRegistrationData.getFullName());

    }

    @Override
    public int getItemCount() {
        return mNewRegisterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView userphoneno, userfullname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userphoneno = itemView.findViewById(R.id.user_newregister_text);
            userfullname = itemView.findViewById(R.id.user_newregister_nametext);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            listener.onClick(v, getAdapterPosition());
        }
    }


    public interface RecyclerNewRegisterClickListener {
        void onClick(View v, int position);

    }
}

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
import com.example.cafyp.domain.RelatedHeirsData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RelatedHeirsAdapter extends RecyclerView.Adapter<RelatedHeirsAdapter.ViewHolder> {

    Context context;
    List<RelatedHeirsData> mRelatedHeirList;

    public RelatedHeirsAdapter(Context context, List<RelatedHeirsData> mRelatedHeirList) {
        this.context = context;
        this.mRelatedHeirList = mRelatedHeirList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.relatedheirs_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RelatedHeirsAdapter.ViewHolder holder, int position) {

        RelatedHeirsData relatedHeirsData = mRelatedHeirList.get(position);
        holder.relatedheirname.setText(relatedHeirsData.getName());
        holder.relatedheiric.setText(relatedHeirsData.getIc());
        holder.relatedheiremail.setText(relatedHeirsData.getEmail());
        holder.relatedheirphone.setText(relatedHeirsData.getPhoneno());
        holder.relatedheirrelationship.setText(relatedHeirsData.getRelationship());

    }

    @Override
    public int getItemCount() {
        return mRelatedHeirList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView relatedheirname, relatedheiric, relatedheiremail, relatedheirphone, relatedheirrelationship;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            relatedheirname = itemView.findViewById(R.id.relatedheir_name);
            relatedheiric = itemView.findViewById(R.id.relatedheir_ic);
            relatedheiremail = itemView.findViewById(R.id.relatedheir_email);
            relatedheirphone = itemView.findViewById(R.id.relatedheir_phone);
            relatedheirrelationship = itemView.findViewById(R.id.relatedheir_relationship);
        }
    }
}

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

import com.example.cafyp.Admin.RelatedPaymentControl;
import com.example.cafyp.R;
import com.example.cafyp.domain.RelatedPaymentData;

import java.util.ArrayList;
import java.util.List;

public class RelatedPaymentAdapter extends RecyclerView.Adapter<RelatedPaymentAdapter.ViewHolder> {

    Context context;
    List<RelatedPaymentData> mRelatedPaymentList;
    List<RelatedPaymentData> filteredRelatedPaymentList;
    String userid;

    public RelatedPaymentAdapter(Context context, List<RelatedPaymentData> mRelatedPaymentList, String getrelatedmemberid) {
        this.context = context;
        this.mRelatedPaymentList = mRelatedPaymentList;
        this.filteredRelatedPaymentList = mRelatedPaymentList;
        this.userid = getrelatedmemberid;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.relatedpayment_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RelatedPaymentData relatedPaymentData = filteredRelatedPaymentList.get(position);
        holder.relatedpaymentid.setText(relatedPaymentData.getPid());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, RelatedPaymentControl.class);
                intent.putExtra("paymentid", filteredRelatedPaymentList.get(position).getPid());
                intent.putExtra("userid", userid);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredRelatedPaymentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView relatedpaymentid;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            relatedpaymentid = itemView.findViewById(R.id.relatedmember_payment_text);
        }
    }

    public Filter getfilter(){

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String Key = constraint.toString();
                if (Key.isEmpty()) {
                    filteredRelatedPaymentList = mRelatedPaymentList;
                } else {
                    List<RelatedPaymentData> lstFiltered = new ArrayList<>();
                    for (RelatedPaymentData row : mRelatedPaymentList) {
                        if (row.getPid().toLowerCase().contains(Key.toLowerCase())) {
                            lstFiltered.add(row);
                        }
                    }

                    filteredRelatedPaymentList = lstFiltered;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredRelatedPaymentList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                filteredRelatedPaymentList = (List<RelatedPaymentData>) results.values;
                notifyDataSetChanged();

            }
        };

    }


}

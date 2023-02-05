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
import com.example.cafyp.PaymentMember.MemberPaymentControl;
import com.example.cafyp.PaymentMember.MemberViewPayment;
import com.example.cafyp.R;
import com.example.cafyp.domain.AllMemberData;
import com.example.cafyp.domain.MemberPaymentData;

import java.util.ArrayList;
import java.util.List;

public class MemberPaymentAdapter extends RecyclerView.Adapter<MemberPaymentAdapter.ViewHolder> {


    Context context;
    List<MemberPaymentData> mmPaymentList;
    List<MemberPaymentData> filteredMemberPaymentList;

    public MemberPaymentAdapter(Context context, List<MemberPaymentData> mmPaymentList) {
        this.context = context;
        this.mmPaymentList = mmPaymentList;
        this.filteredMemberPaymentList = mmPaymentList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.member_payment_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MemberPaymentAdapter.ViewHolder holder, int position) {

        MemberPaymentData memberPaymentData = filteredMemberPaymentList.get(position);
        holder._paymentid.setText(memberPaymentData.getPid());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MemberPaymentControl.class);
                intent.putExtra("paymentid", filteredMemberPaymentList.get(position).getPid());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredMemberPaymentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView _paymentid;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            _paymentid = itemView.findViewById(R.id.member_payment_text);
        }
    }
    public Filter getfilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String Key = constraint.toString();
                if (Key.isEmpty()) {
                    filteredMemberPaymentList = mmPaymentList;
                } else {
                    List<MemberPaymentData> lstFiltered = new ArrayList<>();
                    for (MemberPaymentData row : mmPaymentList) {
                        if (row.getPid().toLowerCase().contains(Key.toLowerCase())) {
                            lstFiltered.add(row);
                        }
                    }

                    filteredMemberPaymentList = lstFiltered;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredMemberPaymentList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                filteredMemberPaymentList = (List<MemberPaymentData>) results.values;
                notifyDataSetChanged();

            }
        };
    }
}

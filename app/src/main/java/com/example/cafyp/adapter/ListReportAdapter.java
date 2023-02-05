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
import com.example.cafyp.AdminMain.Allpaymentlist;
import com.example.cafyp.AdminMain.Reportread;
import com.example.cafyp.Database.PaymentUpdateHelpherClass;
import com.example.cafyp.R;
import com.example.cafyp.domain.AllMemberData;
import com.example.cafyp.domain.ListReportData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ListReportAdapter extends RecyclerView.Adapter<ListReportAdapter.ViewHolder> {


    Context context;
    List<ListReportData> mListReportList;
    List<ListReportData> filteredReportList;

    public ListReportAdapter(Context context, List<ListReportData> mListReportList) {
        this.context = context;
        this.mListReportList = mListReportList;
        this.filteredReportList = mListReportList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listofreport_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ListReportData listReportData = filteredReportList.get(position);
        holder.paymentid.setText(listReportData.getPid());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Reportread.class);
                intent.putExtra("paymentid", filteredReportList.get(position).getPid());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredReportList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView paymentid;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            paymentid = itemView.findViewById(R.id.listreport_payment_text);
        }
    }

    public Filter getFilter(){

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String Key = constraint.toString();
                if(Key.isEmpty()){
                    filteredReportList = mListReportList;
                }
                else
                {
                    List<ListReportData> lstFiltered = new ArrayList<>();
                    for (ListReportData row : mListReportList){
                        if(row.getPid().toLowerCase().contains(Key.toLowerCase())){
                            lstFiltered.add(row);
                        }
                    }

                    filteredReportList = lstFiltered;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredReportList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredReportList = (List<ListReportData>)results.values;
                notifyDataSetChanged();
            }
        };


    }

}

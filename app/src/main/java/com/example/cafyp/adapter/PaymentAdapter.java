package com.example.cafyp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafyp.Admin.AdminDashDoard;
import com.example.cafyp.Admin.RelatedMemberControl;
import com.example.cafyp.PaymentAdmin.PaymentControl;
import com.example.cafyp.PaymentAdmin.PaymentDashboard;
import com.example.cafyp.PaymentAdmin.ViewPayment;
import com.example.cafyp.R;
import com.example.cafyp.domain.AllMemberData;
import com.example.cafyp.domain.PaymentData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {

    Context applicationContext;
    List<PaymentData> mPaymentList;
    List<PaymentData> filteredPaymentList;
    String invicode;

    public PaymentAdapter(Context applicationContext, List<PaymentData> mPaymentList, String invicode) {
        this.applicationContext = applicationContext;
        this.mPaymentList = mPaymentList;
        this.filteredPaymentList = mPaymentList;
        this.invicode = invicode;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(applicationContext).inflate(R.layout.payment_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentAdapter.ViewHolder holder, int position) {

        PaymentData paymentData = filteredPaymentList.get(position);
        holder._paymentid.setText(paymentData.getPid());
        holder._paymentdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(applicationContext);
                builder.setTitle("Are your sure??");
                builder.setMessage("Deleted data can't be Undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseDatabase dltdatabase = FirebaseDatabase.getInstance();
                        DatabaseReference dltref = dltdatabase.getReference(invicode).child("Payments").child(filteredPaymentList.get(position).getPid());
                        dltref.removeValue();
                        Toast.makeText(applicationContext,"Deleted", Toast.LENGTH_SHORT).show();
                        applicationContext.startActivity(new Intent(applicationContext,ViewPayment.class));
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(applicationContext,"Canceled", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(applicationContext, PaymentControl.class);
                intent.putExtra("paymentid", filteredPaymentList.get(position).getPid());
                applicationContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return filteredPaymentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView _paymentid;
        ImageView _paymentdelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            _paymentid = itemView.findViewById(R.id.payment_idusername_text);
            _paymentdelete = itemView.findViewById(R.id.payment_delete_button);

        }
    }

    public Filter getfilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String Key = constraint.toString();
                if (Key.isEmpty()) {
                    filteredPaymentList = mPaymentList;
                } else {
                    List<PaymentData> lstFiltered = new ArrayList<>();
                    for (PaymentData row : mPaymentList) {
                        if (row.getPid().toLowerCase().contains(Key.toLowerCase())) {
                            lstFiltered.add(row);
                        }
                    }

                    filteredPaymentList = lstFiltered;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredPaymentList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                filteredPaymentList = (List<PaymentData>) results.values;
                notifyDataSetChanged();

            }
        };
    }

}

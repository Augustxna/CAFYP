package com.example.cafyp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.cafyp.Admin.RelatedMemberControl;
import com.example.cafyp.Admin.RelatedMemberView;
import com.example.cafyp.PaymentAdmin.ViewPayment;
import com.example.cafyp.R;
import com.example.cafyp.domain.AllMemberData;
import com.example.cafyp.domain.RelatedMemberData;
import com.example.cafyp.domain.RelatedPaymentData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RelatedMemberAdapter extends RecyclerView.Adapter<RelatedMemberAdapter.ViewHolder> {

    Context context;
    List<RelatedMemberData> mRelatedList;
    List<RelatedMemberData> filteredRelatedMemeberList;
    String invicode;

    public RelatedMemberAdapter(Context context, List<RelatedMemberData> mRelatedList, String invicode) {
        this.context = context;
        this.mRelatedList = mRelatedList;
        this.filteredRelatedMemeberList = mRelatedList;
        this.invicode = invicode;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.relatedmember_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RelatedMemberData relatedMemberData = filteredRelatedMemeberList.get(position);
        holder.relatedmemberphone.setText(relatedMemberData.getPhoneno());
        holder.relatedmembername.setText(relatedMemberData.getFullName());
        holder._deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are your sure?");
                builder.setMessage("Deleted data can't be Undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DatabaseReference dltrelatedref = FirebaseDatabase.getInstance().getReference(invicode).child("Users").child(filteredRelatedMemeberList.get(position).getPhoneno());
                        DatabaseReference dltoutref = FirebaseDatabase.getInstance().getReference("Members").child(filteredRelatedMemeberList.get(position).getPhoneno());
                        dltrelatedref.removeValue();
                        dltoutref.removeValue();
                        Toast.makeText(context,"Deleted", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, RelatedMemberView.class));
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(context,"Canceled", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, RelatedMemberControl.class);
                intent.putExtra("memberid", filteredRelatedMemeberList.get(position).getPhoneno());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredRelatedMemeberList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView relatedmembername, relatedmemberphone;
        ImageView _deletebtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            relatedmembername = itemView.findViewById(R.id.relatedember_name_text);
            relatedmemberphone = itemView.findViewById(R.id.relatedember_phonenno_text);
            _deletebtn = itemView.findViewById(R.id.delete_button);

        }
    }

    public Filter getfilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String Key = constraint.toString();
                if (Key.isEmpty()) {
                    filteredRelatedMemeberList = mRelatedList;
                } else {
                    List<RelatedMemberData> lstFiltered = new ArrayList<>();
                    for (RelatedMemberData row : mRelatedList) {
                        if (row.getFullName().toLowerCase().contains(Key.toLowerCase())) {
                            lstFiltered.add(row);
                        }
                    }

                    filteredRelatedMemeberList = lstFiltered;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredRelatedMemeberList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                filteredRelatedMemeberList = (List<RelatedMemberData>) results.values;
                notifyDataSetChanged();

            }
        };

    }


}

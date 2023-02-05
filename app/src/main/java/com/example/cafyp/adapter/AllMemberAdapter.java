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
import com.example.cafyp.AdminMainViewMember.MainAdminAllMemberControl;
import com.example.cafyp.R;
import com.example.cafyp.domain.AllMemberData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AllMemberAdapter extends RecyclerView.Adapter<AllMemberAdapter.ViewHolder> {

    Context context;
    List<AllMemberData> mAllList;
    List<AllMemberData> filteredUserDataList;
    String invicode;

    public AllMemberAdapter(Context context, List<AllMemberData> mAllList, String invicode) {
        this.context = context;
        this.mAllList = mAllList;
        this.filteredUserDataList = mAllList;
        this.invicode = invicode;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.allmember_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        AllMemberData allMemberData = filteredUserDataList.get(position);
        holder.allmembername.setText(allMemberData.getFullName());
        holder.allmemberphoneno.setText(allMemberData.getPhoneno());
        holder._deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are your sure?");
                builder.setMessage("Deleted data can't be Undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DatabaseReference dltrelatedref = FirebaseDatabase.getInstance().getReference(invicode).child("Users").child(filteredUserDataList.get(position).getPhoneno());
                        DatabaseReference dltoutref = FirebaseDatabase.getInstance().getReference("Members").child(filteredUserDataList.get(position).getPhoneno());
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

                Intent intent = new Intent(context, MainAdminAllMemberControl.class);
                intent.putExtra("memberid", filteredUserDataList.get(position).getPhoneno());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredUserDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView allmembername, allmemberphoneno;
        ImageView _deletebtn;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            allmembername = itemView.findViewById(R.id.allmember_name_text);
            allmemberphoneno = itemView.findViewById(R.id.allmember_phonenno_text);
            _deletebtn = itemView.findViewById(R.id.delete_button);
        }
    }

    public Filter getfilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String Key = constraint.toString();
                if (Key.isEmpty()) {
                    filteredUserDataList = mAllList;
                } else {
                    List<AllMemberData> lstFiltered = new ArrayList<>();
                    for (AllMemberData row : mAllList) {
                        if (row.getFullName().toLowerCase().contains(Key.toLowerCase())) {
                            lstFiltered.add(row);
                        }
                    }
                    filteredUserDataList = lstFiltered;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredUserDataList;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredUserDataList = (List<AllMemberData>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}

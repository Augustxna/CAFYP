package com.example.cafyp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafyp.Admin.RelatedMemberControl;
import com.example.cafyp.AdminMain.AdminControl;
import com.example.cafyp.AdminMain.ViewAdminBoard;
import com.example.cafyp.PaymentAdmin.ViewPayment;
import com.example.cafyp.R;
import com.example.cafyp.domain.AdminData;
import com.example.cafyp.domain.AllMemberData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder> {
    Context applicationContext;
    List<AdminData> mAdminList;
    List<AdminData> filteredAdminList;
    String invicode;

    public AdminAdapter(Context applicationContext, List<AdminData> mAdminList, String invicode) {
        this.applicationContext = applicationContext;
        this.mAdminList = mAdminList;
        this.filteredAdminList = mAdminList;
        this.invicode = invicode;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(applicationContext).inflate(R.layout.admin_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAdapter.ViewHolder holder, int position) {

        AdminData adminData = filteredAdminList.get(position);
        holder.Ausername.setText(adminData.getUsername());
        holder._deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(applicationContext);
                builder.setTitle("Are your sure?");
                builder.setMessage("Deleted data can't be Undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(invicode).child("Admins").child(filteredAdminList.get(position).getUsername());
                        DatabaseReference mref = FirebaseDatabase.getInstance().getReference("Admins").child(filteredAdminList.get(position).getUsername());
                        mref.removeValue();
                        ref.removeValue();
                        Toast.makeText(applicationContext,"Deleted", Toast.LENGTH_SHORT).show();
                        applicationContext.startActivity(new Intent(applicationContext, ViewAdminBoard.class));
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

                Intent intent = new Intent(applicationContext, AdminControl.class);
                intent.putExtra("adminid", filteredAdminList.get(position).getUsername());
                applicationContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return filteredAdminList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView Ausername;
        ImageView _deletebtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Ausername = itemView.findViewById(R.id.admin_username_text);
            _deletebtn = itemView.findViewById(R.id.delete_button);

        }

    }

    public Filter getfilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String Key = constraint.toString();
                if (Key.isEmpty()) {
                    filteredAdminList = mAdminList;
                } else {
                    List<AdminData> lstFiltered = new ArrayList<>();
                    for (AdminData row : mAdminList) {
                        if (row.getUsername().toLowerCase().contains(Key.toLowerCase())) {
                            lstFiltered.add(row);
                        }
                    }

                    filteredAdminList = lstFiltered;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredAdminList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                filteredAdminList = (List<AdminData>) results.values;
                notifyDataSetChanged();

            }
        };
    }



}

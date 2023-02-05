package com.example.cafyp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafyp.PaymentAdmin.PaymentControl;
import com.example.cafyp.PaymentAdmin.ViewPayment;
import com.example.cafyp.R;
import com.example.cafyp.User.Heirs.HeirControlBoard;
import com.example.cafyp.User.Heirs.user_heirs;
import com.example.cafyp.domain.HeirData;
import com.example.cafyp.domain.PaymentData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class HeirAdapter extends RecyclerView.Adapter<HeirAdapter.ViewHolder> {
    Context applicationContext;
    List<HeirData> mHeirList;
    List<HeirData> filteredHeirList;
    String invicode;
    String phonenumber;

    public HeirAdapter(Context applicationContext, List<HeirData> mHeirList, String invicode, String phonenumber) {
        this.applicationContext = applicationContext;
        this.mHeirList = mHeirList;
        this.filteredHeirList = mHeirList;
        this.invicode = invicode;
        this.phonenumber = phonenumber;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(applicationContext).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeirAdapter.ViewHolder holder, int position) {

        HeirData heirData = filteredHeirList.get(position);
        holder.Hname.setText(heirData.getName());
        holder._deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(applicationContext);
                builder.setTitle("Are your sure?");
                builder.setMessage("Deleted data can't be Undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseDatabase heirdatabase = FirebaseDatabase.getInstance();
                        DatabaseReference heirref = heirdatabase.getReference(invicode).child("Heirs").child(phonenumber).child(filteredHeirList.get(position).getName());
                        heirref.removeValue();
                        Toast.makeText(applicationContext,"Deleted", Toast.LENGTH_SHORT).show();
                        applicationContext.startActivity(new Intent(applicationContext, user_heirs.class));

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

                Intent intent = new Intent(applicationContext, HeirControlBoard.class);
                intent.putExtra("heirname", filteredHeirList.get(position).getName());
                applicationContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mHeirList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Hname;
        ImageView _deletebtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Hname = itemView.findViewById(R.id.user_heirs_nametext);
            _deletebtn = itemView.findViewById(R.id.delete_button);
        }
    }


    public Filter getfilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String Key = constraint.toString();
                if (Key.isEmpty()) {
                    filteredHeirList = mHeirList;
                } else {
                    List<HeirData> lstFiltered = new ArrayList<>();
                    for (HeirData row : mHeirList) {
                        if (row.getName().toLowerCase().contains(Key.toLowerCase())) {
                            lstFiltered.add(row);
                        }
                    }

                    filteredHeirList = lstFiltered;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredHeirList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                filteredHeirList = (List<HeirData>) results.values;
                notifyDataSetChanged();

            }
        };
    }

}

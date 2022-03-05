package com.kevinnt.myaddressbook.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kevinnt.myaddressbook.MainActivity;
import com.kevinnt.myaddressbook.R;
import com.kevinnt.myaddressbook.models.Employee;
import com.kevinnt.myaddressbook.models.EmployeeTableModel;

import java.util.ArrayList;

public class AddressBookAdapter extends RecyclerView.Adapter<AddressBookAdapter.AddressBookViewHolder> {
    private Context context;
    private ArrayList<EmployeeTableModel> employeesList;

    public AddressBookAdapter(Context context, ArrayList<EmployeeTableModel> employeesList) {
        this.context = context;
        this.employeesList = employeesList;
    }

    @NonNull
    @Override
    public AddressBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_list_address_book, parent, false);

        return new AddressBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressBookViewHolder holder, int position) {

        EmployeeTableModel emp = employeesList.get(position);

        holder.tv_address_name.setText(emp.getName());
        holder.tv_address_city.setText(emp.getCity());

        Glide.with(context).load(emp.getProfileUrl()).circleCrop()
                .into(holder.iv_address_profile);

        holder.btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + emp.getCall()));
                ((MainActivity)context).startActivity(intent);
            }
        });

        holder.btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"+emp.getEmail()));
                ((MainActivity)context).startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return employeesList.size();
    }


    public class AddressBookViewHolder extends RecyclerView.ViewHolder{

        private ImageView iv_address_profile;
        private TextView tv_address_name, tv_address_city;
        private Button btn_call, btn_email;

        public AddressBookViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_address_profile = itemView.findViewById(R.id.iv_address_profile);
            tv_address_name = itemView.findViewById(R.id.tv_address_name);
            tv_address_city = itemView.findViewById(R.id.tv_address_city);
            btn_call = itemView.findViewById(R.id.btn_call);
            btn_email = itemView.findViewById(R.id.btn_email);

        }
    }

}

package com.kevinnt.myaddressbook.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kevinnt.myaddressbook.MainActivity;
import com.kevinnt.myaddressbook.R;
import com.kevinnt.myaddressbook.fragments.EmployeeDetailsFragment;
import com.kevinnt.myaddressbook.models.Employee;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class EmployeesAdapter extends RecyclerView.Adapter<EmployeesAdapter.EmployeeViewHolder> {

    private Context context;
    private ArrayList<Employee> employeesList;

    public EmployeesAdapter(Context context, ArrayList<Employee> employeesList) {
        this.context = context;
        this.employeesList = employeesList;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_list_employees, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        holder.tv_name.setText(employeesList.get(position).getName().getFirst() +" "+ employeesList.get(position).getName().getLast());
        holder.tv_location.setText(employeesList.get(position).getLocation().getCity() + ", " + employeesList.get(position).getLocation().getCountry());
        holder.tv_phone.setText(employeesList.get(position).getPhone().replaceAll("[^\\d]", "") +
                " / "+ employeesList.get(position).getCell().replaceAll("[^\\d.]", ""));
        holder.tv_member_since.setText(new SimpleDateFormat("MMM yyyy").format(employeesList.get(position).getRegistered().getDate()));

        Glide.with(context).load(employeesList.get(position).getPicture().getMedium()).circleCrop()
                .into(holder.iv_profile);

        holder.cl_list_employee_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)context).setSelectedEmployeeId(employeesList.get(position).getEmployeeId());
                ((MainActivity) context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container, new EmployeeDetailsFragment(context), null)
                        .addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return employeesList.size();
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_profile;
        private TextView tv_name, tv_location, tv_phone, tv_member_since;
        private ConstraintLayout cl_list_employee_container;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            cl_list_employee_container = itemView.findViewById(R.id.cl_list_employee_container);
            iv_profile = itemView.findViewById(R.id.iv_profile);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_location = itemView.findViewById(R.id.tv_location);
            tv_phone = itemView.findViewById(R.id.tv_phone);
            tv_member_since = itemView.findViewById(R.id.tv_member_since);
        }
    }

    public ArrayList<Employee> getEmployeesList() {
        return employeesList;
    }

    public void setEmployeesList(ArrayList<Employee> employeesList) {
        this.employeesList = employeesList;
        notifyDataSetChanged();
    }
}

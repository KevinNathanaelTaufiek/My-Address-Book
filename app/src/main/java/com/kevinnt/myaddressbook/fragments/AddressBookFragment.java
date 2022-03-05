package com.kevinnt.myaddressbook.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kevinnt.myaddressbook.R;
import com.kevinnt.myaddressbook.adapters.AddressBookAdapter;
import com.kevinnt.myaddressbook.adapters.EmployeesAdapter;
import com.kevinnt.myaddressbook.database.DatabaseHelper;
import com.kevinnt.myaddressbook.models.Employee;
import com.kevinnt.myaddressbook.models.EmployeeTableModel;

import java.util.ArrayList;
import java.util.List;

public class AddressBookFragment extends Fragment {

    private Context context;
    private RecyclerView rv_address_employees;
    private AddressBookAdapter adapter;

    public AddressBookFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_address_book, container, false);

        rv_address_employees = view.findViewById(R.id.rv_address_employees);

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        List<EmployeeTableModel> employees = databaseHelper.getAllEmployee();

        adapter = new AddressBookAdapter(context, (ArrayList<EmployeeTableModel>) employees);
        rv_address_employees.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        rv_address_employees.setAdapter(adapter);


        return view;
    }
}
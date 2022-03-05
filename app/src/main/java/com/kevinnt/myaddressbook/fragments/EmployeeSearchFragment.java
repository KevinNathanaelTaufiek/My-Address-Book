package com.kevinnt.myaddressbook.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kevinnt.myaddressbook.R;
import com.kevinnt.myaddressbook.adapters.EmployeesAdapter;
import com.kevinnt.myaddressbook.models.Employee;
import com.kevinnt.myaddressbook.models.GetEmployeeEndpoint;
import com.kevinnt.myaddressbook.retrofit_client.EmployeeClient;
import com.kevinnt.myaddressbook.retrofit_interface.EmployeeInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeSearchFragment extends Fragment {

    private Context context;

    private EditText et_search;
    private Button btn_search;
    private TextView tv_username, tv_user_id;

    private RecyclerView rv_employees;
    private EmployeesAdapter adapter;
    private EmployeeInterface employeeInterface;

    private List<Employee> employeesList;

    public EmployeeSearchFragment(Context context) {
        this.context = context;
    }

    public void getAllEmployee(){
        Call<GetEmployeeEndpoint> employeeEndpointCall = employeeInterface.getAllEmployees();
        employeeEndpointCall.enqueue(new Callback<GetEmployeeEndpoint>() {
            @Override
            public void onResponse(Call<GetEmployeeEndpoint> call, Response<GetEmployeeEndpoint> response) {
                employeesList = new ArrayList<>(response.body().getEmployees());
                tv_user_id.setText(response.body().getNim());
                tv_username.setText(response.body().getNama());
                bindDataToAdapter();
            }

            @Override
            public void onFailure(Call<GetEmployeeEndpoint> call, Throwable t) {
                employeesList = null;
            }
        });

    }

    private void bindDataToAdapter() {

        if(employeesList != null){

            adapter = new EmployeesAdapter(getContext(), (ArrayList<Employee>) employeesList);
            rv_employees.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
            rv_employees.setAdapter(adapter);

            btn_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<Employee> searchedEmployee = new ArrayList<>();
                    String searchString = et_search.getText().toString().toLowerCase();

                    for (Employee emp: employeesList) {
                        String empName = (emp.getName().getFirst() +" "+ emp.getName().getLast()).toLowerCase();
                        if(empName.contains(searchString)){
                            searchedEmployee.add(emp);
                        }
                    }

                    adapter.setEmployeesList(searchedEmployee);

                }
            });

        }
        else{
            Toast.makeText(context, "Something is wrong!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_employee_search, container, false);

        rv_employees = view.findViewById(R.id.rv_employees);
        et_search = view.findViewById(R.id.et_search);
        btn_search = view.findViewById(R.id.btn_search);
        tv_username = view.findViewById(R.id.tv_username);
        tv_user_id = view.findViewById(R.id.tv_user_id);
        employeeInterface = EmployeeClient.getInstance().create(EmployeeInterface.class);

        getAllEmployee();


        return view;
    }

}
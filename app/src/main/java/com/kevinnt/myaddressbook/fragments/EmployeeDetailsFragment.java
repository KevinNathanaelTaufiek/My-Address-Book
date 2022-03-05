package com.kevinnt.myaddressbook.fragments;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kevinnt.myaddressbook.MainActivity;
import com.kevinnt.myaddressbook.R;
import com.kevinnt.myaddressbook.database.DatabaseHelper;
import com.kevinnt.myaddressbook.models.Employee;
import com.kevinnt.myaddressbook.models.EmployeeTableModel;
import com.kevinnt.myaddressbook.models.GetEmployeeEndpoint;
import com.kevinnt.myaddressbook.retrofit_client.EmployeeClient;
import com.kevinnt.myaddressbook.retrofit_interface.EmployeeInterface;

import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeDetailsFragment extends Fragment {

    private Context context;
    private TextView tv_detail_name, tv_detail_city, tv_detail_phone, tv_detail_member_since, tv_detail_email;
    private Button btn_add;

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;

    private Employee selectedEmployee = null;
    private EmployeeInterface employeeInterface = EmployeeClient.getInstance().create(EmployeeInterface.class);

    public EmployeeDetailsFragment(Context context) {
        this.context = context;
    }

    private void getEmployeeDetail(int id) {
        Call<GetEmployeeEndpoint> employeeEndpointCall = employeeInterface.getEmployeeDetail(id);
        employeeEndpointCall.enqueue(new Callback<GetEmployeeEndpoint>() {
            @Override
            public void onResponse(Call<GetEmployeeEndpoint> call, Response<GetEmployeeEndpoint> response) {
                selectedEmployee = response.body().getEmployees().get(0);
                bindDataToView();
                setMapLocation();
            }

            @Override
            public void onFailure(Call<GetEmployeeEndpoint> call, Throwable t) {
                selectedEmployee = null;
            }
        });
    }

    private void bindDataToView() {
        if (selectedEmployee != null){

            tv_detail_name.setText(selectedEmployee.getName().getFirst() + " " + selectedEmployee.getName().getLast());
            tv_detail_city.setText(selectedEmployee.getLocation().getCity() + ", " + selectedEmployee.getLocation().getCountry());
            tv_detail_phone.setText(selectedEmployee.getPhone().replaceAll("[^\\d]", "") + " / " +
                    selectedEmployee.getCell().replaceAll("[^\\d]", ""));
            tv_detail_member_since.setText(new SimpleDateFormat("MMM yyyy").format(selectedEmployee.getRegistered().getDate()));
            tv_detail_email.setText(selectedEmployee.getEmail());

            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    EmployeeTableModel employeeTable;
                    boolean insertResult = false;

                    try {
                        employeeTable = new EmployeeTableModel(selectedEmployee.getEmployeeId(),
                                selectedEmployee.getName().getFirst() + " " + selectedEmployee.getName().getLast(),
                                selectedEmployee.getLocation().getCity() + ", " + selectedEmployee.getLocation().getCountry(),
                                selectedEmployee.getPhone().replaceAll("[^\\d]", ""),
                                selectedEmployee.getEmail(),
                                selectedEmployee.getPicture().getMedium());
                        DatabaseHelper databaseHelper = new DatabaseHelper(context);
                        insertResult = databaseHelper.insertEmployee(employeeTable);

                        if (insertResult) notifySuccess();

                        getParentFragmentManager().popBackStack();
                        ((MainActivity) context).getBottom_nav().setSelectedItemId(R.id.menu_book);
                        ((MainActivity) context).getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fl_container, new AddressBookFragment(context), null)
                                .commit();


                    }catch (Exception exception){
                        Toast.makeText(context, "Something is wrong!", Toast.LENGTH_SHORT).show();
                    }

                    if (!insertResult)
                        Toast.makeText(context, "This Employee already on Address book", Toast.LENGTH_LONG).show();
                }
            });

        }
        else {
            Toast.makeText(context, "Something is wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    private void notifySuccess() {
        // Notification Service
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel nc = new NotificationChannel("MyAddressBook", "My Address Book", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager nm = ((MainActivity)context).getSystemService(NotificationManager.class);
            nm.createNotificationChannel(nc);
        }

        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(context, "MyAddressBook");
        notifBuilder.setSmallIcon(R.drawable.ic_book);
        notifBuilder.setContentTitle("Successfully Add Employee to Address Book!");
        notifBuilder.setContentText("You just add "+ selectedEmployee.getName().getFirst() + " to Address Book");

        NotificationManagerCompat nmc = NotificationManagerCompat.from(context);
        nmc.notify(1, notifBuilder.build());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee_details, container, false);

        tv_detail_name = view.findViewById(R.id.tv_detail_name);
        tv_detail_city = view.findViewById(R.id.tv_detail_city);
        tv_detail_phone = view.findViewById(R.id.tv_detail_phone);
        tv_detail_member_since = view.findViewById(R.id.tv_detail_member_since);
        tv_detail_email = view.findViewById(R.id.tv_detail_email);
        btn_add = view.findViewById(R.id.btn_add);

        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mv_location);

        getEmployeeDetail(((MainActivity)context).getSelectedEmployeeId());



        return view;
    }

    private void setMapLocation() {
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                LatLng employeeLocation = new LatLng(selectedEmployee.getLocation().getCoordinates().getLatitude(), selectedEmployee.getLocation().getCoordinates().getLongitude());
                mMap.addMarker(new MarkerOptions()
                        .position(employeeLocation)
                        .title("Employee Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(employeeLocation));
            }
        });
    }

}
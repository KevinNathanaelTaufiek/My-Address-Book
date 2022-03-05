package com.kevinnt.myaddressbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kevinnt.myaddressbook.database.DatabaseHelper;
import com.kevinnt.myaddressbook.fragments.AddressBookFragment;
import com.kevinnt.myaddressbook.fragments.EmployeeSearchFragment;
import com.kevinnt.myaddressbook.models.Employee;
import com.kevinnt.myaddressbook.models.GetEmployeeEndpoint;
import com.kevinnt.myaddressbook.retrofit_client.EmployeeClient;
import com.kevinnt.myaddressbook.retrofit_interface.EmployeeInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    /*
    Kevin Nathanael Taufiek
    2301907901
    UAS Mobile Programming
     */

    private BottomNavigationView bottom_nav;

    private EmployeeInterface employeeInterface = EmployeeClient.getInstance().create(EmployeeInterface.class);
    private int selectedEmployeeId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottom_nav = findViewById(R.id.bottom_nav);


        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, new EmployeeSearchFragment(this), null).commit();

        bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.menu_search:
                        fragment = new EmployeeSearchFragment(MainActivity.this);
                        getSupportFragmentManager().popBackStack();
                        break;
                    case R.id.menu_book:
                        fragment = new AddressBookFragment(MainActivity.this);
                        getSupportFragmentManager().popBackStack();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, fragment, null).commit();
                return true;
            }
        });

    }

    public int getSelectedEmployeeId() {
        return selectedEmployeeId;
    }

    public void setSelectedEmployeeId(int selectedEmployeeId) {
        this.selectedEmployeeId = selectedEmployeeId;
    }

    public BottomNavigationView getBottom_nav() {
        return bottom_nav;
    }
}
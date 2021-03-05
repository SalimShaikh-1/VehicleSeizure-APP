package org.sk.jih;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.sk.jih.adapters.VehicleAdapter;
import org.sk.jih.controller.VehicleListController;
import org.sk.jih.modal.Global;
import org.sk.jih.modal.Vehicle;
import org.sk.jih.response.VehicleResponse;
import org.sk.jih.sqlite.DatabaseHandler;
import org.sk.jih.utility.CustomProgressDialog;
import org.sk.jih.utility.CustomToast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Timer timer;
    String endTime = "20:30:00";
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    DatabaseHandler db = new DatabaseHandler(this);
    VehicleListController vehicleListController;
    private RecyclerView rvProducts;
    private VehicleAdapter vehicleAdapter;
    VehicleResponse vehicleResponse;
    private List<Vehicle> vehicles, vehicleList;
    EditText search;
    final String strMessage = "http://vehicleseizure.7techitservices.com/Uploads/TextFiles/VehicleNo.txt";

    ProgressBar pb;
    List<String> stringList = new ArrayList<>();
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigation;
    Toolbar toolbar;


    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vehicles = db.getAllVehicles();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Vehicle Sezing");
        setSupportActionBar(toolbar);

//        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline);

        initInstances();

        if(!Global.isStatus()){
            new MyTask().execute();
            CustomProgressDialog.show(this, R.string.syncMessage);
        }
        else{
            Log.d("Reading: ", "Reading all contacts.." + Global.getVehicles().size());
            vehicles = Global.getVehicles();
        }


        vehicleAdapter = new VehicleAdapter(this, vehicles);
        rvProducts = findViewById(R.id.rvVehicle);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvProducts.setLayoutManager(mLayoutManager);
        rvProducts.setItemAnimator(new DefaultItemAnimator());
        rvProducts.setAdapter(vehicleAdapter);

        search = findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        timer = new Timer();
        Log.i("Main", "Invoking logout timer");
        LogOutTimerTask logoutTimeTask = new LogOutTimerTask();
        timer.scheduleAtFixedRate(logoutTimeTask, 0 , 5000);
    }

    private void initInstances() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigation = (NavigationView) findViewById(R.id.nav_view);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                Log.d("signM", String.valueOf(id));
                switch (id) {
                    case R.id.refresh:
                        vehicles.clear();
                        new MyTask().execute();
                        error();
                        break;
                    case R.id.signout:
                        ask_exit();
                        break;
                }
                return false;
            }
        });
    }

    private void filter(String text) {
        ArrayList<Vehicle> filteredList = new ArrayList<>();
        for (Vehicle item : vehicles) {
            if (item.getVehicleNumber().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        vehicleAdapter.filterList(filteredList);
    }

    public void onRes(List<Vehicle> vehicleList1) {
        Log.d("userList", "Test : " + vehicleList1.size());
        for(int i = 0; i <= vehicleList1.size() - 1; i++){
            String no =  vehicleList1.get(i).getVehicleNumber();
            db.addVehicle(new Vehicle(no));
        }
        Global.setStatus(true);
        vehicles = db.getAllVehicles();
        Log.d("vehicles", "Test123 : " + vehicles.get(0).getVehicleNumber());
        vehicleAdapter.setData(vehicles);
//        vehicleAdapter = new VehicleAdapter(this,vehicles);
    }

    public void setDate(){
        vehicles = db.getAllVehicles();
        Log.d("vehicles", "Test123 : " + vehicles.get(0).getVehicleNumber());
    }

    public void addData(){
        for(int i = 0; i <= stringList.size(); i++) {
            if(i == stringList.size()){
                CustomProgressDialog.dismiss(this);
            }
            else {
                vehicles.add(new Vehicle(stringList.get(i)));
            }

        }
        Global.setVehicles(vehicles);
        vehicleAdapter.setData(vehicles);
    }

    public void error(){
        CustomProgressDialog.show(this, R.string.syncMessage);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("itemId", String.valueOf(item.getItemId()));
        drawerLayout.openDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }

    private class LogOutTimerTask extends TimerTask {
        @Override
        public void run() {
            Log.d("Test", "test");
            //redirect user to login screen
            try {
                String currentDate = sdf.format(new Date().getTime());
                Date currentDate1 = sdf.parse(currentDate);
                Date endTime1 = sdf.parse(endTime);

                int diff2 = endTime1.compareTo(currentDate1);

                if(diff2 < 0){
                    Log.d("Login : ", "true : " + diff2);
                    timer.cancel();
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        String result;

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Void doInBackground(Void... voids) {
            URL url;
            try {
                url = new URL(strMessage);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
                String stringBuffer;
                String string = "";

                Scanner s = new Scanner(url.openStream());
                int i = 1;
                String line = "";
                while (s.hasNextLine()) {
                    line = s.nextLine();
//                    line = String.format("%s%s", line,  s.nextLine());
                    Log.d("String", " : " + i +" : " + line);
                    Log.d("stringList", " : " + stringList.size());
                    stringList.add(line);
                    i++;
                }
                Log.d("String", " : " + i +" : " + stringList.size());
                bufferedReader.close();
                result = string;
            } catch (IOException e){
                e.printStackTrace();
                result = e.toString();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            addData();
            Log.d("onPostExecute", " : Done");
            super.onPostExecute(aVoid);
        }
    }

    @Override
    public void onBackPressed() {
        ask_exit();
    }

    public void ask_exit(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle(getString(R.string.exit_title));
        builder.setMessage(getString(R.string.exit_subtitle));
        builder.setCancelable(true);

        // Action if user selects 'yes'
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                onDestroy();
//                System.exit(0);
            }
        });

        // Actions if user selects 'no'
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        // Create the alert dialog using alert dialog builder
        AlertDialog dialog = builder.create();

        // Finally, display the dialog when user press back button
        dialog.show();
    }


}
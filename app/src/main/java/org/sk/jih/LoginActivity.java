package org.sk.jih;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
//import android.view.WindowManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.sk.jih.controller.LocationController;
import org.sk.jih.controller.LoginController;
import org.sk.jih.modal.Global;
import org.sk.jih.sqlite.DatabaseHandler;
import org.sk.jih.utility.CustomToast;
import org.sk.jih.utility.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginActivity extends AppCompatActivity implements LocationListener {
    TelephonyManager telephonyManager;
    EditText userName, password;
    Boolean loginStatus = false;
    protected LocationManager locationManager;
    TextView txtLat;
    Button btn;
    DatabaseHandler db = new DatabaseHandler(this);
    LoginController controller;
    LocationController locationController;
    String latitude, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        controller = new LoginController(this);
        locationController = new LocationController(this);

        userName = (EditText) findViewById(R.id.editTextTextPersonName);
        password = (EditText) findViewById(R.id.editTextTextPersonName2);
        btn = (Button) findViewById(R.id.button);

        deviceId();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteVehicles();
                Global.setStatus(false);
                setData();
            }
        });
    }


    @Override
    public void onLocationChanged(Location location) {
        latitude = String.valueOf(location.getLatitude());
        longitude = String.valueOf(location.getLongitude());
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

    public String getUserName() {
        return userName.getText().toString();
    }

    public String getPassword() {
        return password.getText().toString();
    }

    public boolean validate() {
        String userName = getUserName();
        String passwords = getPassword();

//        DeviceUtils.hideSoftKeyboard(password);

        if (TextUtils.isEmpty(userName)) {
            CustomToast.show(this,
                    "Enter UserName", CustomToast.LENGTH_LONG);
            return false;
        }
        if (TextUtils.isEmpty(passwords)) {
            CustomToast.show(this,
                    "Enter Password", CustomToast.LENGTH_LONG);
            return false;
        }
        return true;
    }



    private void deviceId() {
        telephonyManager = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
            return;
        }

        else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            return;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        Log.d("requestCode", "requestCode : " + requestCode);
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
                        return;
                    }
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                        return;
                    }
//                    Toast.makeText(this,imeiNumber,Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this,"Without permission we check",Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @SuppressLint("MissingPermission")
    public void setData(){
//        vehicleListController = new VehicleListController(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        @SuppressLint("MissingPermission") String imeiNumber = telephonyManager.getDeviceId();
//        editText.setText(imeiNumber);
        SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm:ss");
        String currentDate = formatDate.format(new Date().getTime());

        String startTime = "10:00:00";
        String endTime = "20:38:00";
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {
            Date startTime1 = sdf.parse(startTime);
            Date currentDate1 = sdf.parse(currentDate);
            Date endTime1 = sdf.parse(endTime);
            long elapsed = endTime1.getTime() - currentDate1.getTime();
            Log.d("elapsed : ", "" + elapsed);

            Utility.setTime(elapsed);

            int diff = startTime1.compareTo(currentDate1);
            int diff2 = endTime1.compareTo(currentDate1);
            Log.d("diff : ", "" + diff);
            if(diff < 0 && diff2 > 0){
                Log.d("Login : ", "true");
                boolean status = validate();
                if(status){
                    controller.login(getUserName(), getPassword(), imeiNumber);
                }
            }else{
                loginStatus = false;
                CustomToast.show(this,
                        "User not login valid at this time.", CustomToast.LENGTH_LONG);
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setLocation(){
        Log.d("userId", Global.getUser().getId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = sdf.format(new Date());
        Log.d("currentDate", currentDate);
        org.sk.jih.modal.Location location = new org.sk.jih.modal.Location();
        location.setId(null);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setLoginTime(currentDate);
        location.setLogoutTime(null);
        location.setStatus("true");
        location.setLoctionDateTime(currentDate);
        location.setUserId(Global.getUser().getId());
        location.setUser(null);

        locationController.location(location);
    }

    public void onRes() {

        Log.d("userList", "Test");
        setLocation();
    }

    public void onRes1(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        loginStatus = true;
    }

//    private void getUserListData() {
//
//        final ProgressDialog progressDialog = new ProgressDialog(SplashActivity.this);
//        progressDialog.setCancelable(false); // set cancelable to false
//        progressDialog.setMessage("Please Wait"); // set message
//        progressDialog.show(); // show progress dialog
//
//        (APIClient.getClient().getVehicleList()).enqueue(new Callback<VehicleResponse>() {
//            @Override
//            public void onResponse(Call<VehicleResponse> call, Response<VehicleResponse> response) {
//                Log.d("responseGET", String.valueOf(response.body().getData()));
//                progressDialog.dismiss(); //dismiss progress dialog
//                vehicleList = (List<Vehicle>) response.body();
//            }
//
//            @Override
//            public void onFailure(Call<VehicleResponse> call, Throwable t) {
//                // if error occurs in network transaction then we can get the error in this method.
//                Toast.makeText(SplashActivity.this, t.toString(), Toast.LENGTH_LONG).show();
//                progressDialog.dismiss(); //dismiss progress dialog
//            }
//        });
//
//    }

}

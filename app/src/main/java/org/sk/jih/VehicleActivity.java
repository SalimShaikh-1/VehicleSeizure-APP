package org.sk.jih;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.sk.jih.controller.ImageUploadController;
import org.sk.jih.controller.VehicleDetailsController;
import org.sk.jih.controller.VehicleParkedController;
import org.sk.jih.modal.Global;
import org.sk.jih.modal.ImageUpload;
import org.sk.jih.modal.Parked;
import org.sk.jih.modal.VehicleDetails;
import org.sk.jih.modal.VehicleParked;
import org.sk.jih.utility.CustomProgressDialog;
import org.sk.jih.utility.CustomToast;
import org.sk.jih.utility.RealPathUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class VehicleActivity extends AppCompatActivity {
    public static final int PICKFILE_RESULT_CODE = 1;
    VehicleDetailsController vehicleDetailsController;
    VehicleParkedController vehicleParkedController;
    ImageUploadController imageUploadController;
    VehicleDetails vehicleDetails;
    VehicleParked vehicleParked;
    TextView vehicleNumberText, customerNameText, chassisNumberText, loanNumberLblText, agencyNameText, branchNameText, agentNameText;
    Button sendSms, parkedVehicle, upload;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String vehicleNo;
    boolean vehiclePareked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_details);
        vehicleDetailsController = new VehicleDetailsController(this);
        vehicleParkedController = new VehicleParkedController(this);
        imageUploadController = new ImageUploadController(this);
        vehicleNo = getIntent().getStringExtra("vehicleNo");
        Log.d("vehicleNo : ", vehicleNo);
        vehicleDetailsController.getDetails(vehicleNo);
        requestPermission();
        vehicleNumberText = (TextView) findViewById(R.id.vehicleNumberText);
        customerNameText = (TextView) findViewById(R.id.customerNameText);
        chassisNumberText = (TextView) findViewById(R.id.chassisNumberText);
        loanNumberLblText = (TextView) findViewById(R.id.loanNumberLblText);
        agencyNameText = (TextView) findViewById(R.id.agencyNameText);
        branchNameText = (TextView) findViewById(R.id.branchNameText);
        agentNameText = (TextView) findViewById(R.id.agentNameText);

        sendSms = (Button) findViewById(R.id.sendSms);
        parkedVehicle = (Button) findViewById(R.id.parkedVehicle);
        upload = (Button) findViewById(R.id.upload);

        parkedVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentDate = sdf.format(new Date());
                Parked parked = new Parked();
                parked.setVehicleNumber(vehicleNo);
                parked.setParkedOn(currentDate);
                parked.setUserId(Global.getUser().getId());

                vehicleParkedController.vehicleParked(parked);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vehiclePareked) {
                    Intent openGalleryIntent = new Intent();
                    openGalleryIntent.setType("*/*");
                    openGalleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    openGalleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(openGalleryIntent, 200);
                }
                else{

                    error();
                }

            }
        });
    }

    public void error(){
        CustomToast.show(this,
                R.string.firstParkedVehicle, CustomToast.LENGTH_LONG);
    }


    public void onRes(VehicleDetails vehicle) {
        vehicleDetails = vehicle;
        vehiclePareked = true;
        setData();
        Log.d("userList", String.valueOf(vehicleDetails.getAgent() != null));
    }

    public void onParkedResponse(VehicleParked parked){
        vehicleParked = parked;
        Log.d("vehicleParked", vehicleParked.getId());
    }

    public void setData(){
        vehicleNumberText.setText(vehicleDetails.getVehicleNumber());
        customerNameText.setText(vehicleDetails.getCustomerName());
        chassisNumberText.setText(vehicleDetails.getChassisNumber());
        loanNumberLblText.setText(vehicleDetails.getLoanNumber());

        if(vehicleDetails.getBranch() != null){
            agencyNameText.setText(vehicleDetails.getBranch().getAgency().getName());
            branchNameText.setText(vehicleDetails.getBranch().getName());
        }

        if(vehicleDetails.getAgent() != null){
            agentNameText.setText(vehicleDetails.getAgent().getName());
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200 && resultCode == Activity.RESULT_OK && data != null) {

            ArrayList<String> files = new ArrayList<>();

            System.out.println("step2: Now the user has choose pictures");
            if (data.getClipData() != null) {
                System.out.println("step3: multiple pictures are chose");
                int count = data.getClipData().getItemCount();
                System.out.println("files chosen :" + count);
                ClipData mClipData = data.getClipData();

                if (mClipData != null && mClipData.getItemCount() > 0) {
                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        ClipData.Item mItem = mClipData.getItemAt(i);
                        String filePath = getAbsolutePath(mItem.getUri());
                        files.add(filePath);
                    }
                }

            } else if (data.getData() != null) {
                System.out.println("step3: One single picture is choose");
                Uri imageUri = data.getData();
                String filePath = getAbsolutePath(imageUri);
                files.add(filePath);
            }
            Log.d("FileSize", files.size() + files.get(0));
            System.out.println("step4: Send pictures to server");
            // now that we have multiple files ready
            // let's call retrofit service and send images to server

            imageUploadController.uploadImages(files, vehicleParked.getId(), Global.getUser().getId());

        } else {
            CustomToast.show(this,
                    "Sorry permission error", CustomToast.LENGTH_LONG);
        }
    }

    public void onuploadResponse(){
        Log.d("vehicleParked", "Uploaded");
    }

    private String getAbsolutePath(Uri uri) {
        String path = null;
        path = RealPathUtils.getRealPath(this, uri);
        return path;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        if (requestCode == 201 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 201);
        }
    }
}

package org.sk.jih.controller;


import org.sk.jih.R;
import org.sk.jih.VehicleActivity;
import org.sk.jih.api.APIClient;
import org.sk.jih.api.APIInterface;
import org.sk.jih.response.VehicleDetailsResponse;
import org.sk.jih.utility.CustomProgressDialog;
import org.sk.jih.utility.CustomToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleDetailsController implements Callback<VehicleDetailsResponse> {
    private APIInterface apiInterface;
    VehicleActivity vehicleActivity;

    public VehicleDetailsController(VehicleActivity activity) {
        this.vehicleActivity = activity;
        apiInterface = APIClient.getApiInterface();
    }

    public void getDetails(String vehicleNo) {
        CustomProgressDialog.show(vehicleActivity, R.string.msg_loading);
        Call<VehicleDetailsResponse> loginCall = apiInterface.getVehicleDetails(vehicleNo);
        loginCall.enqueue(this);
    }

    @Override
    public void onResponse(Call<VehicleDetailsResponse> call, Response<VehicleDetailsResponse> response) {
        CustomProgressDialog.dismiss(vehicleActivity);
        if (response != null && response.body() != null) {
            CustomToast.show(vehicleActivity,
                    R.string.vehicleDetailsFetchSuccessfully, CustomToast.LENGTH_LONG);
            vehicleActivity.onRes(response.body().getData());
        } else {
            CustomToast.show(vehicleActivity,
                    R.string.vehicleDetailsFetchFailed, CustomToast.LENGTH_LONG);
        }
    }

    @Override
    public void onFailure(Call<VehicleDetailsResponse> call, Throwable t) {
        CustomProgressDialog.dismiss(vehicleActivity);
    }
}

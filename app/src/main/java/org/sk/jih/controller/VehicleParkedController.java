package org.sk.jih.controller;

import org.sk.jih.R;
import org.sk.jih.VehicleActivity;
import org.sk.jih.api.APIClient;
import org.sk.jih.api.APIInterface;
import org.sk.jih.modal.Parked;
import org.sk.jih.response.VehicleParkedResponse;
import org.sk.jih.utility.CustomProgressDialog;
import org.sk.jih.utility.CustomToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleParkedController implements Callback<VehicleParkedResponse> {
    private VehicleActivity activity;
    private APIInterface apiInterface;

    public VehicleParkedController(VehicleActivity activity) {
        this.activity = activity;
        apiInterface = APIClient.getApiInterface();
    }

    public void vehicleParked(Parked parked) {
        CustomProgressDialog.show(activity, R.string.msg_loading);
        Call<VehicleParkedResponse> vehicleParkedResponseCall = apiInterface.vehicleParked(parked);
        vehicleParkedResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Call<VehicleParkedResponse> call, Response<VehicleParkedResponse> response) {
        CustomProgressDialog.dismiss(activity);
        if (response != null && response.body() != null) {
            CustomToast.show(activity,
                    R.string.vehicleParkedSuccessfully, CustomToast.LENGTH_LONG);
            activity.onParkedResponse(response.body().getData());
        } else {
            CustomToast.show(activity,
                    R.string.vehicleParkedFailed, CustomToast.LENGTH_LONG);
        }
    }

    @Override
    public void onFailure(Call<VehicleParkedResponse> call, Throwable t) {
        CustomProgressDialog.dismiss(activity);
    }
}

package org.sk.jih.controller;

import android.util.Log;

import org.sk.jih.MainActivity;
import org.sk.jih.api.APIClient;
import org.sk.jih.api.APIInterface;
import org.sk.jih.response.VehicleResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleListController implements Callback<VehicleResponse> {
    private APIInterface apiInterface;
    MainActivity sa;
    public VehicleListController(MainActivity s) {
        sa = s;
        apiInterface = APIClient.getApiInterface();
        getVehicleList();
    }

    public void getVehicleList(){
        Call<VehicleResponse> vehicleListCall = apiInterface.getVehicleList();
        vehicleListCall.enqueue(this);
    }


    @Override
    public void onResponse(Call<VehicleResponse> call, Response<VehicleResponse> response) {
        Log.d("onResponse", "onResponse:" + response.body());
            sa.onRes(response.body().getData());
    }

    @Override
    public void onFailure(Call<VehicleResponse> call, Throwable t) {
        Log.d("Throwable", "onResponse:" + t);
    }
}

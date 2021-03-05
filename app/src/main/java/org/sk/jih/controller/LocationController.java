package org.sk.jih.controller;

import org.sk.jih.R;
import org.sk.jih.LoginActivity;
import org.sk.jih.api.APIClient;
import org.sk.jih.api.APIInterface;
import org.sk.jih.modal.Location;
import org.sk.jih.response.LocationResponse;
import org.sk.jih.utility.CustomProgressDialog;
import org.sk.jih.utility.CustomToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationController implements Callback<LocationResponse> {

    private LoginActivity activity;
    private APIInterface apiInterface;

    public LocationController(LoginActivity activity) {
        this.activity = activity;
        apiInterface = APIClient.getApiInterface();
    }

    public void location(Location location) {
        CustomProgressDialog.show(activity, R.string.msg_login_in);
        Call<LocationResponse> loginCall = apiInterface.postLocation(location);
        loginCall.enqueue(this);
    }

    @Override
    public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
        CustomProgressDialog.dismiss(activity);
        if (response != null && response.body() != null) {
            CustomToast.show(activity,
                    R.string.loginSuccessfully, CustomToast.LENGTH_LONG);
            activity.onRes1();
        } else {
            CustomToast.show(activity,
                    R.string.invalidUsernameORPassword, CustomToast.LENGTH_LONG);
        }
    }

    @Override
    public void onFailure(Call<LocationResponse> call, Throwable t) {
        CustomProgressDialog.dismiss(activity);
    }
}

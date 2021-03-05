package org.sk.jih.controller;

import android.util.Log;

import org.sk.jih.R;
import org.sk.jih.LoginActivity;
import org.sk.jih.api.APIClient;
import org.sk.jih.api.APIInterface;
import org.sk.jih.modal.Global;
import org.sk.jih.modal.Login;
import org.sk.jih.response.LoginResponse;
import org.sk.jih.utility.CustomProgressDialog;
import org.sk.jih.utility.CustomToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginController implements Callback<LoginResponse> {
    private LoginActivity activity;
    private APIInterface apiInterface;

    public LoginController(LoginActivity activity) {
        this.activity = activity;
        apiInterface = APIClient.getApiInterface();
    }

    public void login(String userName, String password, String imeiNumber) {
        CustomProgressDialog.show(activity, R.string.msg_login_in);
        Login login = new Login();
        login.setUserName(userName);
        login.setPassword(password);
        login.setImeiNumber(imeiNumber);
        Call<LoginResponse> loginCall = apiInterface.postLogin(login);
        loginCall.enqueue(this);
    }




    @Override
    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
        CustomProgressDialog.dismiss(activity);
        Log.d("LoginResponse", "LoginResponse" + response.isSuccessful());
        if (response != null && response.body() != null) {
            Global.setUser(response.body().getData());
            activity.onRes();
        } else {
            CustomToast.show(activity,
                    "Invalid Username OR Password", CustomToast.LENGTH_LONG);
        }

    }

    @Override
    public void onFailure(Call<LoginResponse> call, Throwable t) {
        CustomProgressDialog.dismiss(activity);
    }
}

package org.sk.jih.controller;


import org.sk.jih.R;
import org.sk.jih.VehicleActivity;
import org.sk.jih.api.APIClient;
import org.sk.jih.api.APIInterface;
import org.sk.jih.modal.Parked;
import org.sk.jih.response.ImageUploadResponse;
import org.sk.jih.response.VehicleParkedResponse;
import org.sk.jih.utility.CustomProgressDialog;
import org.sk.jih.utility.CustomToast;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageUploadController implements Callback<ImageUploadResponse> {
    private VehicleActivity activity;
    private APIInterface apiInterface;

    public ImageUploadController(VehicleActivity activity) {
        this.activity = activity;
        apiInterface = APIClient.getApiInterface();
    }

    public void uploadImages(ArrayList<String> files, String id, String userId) {
        CustomProgressDialog.show(activity, R.string.msg_loading);

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("id", id);
        builder.addFormDataPart("userId", userId);

        for (int i = 0; i < files.size(); i++) {
            File file = new File(files.get(i));
            builder.addFormDataPart("files", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
        }

        MultipartBody requestBody = builder.build();


        Call<ImageUploadResponse> imageUploadResponseCallCall = apiInterface.uploadImages(requestBody);
        imageUploadResponseCallCall.enqueue(this);
    }


    @Override
    public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
        CustomProgressDialog.dismiss(activity);
        if (response != null && response.body() != null) {
            CustomToast.show(activity,
                    R.string.imageUploadSuccessfully, CustomToast.LENGTH_LONG);
            activity.onuploadResponse();
        } else {
            CustomToast.show(activity,
                    R.string.imageUploadFailed, CustomToast.LENGTH_LONG);
        }
    }

    @Override
    public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
        CustomProgressDialog.dismiss(activity);
    }
}

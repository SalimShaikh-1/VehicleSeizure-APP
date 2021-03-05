package org.sk.jih.api;

import org.sk.jih.modal.Location;
import org.sk.jih.modal.Login;
import org.sk.jih.modal.Parked;
import org.sk.jih.modal.VehicleParked;
import org.sk.jih.response.ImageUploadResponse;
import org.sk.jih.response.LocationResponse;
import org.sk.jih.response.LoginResponse;
import org.sk.jih.response.VehicleDetailsResponse;
import org.sk.jih.response.VehicleParkedResponse;
import org.sk.jih.response.VehicleResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Salim Shaikh
 */

public interface APIInterface {

    @GET(APIConstants.BASE_FOLDER + "demo/GetOnlyMGStateRecord")
    Call<VehicleResponse> getVehicleList();

    @POST(APIConstants.BASE_FOLDER + "operators/login")
    Call<LoginResponse> postLogin(@Body Login login);

    @POST(APIConstants.BASE_FOLDER + "locations")
    Call<LocationResponse> postLocation(@Body Location location);

    @GET(APIConstants.BASE_FOLDER + "vehicles/vehicleNumber")
    Call<VehicleDetailsResponse> getVehicleDetails(@Query("vehicleNumber") String vehicleNumber);

    @POST(APIConstants.BASE_FOLDER + "parkedvehicles")
    Call<VehicleParkedResponse> vehicleParked(@Body Parked parked);

    @POST(APIConstants.BASE_FOLDER + "parkedvehicles/images")
    Call<ImageUploadResponse> uploadImages(@Body MultipartBody requestBody);

}
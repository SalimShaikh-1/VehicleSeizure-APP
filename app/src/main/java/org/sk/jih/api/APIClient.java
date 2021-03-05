package org.sk.jih.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static Retrofit retrofit = null;
    private static OkHttpClient client;
    private static HttpLoggingInterceptor interceptor;
    private static APIInterface apiInterface;

    private APIClient() {
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            //HttpUrl httpUrl = HttpUrl.parse(APIConstants.BASE_URL);
            retrofit = new Retrofit.Builder()
                    .baseUrl(APIConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();


        }
        return retrofit;
    }

    public static APIInterface getApiInterface() {
        return APIClient.getClient().create(APIInterface.class);
    }



}
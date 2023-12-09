package com.stetig.solitaire.api;

import android.app.Activity;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Pratik Katariya on 24-09-2020
 */
public class RestClient {

    private static Retrofit retrofit;
    private static int CONNECT_TIME_OUT = 60;
    private static int READ_TIME_OUT = 60;

    private static String BASE_URL = "https://solitaire.my.salesforce.com/services/apexrest/";/*production v2*/
//    private static String BASE_URL = "https://solitaire--dev.sandbox.my.salesforce.com/services/apexrest/";/*Dev v2*/

    private static Activity mActivity;

    private static final  RestClient ourInstance = new  RestClient();

    public static  RestClient getInstance(Activity activity) {
        mActivity = activity;
        return ourInstance;
    }

    public static  RestClient getInstance() {
        return ourInstance;
    }

    private RestClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    public ApiStructure getService() {
        return retrofit.create(ApiStructure.class);
    }
}
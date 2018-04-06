package com.example.assignment.helpers;

import android.support.annotation.NonNull;

import com.example.assignment.i002.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS);


    private static String TAG = ApiService.class.getSimpleName();
    private static Retrofit.Builder builder = getBuilder();

    @NonNull
    private static Retrofit.Builder getBuilder() {
        return new Retrofit.Builder()
            .baseUrl(ApiConstants.getInstance().getUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
    }

    static void initBuilder(String url) {
        ApiConstants.getInstance().setUrl(url);
        builder = getBuilder();
    }


    public static String getUrl() {
        return ApiConstants.getInstance().getUrl();
    }

    public static <S> S createService(Class<S> serviceClass) {
        OkHttpClient client = getOkHttpClient();
        Retrofit retrofit = builder
            .client(client)
            .build();
        return retrofit.create(serviceClass);
    }

    @NonNull
    private static OkHttpClient getOkHttpClient() {
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }
        httpClient.followRedirects(false);
        try {
            httpClient.addInterceptor(chain -> {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json");
                requestBuilder.method(original.method(), original.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            });
        } catch (Exception e) {
//            cp.e(TAG, e);
        }
        return httpClient.build();
    }
}
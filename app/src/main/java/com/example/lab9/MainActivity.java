package com.example.lab9;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import java.io.IOException;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://reqres.in/api/users?page=1";

    public interface UserApi {
        @GET("users?page=2")
        Call<ApiResponse> getUsers();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserApi userApi = retrofit.create(UserApi.class);

        Call<ApiResponse> call = userApi.getUsers();
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    List<User> users = apiResponse.getUsers();
                    for (User user : users) {
                        Log.d("MainActivity", "Nombre: " + user.getFirstName());
                        Log.d("MainActivity", "Email: " + user.getEmail());
                        Log.d("MainActivity", "-------------------");
                    }
                } else {
                    Log.e("MainActivity", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("MainActivity", "Error en la llamada: " + t.getMessage());
            }
        });
    }
}

package com.example.lab9;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

import java.util.List;


public class ApiService {
    private static final String BASE_URL = "https://reqres.in/api/users?page=1";
    public interface UserApi {
        @GET("users?page=2")
        Call<ApiResponse> getUsers();
    }
    public static void main(String[] args) {
        // Crear una instancia de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Crear una instancia de la interfaz de la API
        UserApi userApi = retrofit.create(UserApi.class);
        // Realizar la llamada a la API
        Call<ApiResponse> call = userApi.getUsers();
        try {
            // Ejecutar la llamada y obtener la respuesta
            ApiResponse response = call.execute().body();

            // Verificar si la respuesta es exitosa
            if (response != null) {
                List<User> users = response.getUsers();
                for (User user : users) {
                    System.out.println("Nombre: " + user.getFirstName());
                    System.out.println("Email: " + user.getEmail());
                    System.out.println("-------------------");
                }
            } else {
                System.out.println("No se recibió una respuesta válida");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

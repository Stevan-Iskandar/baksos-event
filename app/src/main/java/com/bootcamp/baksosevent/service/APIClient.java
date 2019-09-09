package com.bootcamp.baksosevent.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.bootcamp.baksosevent.utils.StringConverter;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by User on 1/10/2018.
 */

public class APIClient {
  public static String localhost = "192.168.5.150";
  public static String BASE_URL = "http://"+localhost+"/baksosevent/api/baksos/";
  private static Retrofit retrofit = null;

  public static Retrofit getClient() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(String.class, new StringConverter());
        gb.serializeNulls();
        Gson gson = gb.create();

        retrofit = new Retrofit.Builder()
                .baseUrl(APIClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))

                .client(client)
                .build();

        return retrofit;
    }
}
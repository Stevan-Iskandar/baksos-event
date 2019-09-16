package com.bootcamp.baksosevent.service;

/**
 * Created by User on 1/10/2018.
 */

import com.bootcamp.baksosevent.model.Event;
import com.bootcamp.baksosevent.model.Peserta;
import com.bootcamp.baksosevent.model.ResponseAPI;
import com.bootcamp.baksosevent.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by anupamchugh on 09/01/17.
 */

public interface APIInterfacesRest {
  @GET("getevent")
  Call<List<Event>> getAllEvent();

  @FormUrlEncoded
  @POST("loginuser")
  Call<ResponseAPI> postLogin(@Field("username") String username,
                              @Field("password") String password);

  @FormUrlEncoded
  @POST("setuser")
  Call<User> postUser(@Field("type") String type,
                      @Field("username") String username,
                      @Field("password") String password,
                      @Field("nama") String nama,
                      @Field("email") String email,
                      @Field("alamat") String alamat,
                      @Field("tlp") String tlp);

  @GET("getallpesertawhere")
  Call<List<Peserta>> getAllPesertaWhere(@Query("id_event") String id_event);

  @FormUrlEncoded
  @POST("daftarevent")
  Call<ResponseAPI> postDaftarEvent(@Field("id_event") String id_event,
                                    @Field("username") String username);
}

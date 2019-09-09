package com.bootcamp.baksosevent.service;

/**
 * Created by User on 1/10/2018.
 */

import com.bootcamp.baksosevent.model.Event;
import com.bootcamp.baksosevent.model.ResponseAPI;
import com.bootcamp.baksosevent.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by anupamchugh on 09/01/17.
 */

public interface APIInterfacesRest {
//  @GET("users")
//  Call<com.juaracoding.absensi.model.reqres.User> getUserReqres(@Query("page") String page);
//
//  @GET("comments")
//  Call<List<Comment>> getComment(@Query("postId") String postId);

  @GET("getevent")
  Call<List<Event>> getAllEvent();

  @FormUrlEncoded
  @POST("loginuser")
  Call<ResponseAPI> postLogin(@Field("username") String username, @Field("password") String password);

  @FormUrlEncoded
  @POST("setuser")
  Call<User> postUser(@Field("type") String type,
                      @Field("username") String username,
                      @Field("password") String password,
                      @Field("nama") String nama,
                      @Field("email") String email,
                      @Field("alamat") String alamat,
                      @Field("tlp") String tlp);
}

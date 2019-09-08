package com.bootcamp.baksosevent.service;

/**
 * Created by User on 1/10/2018.
 */

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by anupamchugh on 09/01/17.
 */

public interface APIInterfacesRest {
//  @GET("absensi/User/all")
//  Call<List<User>> getUser();
//
//  @GET("users")
//  Call<com.juaracoding.absensi.model.reqres.User> getUserReqres(@Query("page") String page);
//
//  @GET("posts")
//  Call<List<com.juaracoding.absensi.model.typicode.Post>> getTypicode();
//
//  @GET("posts")
//  Call<List<Post>> getPost();
//
//  @GET("comments")
//  Call<List<Comment>> getComment(@Query("postId") String postId);

  @FormUrlEncoded
  @POST("loginuser")
  Call<ResponseBody> postLogin(@Field("username") String username, @Field("password") String password);
}

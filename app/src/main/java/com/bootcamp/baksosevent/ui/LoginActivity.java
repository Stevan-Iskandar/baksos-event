package com.bootcamp.baksosevent.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bootcamp.baksosevent.R;
import com.bootcamp.baksosevent.adapter.AllEventAdapter;
import com.bootcamp.baksosevent.model.Event;
import com.bootcamp.baksosevent.model.ResponseAPI;
import com.bootcamp.baksosevent.service.APIClient;
import com.bootcamp.baksosevent.service.APIInterfacesRest;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
  ResponseAPI responseAPI;

  EditText etUsername, etPassword;
  Button btnLogin;
  TextView txtBuatAkun;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    etUsername = findViewById(R.id.etUsername);
    etPassword = findViewById(R.id.etPassword);
    btnLogin = findViewById(R.id.btnLogin);
    txtBuatAkun = findViewById(R.id.txtBuatAkun);

    btnLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        loginAPI();
      }
    });

    txtBuatAkun.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(LoginActivity.this, BuatAkunUserActivity.class);
        startActivity(intent);
      }
    });
  }

  APIInterfacesRest apiInterface;
  ProgressDialog progressDialog;
  public void loginAPI() {
    apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
    progressDialog = new ProgressDialog(LoginActivity.this);
    progressDialog.setTitle("Loading");
    progressDialog.show();
    Call<ResponseAPI> mulaiRequest = apiInterface.postLogin(etUsername.getText().toString(), etPassword.getText().toString());
    mulaiRequest.enqueue(new Callback<ResponseAPI>() {
      @Override
      public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
        progressDialog.dismiss();
        responseAPI = response.body();
        if (responseAPI != null) {
          kondisiLogin();
//          Toast.makeText(LoginActivity.this, responseAPI.getMessage(), Toast.LENGTH_LONG).show();
        } else {
          try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            Toast.makeText(LoginActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
          } catch (Exception e) {
            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
          }
        }
      }

      @Override
      public void onFailure(Call<ResponseAPI> call, Throwable t) {
        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(), "Maaf koneksi bermasalah", Toast.LENGTH_LONG).show();
        call.cancel();
      }
    });
  }

  private void kondisiLogin() {
    if (responseAPI.getStatus().booleanValue() == true) {
      Intent intent = new Intent(LoginActivity.this, AllEventActivity.class);
      intent.putExtra("username", responseAPI.getMessage());
      startActivity(intent);
    }
    else {
      Toast.makeText(LoginActivity.this, responseAPI.getMessage(), Toast.LENGTH_LONG).show();
    }
  }
}

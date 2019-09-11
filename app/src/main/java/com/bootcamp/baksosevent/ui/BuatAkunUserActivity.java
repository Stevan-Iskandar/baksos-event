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
import com.bootcamp.baksosevent.model.ResponseAPI;
import com.bootcamp.baksosevent.model.User;
import com.bootcamp.baksosevent.service.APIClient;
import com.bootcamp.baksosevent.service.APIInterfacesRest;
import com.location.aravind.getlocation.GeoLocator;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuatAkunUserActivity extends AppCompatActivity {
  GeoLocator geoLocator;

  TextView txtUsernameError, txtPasswordError, txtNamaError, txtEmailError, txtAlamatError, txtTlpError;
  EditText etUsername, etPassword, etPasswordRetype, etNama, etEmail, etAlamat, etTlp;
  Button btnBuatAkunUser, btnAlamat;
  User user;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    geoLocator = new GeoLocator(getApplicationContext(), BuatAkunUserActivity.this);
    setContentView(R.layout.activity_buat_akun_user);

    txtUsernameError = findViewById(R.id.txtUsernameError);
    txtPasswordError = findViewById(R.id.txtPasswordError);
    txtNamaError = findViewById(R.id.txtNamaError);
    txtEmailError = findViewById(R.id.txtEmailError);
    txtAlamatError = findViewById(R.id.txtAlamatError);
    txtTlpError = findViewById(R.id.txtTlpError);

    txtUsernameError.setVisibility(View.GONE);
    txtPasswordError.setVisibility(View.GONE);
    txtNamaError.setVisibility(View.GONE);
    txtEmailError.setVisibility(View.GONE);
    txtAlamatError.setVisibility(View.GONE);
    txtTlpError.setVisibility(View.GONE);

    etUsername = findViewById(R.id.etUsername);
    etPassword = findViewById(R.id.etPassword);
    etPasswordRetype = findViewById(R.id.etPasswordRetype);
    etNama = findViewById(R.id.etNama);
    etEmail = findViewById(R.id.etEmail);
    etAlamat = findViewById(R.id.etAlamat);
    etTlp = findViewById(R.id.etTlp);

    btnAlamat = findViewById(R.id.btnAlamat);
    btnAlamat.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        etAlamat.setText(geoLocator.getAddress());
      }
    });

    btnBuatAkunUser = findViewById(R.id.btnBuatAkunUser);
    btnBuatAkunUser.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        buatAkunUser();
      }
    });
  }

  private void buatAkunUser() {
    if (etUsername.getText().toString().equals("") ||
      etPassword.getText().toString().equals("") ||
      !etPassword.getText().toString().equals(etPasswordRetype.getText().toString()) ||
      etNama.getText().toString().equals("") ||
      etEmail.getText().toString().equals("") ||
      etAlamat.getText().toString().equals("") ||
      etTlp.getText().toString().equals("")) {
      if (etUsername.getText().toString().equals("")) {
        txtUsernameError.setText("Username tidak boleh kosong");
        txtUsernameError.setVisibility(View.VISIBLE);
      } else {
        txtUsernameError.setVisibility(View.GONE);
      }
      if (etPassword.getText().toString().equals("")) {
        txtPasswordError.setText("Password tidak boleh kosong");
        txtPasswordError.setVisibility(View.VISIBLE);
      } else {
        if (etPassword.getText().toString().equals(etPasswordRetype.getText().toString())) {
          txtPasswordError.setVisibility(View.GONE);
        } else {
          txtPasswordError.setText("Password tidak sama");
          txtPasswordError.setVisibility(View.VISIBLE);
        }
      }

      if (etNama.getText().toString().equals("")) {
        txtNamaError.setText("Nama tidak boleh kosong");
        txtNamaError.setVisibility(View.VISIBLE);
      } else {
        txtNamaError.setVisibility(View.GONE);
      }
      if (etEmail.getText().toString().equals("")) {
        txtEmailError.setText("Email tidak boleh kosong");
        txtEmailError.setVisibility(View.VISIBLE);
      } else {
        txtEmailError.setVisibility(View.GONE);
      }
      if (etAlamat.getText().toString().equals("")) {
        txtAlamatError.setText("Alamat tidak boleh kosong");
        txtAlamatError.setVisibility(View.VISIBLE);
      } else {
        txtAlamatError.setVisibility(View.GONE);
      }
      if (etTlp.getText().toString().equals("")) {
        txtTlpError.setText("No Telepon tidak boleh kosong");
        txtTlpError.setVisibility(View.VISIBLE);
      } else {
        txtTlpError.setVisibility(View.GONE);
      }
    } else {
      txtUsernameError.setVisibility(View.GONE);
      txtPasswordError.setVisibility(View.GONE);
      txtNamaError.setVisibility(View.GONE);
      txtEmailError.setVisibility(View.GONE);
      txtAlamatError.setVisibility(View.GONE);
      txtTlpError.setVisibility(View.GONE);
//      Toast.makeText(BuatAkunUserActivity.this, "berhasil", Toast.LENGTH_LONG).show();

      buatAkunUserAPI();
    }
  }

  APIInterfacesRest apiInterface;
  ProgressDialog progressDialog;
  public void buatAkunUserAPI() {
    apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
    progressDialog = new ProgressDialog(BuatAkunUserActivity.this);
    progressDialog.setTitle("Loading");
    progressDialog.show();

    Call<User> mulaiRequest = apiInterface.postUser(
      "insert",
      etUsername.getText().toString(),
      etPassword.getText().toString(),
      etNama.getText().toString(),
      etEmail.getText().toString(),
      etAlamat.getText().toString(),
      etTlp.getText().toString());

    mulaiRequest.enqueue(new Callback<User>() {
      @Override
      public void onResponse(Call<User> call, Response<User> response) {
        progressDialog.dismiss();
        user = response.body();
        if (user != null) {
          Toast.makeText(BuatAkunUserActivity.this, "Akun berhasil dibuat", Toast.LENGTH_LONG).show();
          finish();
        } else {
          try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            Toast.makeText(BuatAkunUserActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
          } catch (Exception e) {
            Toast.makeText(BuatAkunUserActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
          }
        }
      }

      @Override
      public void onFailure(Call<User> call, Throwable t) {
        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(), "Maaf koneksi bermasalah", Toast.LENGTH_LONG).show();
        call.cancel();
      }
    });
  }
}

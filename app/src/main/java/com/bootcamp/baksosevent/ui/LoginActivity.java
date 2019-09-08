package com.bootcamp.baksosevent.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bootcamp.baksosevent.R;

public class LoginActivity extends AppCompatActivity {
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
  }
}

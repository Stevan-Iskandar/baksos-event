package com.bootcamp.baksosevent.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bootcamp.baksosevent.R;
import com.bootcamp.baksosevent.application.AppController;
import com.bootcamp.baksosevent.model.Event;
import com.bootcamp.baksosevent.model.Peserta;
import com.bootcamp.baksosevent.model.ResponseAPI;
import com.bootcamp.baksosevent.service.APIClient;
import com.bootcamp.baksosevent.service.APIInterfacesRest;
import com.bootcamp.baksosevent.utils.SharedPreferencesUtil;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.queriable.StringQuery;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailEventActivity extends AppCompatActivity {
  String localhost = APIClient.localhost;
  TextView txtPeserta, txtNama, txtDeskripsi, txtDonasi, txtTanggalWaktu, txtLokasi;
  ImageView ivEvent;
  Button btnDaftar;
  Event event;
  List<Peserta> listPeserta;
  String id;

  SharedPreferencesUtil session;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail_event);

    event = new Event();
    session = new SharedPreferencesUtil(DetailEventActivity.this);
    ivEvent = findViewById(R.id.ivEvent);
    txtPeserta = findViewById(R.id.txtPeserta);
    txtNama = findViewById(R.id.txtNama);
    txtDeskripsi = findViewById(R.id.txtDeskripsi);
    txtDonasi = findViewById(R.id.txtDonasi);
    txtTanggalWaktu = findViewById(R.id.txtTanggalWaktu);
    txtLokasi = findViewById(R.id.txtLokasi);
    btnDaftar = findViewById(R.id.btnDaftar);
    id = getIntent().getStringExtra("eventId");

    sqlQueryList();
  }

  APIInterfacesRest apiInterface;
  ProgressDialog progressDialog;
  public void callPesertaEventAPI() {
    apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
    progressDialog = new ProgressDialog(DetailEventActivity.this);
    progressDialog.setTitle("Loading");
    progressDialog.show();
    Call<List<Peserta>> mulaiRequest = apiInterface.getAllPesertaWhere(id);
    mulaiRequest.enqueue(new Callback<List<Peserta>>() {
      @Override
      public void onResponse(Call<List<Peserta>> call, Response<List<Peserta>> response) {
        progressDialog.dismiss();
        listPeserta = response.body();
        if (listPeserta != null) {
          savedb();
        } else {
          try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            Toast.makeText(DetailEventActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
          } catch (Exception e) {
            Toast.makeText(DetailEventActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
          }
        }
      }

      @Override
      public void onFailure(Call<List<Peserta>> call, Throwable t) {
        progressDialog.dismiss();
        sqlQueryList();
        Toast.makeText(getApplicationContext(), "Maaf koneksi bermasalah", Toast.LENGTH_LONG).show();
        call.cancel();
      }
    });
  }

  public void savedb(){
    FlowManager.getDatabase(AppController.class)
      .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
        new ProcessModelTransaction.ProcessModel<Peserta>() {
          @Override
          public void processModel(Peserta peserta, DatabaseWrapper wrapper) {

            peserta.save();

          }

        }).addAll(listPeserta).build())  // add elements (can also handle multiple)
      .error(new Transaction.Error() {
        @Override
        public void onError(Transaction transaction, Throwable error) {
          Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
        }
      })
      .success(new Transaction.Success() {
        @Override
        public void onSuccess(Transaction transaction) {
          Toast.makeText(getApplicationContext(), "Data Tersimpan", Toast.LENGTH_LONG).show();
          int jml = 0;
          for (int i=0; i<listPeserta.size(); i++) {
            if (listPeserta.get(i).getIsRegistered().equals(1)) {
              jml++;
            }
          }
          txtPeserta.setText(jml + " peserta");
          txtPeserta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent = new Intent(DetailEventActivity.this, PesertaActivity.class);
              intent.putExtra("idEvent", id);
              startActivity(intent);
            }
          });
        }
      }).build().execute();
  }

  public void sqlQueryList(){
    String rawQuery = "SELECT * FROM `Event` WHERE id = " + id;
    StringQuery<Event> stringQuery = new StringQuery(Event.class, rawQuery);
    stringQuery
      .async()
      .queryListResultCallback(new QueryTransaction.QueryResultListCallback<Event>() {
                                 @Override
                                 public void onListQueryResult(QueryTransaction transaction, @NonNull List<Event> models) {
                                   event = models.get(0);

                                   Picasso.get().load("http://"+localhost+"/baksosevent/assets/img/event/" + event.getImage()).into(ivEvent);
                                   txtNama.setText(event.getNama());
                                   txtDeskripsi.setText(event.getDeskripsi());
                                   txtDonasi.setText(event.getDataDonasi());
                                   txtTanggalWaktu.setText(event.getTanggal() + " | " + event.getWaktu());
                                   txtLokasi.setText(event.getLokasi());

                                   btnDaftar.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                       callDaftarEventAPI();
                                     }
                                   });

                                   callPesertaEventAPI();
                                 }
                               }
      ).execute();
  }

  ResponseAPI responseAPIdaftar;

  APIInterfacesRest apiInterfaceDaftar;
  ProgressDialog progressDialogDaftar;
  public void callDaftarEventAPI() {
    apiInterfaceDaftar = APIClient.getClient().create(APIInterfacesRest.class);
    progressDialogDaftar = new ProgressDialog(DetailEventActivity.this);
    progressDialogDaftar.setTitle("Loading");
    progressDialogDaftar.show();
    Call<ResponseAPI> mulaiRequest = apiInterfaceDaftar.postDaftarEvent(id, session.getUsername());
    mulaiRequest.enqueue(new Callback<ResponseAPI>() {
      @Override
      public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
        progressDialogDaftar.dismiss();
        responseAPIdaftar = response.body();
        if (responseAPIdaftar != null) {
          Toast.makeText(DetailEventActivity.this, responseAPIdaftar.getMessage(), Toast.LENGTH_LONG).show();
          finish();
          startActivity(getIntent());
        } else {
          try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            Toast.makeText(DetailEventActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
          } catch (Exception e) {
            Toast.makeText(DetailEventActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
          }
        }
      }

      @Override
      public void onFailure(Call<ResponseAPI> call, Throwable t) {
        progressDialogDaftar.dismiss();
        sqlQueryList();
        Toast.makeText(getApplicationContext(), "Maaf koneksi bermasalah", Toast.LENGTH_LONG).show();
        call.cancel();
      }
    });
  }
}

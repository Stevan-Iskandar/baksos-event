package com.bootcamp.baksosevent.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.bootcamp.baksosevent.R;
import com.bootcamp.baksosevent.adapter.AllEventAdapter;
import com.bootcamp.baksosevent.adapter.AllPesertaAdapter;
import com.bootcamp.baksosevent.application.AppController;
import com.bootcamp.baksosevent.model.Event;
import com.bootcamp.baksosevent.model.Peserta;
import com.bootcamp.baksosevent.service.APIClient;
import com.bootcamp.baksosevent.service.APIInterfacesRest;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.queriable.StringQuery;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesertaActivity extends AppCompatActivity {
  RecyclerView listAllPeserta;
  String idEvent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_peserta);

    idEvent = getIntent().getStringExtra("idEvent");
    listAllPeserta = findViewById(R.id.listAllPeserta);
    sqlQueryList();
  }

  public void sqlQueryList(){
    String rawQuery = "SELECT * FROM `Peserta` WHERE idEvent = " + idEvent;
    StringQuery<Peserta> stringQuery = new StringQuery<>(Peserta.class, rawQuery);
    stringQuery
      .async()
      .queryListResultCallback(new QueryTransaction.QueryResultListCallback<Peserta>() {
                                 @Override
                                 public void onListQueryResult(QueryTransaction transaction, @NonNull List<Peserta> models) {
                                   setupAdapterList(models);
                                 }
                               }
      ).execute();
  }

  public void setupAdapterList(List<Peserta> model){
    AllPesertaAdapter toadapter = new AllPesertaAdapter (PesertaActivity.this, model);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PesertaActivity.this, LinearLayoutManager.VERTICAL, false);
    listAllPeserta.setLayoutManager(linearLayoutManager);

    listAllPeserta.setAdapter(toadapter);
  }
}

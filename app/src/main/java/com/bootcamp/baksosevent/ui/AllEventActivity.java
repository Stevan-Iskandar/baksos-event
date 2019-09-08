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
import com.bootcamp.baksosevent.application.AppController;
import com.bootcamp.baksosevent.model.Event;
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

public class AllEventActivity extends AppCompatActivity {
  RecyclerView listAllEvent;
  List<Event> listEvent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_all_event);

    listAllEvent = findViewById(R.id.listAllEvent);
    callEvent();
  }

  APIInterfacesRest apiInterface;
  ProgressDialog progressDialog;
  public void callEvent() {
    apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
    progressDialog = new ProgressDialog(AllEventActivity.this);
    progressDialog.setTitle("Loading");
    progressDialog.show();
    Call<List<Event>> mulaiRequest = apiInterface.getAllEvent();
    mulaiRequest.enqueue(new Callback<List<Event>>() {
      @Override
      public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
        progressDialog.dismiss();
        listEvent = response.body();
//        Toast.makeText(AllEventActivity.this, listEvent.toString(), Toast.LENGTH_LONG).show();
        if (listEvent != null) {
          savedb(); //setupAdapterList(userList);
        } else {
          try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            Toast.makeText(AllEventActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
          } catch (Exception e) {
            Toast.makeText(AllEventActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
          }
        }
      }

      @Override
      public void onFailure(Call<List<Event>> call, Throwable t) {
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
        new ProcessModelTransaction.ProcessModel<Event>() {
          @Override
          public void processModel(Event post, DatabaseWrapper wrapper) {

            post.save();

          }

        }).addAll(listEvent).build())  // add elements (can also handle multiple)
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
          sqlQueryList();
        }
      }).build().execute();
  }

  public void sqlQueryList(){
    String rawQuery = "SELECT * FROM `Event` WHERE isActive = 1";
    StringQuery<Event> stringQuery = new StringQuery<>(Event.class, rawQuery);
    stringQuery
      .async()
      .queryListResultCallback(new QueryTransaction.QueryResultListCallback<Event>() {
                                 @Override
                                 public void onListQueryResult(QueryTransaction transaction, @NonNull List<Event> models) {

                                   setupAdapterList(models);
                                 }
                               }
      ).execute();
  }

  public void setupAdapterList(List<Event> model){
    AllEventAdapter toadapter = new AllEventAdapter (AllEventActivity.this, model);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AllEventActivity.this, LinearLayoutManager.VERTICAL, false);
    listAllEvent.setLayoutManager(linearLayoutManager);

    listAllEvent.setAdapter(toadapter);
  }
}

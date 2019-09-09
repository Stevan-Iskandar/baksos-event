package com.bootcamp.baksosevent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bootcamp.baksosevent.R;
import com.bootcamp.baksosevent.model.Event;
import com.bootcamp.baksosevent.service.APIClient;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AllEventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  String localhost = APIClient.localhost;
  Context context;
  List<Event> lstEvent;

  public AllEventAdapter(Context context , List<Event> lstEvent ) {
    this.context = context;
    this.lstEvent = lstEvent;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    RecyclerView.ViewHolder vh;
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_all_event, parent, false);
    vh = new ContohViewHolder(v);
    return vh;
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof ContohViewHolder) {
      ContohViewHolder view = (ContohViewHolder) holder;
      final Event event = lstEvent.get(position);

      Picasso.get().load("http://"+localhost+"/baksosevent/assets/img/event/" + event.getImage()).into(view.ivAllEvent);
      view.txtNama.setText(event.getNama());
      view.txtLokasi.setText(event.getLokasi());
      view.txtTanggal.setText(event.getTanggal());
      view.txtWaktu.setText(event.getWaktu());
      view.cvAllEvent.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          Toast.makeText(context, "Card dipencet " + event.getId(), Toast.LENGTH_LONG).show();
        }
      });
    }
  }

  @Override
  public int getItemCount() {
    return lstEvent.size();
  }

  public class ContohViewHolder extends RecyclerView.ViewHolder {
    ImageView ivAllEvent;
    TextView txtNama, txtLokasi, txtTanggal, txtWaktu;
    CardView cvAllEvent;

    public ContohViewHolder(@NonNull View itemView) {
      super(itemView);
      cvAllEvent = itemView.findViewById(R.id.cvAllEvent);
      ivAllEvent = itemView.findViewById(R.id.ivAllEvent);
      txtNama = itemView.findViewById(R.id.txtNama);
      txtLokasi = itemView.findViewById(R.id.txtLokasi);
      txtTanggal = itemView.findViewById(R.id.txtTanggal);
      txtWaktu = itemView.findViewById(R.id.txtWaktu);
    }
  }
}

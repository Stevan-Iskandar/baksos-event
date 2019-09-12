package com.bootcamp.baksosevent.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bootcamp.baksosevent.R;
import com.bootcamp.baksosevent.model.Event;
import com.bootcamp.baksosevent.model.Peserta;
import com.bootcamp.baksosevent.ui.DetailEventActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AllPesertaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  Context context;
  List<Peserta> lstPeserta;

  public AllPesertaAdapter(Context context , List<Peserta> lstPeserta ) {
    this.context = context;
    this.lstPeserta = lstPeserta;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    RecyclerView.ViewHolder vh;
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_all_peserta, parent, false);
    vh = new ContohViewHolder(v);
    return vh;
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof ContohViewHolder) {
      ContohViewHolder view = (ContohViewHolder) holder;
      final Peserta peserta = lstPeserta.get(position);

      view.txtListUsername.setText(peserta.getUsername());
    }
  }

  @Override
  public int getItemCount() {
    return lstPeserta.size();
  }

  public class ContohViewHolder extends RecyclerView.ViewHolder {
    TextView txtListUsername;

    public ContohViewHolder(@NonNull View itemView) {
      super(itemView);
      txtListUsername = itemView.findViewById(R.id.txtListUsername);
    }
  }
}

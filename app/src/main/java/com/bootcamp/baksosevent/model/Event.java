package com.bootcamp.baksosevent.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bootcamp.baksosevent.application.AppController;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

@Table(database = AppController.class)
public class Event extends BaseModel implements Serializable, Parcelable {
  @SerializedName("id")
  @Expose
  @PrimaryKey (autoincrement = true)
  private Integer id;
  @SerializedName("image")
  @Expose
  @Column
  private String image;
  @SerializedName("nama")
  @Expose
  @Column
  private String nama;
  @SerializedName("lokasi")
  @Expose
  @Column
  private String lokasi;
  @SerializedName("tanggal")
  @Expose
  @Column
  private String tanggal;
  @SerializedName("waktu")
  @Expose
  @Column
  private String waktu;
  @SerializedName("deskripsi")
  @Expose
  @Column
  private String deskripsi;
  @SerializedName("data_donasi")
  @Expose
  @Column
  private String dataDonasi;
  @SerializedName("maker")
  @Expose
  @Column
  private String maker;
  @SerializedName("is_active")
  @Expose
  @Column
  private Integer isActive;

  private final static long serialVersionUID = -28233401489564790L;

  public final static Creator<Event> CREATOR = new Creator<Event>() {
    @SuppressWarnings({
      "unchecked"
    })
    public Event createFromParcel(Parcel in) {
      return new Event(in);
    }
    public Event[] newArray(int size) {
      return (new Event[size]);
    }
  };

  protected Event(Parcel in) {
    this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
    this.image = ((String) in.readValue((String.class.getClassLoader())));
    this.nama = ((String) in.readValue((String.class.getClassLoader())));
    this.lokasi = ((String) in.readValue((String.class.getClassLoader())));
    this.tanggal = ((String) in.readValue((String.class.getClassLoader())));
    this.waktu = ((String) in.readValue((String.class.getClassLoader())));
    this.deskripsi = ((String) in.readValue((String.class.getClassLoader())));
    this.dataDonasi = ((String) in.readValue((String.class.getClassLoader())));
    this.maker = ((String) in.readValue((String.class.getClassLoader())));
    this.isActive = ((Integer) in.readValue((Integer.class.getClassLoader())));
  }

  public Event() {
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeValue(id);
    dest.writeValue(image);
    dest.writeValue(nama);
    dest.writeValue(lokasi);
    dest.writeValue(tanggal);
    dest.writeValue(waktu);
    dest.writeValue(deskripsi);
    dest.writeValue(dataDonasi);
    dest.writeValue(maker);
    dest.writeValue(isActive);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getNama() {
    return nama;
  }

  public void setNama(String nama) {
    this.nama = nama;
  }

  public String getLokasi() {
    return lokasi;
  }

  public void setLokasi(String lokasi) {
    this.lokasi = lokasi;
  }

  public String getTanggal() {
    return tanggal;
  }

  public void setTanggal(String tanggal) {
    this.tanggal = tanggal;
  }

  public String getWaktu() {
    return waktu;
  }

  public void setWaktu(String waktu) {
    this.waktu = waktu;
  }

  public String getDeskripsi() {
    return deskripsi;
  }

  public void setDeskripsi(String deskripsi) {
    this.deskripsi = deskripsi;
  }

  public String getDataDonasi() {
    return dataDonasi;
  }

  public void setDataDonasi(String dataDonasi) {
    this.dataDonasi = dataDonasi;
  }

  public String getMaker() {
    return maker;
  }

  public void setMaker(String maker) {
    this.maker = maker;
  }

  public Integer getIsActive() {
    return isActive;
  }

  public void setIsActive(Integer isActive) {
    this.isActive = isActive;
  }
}

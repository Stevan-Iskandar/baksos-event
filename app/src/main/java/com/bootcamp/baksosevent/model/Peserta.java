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
public class Peserta extends BaseModel implements Serializable, Parcelable {
  @SerializedName("id")
  @Expose
  @PrimaryKey(autoincrement = true)
  private Integer id;
  @SerializedName("id_event")
  @Expose
  @Column
  private Integer idEvent;
  @SerializedName("username")
  @Expose
  @Column
  private String username;
  @SerializedName("is_registered")
  @Expose
  @Column
  private Integer isRegistered;

  private final static long serialVersionUID = -28233401489564790L;

  public final static Creator<Peserta> CREATOR = new Creator<Peserta>() {
    @SuppressWarnings({
      "unchecked"
    })
    public Peserta createFromParcel(Parcel in) {
      return new Peserta(in);
    }
    public Peserta[] newArray(int size) {
      return (new Peserta[size]);
    }
  };

  protected Peserta(Parcel in) {
    this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
    this.idEvent = ((Integer) in.readValue((Integer.class.getClassLoader())));
    this.username = ((String) in.readValue((String.class.getClassLoader())));
    this.isRegistered = ((Integer) in.readValue((Integer.class.getClassLoader())));
  }

  public Peserta() {
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeValue(id);
    dest.writeValue(idEvent);
    dest.writeValue(username);
    dest.writeValue(isRegistered);
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

  public Integer getIdEvent() {
    return idEvent;
  }

  public void setIdEvent(Integer idEvent) {
    this.idEvent = idEvent;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Integer getIsRegistered() {
    return isRegistered;
  }

  public void setIsRegistered(Integer isRegistered) {
    this.isRegistered = isRegistered;
  }
}

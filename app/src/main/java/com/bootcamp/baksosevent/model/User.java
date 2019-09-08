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
public class User extends BaseModel implements Serializable, Parcelable {
  @SerializedName("username")
  @Expose
  @PrimaryKey
  private String username;
  @SerializedName("password")
  @Expose
  @Column
  private String password;
  @SerializedName("nama")
  @Expose
  @Column
  private String nama;
  @SerializedName("email")
  @Expose
  @Column
  private String email;
  @SerializedName("alamat")
  @Expose
  @Column
  private String alamat;
  @SerializedName("tlp")
  @Expose
  @Column
  private String tlp;

  public final static Creator<User> CREATOR = new Creator<User>() {
    @SuppressWarnings({
      "unchecked"
    })
    public User createFromParcel(Parcel in) {
      return new User(in);
    }
    public User[] newArray(int size) {
      return (new User[size]);
    }
  };

  private final static long serialVersionUID = -28233401489564790L;

  protected User(Parcel in) {
    this.username = ((String) in.readValue((String.class.getClassLoader())));
    this.password = ((String) in.readValue((String.class.getClassLoader())));
    this.nama = ((String) in.readValue((String.class.getClassLoader())));
    this.email = ((String) in.readValue((String.class.getClassLoader())));
    this.alamat = ((String) in.readValue((String.class.getClassLoader())));
    this.tlp = ((String) in.readValue((String.class.getClassLoader())));
  }

  public User() {
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeValue(username);
    dest.writeValue(password);
    dest.writeValue(nama);
    dest.writeValue(email);
    dest.writeValue(alamat);
    dest.writeValue(tlp);
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getNama() {
    return nama;
  }

  public void setNama(String nama) {
    this.nama = nama;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAlamat() {
    return alamat;
  }

  public void setAlamat(String alamat) {
    this.alamat = alamat;
  }

  public String getTlp() {
    return tlp;
  }

  public void setTlp(String tlp) {
    this.tlp = tlp;
  }
}

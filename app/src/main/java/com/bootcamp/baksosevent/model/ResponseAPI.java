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

public class ResponseAPI implements Serializable, Parcelable {
  @SerializedName("status")
  @Expose
  private Boolean status;
  @SerializedName("message")
  @Expose
  private String message;

  private final static long serialVersionUID = -5422878540292933318L;

  public final static Creator<ResponseAPI> CREATOR = new Creator<ResponseAPI>() {
    @SuppressWarnings({
      "unchecked"
    })
    public ResponseAPI createFromParcel(Parcel in) {
      return new ResponseAPI(in);
    }
    public ResponseAPI[] newArray(int size) {
      return (new ResponseAPI[size]);
    }
  };

  protected ResponseAPI(Parcel in) {
    this.status = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    this.message = ((String) in.readValue((String.class.getClassLoader())));
  }

  public ResponseAPI() {
  }

  public void writeToParcel(Parcel dest, int flags) {
    dest.writeValue(status);
    dest.writeValue(message);
  }

  public int describeContents() {
    return  0;
  }

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}

package com.bootcamp.baksosevent.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bootcamp.baksosevent.R;
import com.bootcamp.baksosevent.model.Event;
import com.bootcamp.baksosevent.model.ResponseAPI;
import com.bootcamp.baksosevent.model.User;
import com.bootcamp.baksosevent.service.APIClient;
import com.bootcamp.baksosevent.service.APIInterfacesRest;
import com.bootcamp.baksosevent.utils.AppUtil;
import com.bootcamp.baksosevent.utils.SharedPreferencesUtil;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuatEventActivity extends AppCompatActivity {
  private static final int GALLERY_REQUEST_CODE = 1;
  Uri selectedImage;

  private DatePickerDialog datePickerDialog;
  private SimpleDateFormat dateFormatter;

  SharedPreferencesUtil session;
  ImageView ivPostEvent;
  TextView txtInsertPhoto;
  EditText etNamaEvent, etLokasiEvent, etTanggalEvent, etWaktuEvent, etDeskripsiEvent, etDataDonasiEvent;
  Button btnPostEvent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_buat_event);

    dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.JAPAN);
    session = new SharedPreferencesUtil(BuatEventActivity.this);
    ivPostEvent = findViewById(R.id.ivPostEvent);
    txtInsertPhoto = findViewById(R.id.txtInsertPhoto);
    etNamaEvent = findViewById(R.id.etNamaEvent);
    etLokasiEvent = findViewById(R.id.etLokasiEvent);
    etTanggalEvent = findViewById(R.id.etTanggalEvent);
    etWaktuEvent = findViewById(R.id.etWaktuEvent);
    etDeskripsiEvent = findViewById(R.id.etDeskripsiEvent);
    etDataDonasiEvent = findViewById(R.id.etDataDonasiEvent);
    btnPostEvent = findViewById(R.id.btnPostEvent);

    ivPostEvent.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeType = {"image/jpg", "image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
      }
    });

    etTanggalEvent.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        showDateDialog();
      }
    });

    btnPostEvent.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        buatEventAPI();
      }
    });
  }

  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if(resultCode == Activity.RESULT_OK) {
      switch (requestCode) {
        case GALLERY_REQUEST_CODE :
          selectedImage = data.getData();
          ivPostEvent.setImageURI(selectedImage);
          txtInsertPhoto.setVisibility(View.GONE);
          break;
      }
    }
  }

  private void showDateDialog(){
    /**
     * Calendar untuk mendapatkan tanggal sekarang
     */
    Calendar newCalendar = Calendar.getInstance();
    /**
     * Initiate DatePicker dialog
     */
    datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

      @Override
      public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        /**
         * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
         */

        /**
         * Set Calendar untuk menampung tanggal yang dipilih
         */
        Calendar newDate = Calendar.getInstance();
        newDate.set(year, monthOfYear, dayOfMonth);
        /**
         * Update TextView dengan tanggal yang kita pilih
         */
        etTanggalEvent.setText(dateFormatter.format(newDate.getTime()));
      }

    },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    /**
     * Tampilkan DatePicker dialog
     */
    datePickerDialog.show();
  }

  ResponseAPI responseAPI;
  Bitmap bitmap;

  APIInterfacesRest apiInterface;
  ProgressDialog progressDialog;
  public void buatEventAPI() {
    apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
    progressDialog = new ProgressDialog(BuatEventActivity.this);
    progressDialog.setTitle("Loading");
    progressDialog.show();

    try {
      if(selectedImage != null) {
        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
      }
    }
    catch (Exception e) {
      //handle exception
    }

    File file = createTempFile(bitmap);
    byte[] image  = AppUtil.FiletoByteArray(file);
    RequestBody requestFile1 = RequestBody.create(MediaType.parse("image/*"), compressCapture(image,100));
    MultipartBody.Part bodyImage = MultipartBody.Part.createFormData("image", file.getName()+".jpg", requestFile1);

    Call<ResponseAPI> mulaiRequest = apiInterface.postEvent(
      bodyImage,
      toRequestBody(AppUtil.replaceNull(etNamaEvent.getText().toString())),
      toRequestBody(AppUtil.replaceNull(etLokasiEvent.getText().toString())),
      toRequestBody(AppUtil.replaceNull(etTanggalEvent.getText().toString())),
      toRequestBody(AppUtil.replaceNull(etWaktuEvent.getText().toString())),
      toRequestBody(AppUtil.replaceNull(etDeskripsiEvent.getText().toString())),
      toRequestBody(AppUtil.replaceNull(etDataDonasiEvent.getText().toString())),
      toRequestBody(AppUtil.replaceNull(session.getUsername()))
    );

    mulaiRequest.enqueue(new Callback<ResponseAPI>() {
      @Override
      public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
        progressDialog.dismiss();
        responseAPI = response.body();
        if (responseAPI != null) {
          Toast.makeText(BuatEventActivity.this, responseAPI.getMessage(), Toast.LENGTH_LONG).show();
          finish();
        } else {
          try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            Toast.makeText(BuatEventActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
          } catch (Exception e) {
            Toast.makeText(BuatEventActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
          }
        }
      }

      @Override
      public void onFailure(Call<ResponseAPI> call, Throwable t) {
        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(), "Maaf koneksi bermasalah", Toast.LENGTH_LONG).show();
        call.cancel();
      }
    });
  }

//  fungsi convert image to bitmap
  public RequestBody toRequestBody(String value) {
    if (value==null){
      value="";
    }
    RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
    return body;
  }

  public static byte[] compressCapture(byte[] capture, int maxSizeKB) {

    // This should be different based on the original capture size
    int compression = 12;

    Bitmap bitmap  = BitmapFactory.decodeByteArray(capture, 0, capture.length);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG, compression, outputStream);
    return outputStream.toByteArray();
  }

  private File createTempFile(Bitmap bitmap) {
    File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)
      , System.currentTimeMillis() +"_image.webp");
    ByteArrayOutputStream bos = new ByteArrayOutputStream();

    bitmap.compress(Bitmap.CompressFormat.WEBP,0, bos);
    byte[] bitmapdata = bos.toByteArray();
    //write the bytes in file

    try {
      FileOutputStream fos = new FileOutputStream(file);
      fos.write(bitmapdata);
      fos.flush();
      fos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return file;
  }
//  fungsi convert image to bitmap (end)
}

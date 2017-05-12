/*
 * Copyright (c) 2017. Gilang Ramadhan (gilangramadhan96.gr@gmail.com)
 */

package id.codinate.workshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TambahActivity extends AppCompatActivity {
    EditText edNama, edKet, edTempat;
    String nama, ket, tempat;
    Button btnTambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //deklarasi
        fab.setVisibility(View.GONE);
        edNama = (EditText) findViewById(R.id.edtNamaW);
        edKet = (EditText) findViewById(R.id.edtKetW);
        edTempat = (EditText) findViewById(R.id.edtTempatW);
        btnTambah = (Button) findViewById(R.id.btnTambah);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edNama.setError(null);
                edKet.setError(null);
                edTempat.setError(null);
                if (CekKosong(edNama)) {
                    edNama.setError("Nama masih kosong");
                } else if (CekKosong(edTempat)) {
                    edTempat.setError("Tempat masih ksong");
                } else if (CekKosong(edKet)) {
                    edKet.setError("Keterangan masih kosong");
                } else {
                    // eksekusi
                    AQuery aQuery = new AQuery(TambahActivity.this);
                    ProgressDialog progressDialog = new ProgressDialog(TambahActivity.this);
                    progressDialog.setMessage("Loading bro");
                    String url = "http://192.168.1.15/techcomfest/tambah.php";

                    nama = edNama.getText().toString();
                    tempat = edTempat.getText().toString();
                    ket = edKet.getText().toString();

                    Map<String, String> param = new HashMap<String, String>();
                    param.put("nama_w", nama);
                    param.put("ket_w", ket);
                    param.put("tempat_w", tempat);

                    aQuery.progress(progressDialog).ajax(url, param, String.class, new AjaxCallback<String>() {
                        @Override
                        public void callback(String url, String object, AjaxStatus status) {
                            super.callback(url, object, status);
                            if (object != null) {
                                Log.d("hasil", object);
                                try {
                                    JSONObject jsonObject = new JSONObject(object);
                                    String pesan = jsonObject.getString("message");
                                    String sukses = jsonObject.getString("success");
                                    if (sukses.equalsIgnoreCase("true")) {
                                        Toast.makeText(getApplicationContext(), pesan, Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), pesan, Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Url error", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }

    public boolean CekKosong(EditText editText) {
        if (editText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

}

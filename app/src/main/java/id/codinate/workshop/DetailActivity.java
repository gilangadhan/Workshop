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

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
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
        fab.setVisibility(View.GONE);

        String nama = getIntent().getStringExtra("nama");
        String ket = getIntent().getStringExtra("keterangan");
        final String id = getIntent().getStringExtra("id");
        String tempat = getIntent().getStringExtra("tempat");

        final EditText ednama, edket, edtempat;
        ednama = (EditText) findViewById(R.id.edtNamaD);
        edket = (EditText) findViewById(R.id.edtKetD);
        edtempat = (EditText) findViewById(R.id.edtTempatD);

        ednama.setText(nama);
        edket.setText(ket);
        edtempat.setText(tempat);

        final Button edit = (Button) findViewById(R.id.btnEdit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ednama.setError(null);
                edket.setError(null);
                edtempat.setError(null);
                if (CekKosong(ednama)) {
                    ednama.setError("Nama masih kosong");
                } else if (CekKosong(edtempat)) {
                    edtempat.setError("Tempat masih ksong");
                } else if (CekKosong(edket)) {
                    edket.setError("Keterangan masih kosong");
                } else {
                    // eksekusi
                    AQuery aQuery = new AQuery(DetailActivity.this);
                    ProgressDialog progressDialog = new ProgressDialog(DetailActivity.this);
                    progressDialog.setMessage("Loading bro");
                    String url = "http://192.168.1.15/techcomfest/edit.php";

                    String namas, tempats, kets;
                    namas = ednama.getText().toString();
                    tempats = edtempat.getText().toString();
                    kets = edket.getText().toString();

                    Map<String, String> param = new HashMap<String, String>();
                    param.put("nama_w", namas);
                    param.put("id_w", id);
                    param.put("ket_w", kets);
                    param.put("tempat_w", tempats);
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
        Button hapus = (Button) findViewById(R.id.btnHapus);
        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AQuery aQuery = new AQuery(DetailActivity.this);
                ProgressDialog progressDialog = new ProgressDialog(DetailActivity.this);
                progressDialog.setMessage("Loading bro");
                String url = "http://192.168.1.15/techcomfest/hapus.php";

                Map<String, String> param = new HashMap<String, String>();

                param.put("id_w", id);
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

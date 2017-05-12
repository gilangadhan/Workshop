/*
 * Copyright (c) 2017. Gilang Ramadhan (gilangramadhan96.gr@gmail.com)
 */

package id.codinate.workshop;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

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
        String id = getIntent().getStringExtra("id");
        String tempat = getIntent().getStringExtra("tempat");

        EditText ednama, edket, edtempat;
        ednama = (EditText) findViewById(R.id.edtNamaD);
        edket = (EditText) findViewById(R.id.edtKetD);
        edtempat = (EditText) findViewById(R.id.edtTempatD);

        ednama.setText(nama);
        edket.setText(ket);
        edtempat.setText(tempat);

    }

}

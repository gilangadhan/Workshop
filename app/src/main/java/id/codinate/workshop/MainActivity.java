/*
 * Copyright (c) 2017. Gilang Ramadhan (gilangramadhan96.gr@gmail.com)
 */

package id.codinate.workshop;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    AQuery aQuery;
    ProgressDialog progressDialog;
    ArrayList<HashMap<String, String>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TambahActivity.class));
            }
        });
        listView = (ListView) findViewById(R.id.listView);
        aQuery = new AQuery(MainActivity.this);
        data = new ArrayList<HashMap<String, String>>();
        AmbilData();
    }

    private void AmbilData() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading bro");
        String url = "http://192.168.1.15/techcomfest/workshop.php";
        aQuery.progress(progressDialog).ajax(url, String.class, new AjaxCallback<String>() {
                    @Override
                    public void callback(String url, String object, AjaxStatus status) {
                        super.callback(url, object, status);
                        if (object != null) {
                            Log.d("Respon", object);
                            try {
                                JSONObject jsonObject = new JSONObject(object);
                                String success = jsonObject.getString("success");
                                String message = jsonObject.getString("message");
                                if (success.equalsIgnoreCase("true")) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("workshop");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject objectArray = jsonArray.getJSONObject(i);
                                        String id = objectArray.getString("id_workshop");
                                        String nama = objectArray.getString("nama_workshop");
                                        String tempat = objectArray.getString("tempat_workshop");
                                        String tanggal = objectArray.getString("tanggal_workshop");
                                        String keterangan = objectArray.getString("ket_workshop");
                                        HashMap<String, String> map = new HashMap<String, String>();
                                        map.put("id", id);
                                        map.put("nama", nama);
                                        map.put("tempat", tempat);
                                        map.put("tanggal", tanggal);
                                        map.put("keterangan", keterangan);
                                        data.add(map);
                                        setListAdapter(data);
                                    }

                                } else {
                                    pesan(message);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            pesan("url error");
                        }
                    }
                }
        );

    }

    private void setListAdapter(final ArrayList<HashMap<String, String>> data) {
        CustomAdapter adapter = new CustomAdapter(this, data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String , String > onData = data.get(position);
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("id", onData.get("id"));
                intent.putExtra("nama", onData.get("nama"));
                intent.putExtra("tempat", onData.get("tempat"));
                intent.putExtra("tanggal", onData.get("tanggal"));
                intent.putExtra("keterangan", onData.get("keterangan"));
                startActivity(intent);
            }
        });

    }

    private void pesan(String gagal) {
        Toast.makeText(getApplicationContext(), gagal, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class CustomAdapter extends BaseAdapter {
        Activity activity;
        ArrayList<HashMap<String, String>> datalanjutan;
        LayoutInflater layoutInflater;

        public CustomAdapter(Activity activity, ArrayList<HashMap<String, String>> dataCustom) {
            this.activity = activity;
            this.datalanjutan = dataCustom;
        }

        @Override
        public int getCount() {
            return datalanjutan.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(R.layout.list_item, null);
            TextView id = (TextView) v.findViewById(R.id.txtid);
            TextView nama = (TextView) v.findViewById(R.id.txtnama);
            TextView ket = (TextView) v.findViewById(R.id.txtket);
            TextView tanggal = (TextView) v.findViewById(R.id.txttanggal);
            TextView tempat = (TextView) v.findViewById(R.id.txttempat);

            HashMap<String, String> data = new HashMap<>();
            data = datalanjutan.get(position);
            id.setText(data.get("id"));
            nama.setText(data.get("nama"));
            tempat.setText(data.get("tempat"));
            ket.setText(data.get("keterangan"));
            tanggal.setText(data.get("tanggal"));
            return v;
        }
    }
}

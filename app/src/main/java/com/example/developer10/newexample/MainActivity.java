package com.example.developer10.newexample;

import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.developer10.newexample.Models.Character;
import com.example.developer10.newexample.Servicios.AppStatus;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> quotes;
    Button btnCon;
    RequestQueue queue;
    DatosAdapter adapter;
    ImageView img;
    Intent intent;
    Spinner spinner;
    String seleccion;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.cajaFrases);
        btnCon = findViewById(R.id.botonConsultar);
        img = findViewById(R.id.imagen);
        spinner = findViewById(R.id.spinnerNum);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Por favor espere.");
        progressDialog.setMessage("Cargando...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        String[] numeros = {"1","2","3","4","5", "6", "8", "9", "10"};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, numeros));
        spinner.setPrompt("Seleccione número de frases");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                seleccion = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        queue = Volley.newRequestQueue(this);

        intent = new Intent(getBaseContext(), CharacterActivity.class);

        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkConnection())
                    peticion();
                else
                    Toast.makeText(getApplicationContext(), "Ooops! No hay conexion a internet!", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));

    }

    public boolean checkConnection()
    {
        if(AppStatus.getInstance(getApplicationContext()).isOnline()) {
            return true;
        } else {
            return false;
        }

    }

    private void peticion(){

        final String url = "https://thesimpsonsquoteapi.glitch.me/quotes?count=" + seleccion;


        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        //adapter.notifyDataSetChanged();
                        fillQuotes(response);
                        fillObject(response);
                        //Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );
        progressDialog.show();
        queue.add(getRequest);
    }

    public void fillObject(JSONArray jsonArray){
        List<Character> contacts;
        Type listType = new TypeToken<List<Character>>() {
        }.getType();

        contacts = new Gson().fromJson(String.valueOf(jsonArray), listType);

        adapter.fillObject(contacts);
    }


    private void fillQuotes(JSONArray json){
        quotes = new ArrayList<>();

        for(int i = 0; i < json.length(); i++)
        {
            try {
                quotes.add(json.getJSONObject(i).getString("quote"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        adapter = new DatosAdapter(quotes);

        
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();        
            }
        });

        progressDialog.dismiss();
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);
    }
}

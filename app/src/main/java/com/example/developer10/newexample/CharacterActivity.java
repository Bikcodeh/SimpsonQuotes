package com.example.developer10.newexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class CharacterActivity extends AppCompatActivity {

    ImageView img;
    TextView personaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        img = findViewById(R.id.imagen);
        personaje = findViewById(R.id.name);
        Bundle extras = getIntent().getExtras();
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getString(R.string.app_name));
        mToolbar.setNavigationIcon(R.drawable.ic_action_arrow_back);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        personaje.setText(extras.getString("name"));
        Picasso.with(this).load(extras.getString("url")).into(img);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}

package com.example.redsocialuca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Pantalla_de_carga extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primera_pantalla);
        final int Duración= 2500;


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            Intent intent =new Intent(Pantalla_de_carga.this, MainActivity.class);
            startActivity(intent);
            //esto nos permite cambiar de una actividad a otra

            }
        },Duración);
    }
}
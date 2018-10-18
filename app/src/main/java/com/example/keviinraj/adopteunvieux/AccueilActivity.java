package com.example.keviinraj.adopteunvieux;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

public class AccueilActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton imgBtnProfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgBtnProfil = (ImageButton) findViewById(R.id.imgBtnProfil);
        imgBtnProfil.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBtnProfil:
                Intent profilIntent = new Intent(AccueilActivity.this, ProfilActivity.class);
                startActivity(profilIntent);
                break;
        }
    }
}
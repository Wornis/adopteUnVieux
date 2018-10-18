package com.example.keviinraj.adopteunvieux;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by keviinraj on 18/10/2018.
 */

public class ProfilActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnClose;
    private ImageButton btnValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        Toolbar toolbar =   (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnClose = (ImageButton) findViewById(R.id.btnClose);
        btnValid = (ImageButton) findViewById(R.id.btnValid);
        btnClose.setOnClickListener(this);
        btnValid.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent accueilIntent = new Intent(ProfilActivity.this, AccueilActivity.class);
        switch (v.getId()) {
            case R.id.btnClose:
                startActivity(accueilIntent);
                break;
            case R.id.btnValid:
                startActivity(accueilIntent);
                break;
        }
    }
}

package com.example.keviinraj.adopteunvieux;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.keviinraj.adopteunvieux.Utils.HttpUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by keviinraj on 19/10/2018.
 */

public class InscriptionActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText username;
    private EditText password;
    private EditText email;
    private EditText fname;
    private EditText lname;
    private Button subscribe;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        subscribe = (Button) findViewById(R.id.subscribe);
        cancel = (Button) findViewById(R.id.cancel);

        subscribe.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == subscribe.getId()) {
            RequestParams params = new RequestParams();
            params.put("username", username.getText().toString());
            params.put("password", password.getText().toString());
            params.put("email", email.getText().toString());
            params.put("fname", fname.getText().toString());
            params.put("lname", lname.getText().toString());
            params.put("birthdate", "2018-10-18T12:47:47.291Z");
            params.put("old", "true");

            HttpUtils.post("profiles", params, new JsonHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Toast.makeText(getApplicationContext(), "Erreur, le profil ne peut être créée.",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Intent mainIntent = new Intent(InscriptionActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    Toast.makeText(getApplicationContext(), "Votre profil a été créée.",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if (v.getId() == cancel.getId()) {
            Intent intent = new Intent(InscriptionActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }}
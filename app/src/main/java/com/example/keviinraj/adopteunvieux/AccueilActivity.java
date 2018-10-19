package com.example.keviinraj.adopteunvieux;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.keviinraj.adopteunvieux.Utils.HttpUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.example.keviinraj.adopteunvieux.R.id.cancel;
import static com.example.keviinraj.adopteunvieux.R.id.imgPhoto;

public class AccueilActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton imgBtnProfil;
    private SharedPreferences globalDatas;
    private ImageButton imgExit;
    private JSONArray profiles;
    private ImageButton imgProfile;
    int intProfile = 0;
    private Button btnOther;
    private Button btnThis;
    private TextView textViewNom;
    private TextView textViewPrenom;
    private TextView textViewDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgBtnProfil = (ImageButton) findViewById(R.id.imgBtnProfil);
        imgExit = (ImageButton) findViewById(R.id.btnExit);
        imgProfile = (ImageButton) findViewById(R.id.imgProfile);
        btnOther = (Button) findViewById(R.id.btnOther);
        btnThis = (Button) findViewById(R.id.btnThis);
        textViewNom = (TextView) findViewById(R.id.textViewNom);
        textViewPrenom = (TextView) findViewById(R.id.textViewPrenom);
        textViewDescription = (TextView) findViewById(R.id.textViewDesc);

        imgBtnProfil.setOnClickListener(this);
        imgExit.setOnClickListener(this);
        btnOther.setOnClickListener(this);
        btnThis.setOnClickListener(this);
        globalDatas = getSharedPreferences("app", MODE_PRIVATE);

        HttpUtils.get("profiles", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                profiles = response;
                String profileId = "";
                try {
                    textViewNom.setText(profiles.getJSONObject(intProfile).getString("fname"));
                    textViewPrenom.setText(profiles.getJSONObject(intProfile).getString("lname"));
                    textViewDescription.setText(profiles.getJSONObject(intProfile).getString("description"));
                    profileId = profiles.getJSONObject(intProfile++).getString("id");
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                HttpUtils.get("containers/" + profileId + "/files", null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        try {
                            JSONObject datas = response.getJSONObject(0);
                            String urlPhotoContainer = datas.getString("container");
                            String urlPhotoFile = datas.getString("name");
                            Picasso.with(getApplicationContext())
                                    .load("http://10.0.2.2:3000/api/containers/" + urlPhotoContainer
                                            + "/download/" +urlPhotoFile).into(imgProfile);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Toast.makeText(getApplicationContext(),
                        "Impossible de récupérer les profils", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBtnProfil:
                Intent profilIntent = new Intent(AccueilActivity.this, ProfilActivity.class);
                startActivity(profilIntent);
                break;
            case R.id.btnExit:
                Intent mainIntent = new Intent(AccueilActivity.this, MainActivity.class);
                startActivity(mainIntent);
                break;
            case R.id.btnOther:
                String profileId = "";
                try {
                    profileId = profiles.getJSONObject(intProfile++).getString("id");
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                HttpUtils.get("containers/" + profileId + "/files", null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        try {
                            textViewNom.setText(profiles.getJSONObject(intProfile).getString("fname"));
                            textViewPrenom.setText(profiles.getJSONObject(intProfile).getString("lname"));
                            textViewDescription.setText(profiles.getJSONObject(intProfile).getString("description"));
                            JSONObject datas = response.getJSONObject(0);
                            String urlPhotoContainer = datas.getString("container");
                            String urlPhotoFile = datas.getString("name");
                            Picasso.with(getApplicationContext())
                                    .load("http://10.0.2.2:3000/api/containers/" + urlPhotoContainer
                                            + "/download/" +urlPhotoFile).into(imgProfile);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;

        }
    }
}
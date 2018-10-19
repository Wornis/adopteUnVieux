package com.example.keviinraj.adopteunvieux;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.keviinraj.adopteunvieux.Utils.HttpUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import cz.msebera.android.httpclient.Header;


/**
 * Created by keviinraj on 18/10/2018.
 */

public class ProfilActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnClose;
    private ImageButton btnValid;
    private EditText editPrenom;
    private EditText editNom;
    private EditText editBirthDay;
    private EditText editBirthMonth;
    private EditText editBirthYear;
    private EditText editLocation;
    private EditText editDescription;
    private SharedPreferences globalDatas;
    private ImageButton imgPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        Toolbar toolbar =   (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        globalDatas = getSharedPreferences("app", MODE_PRIVATE);

        btnClose = (ImageButton) findViewById(R.id.btnClose);
        btnValid = (ImageButton) findViewById(R.id.btnValid);
        editPrenom = (EditText) findViewById(R.id.editPrenom);
        editNom = (EditText) findViewById(R.id.editNom);
        editBirthDay = (EditText) findViewById(R.id.editDay);
        editBirthMonth = (EditText) findViewById(R.id.editMonth);
        editBirthYear = (EditText) findViewById(R.id.editYear);
        editLocation = (EditText) findViewById(R.id.editPlace);
        editDescription = (EditText) findViewById(R.id.editDesc);
        imgPhoto = (ImageButton) findViewById(R.id.imgPhoto);

        editPrenom.setText(globalDatas.getString("fname", null));
        editNom.setText(globalDatas.getString("lname", null));
        editLocation.setText(globalDatas.getString("location", null));
        editDescription.setText(globalDatas.getString("description", null));

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        DateTime dtBirthDate = formatter.parseDateTime(globalDatas.getString("birthdate", null));
        editBirthDay.setText(String.valueOf(dtBirthDate.getDayOfMonth()));
        editBirthMonth.setText(String.valueOf(dtBirthDate.getMonthOfYear()));
        editBirthYear.setText(String.valueOf(dtBirthDate.getYear()));
        btnClose.setOnClickListener(this);
        btnValid.setOnClickListener(this);

        HttpUtils.get("containers/" + globalDatas.getInt("id", 0) + "/files", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    JSONObject datas = response.getJSONObject(0);
                    String urlPhotoContainer = datas.getString("container");
                    String urlPhotoFile = datas.getString("name");

                    SharedPreferences.Editor editor = globalDatas.edit();
                    editor.putString("urlPhotoContainer", urlPhotoContainer);
                    editor.putString("urlPhotoFile", urlPhotoFile);
                    editor.apply();
                    Picasso.with(getApplicationContext())
                            .load("http://10.0.2.2:3000/api/containers/" + urlPhotoContainer + "/download/" +urlPhotoFile).into(imgPhoto);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Toast.makeText(getApplicationContext(), "Impossible de récupérer la photo \n Elle n'existe surrement pas.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        final Intent accueilIntent = new Intent(ProfilActivity.this, AccueilActivity.class);
        switch (v.getId()) {
            case R.id.btnClose:
                startActivity(accueilIntent);
                break;
            case R.id.btnValid:
                RequestParams params = new RequestParams();
                params.put("fname", editPrenom.getText().toString());
                params.put("lname", editNom.getText().toString());
                params.put("location", editLocation.getText().toString());
                params.put("description", editDescription.getText().toString());
                HttpUtils.patch("profiles/" + String.valueOf(globalDatas.getInt("id", 0)), params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        SharedPreferences.Editor editor = globalDatas.edit();
                        try {
                            editor.putBoolean("old", response.getBoolean("old"));
                            editor.putString("fname", response.getString("fname"));
                            editor.putString("lname", response.getString("lname"));
                            editor.putString("birthdate", response.getString("birthdate"));
                            editor.putString("photoid", response.getString("photoid"));
                            editor.putString("description", response.getString("description"));
                            editor.putString("location", response.getString("location"));
                            editor.putString("realm", response.getString("realm"));
                            editor.putString("username", response.getString("username"));
                            editor.putString("email", response.getString("email"));
                            editor.putBoolean("emailVerified", false);
                            editor.putInt("id", response.getInt("id"));
                            editor.apply();
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivity(accueilIntent);
                        Toast.makeText(getApplicationContext(), "Profil mis à jour", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Toast.makeText(getApplicationContext(), "Impossible de mettre à jour le profil. \n Problème avec l'api",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
}

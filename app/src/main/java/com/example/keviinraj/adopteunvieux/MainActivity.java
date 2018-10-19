package com.example.keviinraj.adopteunvieux;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.keviinraj.adopteunvieux.Utils.HttpUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextLogin;
    private EditText editTextPassword;
    private Button btnLogin;
    private TextView textSubscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextLogin = (EditText) findViewById(R.id.editTextLogin);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        textSubscribe = (TextView) findViewById(R.id.textViewSubscribe);

        btnLogin.setOnClickListener(this);
        textSubscribe.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                RequestParams params = new RequestParams();
                params.put("username", editTextLogin.getText().toString());
                params.put("password", editTextPassword.getText().toString());
                HttpUtils.post("profiles/login", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        System.out.println(errorResponse.toString());
                        Toast.makeText(getApplicationContext(), "Identifiants invalides ", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        if (statusCode == 200) {
                            try {
                                String userId = String.valueOf(response.get("userId"));
                                HttpUtils.get("profiles/" + userId, null, new JsonHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                        SharedPreferences shared = getApplicationContext().getSharedPreferences("app", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = shared.edit();
                                        editor.clear().apply();
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
                                            Intent accueilIntent = new Intent(MainActivity.this, AccueilActivity.class);
                                            startActivity(accueilIntent);

                                        }
                                        catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                        System.out.println(errorResponse.toString());
                                        Toast.makeText(getApplicationContext(), "Problème avec l'api", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                break;
            case R.id.textViewSubscribe:
                Intent subscribeIntent = new Intent(MainActivity.this, InscriptionActivity.class);
                startActivity(subscribeIntent);
                break;
        }
    }
}

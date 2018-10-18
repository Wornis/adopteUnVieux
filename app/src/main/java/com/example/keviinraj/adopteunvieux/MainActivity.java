package com.example.keviinraj.adopteunvieux;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.keviinraj.adopteunvieux.Utils.HttpUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextLogin;
    private EditText editTextPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextLogin = (EditText) findViewById(R.id.editTextLogin);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                RequestParams rp = new RequestParams();
                HttpUtils.get("profiles/1", null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        Log.d("testKevin", "---------------- this is response : " + response);
                        try {
                            JSONObject serverResp = new JSONObject(response.toString());
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
               /* Intent accueilIntent = new Intent(MainActivity.this, AccueilActivity.class);
                startActivity(accueilIntent); */
                break;
        }
    }
}

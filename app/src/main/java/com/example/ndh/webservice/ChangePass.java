package com.example.ndh.webservice;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePass extends AppCompatActivity {

    Toolbar toolbar;
    Button btnChangepass;
    TextInputLayout inputpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        toolbar = findViewById(R.id.changepasstoolbar);
        btnChangepass = findViewById(R.id.btnchang);
        inputpass = findViewById(R.id.inputchangepassword);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Change password");

        final String id = getIntent().getStringExtra("mId");
        final String key = getIntent().getStringExtra("mKey");

        btnChangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String password = inputpass.getEditText().getText().toString();
                if(password.isEmpty()){
                    Toast.makeText(ChangePass.this, "pass word not empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    RequestQueue queue = Volley.newRequestQueue(ChangePass.this);
                    StringRequest request = new StringRequest(Request.Method.POST, Public.urlchangepass, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(ChangePass.this, "my error :"+error, Toast.LENGTH_LONG).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String,String> map = new HashMap<String, String>();
                            map.put("ID",id);
                            map.put("KEY",key);
                            map.put("PASSWORD",password);
                            return map;
                        }
                    };
                    queue.add(request);
                }
                Toast.makeText(ChangePass.this, "Success", Toast.LENGTH_SHORT).show();
                finish();
            }

        });
    }
}

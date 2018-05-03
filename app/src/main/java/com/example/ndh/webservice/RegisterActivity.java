package com.example.ndh.webservice;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
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

public class RegisterActivity extends AppCompatActivity {
    private Toolbar registerToolbar;
    Button btnRegister;
    RadioButton radMale,radFemale;
    Boolean sex;
    TextInputLayout inputDislayName,inputEmailregister,inputPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerToolbar=findViewById(R.id.registerToolbar);
        setSupportActionBar(registerToolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnRegister = findViewById(R.id.btnReg);
        inputDislayName = findViewById(R.id.inputRegName);
        inputEmailregister = findViewById(R.id.inputRegEmail);
        inputPassword = findViewById(R.id.inputRegPassword);
        radMale = findViewById(R.id.radmale);
        radFemale = findViewById(R.id.radFemale);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String ten = inputDislayName.getEditText().getText().toString().trim();
                final String tk = inputEmailregister.getEditText().getText().toString().trim();
                final String mk = inputPassword.getEditText().getText().toString().trim();
                if(radMale.isChecked()){
                    sex = true;
                }else if(radFemale.isChecked()){
                    sex =false;
                }

                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);

                StringRequest request = new StringRequest(Request.Method.POST, Public.urlDangKy, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(RegisterActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                        JSONObject json = null;

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(RegisterActivity.this, "my error :"+error, Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String,String> map = new HashMap<String, String>();
                        map.put("email",tk);
                        map.put("password",mk);
                        map.put("sex", String.valueOf(sex));
                        map.put("username",ten);
                        return map;
                    }
                };
                queue.add(request);
            }
        });
    }
}

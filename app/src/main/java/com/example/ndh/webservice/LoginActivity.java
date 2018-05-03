package com.example.ndh.webservice;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import io.socket.client.IO;

public class LoginActivity extends AppCompatActivity {
    private Toolbar loginToolbar;
    Button btnLogin;
    TextInputLayout inputEmailDangNhap,inputPasswordDangNhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        inputEmailDangNhap = findViewById(R.id.inputLoginEmail);
        inputPasswordDangNhap = findViewById(R.id.inputLoginPassword);
        //Toolbar
        loginToolbar=findViewById(R.id.loginToolbar);
        setSupportActionBar(loginToolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String tk = inputEmailDangNhap.getEditText().getText().toString().trim();
                final String mk = inputPasswordDangNhap.getEditText().getText().toString().trim();

                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                StringRequest request = new StringRequest(Request.Method.POST, Public.urlDangNhap, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(LoginActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                        JSONObject json = null;
                        try {
                            json = new JSONObject(response);
                            int ketqua = json.getInt("KETQUA");
                            if(ketqua == 1){
                                JSONObject userobject = json.getJSONObject("USER") ;
                                String userId = userobject.getString("ID");
                                boolean sex = userobject.getBoolean("SEX");
                                String username = userobject.getString("USERNAME");
                                String key = userobject.getString("KEY");
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("ID",userId);
                                bundle.putBoolean("SEX",sex);
                                bundle.putString("USERNAME",username);
                                bundle.putString("KEY",key);
                                intent.putExtra("USERBUNDLE",bundle);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "Thất Bại", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(LoginActivity.this, "my error :"+error, Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String,String> map = new HashMap<String, String>();
                        map.put("email",tk);
                        map.put("password",mk);
                        return map;
                    }
                };
                queue.add(request);
            }
        });
    }
}

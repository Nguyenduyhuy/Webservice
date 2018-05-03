package com.example.ndh.webservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    Button btnRegister,btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        anhxa();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginintent = new Intent(StartActivity.this,LoginActivity.class);
                startActivity(loginintent);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerintent = new Intent(StartActivity.this,RegisterActivity.class);
                startActivity(registerintent);
            }
        });

    }

    private void anhxa() {
        btnLogin = findViewById(R.id.btnAlreadyHave);
        btnRegister= findViewById(R.id.btnRegister);
    }
}

package com.example.win.chatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartPageActivity extends AppCompatActivity {

    Button login;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        login = (Button)findViewById(R.id.sign_in_button);
        register = (Button)findViewById(R.id.sign_up_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginintent = new Intent(StartPageActivity.this, LoginActivity.class);
                startActivity(loginintent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerintent = new Intent(StartPageActivity.this, RegisterActivity.class);
                startActivity(registerintent);
            }
        });
    }


}

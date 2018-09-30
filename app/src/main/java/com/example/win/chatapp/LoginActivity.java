package com.example.win.chatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Toolbar mToolbar;


    private EditText userEmail;
    private EditText userPass;
    private Button login_button;


    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mToolbar = (Toolbar)findViewById(R.id.login_toolbar);

        mAuth = FirebaseAuth.getInstance();

        loadingBar = new ProgressDialog(this);

        userEmail = (EditText)findViewById(R.id.login_email);
        userPass = (EditText)findViewById(R.id.login_pass);
        login_button = (Button)findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString();
                String pass = userPass.getText().toString();
                loginuser(email, pass);
            }
        });


        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Sign In");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void loginuser(String email, String pass){
        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(LoginActivity.this, "Please write your email", Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(pass))
        {
            Toast.makeText(LoginActivity.this, "Please write your password", Toast.LENGTH_LONG).show();
        }
        else
        {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please Wait, While we are verifying your account  ..");
            loadingBar.show();
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(mainIntent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Check your email and password", Toast.LENGTH_LONG).show();
                    }
                    loadingBar.dismiss();
                }
            });
        }
    }
}

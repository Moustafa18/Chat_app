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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private DatabaseReference storeUserDefaultDataReference;

    private FirebaseAuth mAuth;
    private EditText userRegisterName;
    private EditText userRegisterEmail;
    private EditText userRegisterPassword;
    private Button createAccountButton;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mToolbar = (Toolbar)findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        userRegisterName = (EditText)findViewById(R.id.register_name);
        userRegisterEmail = (EditText)findViewById(R.id.register_email);
        userRegisterPassword = (EditText)findViewById(R.id.register_passworrd);
        createAccountButton = (Button)findViewById(R.id.create_account_button);
        loadingBar = new ProgressDialog(this);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = userRegisterName.getText().toString();
                String email = userRegisterEmail.getText().toString();
                String pass = userRegisterPassword.getText().toString();
                Register(name, email, pass);
                
                
            }
        });
    }

    private void Register(final String name, String email, String pass) {

        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(RegisterActivity.this, "Please write your name", Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(RegisterActivity.this, "Please write your email", Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(pass))
        {
            Toast.makeText(RegisterActivity.this, "Please write your password", Toast.LENGTH_LONG).show();
        }
        else
        {
            loadingBar.setTitle("Creating New Account");
            loadingBar.setMessage("Please Wait, While we are Creating account for you ..");
            loadingBar.show();
            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        String currentUserId = mAuth.getCurrentUser().getUid();
                        storeUserDefaultDataReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
                        storeUserDefaultDataReference.child("User_Name").setValue(name);
                        storeUserDefaultDataReference.child("User_Status").setValue("use chat");
                        storeUserDefaultDataReference.child("User_Image").setValue("default_profile");
                        storeUserDefaultDataReference.child("User_thumb_image").setValue("default_image").
                                addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                       {
                                           Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                                           mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                           startActivity(mainIntent);
                                           finish();
                                        }
                                    }
                                });

                        //Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                        //mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        //startActivity(mainIntent);
                        //finish();
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, "Error occured, Try again", Toast.LENGTH_LONG).show();
                    }
                    loadingBar.dismiss();
                }
            });
        }

    }
}

package com.example.win.chatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class StateActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);

        mToolbar = (Toolbar)findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Change Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

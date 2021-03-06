package com.example.win.chatapp;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.design.widget.TabLayout;
import android.widget.TableLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

//    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabl;
    private TabsPagerAdaptor mTabsPagerAdaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mAuth = FirebaseAuth.getInstance();

        mViewPager = (ViewPager)findViewById(R.id.main_tabs_page);
        mTabsPagerAdaptor = new TabsPagerAdaptor(getSupportFragmentManager());
        mViewPager.setAdapter(mTabsPagerAdaptor);
        mTabl = (TabLayout)findViewById(R.id.main_tabs);
        mTabl.setupWithViewPager(mViewPager);


        mToolbar = (Toolbar)findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Chat");
    }



    @Override
    protected void onStart() {
        super.onStart();

        /*FirebaseUser currentUser = mAuth.getCurrentUser();
        // not login
        if (currentUser == null)
        {
            LogoutUser();
        }*/
    }

    private void LogoutUser() {
        Intent startPageIntent = new Intent(MainActivity.this, StartPageActivity.class);
        // handle when go back not to get out of the email
        startPageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(startPageIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.main_logout_button)
        {
            //mAuth.signOut();
            LogoutUser();

        }
        if (item.getItemId() == R.id.main_account_setting_button)
        {
            Intent showProfile = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(showProfile);
        }
       /* if (item.getItemId() == R.id.main_all_users_button)
        {
            //Intent showAllUser = new Intent(MainActivity.this, AllUserActivity.class);
            //startActivity(showAllUser);
        }*/
        return true;
    }
}

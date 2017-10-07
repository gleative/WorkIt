package com.example.gleative.workit;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.gleative.workit.fragments.NavigationDrawerFragment;

public class ExerciseActivity extends AppCompatActivity {

    Toolbar toolbar;
    NavigationDrawerFragment navigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        toolbar = (Toolbar) findViewById(R.id.toolbar); // Finner toolbaren i Activity
//        SJEKK MANIFEST OG PÅ EXERCISES, VI KAN DEFINERE NAVN PÅ ACTIONBAR DER!
//        toolbar.setTitle("Exercises"); // Setter title på actionbar
        setSupportActionBar(toolbar);

        setUpDrawer();
    }

    private void setUpDrawer() {
        navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationDrawerFragment.setUpDrawer(drawerLayout, toolbar, R.id.nav_home);
    }

    @Override
    protected void onStart(){
        navigationDrawerFragment.updateCheckedItem(R.id.nav_home);

        super.onStart();
    }



}

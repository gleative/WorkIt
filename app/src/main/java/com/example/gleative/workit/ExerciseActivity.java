package com.example.gleative.workit;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.gleative.workit.adapter.ExercisesRecyclerAdapter;
import com.example.gleative.workit.fragments.NavigationDrawerFragment;
import com.example.gleative.workit.model.Exercise;

public class ExerciseActivity extends AppCompatActivity {

    Toolbar toolbar;
    NavigationDrawerFragment navigationDrawerFragment;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        toolbar = (Toolbar) findViewById(R.id.toolbar); // Finner toolbaren i Activity
//        SJEKK MANIFEST OG PÅ EXERCISES, VI KAN DEFINERE NAVN PÅ ACTIONBAR DER!
//        toolbar.setTitle("Exercises"); // Setter title på actionbar
        setSupportActionBar(toolbar);

        setUpDrawer();
        setUpRecyclerView();
    }

    private void setUpDrawer() {
        navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationDrawerFragment.setUpDrawer(drawerLayout, toolbar, R.id.nav_exercises);
    }

    @Override
    protected void onStart(){
        navigationDrawerFragment.updateCheckedItem(R.id.nav_exercises);

        super.onStart();
    }

    private void setUpRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.exercise_recycler_view);
        ExercisesRecyclerAdapter adapter = new ExercisesRecyclerAdapter(this, Exercise.getData()); // Må ha constructor på adapteren ellers du får error!
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManagerVertical = new LinearLayoutManager(this);
        linearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManagerVertical);
    }



}

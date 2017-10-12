package com.example.gleative.workit;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.gleative.workit.adapter.ExercisesRecyclerAdapter;
import com.example.gleative.workit.fragments.ExerciseInfoFragment;
import com.example.gleative.workit.fragments.ExerciseListFragment;
import com.example.gleative.workit.fragments.NavigationDrawerFragment;
import com.example.gleative.workit.model.Exercise;

public class ExerciseActivity extends AppCompatActivity implements ExerciseListFragment.OnExerciseFragmentSelectedListener{

    Toolbar toolbar;
    NavigationDrawerFragment navigationDrawerFragment;
    ExerciseInfoFragment exerciseInfoFragment;
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

    @Override
    public void onExerciseSelected(Exercise selectedExercise) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
        exerciseInfoFragment = (ExerciseInfoFragment) getSupportFragmentManager().findFragmentById(R.id.exercise_info_fragment); // Fragment from "activity_exercise_info"

        exerciseInfoFragment.setDisplayDetail(selectedExercise.getExerciseID());

        Intent intent = new Intent(this, ExerciseInfoActivity.class);
        intent.putExtra("exercise_id", selectedExercise.getExerciseID());
        startActivity(intent);
    }
}

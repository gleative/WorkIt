package com.example.gleative.workit;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.gleative.workit.fragments.CreateWorkoutFragment;
import com.example.gleative.workit.fragments.ExerciseListFragment;
import com.example.gleative.workit.fragments.NavigationDrawerFragment;
import com.example.gleative.workit.model.Exercise;

public class CreateWorkoutActivity extends AppCompatActivity implements ExerciseListFragment.OnExerciseFragmentInteractionListener{

    Toolbar toolbar;
    NavigationDrawerFragment navigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUpDrawer();

        FragmentManager fragmentManager = getSupportFragmentManager();
        CreateWorkoutFragment createWorkoutFragment = (CreateWorkoutFragment) fragmentManager.findFragmentById(R.id.create_workout_fragment);

    }

    private void setUpDrawer() {
        navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationDrawerFragment.setUpDrawer(drawerLayout, toolbar, R.id.nav_myWorkouts);
    }

    @Override
    protected void onStart(){
        navigationDrawerFragment.updateCheckedItem(R.id.nav_myWorkouts);

        super.onStart();
    }

    // FAB button
    public void addCustomExercise(View view) {
        Intent intent = new Intent(this, ExerciseActivity.class);
        startActivity(intent);
    }

    // When user presses the add button
    public void addExercise(Exercise exercise){
        Intent intent = new Intent(this, CreateCustomExerciseActivity.class);
        intent.putExtra("exercise_id", exercise.getExerciseID());
        startActivity(intent);
    }

    @Override
    public void onExerciseSelected(Exercise exercise) {
        // Her kan du ha at den går til vinduet hvor du kan definere hvor mange sets og reps du skal ha
        Intent intent = new Intent(this, ExerciseInfoActivity.class);
        intent.putExtra("exercise_id", exercise.getExerciseID());
        startActivity(intent);
    }

}

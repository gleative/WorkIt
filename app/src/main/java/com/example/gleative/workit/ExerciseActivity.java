package com.example.gleative.workit;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.gleative.workit.adapter.ExercisesRecyclerAdapter;
import com.example.gleative.workit.fragments.ExerciseInfoFragment;
import com.example.gleative.workit.fragments.ExerciseListFragment;
import com.example.gleative.workit.fragments.NavigationDrawerFragment;
import com.example.gleative.workit.model.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ExerciseActivity extends AppCompatActivity implements ExerciseListFragment.OnExerciseFragmentInteractionListener{

    Toolbar toolbar;
    NavigationDrawerFragment navigationDrawerFragment;
    ExerciseInfoFragment exerciseInfoFragment;
    ExerciseListFragment exerciseListFragment;
    RecyclerView recyclerView;
    ExercisesRecyclerAdapter exercisesRecyclerAdapter;

    private List<Exercise> exercisesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        toolbar = (Toolbar) findViewById(R.id.toolbar); // Finner toolbaren i Activity
//        SJEKK MANIFEST OG PÅ EXERCISES, VI KAN DEFINERE NAVN PÅ ACTIONBAR DER!
//        toolbar.setTitle("Exercises"); // Setter title på actionbar
        setSupportActionBar(toolbar);

        exercisesList = Exercise.getData();

        // So we can access its methods
//        exerciseListFragment = (ExerciseListFragment) getSupportFragmentManager().findFragmentById(R.id.exercise_list_fragment);

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
        Intent intent = new Intent(this, ExerciseInfoActivity.class);
        intent.putExtra("exercise_id", selectedExercise.getExerciseID());
        startActivity(intent);


    }

}

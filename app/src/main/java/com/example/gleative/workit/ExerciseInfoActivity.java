package com.example.gleative.workit;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.gleative.workit.fragments.ExerciseInfoFragment;
import com.example.gleative.workit.fragments.NavigationDrawerFragment;
import com.example.gleative.workit.model.Exercise;

public class ExerciseInfoActivity extends AppCompatActivity {

//    Exercise selectedExercise;

    Toolbar toolbar;
    NavigationDrawerFragment navigationDrawerFragment;

    TextView textViewExerciseDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_info);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle(selectedExercise.getExerciseName());
        setSupportActionBar(toolbar);

        int id = getIntent().getIntExtra("exercise_id", 1);

        FragmentManager fragmentManager = getSupportFragmentManager();
        ExerciseInfoFragment exerciseInfoFragment = (ExerciseInfoFragment) fragmentManager.findFragmentById(R.id.exercise_info_fragment);

        exerciseInfoFragment.setDisplayedDetail(id);

//        setUpDrawer();
//        addData();
    }

//    private void setUpDrawer() {
//        navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
//        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        navigationDrawerFragment.setUpDrawer(drawerLayout, toolbar, R.id.nav_exercises);
//    }
//
//    @Override
//    protected void onStart(){
//        navigationDrawerFragment.updateCheckedItem(R.id.nav_exercises);
//
//        super.onStart();
//    }
//
//    private void addData(){
//        textViewExerciseDesc = (TextView) findViewById(R.id.exercise_description);
//        textViewExerciseDesc.setText(selectedExercise.getExerciseDescription());
//    }
}

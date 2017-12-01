package com.example.gleative.workit;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.gleative.workit.fragments.NavigationDrawerFragment;
import com.example.gleative.workit.fragments.WorkoutListFragment;
import com.example.gleative.workit.model.Workout;

public class MyWorkoutActivity extends AppCompatActivity implements WorkoutListFragment.OnWorkoutFragmentInteractionListener{

    // If value "1" the user wants to start a workout
    int startWorkout = 0;

    Toolbar toolbar;
    FloatingActionButton fab;
    NavigationDrawerFragment navigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.create_workout_fab);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            startWorkout = extras.getInt("startWorkout"); // Gives value sendt from MainActivity
            fab.hide(); // Hides the FloatingActionButton, method only called when a user wants to start a workout.
            // For some reason have to set title like this, or else it wouldnt apply the value
            getSupportActionBar().setTitle("Start Workout");
        }

        setUpDrawer();

    }

    private void setUpDrawer() {
        navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        navigationDrawerFragment.setUpDrawer(drawerLayout, toolbar, R.id.nav_exercises);
    }

    @Override
    protected void onStart(){
        // If user is starting a workout, we want the nav "home" to be checked instead of "my workouts"
        if(startWorkout == 1){
            navigationDrawerFragment.updateCheckedItem(R.id.nav_home);
        }
        else{
            navigationDrawerFragment.updateCheckedItem(R.id.nav_myWorkouts);
        }

        super.onStart();
    }

    // FAB button in activity_workout.xml, Starts CreateWorkoutActivity
    public void createWorkout(View view) {
        Intent intent = new Intent(this, CreateWorkoutActivity.class);
        startActivity(intent);
    }

    // Find the specific workout selected by the user
    @Override
    public void onWorkoutSelected(Workout workout) {
        Intent intent;

        // Starts the workout
        if(startWorkout == 1){
            // If there are no exercises in the workout, user can not start a workout
            if(workout.getCustomExercises().size() == 0){
                Toast.makeText(this, workout.getWorkoutName() + " contains no exercises!", Toast.LENGTH_SHORT).show();
            }
            else{
                intent = new Intent(this, StartWorkoutActivity.class);
                intent.putExtra("workout", workout);
                startActivity(intent);
            }
        }
        // Shows the info of the workout
        else{
            intent = new Intent(this, MyWorkoutInfoActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("workout", workout);
            startActivity(intent);
        }


    }
}

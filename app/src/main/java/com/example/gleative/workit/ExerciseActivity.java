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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gleative.workit.adapter.ExercisesRecyclerAdapter;
import com.example.gleative.workit.fragments.ExerciseInfoFragment;
import com.example.gleative.workit.fragments.ExerciseListFragment;
import com.example.gleative.workit.fragments.NavigationDrawerFragment;
import com.example.gleative.workit.model.Exercise;
import com.example.gleative.workit.model.Workout;

import java.util.ArrayList;
import java.util.List;

public class ExerciseActivity extends AppCompatActivity implements ExerciseListFragment.OnExerciseFragmentInteractionListener, Spinner.OnItemSelectedListener{

    // If 1, the user is going to create a custom exercise
    private int createCustomExercise = 0;
    Workout workout;

    Toolbar toolbar;
    NavigationDrawerFragment navigationDrawerFragment;
    ExerciseInfoFragment exerciseInfoFragment;
    ExerciseListFragment exerciseListFragment;
    RecyclerView recyclerView;
    ExercisesRecyclerAdapter exercisesRecyclerAdapter;

    private List<Exercise> exercisesList;

    private String[] categories = {"All", "Arms", "Back", "Chest", "Legs", "Shoulders"};
    ArrayAdapter<String> categoryAdapter;
    Spinner categorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // Disables title bar text

        exercisesList = Exercise.getData();

        Bundle extras = getIntent().getExtras();
        // If user is going to create a custom exercise
        if(extras != null){
            // Knows the user is adding exercise because bundle has workout object
            createCustomExercise = 1;

            // Gets the workout object
            workout = extras.getParcelable("workout");
        }
        else{
            // Knows the user is browsing exercises
            createCustomExercise = 0;
            setUpDrawer();
            navigationDrawerFragment.updateCheckedItem(R.id.nav_exercises);
        }

        setUpSpinner();
    }

    private void setUpDrawer() {
        navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationDrawerFragment.setUpDrawer(drawerLayout, toolbar, R.id.nav_exercises);
    }

    // Sets up a spinner, had to do it here, and not in the fragment. Because here vi access the activity_exercise, which we need!
    private void setUpSpinner(){
        // Spinner element
        categorySpinner = (Spinner) findViewById(R.id.category_spinner);

        // Adds on click listener on the spinner
        categorySpinner.setOnItemSelectedListener(this);

        categoryAdapter = new ArrayAdapter<>(this, R.layout.spinner_category_item, categories); // The layout is for how a element in spinner should look like
        categorySpinner.setAdapter(categoryAdapter);

    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    public void onExerciseSelected(Exercise selectedExercise) {
        // If user is going to create a custom exercise
        if(createCustomExercise == 1){
            Intent intent = new Intent(this, CreateCustomExerciseActivity.class);
            intent.putExtra("workout", workout);
            intent.putExtra("exercise", selectedExercise);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); // Added because if you press fast two times, it goes into the activity twice
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, ExerciseInfoActivity.class);
            intent.putExtra("exercise", selectedExercise);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); // Added because if you press fast two times, it goes into the activity twice
            startActivity(intent);
        }


    }

    // When user selects one of the given categories in the spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getItemAtPosition(position).toString();

        // So we can access its methods
        exerciseListFragment = (ExerciseListFragment) getSupportFragmentManager().findFragmentById(R.id.exercise_list_fragment);

        // Checks which item was selected
        switch (selectedItem){
            case "All":
                // Resets the list so all exercises shows
                exerciseListFragment.filterExercises("");
                break;
            case "Arms":
                exerciseListFragment.spinnerFilterExercises(selectedItem);
                break;
            case "Back":
                exerciseListFragment.spinnerFilterExercises(selectedItem);
                break;
            case "Chest":
                exerciseListFragment.spinnerFilterExercises(selectedItem);
                break;
            case "Legs":
                exerciseListFragment.spinnerFilterExercises(selectedItem);
                break;
            case "Shoulders":
                exerciseListFragment.spinnerFilterExercises(selectedItem);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    public void resetSpinnerPosition(){
        categorySpinner.setSelection(0);
    }
}

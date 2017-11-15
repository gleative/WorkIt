package com.example.gleative.workit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.example.gleative.workit.fragments.ExerciseListFragment;
import com.example.gleative.workit.model.Exercise;
import com.example.gleative.workit.model.Workout;

public class AddExerciseToWorkoutActivity extends AppCompatActivity implements  ExerciseListFragment.OnExerciseFragmentInteractionListener, Spinner.OnItemSelectedListener{

    ExerciseListFragment exerciseListFragment;

    Workout workout;
    Toolbar toolbar;

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


        // Tells host activity that this fragment has menu options it wants to add, or else search bar wont show up

        // Gets the workout object
        workout = getIntent().getParcelableExtra("workout");

        setUpSpinner();
    }

    // Sets up a spinner
    private void setUpSpinner(){
        // Spinner element
        categorySpinner = (Spinner) findViewById(R.id.category_spinner);

        // Adds on click listener on the spinner
        categorySpinner.setOnItemSelectedListener(this);

        categoryAdapter = new ArrayAdapter<>(this, R.layout.spinner_category_item, categories); // The layout is for how a element in spinner should look like
        categorySpinner.setAdapter(categoryAdapter);

    }

    @Override
    public void onExerciseSelected(Exercise exercise) {
        Intent intent = new Intent(this, CreateCustomExerciseActivity.class);
        intent.putExtra("workout", workout);
        intent.putExtra("exercise", exercise);
        startActivity(intent);
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
                Toast.makeText(this, selectedItem + " Selected", Toast.LENGTH_SHORT).show();
                break;
            case "Back":
                exerciseListFragment.spinnerFilterExercises(selectedItem);
                Toast.makeText(this, selectedItem + " Selected", Toast.LENGTH_SHORT).show();
                break;
            case "Chest":
                exerciseListFragment.spinnerFilterExercises(selectedItem);
                Toast.makeText(this, selectedItem + " Selected", Toast.LENGTH_SHORT).show();
                break;
            case "Legs":
                exerciseListFragment.spinnerFilterExercises(selectedItem);
                Toast.makeText(this, selectedItem + " Selected", Toast.LENGTH_SHORT).show();
                break;
            case "Shoulders":
                exerciseListFragment.spinnerFilterExercises(selectedItem);
                Toast.makeText(this, selectedItem + " Selected", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    public void resetSpinnerPosition(){
        categorySpinner.setSelection(0);
    }
}

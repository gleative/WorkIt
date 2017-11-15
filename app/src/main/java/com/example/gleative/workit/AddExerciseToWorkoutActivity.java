package com.example.gleative.workit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.gleative.workit.fragments.ExerciseListFragment;
import com.example.gleative.workit.model.Exercise;
import com.example.gleative.workit.model.Workout;

public class AddExerciseToWorkoutActivity extends AppCompatActivity implements  ExerciseListFragment.OnExerciseFragmentInteractionListener {

    Workout workout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        toolbar = (Toolbar) findViewById(R.id.toolbar); // Finner toolbaren i Activity
//        SJEKK MANIFEST OG PÅ EXERCISES, VI KAN DEFINERE NAVN PÅ ACTIONBAR DER!
//        toolbar.setTitle("Exercises"); // Setter title på actionbar
        setSupportActionBar(toolbar);

        // Tells host activity that this fragment has menu options it wants to add, or else search bar wont show up

        // Gets the workout object
        workout = getIntent().getParcelableExtra("workout");

//        Toast.makeText(this, "Add exercises to: " + workout.getWorkoutName() + workout.getWorkoutID(), Toast.LENGTH_SHORT).show();

    }

    // Creates the search bar
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
//        inflater.inflate(R.menu.menu_search, menu); // The Layout file
//        MenuItem item = menu.findItem(R.id.menu_search_button);
//        SearchView searchView = (SearchView) item.getActionView();
//        searchView.setQueryHint("Exercise name, body part..."); // Adds a hint for what the user can search for
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // Resets the position for the spinner, so searching and choosing between categories will be a better experience for the user
////                ((ExerciseActivity)getActivity()).resetSpinnerPosition();
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
////                filterExercises(newText);
//
//                // Resets the position for the spinner, so searching and choosing between categories will be a better experience for the user
////                ((ExerciseActivity)getActivity()).resetSpinnerPosition();
//
//                return true;
//            }
//        });
//
//    }

    @Override
    public void onExerciseSelected(Exercise exercise) {
        Intent intent = new Intent(this, CreateCustomExerciseActivity.class);
        intent.putExtra("workout", workout);
        intent.putExtra("exercise", exercise);
        startActivity(intent);
    }
}

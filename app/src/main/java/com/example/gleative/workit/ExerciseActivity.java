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

//        // Goes through all items in recycler view, and adds them to exercisesList
//        for(int i = 0; i <= exercisesRecyclerAdapter.getItemCount(); i++){
//            exercisesList.add(exercisesRecyclerAdapter.getItem(i));
//        }

        exercisesList = Exercise.getData();

        setUpDrawer();
    }

    // Adds the search button/bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu); // The layout file
        MenuItem item = menu.findItem(R.id.menu_search_button);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            // Invoked when user presses enter
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // Invoked when user types on the search bar
            @Override
            public boolean onQueryTextChange(String newText) {


                newText = newText.toLowerCase();
                List<Exercise> newList = new ArrayList<>();


                for(Exercise exercise: exercisesList){
                    String exerciseName = exercise.getExerciseName().toLowerCase();
                        // If the name is in the query from the user, add it to a new list, that will be displayed for the user
                        if(exerciseName.contains(newText)){
                            newList.add(exercise);
                        }
                }

                exercisesRecyclerAdapter.setFilter(newList);

                return true;
            }
        });

        return true;
    }

    private void setUpDrawer() {
//        navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
//        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        navigationDrawerFragment.setUpDrawer(drawerLayout, toolbar, R.id.nav_exercises);
    }

    @Override
    protected void onStart(){
//        navigationDrawerFragment.updateCheckedItem(R.id.nav_exercises);

        super.onStart();
    }

    @Override
    public void onExerciseSelected(Exercise selectedExercise) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        exerciseInfoFragment = (ExerciseInfoFragment) fragmentManager.findFragmentById(R.id.exercise_info_fragment); // Fragment from "activity_exercise_info"
//
//        exerciseInfoFragment.setDisplayedDetail(selectedExercise.getExerciseID());

        Intent intent = new Intent(this, ExerciseInfoActivity.class);
        intent.putExtra("exercise_id", selectedExercise.getExerciseID());
        startActivity(intent);


    }
}

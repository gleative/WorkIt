package com.example.gleative.workit.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gleative.workit.ExerciseActivity;
import com.example.gleative.workit.R;
import com.example.gleative.workit.adapter.AddExerciseAdapter;
import com.example.gleative.workit.adapter.ExercisesRecyclerAdapter;
import com.example.gleative.workit.adapter.RecycleAdapterListener;
import com.example.gleative.workit.model.Exercise;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by glenn on 03.11.2017.
 */

public class AddExerciseFragment extends Fragment implements RecycleAdapterListener {

    private RecyclerView recyclerView;
    private ExerciseListFragment.OnExerciseFragmentInteractionListener listener;
    AddExerciseAdapter adapter;
    private List<Exercise> exercisesList;

    // Empty constructor required
    public AddExerciseFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_list, container, false);

        // Tells host activity that this fragment has menu options it wants to add, or else search bar wont show up
        setHasOptionsMenu(true);
        setUpRecyclerView(view);

        return view;
    }

    // Creates the search bar
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_search, menu); // The Layout file
        MenuItem item = menu.findItem(R.id.menu_search_button);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Exercise name, body part..."); // Adds a hint for what the user can search for
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                // Resets the position for the spinner, so searching and choosing between categories will be a better experience for the user
                ((ExerciseActivity)getActivity()).resetSpinnerPosition();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterExercises(newText);

                // Resets the position for the spinner, so searching and choosing between categories will be a better experience for the user
                ((ExerciseActivity)getActivity()).resetSpinnerPosition();

                return true;
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            listener = (ExerciseListFragment.OnExerciseFragmentInteractionListener) getActivity();
        } catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString() + "must implement OnFragmentInteractionListener");
        }
    }

    // Sets up the recycler view with the type of linear layout
    private void setUpRecyclerView(View view){
        recyclerView = view.findViewById(R.id.exercise_recycler_view); // Henter listen fra layout fil "fragment_exercise_list"
        adapter = new AddExerciseAdapter(getContext(), Exercise.getData(), this); // Må ha constructor på adapteren ellers du får error! this, Exercise.getData()
        recyclerView.setAdapter(adapter);

        // Sets up the list in a new layout
        LinearLayoutManager linearLayoutManagerVertical = new LinearLayoutManager(getContext());
        linearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManagerVertical);

    }

    public void spinnerFilterExercises(String selectedCategory){
        exercisesList = Exercise.getData();
        List<Exercise> newList = new ArrayList<>();

        for(Exercise exercise: exercisesList){
            String exerciseBodyPart = exercise.getBodyPart();
            if(exerciseBodyPart.contains(selectedCategory)){
                newList.add(exercise);
            }
        }

        adapter.setFilter(newList);
    }

    // Filters the list so it only contains what the user is searching for
    public void filterExercises(String newText){
        exercisesList = Exercise.getData();
        newText = newText.toLowerCase();
        List<Exercise> newList = new ArrayList<>();


        for(Exercise exercise: exercisesList){
            String exerciseName = exercise.getExerciseName().toLowerCase();
            String exerciseBodyPart = exercise.getBodyPart().toLowerCase();
            // If the exercise name or body part is in the query from the user, add it to a new list, that will be displayed for the user
            if(exerciseName.contains(newText) || exerciseBodyPart.contains(newText)){
                newList.add(exercise);
            }
        }

        // Send it to the adapter, so it can notify the recycler view that it has changed, and will update
        adapter.setFilter(newList);
    }



    @Override
    public void exerciseSelected(Exercise exercise) {
        Toast.makeText(getContext(), exercise.getExerciseName() + " Selected", Toast.LENGTH_SHORT).show();

        listener.onExerciseSelected(exercise);
    }

    public interface OnExerciseFragmentInteractionListener{
        void onExerciseSelected(Exercise exercise);
    }
}
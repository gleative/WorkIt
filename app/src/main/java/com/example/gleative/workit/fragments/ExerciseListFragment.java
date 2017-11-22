package com.example.gleative.workit.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gleative.workit.ExerciseActivity;
import com.example.gleative.workit.R;
import com.example.gleative.workit.adapter.AddExerciseAdapter;
import com.example.gleative.workit.adapter.ExercisesRecyclerAdapter;
import com.example.gleative.workit.adapter.OnExerciseSelectedListener;
import com.example.gleative.workit.adapter.RecycleAdapterListener;
import com.example.gleative.workit.model.CustomExercise;
import com.example.gleative.workit.model.Exercise;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gleative on 12.10.2017.
 *
 * Gets the exercises from the database, and sets it out in a cardview
 */

public class ExerciseListFragment extends Fragment implements RecycleAdapterListener{

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dbReference;

    private RecyclerView recyclerView;
    private OnExerciseFragmentInteractionListener listener;
    ExercisesRecyclerAdapter adapter;
    AddExerciseAdapter addExerciseAdapter;
    RecycleAdapterListener recycleAdapterListener;
    private List<Exercise> exercisesList;
    private List<Exercise> eList;

    private ProgressBar loadingSpinner;

    private int layout;

    // Empty constructor required
    public ExerciseListFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_list, container, false);

        loadingSpinner = view.findViewById(R.id.progress_bar_exercises);

        eList = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();

        dbReference = firebaseDatabase.getReference().child("exercises");

        // Tells host activity that this fragment has menu options it wants to add, or else search bar wont show up
        setHasOptionsMenu(true);

        setUpRecyclerView(view);
        getData();

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
            listener = (OnExerciseFragmentInteractionListener) getActivity();
        } catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString() + "must implement OnFragmentInteractionListener");
        }
    }

    // Retrieves the exercises data from the database and adds the data to the recycler view
    private void getData(){
        loadingSpinner.setVisibility(View.VISIBLE);

        try{
            dbReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    eList.clear(); // Clears the list before filling the list with data again, or else it will have duplicates
                    for(DataSnapshot exerciseSnapshot : dataSnapshot.getChildren()){
                        Exercise exercise = new Exercise();
                        exercise.setExerciseID(Integer.parseInt(exerciseSnapshot.getKey())); // Gets the Key Value, which is the exerciseID in this case
                        exercise.setExerciseName(exerciseSnapshot.child("exerciseName").getValue().toString());
                        exercise.setExerciseDescription(exerciseSnapshot.child("exerciseDescription").getValue().toString());
                        exercise.setBodyPart(exerciseSnapshot.child("bodyPart").getValue().toString());
                        exercise.setImageThumb1(exerciseSnapshot.child("imageThumb1").getValue().toString());
                        exercise.setImageThumb2(exerciseSnapshot.child("imageThumb2").getValue().toString());
                        exercise.setGifImage(exerciseSnapshot.child("gifImage").getValue().toString());
                        eList.add(exercise);

                    }
                    adapter.updateAdapter(eList); // Tells the adapter to update so it has the newest data
                    loadingSpinner.setVisibility(View.GONE); // Hides the loading spinner because the data is loaded
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(), "Failed to load exercise data.", Toast.LENGTH_SHORT).show();
            loadingSpinner.setVisibility(View.GONE); // Hides the loading spinner because it failed loading the data

        }



    }

    // Finds the recycler view and sets it up with the type of linear layout
    private void setUpRecyclerView(View view){
        recyclerView = view.findViewById(R.id.exercise_recycler_view);
        adapter = new ExercisesRecyclerAdapter(getContext(), eList, this);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManagerVertical = new LinearLayoutManager(getContext());
        linearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManagerVertical);

    }

    // Filters the exercises depentend on what catergory on the spinner is selected
    public void spinnerFilterExercises(String selectedCategory){
        // Need to have this if statement, or the program crashes, because the listener will listen before the data is added
        if(adapter != null){
            exercisesList = eList;
            List<Exercise> newList = new ArrayList<>();

            for(Exercise exercise: exercisesList){
                String exerciseBodyPart = exercise.getBodyPart();
                if(exerciseBodyPart.contains(selectedCategory)){
                    newList.add(exercise);
                }
            }
            adapter.setFilter(newList);
        }

    }

    // Filters the list so it only contains what the user is searching for
    public void filterExercises(String newText){
        // Need to have this if statement, or the program crashes, because the listener will listen before the data is added
        if(adapter != null){
            exercisesList = eList; // The list that holds the exercise data from the database
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

    }

    @Override
    public void exerciseSelected(Exercise exercise) {
        listener.onExerciseSelected(exercise);
    }

    @Override
    public void customExerciseSelected(CustomExercise selectedCustomExercise) {}

    public interface OnExerciseFragmentInteractionListener{
        void onExerciseSelected(Exercise exercise);
    }
}
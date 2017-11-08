package com.example.gleative.workit.fragments;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

    DatabaseReference dbReference;

    private RecyclerView recyclerView;
    private OnExerciseFragmentInteractionListener listener;
    ExercisesRecyclerAdapter adapter;
    AddExerciseAdapter addExerciseAdapter;
    RecycleAdapterListener recycleAdapterListener;
    private List<Exercise> exercisesList;
    private List<Exercise> eList;

    private int layout;

    // Empty constructor required
    public ExerciseListFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_list, container, false);

        // Tells host activity that this fragment has menu options it wants to add, or else search bar wont show up
        setHasOptionsMenu(true);
        setUpRecyclerView(view);
        getData(0);

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

//    // Gets the exercises data from the database
//    private void getData(){
//        eList = new ArrayList<>();
//
//        // Gets a reference to the child "exercises" in the database
//        dbReference = FirebaseDatabase.getInstance().getReference().child("exercises");
//
//        dbReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot exerciseSnapshot : dataSnapshot.getChildren()){
//                    Exercise exercise = new Exercise();
//                    System.out.println(exerciseSnapshot.getKey());
//                    exercise.setExerciseID(Integer.parseInt(exerciseSnapshot.getKey())); // Gets the Key Value, which is the exerciseID in this case
//                    exercise.setExerciseName(exerciseSnapshot.child("exerciseName").getValue().toString());
//                    exercise.setExerciseDescription(exerciseSnapshot.child("exerciseDescription").getValue().toString());
//                    exercise.setBodyPart(exerciseSnapshot.child("bodyPart").getValue().toString());
//                    eList.add(exercise);
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//    }

    // Retrieves the exercises data from the database and adds the data to the recycler view
    private void getData(int selectedLayout){
//        recyclerView = view.findViewById(R.id.exercise_recycler_view); // Henter listen fra layout fil "fragment_exercise_list"
//        adapter = new ExercisesRecyclerAdapter(getContext(), Exercise.getData(), this); // Må ha constructor på adapteren ellers du får error! this, Exercise.getData()
//        recyclerView.setAdapter(adapter);
//
//        // Sets up the list in a new layout
//        LinearLayoutManager linearLayoutManagerVertical = new LinearLayoutManager(getContext());
//        linearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(linearLayoutManagerVertical);
        layout = selectedLayout;

        eList = new ArrayList<>();

        // Gets a reference to the child "exercises" in the database
        dbReference = FirebaseDatabase.getInstance().getReference().child("exercises");

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot exerciseSnapshot : dataSnapshot.getChildren()){
                    Exercise exercise = new Exercise();
                    exercise.setExerciseID(Integer.parseInt(exerciseSnapshot.getKey())); // Gets the Key Value, which is the exerciseID in this case
                    exercise.setExerciseName(exerciseSnapshot.child("exerciseName").getValue().toString());
                    exercise.setExerciseDescription(exerciseSnapshot.child("exerciseDescription").getValue().toString());
                    exercise.setBodyPart(exerciseSnapshot.child("bodyPart").getValue().toString());
                    eList.add(exercise);
                }

                // Adds the exercises to the recycler view
                createAdapter(eList, layout);
//                adapter = new ExercisesRecyclerAdapter(getContext(), eList, recycleAdapterListener);
//                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        recyclerView = view.findViewById(R.id.exercise_recycler_view); // Henter listen fra layout fil "fragment_exercise_list"
//        adapter = new ExercisesRecyclerAdapter(getContext(), eList, this); // Må ha constructor på adapteren ellers du får error! this, Exercise.getData()
//        recyclerView.setAdapter(adapter);

        // Sets up the list in a new layout
//        LinearLayoutManager linearLayoutManagerVertical = new LinearLayoutManager(getContext());
//        linearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(linearLayoutManagerVertical);

    }

    // Finds the recycler view and sets it up with the type of linear layout
    private void setUpRecyclerView(View view){
        // Finds the recycler_view from the layout file "fragment_exercise_list"
        recyclerView = view.findViewById(R.id.exercise_recycler_view);
//        adapter = new ExercisesRecyclerAdapter(getContext(), exerciseData, this); // Må ha constructor på adapteren ellers du får error! this, Exercise.getData()

        // Sets up the list in a new layout
        LinearLayoutManager linearLayoutManagerVertical = new LinearLayoutManager(getContext());
        linearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManagerVertical);
    }

    // Initiates a new ExerciseRecyclerAdapter, and adds the exercise data to the recycler view
    private void createAdapter(List<Exercise> exerciseData, int layout){
        if(layout == 1){
            recyclerView.setAdapter(null);
            addExerciseAdapter = new AddExerciseAdapter(getContext(), exerciseData, this);
            recyclerView.setAdapter(addExerciseAdapter);
        }
        else{
            adapter = new ExercisesRecyclerAdapter(getContext(), exerciseData, this);
            recyclerView.setAdapter(adapter);
        }


    }

    public void useAddExerciseLayout(int selectedLayout){
        getData(selectedLayout);
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
        Toast.makeText(getContext(), exercise.getExerciseName() + " Selected", Toast.LENGTH_SHORT).show();

        listener.onExerciseSelected(exercise);
    }

    @Override
    public void customExerciseSelected(CustomExercise selectedCustomExercise) {

    }

    public interface OnExerciseFragmentInteractionListener{
        void onExerciseSelected(Exercise exercise);
    }

}
package com.example.gleative.workit.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gleative.workit.R;
import com.example.gleative.workit.adapter.WorkoutRecycleAdapterListener;
import com.example.gleative.workit.adapter.WorkoutsRecyclerAdapter;
import com.example.gleative.workit.model.CustomExercise;
import com.example.gleative.workit.model.Workout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by glenn on 17.10.2017.
 */

public class WorkoutListFragment extends Fragment implements WorkoutRecycleAdapterListener{

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dbReferenceWorkouts;
    private DatabaseReference dbReferenceCustomExercises;
    private DatabaseReference dbReference3;

    private RecyclerView recyclerView;
    private OnWorkoutFragmentInteractionListener listener;
    private WorkoutsRecyclerAdapter adapter;
    private List<Workout> workoutsList;
    private List<CustomExercise> customExerciseList;

    private ProgressDialog loadingSpinner;


    public WorkoutListFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_list, container, false);

        workoutsList = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();
        dbReferenceWorkouts = firebaseDatabase.getReference().child("workouts");

        setUpProgressDialog();
        setUpRecyclerView(view);
        getData();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            listener = (WorkoutListFragment.OnWorkoutFragmentInteractionListener) getActivity();
        } catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString() + "must implement OnFragmentInteractionListener");
        }
    }

    // Retrieves the workouts data from the database and adds the data to the recycler view
    private void getData(){

        dbReferenceWorkouts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                workoutsList.clear(); // Clear list before getting the data, if data already exist it will be duplicates
                for(DataSnapshot workoutSnapshot : dataSnapshot.getChildren()){
                    Workout workout = new Workout();
                    workout.setWorkoutID(workoutSnapshot.getKey().toString());
                    workout.setWorkoutName(workoutSnapshot.child("workoutName").getValue().toString());
                    workout.setWorkoutDescription(workoutSnapshot.child("workoutDescription").getValue().toString());
                    workoutsList.add(workout);

                    getCustomExercises(workoutsList.size()-1); // The current workout is the one that is last on the list.
                }

                // Tells the adapter to update so it has the newest data
                adapter.updateAdapter(workoutsList);
                loadingSpinner.hide(); // Hides the loading spinner because the data is loaded
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    // Gets the custom exercises that is in the workout
    private void getCustomExercises(final int workoutListID){

        customExerciseList = new ArrayList<>();

        dbReferenceCustomExercises = FirebaseDatabase.getInstance().getReference().child("customExercises");

        dbReferenceCustomExercises.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot customExerciseSnapshot : dataSnapshot.getChildren()){

                    Workout workout = workoutsList.get(workoutListID); // Gets reference to the workout object
                    CustomExercise customExercise = customExerciseSnapshot.getValue(CustomExercise.class);

                    if(customExercise.getWorkoutID().equals(workout.getWorkoutID())){
                        workout.getCustomExercises().add(customExercise);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }

    // Displays a progress dialog with style spinner
    private void setUpProgressDialog(){
        loadingSpinner = new ProgressDialog(getActivity());
        loadingSpinner.setMessage("Loading...");
        loadingSpinner.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingSpinner.show();
    }


    // Finds the recycler view and sets it up with the type of linear layout
    private void setUpRecyclerView(View view){

        // Finds the recycler_view from the layout file "fragment_exercise_list"
        recyclerView = view.findViewById(R.id.workout_recycler_view);
        adapter = new WorkoutsRecyclerAdapter(getContext(), workoutsList, this);
        recyclerView.setAdapter(adapter);

        // Sets up the list in a new layout
        LinearLayoutManager linearLayoutManagerVertical = new LinearLayoutManager(getContext());
        linearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManagerVertical);


    }

    @Override
    public void workoutSelected(Workout workout) {
        listener.onWorkoutSelected(workout);
    }

    public interface OnWorkoutFragmentInteractionListener{
        void onWorkoutSelected(Workout workout);
    }

}

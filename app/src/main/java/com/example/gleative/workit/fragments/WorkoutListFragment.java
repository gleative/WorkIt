package com.example.gleative.workit.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gleative.workit.R;
import com.example.gleative.workit.adapter.WorkoutRecycleAdapterListener;
import com.example.gleative.workit.adapter.WorkoutsRecyclerAdapter;
import com.example.gleative.workit.model.Workout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by glenn on 17.10.2017.
 */

public class WorkoutListFragment extends Fragment implements WorkoutRecycleAdapterListener{

    DatabaseReference dbReference;

    private RecyclerView recyclerView;
    private OnWorkoutFragmentInteractionListener listener;
    private WorkoutsRecyclerAdapter adapter;
    private List<Workout> workoutsList;

    public WorkoutListFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_list, container, false);

        setUpRecyclerView(view);
        getData();

        return view;
    }

    // Retrieves the workouts data from the database and adds the data to the recycler view
    private void getData(){
        workoutsList = new ArrayList<>();

        dbReference = FirebaseDatabase.getInstance().getReference().child("workouts");

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot workoutSnapshot : dataSnapshot.getChildren()){
                    Workout workout = new Workout();
                    workout.setWorkoutID(workoutSnapshot.getKey().toString());
                    workout.setWorkoutName(workoutSnapshot.child("workoutName").getValue().toString());
                    workout.setWorkoutDescription(workoutSnapshot.child("workoutDescription").getValue().toString());
                    workoutsList.add(workout);
                }

                // Adds the exercises to the recycler view
                createAdapter(workoutsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // Finds the recycler view and sets it up with the type of linear layout
    private void setUpRecyclerView(View view){
        // Finds the recycler_view from the layout file "fragment_exercise_list"
        recyclerView = view.findViewById(R.id.exercise_recycler_view);

        // Sets up the list in a new layout
        LinearLayoutManager linearLayoutManagerVertical = new LinearLayoutManager(getContext());
        linearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManagerVertical);

    }

    private void createAdapter(List<Workout> workoutData){
        adapter = new WorkoutsRecyclerAdapter(getContext(), workoutData, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void workoutSelected(Workout workout) {
        Toast.makeText(getContext(), workout.getWorkoutName() + " Selected", Toast.LENGTH_SHORT).show();

        listener.onWorkoutSelected(workout);
    }

    public interface OnWorkoutFragmentInteractionListener{
        void onWorkoutSelected(Workout workout);
    }

}

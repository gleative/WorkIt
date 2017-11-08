package com.example.gleative.workit.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gleative.workit.R;
import com.example.gleative.workit.adapter.CustomExerciseRecyclerAdapter;
import com.example.gleative.workit.adapter.ExercisesRecyclerAdapter;
import com.example.gleative.workit.adapter.RecycleAdapterListener;
import com.example.gleative.workit.model.CustomExercise;
import com.example.gleative.workit.model.Exercise;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gleative on 08.11.2017.
 */

public class WorkoutCustomExercisesListFragment extends Fragment implements RecycleAdapterListener{

    DatabaseReference dbReference;

    private RecyclerView recyclerView;
    private WorkoutCustomExercisesListFragment.OnCustomExerciseFragmentInteractionListener listener;
    private CustomExerciseRecyclerAdapter adapter;
    private List<CustomExercise> customExerciseList;

    public WorkoutCustomExercisesListFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_selected_list, container, false);

        setUpRecyclerView(view);
        getData();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            listener = (WorkoutCustomExercisesListFragment.OnCustomExerciseFragmentInteractionListener) getActivity();
        } catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString() + "must implement OnFragmentInteractionListener");
        }
    }

    private void getData(){
        customExerciseList = new ArrayList<>();

        dbReference = FirebaseDatabase.getInstance().getReference().child("customExercises");

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot customExerciseSnapshot : dataSnapshot.getChildren()){
                    CustomExercise customExercise = new CustomExercise();
                    customExercise.setWorkoutID(customExerciseSnapshot.child("workoutID").getValue().toString());
                    customExercise.setExerciseID(Integer.parseInt(customExerciseSnapshot.child("exerciseID").getValue().toString()));
                    customExercise.setSets(Integer.parseInt(customExerciseSnapshot.child("sets").getValue().toString()));
                    customExercise.setReps(Integer.parseInt(customExerciseSnapshot.child("reps").getValue().toString()));
                    customExerciseList.add(customExercise);
                }

                createAdapter(customExerciseList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // Finds the recycler view and sets it up with the type of linear layout
    private void setUpRecyclerView(View view){
        // Finds the recycler_view from the layout file "fragment_workout_selected_list"
        recyclerView = view.findViewById(R.id.workout_selected_list_recycler_list);

        // Sets up the list in a new layout
        LinearLayoutManager linearLayoutManagerVertical = new LinearLayoutManager(getContext());
        linearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManagerVertical);

    }

    private void createAdapter(List<CustomExercise> customExerciseData){
        adapter = new CustomExerciseRecyclerAdapter(getContext(), customExerciseData, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void customExerciseSelected(CustomExercise selectedCustomExercise) {
        listener.onCustomExerciseSelected(selectedCustomExercise);
    }

    public interface OnCustomExerciseFragmentInteractionListener{
        void onCustomExerciseSelected(CustomExercise customExercise);
    }

    @Override
    public void exerciseSelected(Exercise selectedExercise) {

    }
}

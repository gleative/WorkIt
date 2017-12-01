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
import com.example.gleative.workit.model.Workout;
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

    private RecyclerView recyclerView;
    private WorkoutCustomExercisesListFragment.OnCustomExerciseFragmentInteractionListener listener;
    private CustomExerciseRecyclerAdapter adapter;
    private List<CustomExercise> customExerciseList;

    public WorkoutCustomExercisesListFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_selected_list, container, false);

        customExerciseList = new ArrayList<>();

        setUpRecyclerView(view);

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

    // Gets the custom exercises the workout has, and updates the adapter that is displayed in MyWorkoutInfo
    public void getCustomExercisesFromWorkout(Workout workout){
        customExerciseList = workout.getCustomExercises();
        adapter.updateAdapter(customExerciseList);
    }

    // Finds the recycler view and sets it up with the type of linear layout
    private void setUpRecyclerView(View view){
        // Finds the recycler_view from the layout file "fragment_workout_selected_list"
        recyclerView = view.findViewById(R.id.workout_selected_list_recycler_list);
        adapter = new CustomExerciseRecyclerAdapter(getContext(), customExerciseList, this);
        recyclerView.setAdapter(adapter);

        // Sets up the list in a new layout
        LinearLayoutManager linearLayoutManagerVertical = new LinearLayoutManager(getContext());
        linearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManagerVertical);

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

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

/**
 * Created by glenn on 17.10.2017.
 */

public class WorkoutListFragment extends Fragment implements WorkoutRecycleAdapterListener{

    private RecyclerView recyclerView;
    private OnWorkoutFragmentInteractionListener listener;
    private WorkoutsRecyclerAdapter adapter;

    public WorkoutListFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_list, container, false);

        setUpRecyclerView(view);

        return view;
    }

    // Sets up the recycler view with the type of linear layout
    private void setUpRecyclerView(View view){
        recyclerView = view.findViewById(R.id.exercise_recycler_view); // Henter listen fra layout fil "fragment_exercise_list"
        adapter = new WorkoutsRecyclerAdapter(getContext(), Workout.getData(), this); // Må ha constructor på adapteren ellers du får error! this, Exercise.getData()
        recyclerView.setAdapter(adapter);

        // Sets up the list in a new layout
        LinearLayoutManager linearLayoutManagerVertical = new LinearLayoutManager(getContext());
        linearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManagerVertical);

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

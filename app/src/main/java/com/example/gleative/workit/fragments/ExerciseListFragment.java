package com.example.gleative.workit.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gleative.workit.R;
import com.example.gleative.workit.adapter.ExercisesRecyclerAdapter;
import com.example.gleative.workit.adapter.OnExerciseSelectedListener;
import com.example.gleative.workit.adapter.RecycleAdapterListener;
import com.example.gleative.workit.model.Exercise;

/**
 * Created by gleative on 12.10.2017.
 *
 * Gets the exercises from the database, and sets it out in a cardview
 */

public class ExerciseListFragment extends Fragment implements RecycleAdapterListener{

    private RecyclerView recyclerView;
    private OnExerciseFragmentSelectedListener listener;

    // Empty constructor required
    public ExerciseListFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_list, container, false); // Finds the given layout file that holds a list of exercises,

        setUpRecyclerView(view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        try{
            listener = (OnExerciseFragmentSelectedListener) getActivity();
        } catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString() + " must implement OnFragmentInteractionListener");
        }
    }

    // Sets up the recyclerView with the type of linear layout
    private void setUpRecyclerView(View view){
        recyclerView = view.findViewById(R.id.exercise_recycler_view); // Henter listen fra layout fil "fragment_exercise_list"
        ExercisesRecyclerAdapter adapter = new ExercisesRecyclerAdapter(getContext(), Exercise.getData(), this); // Må ha constructor på adapteren ellers du får error! this, Exercise.getData()
        recyclerView.setAdapter(adapter);

        // Sets up the list in a new layout
        LinearLayoutManager linearLayoutManagerVertical = new LinearLayoutManager(getContext());
        linearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManagerVertical);
    }

    @Override
    public void exerciseSelected(Exercise selectedExercise){
        Toast.makeText(getContext(), selectedExercise.getExerciseName() + " Selected", Toast.LENGTH_SHORT).show();

        listener.onExerciseSelected(selectedExercise);
    }

    public interface OnExerciseFragmentSelectedListener {
        void onExerciseSelected(Exercise selectedExercise);
    }
}
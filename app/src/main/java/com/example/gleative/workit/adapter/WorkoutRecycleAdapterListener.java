package com.example.gleative.workit.adapter;

import android.view.View;

import com.example.gleative.workit.model.Workout;

/**
 * Created by glenn on 17.10.2017.
 */

public interface WorkoutRecycleAdapterListener {
    void workoutSelected(Workout workout);
    void workoutDeleteImageSelected(View v, Workout workout);
}

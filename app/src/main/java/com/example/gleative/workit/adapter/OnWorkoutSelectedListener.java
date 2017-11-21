package com.example.gleative.workit.adapter;

import android.view.View;

/**
 * Created by glenn on 17.10.2017.
 */

public interface OnWorkoutSelectedListener {
    void workoutSelected(int position);
    void workoutDeleteImageSelected(View v, int position);
}

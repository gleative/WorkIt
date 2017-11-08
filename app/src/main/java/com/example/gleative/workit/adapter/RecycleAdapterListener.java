package com.example.gleative.workit.adapter;

import com.example.gleative.workit.model.CustomExercise;
import com.example.gleative.workit.model.Exercise;

/**
 * Created by gleative on 11.10.2017.
 */

public interface RecycleAdapterListener {
    void exerciseSelected(Exercise selectedExercise);

    void customExerciseSelected(CustomExercise selectedCustomExercise);
}

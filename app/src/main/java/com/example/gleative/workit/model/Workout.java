package com.example.gleative.workit.model;

import java.util.List;

/**
 * Created by gleative on 09.10.2017.
 */

public class Workout {

    private String workoutName;
    private String workoutDescription;
    private List<CustomExercise> customExercises;

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public String getWorkoutDescription() {
        return workoutDescription;
    }

    public void setWorkoutDescription(String workoutDescription) {
        this.workoutDescription = workoutDescription;
    }

    public List<CustomExercise> getCustomExercises() {
        return customExercises;
    }

    public void setCustomExercises(List<CustomExercise> customExercises) {
        this.customExercises = customExercises;
    }
}

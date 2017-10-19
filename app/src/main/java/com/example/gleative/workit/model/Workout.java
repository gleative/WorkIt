package com.example.gleative.workit.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gleative on 09.10.2017.
 */

public class Workout {

    private int workoutID;
    private String workoutName;
    private String workoutDescription;
    private List<CustomExercise> customExercises;

    public int getWorkoutID() {
        return workoutID;
    }

    public void setWorkoutID(int workoutID) {
        this.workoutID = workoutID;
    }

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

    public int getAmountExercises(){
        return customExercises.size();
    }

    public static List<Workout> getData(){
        List<Workout> workoutList = new ArrayList<>();

        for(int i = 0; i <= 7; i++){
            Workout workout = new Workout();
            workout.setWorkoutID(i);
            workout.setWorkoutName("Workout " + i);
            workout.setWorkoutDescription("Desc for workout" + i);
            workout.setCustomExercises(CustomExercise.getCustomExerciseData());

            workoutList.add(workout);

        }

        return workoutList;
    }
}

package com.example.gleative.workit.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Is a exercise, but has custom reps, sets or time
 *
 * Created by gleative on 09.10.2017.
 */

public class CustomExercise extends Exercise{

    private Exercise exercise;
    private int sets;
    private int reps;
    private int time;

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public static List<CustomExercise> getCustomExerciseData(){
        List<CustomExercise> customExerciseList = new ArrayList<>();

        Random randomNumber = new Random();
        int number = randomNumber.nextInt(8);

        for(int i = 0; i <= number; i++){
            CustomExercise customExercise = new CustomExercise();
            customExercise.setExercise(new Exercise());
            customExercise.setReps(12);
            customExercise.setSets(3);

            customExerciseList.add(customExercise);
        }

        return customExerciseList;
    }
}

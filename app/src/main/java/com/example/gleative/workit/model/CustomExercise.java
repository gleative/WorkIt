package com.example.gleative.workit.model;

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
}

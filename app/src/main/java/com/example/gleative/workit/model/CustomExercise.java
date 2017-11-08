package com.example.gleative.workit.model;

import com.example.gleative.workit.adapter.ExercisesRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Is a exercise, but has custom reps, sets or time
 *
 * Created by gleative on 09.10.2017.
 */

public class CustomExercise{

    private String workoutID; // Which workout the customexercise is in
    private int exerciseID; // Which exercise is customized
    private int sets;
    private int reps;
    private int time;

    public CustomExercise(){
        super();
    }

    public CustomExercise(String _workoutID, int _exerciseID, int _sets, int _reps){
        this.workoutID = _workoutID;
        this.exerciseID = _exerciseID;
        this.sets = _sets;
        this.reps = _reps;
    }

//    public Workout getWorkout(){
//        return workout;
//    }
//
//    public void setWorkout(Workout workout){
//        this.workout = workout;
//    }
//
//    public Exercise getExercise() {
//        return exercise;
//    }
//
//    public void setExercise(Exercise exercise) {
//        this.exercise = exercise;
//    }

    public String getWorkoutID(){
        return workoutID;
    }

    public void setWorkoutID(String workoutID){
        this.workoutID = workoutID;
    }

    public int getExerciseID(){
        return exerciseID;
    }

    public void setExerciseID(int exerciseID){
        this.exerciseID = exerciseID;
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

//    public static List<CustomExercise> getCustomExerciseData(){
//        List<CustomExercise> customExerciseList = new ArrayList<>();
//
//        Random randomNumber = new Random();
//        int number = randomNumber.nextInt(8);
//
//        for(int i = 0; i <= number; i++){
//            CustomExercise customExercise = new CustomExercise();
//            customExercise.setExercise(new Exercise());
//            customExercise.setReps(12);
//            customExercise.setSets(3);
//
//            customExerciseList.add(customExercise);
//        }
//
//        return customExerciseList;
//    }
}

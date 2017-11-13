package com.example.gleative.workit.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.gleative.workit.adapter.ExercisesRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Is a exercise, but has custom reps, sets or time
 *
 * Created by gleative on 09.10.2017.
 */

public class CustomExercise implements Parcelable{

    private String workoutID; // Which workout the customexercise is in
    private int exerciseID; // Which exercise is customized
    private Exercise exercise;
    private int sets;
    private int reps;
    private int time;

    public CustomExercise(){
        super();
    }

//    public CustomExercise(String _workoutID, int _exerciseID, int _sets, int _reps){
//        this.workoutID = _workoutID;
//        this.exerciseID = _exerciseID;
//        this.sets = _sets;
//        this.reps = _reps;
//    }
//
//    public CustomExercise(Parcel parcel){
//        this.workoutID = parcel.readString();
//        this.exerciseID = parcel.readInt();
//        this.sets = parcel.readInt();
//        this.reps = parcel.readInt();
//        this.time = parcel.readInt();
//    }
//
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(this.workoutID);
//        dest.writeInt(this.exerciseID);
//        dest.writeInt(this.sets);
//        dest.writeInt(this.reps);
//        dest.writeInt(this.time);
//    }

    public CustomExercise(String _workoutID, int _exerciseID, Exercise _exercise, int _sets, int _reps){
        this.workoutID = _workoutID;
        this.exerciseID = _exerciseID;
        this.exercise = _exercise;
        this.sets = _sets;
        this.reps = _reps;
    }

    public CustomExercise(Parcel parcel){
        this.workoutID = parcel.readString();
        this.exerciseID = parcel.readInt();
        this.exercise = parcel.readParcelable(Exercise.class.getClassLoader());
        this.sets = parcel.readInt();
        this.reps = parcel.readInt();
        this.time = parcel.readInt();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.workoutID);
        dest.writeInt(this.exerciseID);
        dest.writeParcelable(this.exercise, flags);
        dest.writeInt(this.sets);
        dest.writeInt(this.reps);
        dest.writeInt(this.time);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CustomExercise> CREATOR = new Creator<CustomExercise>() {
        @Override
        public CustomExercise createFromParcel(Parcel in) {
            return new CustomExercise(in);
        }

        @Override
        public CustomExercise[] newArray(int size) {
            return new CustomExercise[size];
        }
    };

    //    public Workout getWorkout(){
//        return workout;
//    }
//
//    public void setWorkout(Workout workout){
//        this.workout = workout;
//    }
//
    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

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

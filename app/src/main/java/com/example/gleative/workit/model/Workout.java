package com.example.gleative.workit.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gleative on 09.10.2017.
 */

public class Workout implements Parcelable {

    private String workoutID;
    private String workoutName;
    private String workoutDescription;
    private List<CustomExercise> customExercises;

    public Workout(){
        super();
    }

    // Constructor needed when making a workout and sending data to database
    public Workout(String _workoutName, String _workoutDescription){
        super();
        this.workoutName = _workoutName;
        this.workoutDescription = _workoutDescription;
    }

    public Workout(Parcel parcel){
        this.workoutID = parcel.readString();
        this.workoutName = parcel.readString();
        this.workoutDescription = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.workoutID);
        dest.writeString(this.workoutName);
        dest.writeString(this.workoutDescription);
    }

    public static final Creator<Workout> CREATOR = new Creator<Workout>() {
        @Override
        public Workout createFromParcel(Parcel source) {
            return new Workout(source);
        }

        @Override
        public Workout[] newArray(int size) {
            return new Workout[0];
        }
    };

    public String getWorkoutID() {
        return workoutID;
    }

    public void setWorkoutID(String workoutID) {
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
        if(customExercises != null){
            return customExercises.size();
        }
        else{
            return 0;
        }
    }

//    public static List<Workout> getData(){
//        List<Workout> workoutList = new ArrayList<>();
//
//        for(int i = 0; i <= 7; i++){
//            Workout workout = new Workout();
//            workout.setWorkoutID(i);
//            workout.setWorkoutName("Workout " + i);
//            workout.setWorkoutDescription("Desc for workout" + i);
//            workout.setCustomExercises(CustomExercise.getCustomExerciseData());
//
//            workoutList.add(workout);
//
//        }
//
//        return workoutList;
//    }
}

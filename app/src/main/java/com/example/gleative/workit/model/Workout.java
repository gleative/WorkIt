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
    private List<CustomExercise> customExercises = new ArrayList<>();

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
        this.customExercises = parcel.readArrayList(Workout.class.getClassLoader());
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
        dest.writeList(this.customExercises);

    }

    public static final Creator<Workout> CREATOR = new Creator<Workout>() {
        @Override
        public Workout createFromParcel(Parcel source) {
            return new Workout(source);
        }

        @Override
        public Workout[] newArray(int size) {
            return new Workout[size];
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

    public void addCustomExerciseToWorkout(CustomExercise customExercise){
        customExercises.add(customExercise);
    }

    public int getAmountExercises(){
        if(customExercises != null){
            return customExercises.size();
        }
        else{
            return 0;
        }
    }
}

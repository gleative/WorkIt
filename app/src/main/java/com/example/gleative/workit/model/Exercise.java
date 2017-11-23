package com.example.gleative.workit.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.gleative.workit.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Exercise implements Parcelable because we need to send this object to the ExerciseInfoFragment
 */

public class Exercise implements Parcelable{

    private int exerciseID;
    private String exerciseName;
    private String exerciseDescription;
    private String bodyPart;
    private boolean timeBased;
    private int videoID;
    private String imageThumb1;
    private String imageThumb2;
    private String gifImage;
    private String starred; // 1 means true, 0 false

    public Exercise(){
        super();
    }

    public Exercise(Parcel parcel){
        this.exerciseID = parcel.readInt();
        this.exerciseName = parcel.readString();
        this.exerciseDescription = parcel.readString();
        this.bodyPart = parcel.readString();
        this.imageThumb1 = parcel.readString();
        this.imageThumb2 = parcel.readString();
        this.gifImage = parcel.readString();
        this.starred = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.exerciseID);
        dest.writeString(this.exerciseName);
        dest.writeString(this.exerciseDescription);
        dest.writeString(this.bodyPart);
        dest.writeString(this.imageThumb1);
        dest.writeString(this.imageThumb2);
        dest.writeString(this.gifImage);
        dest.writeString(this.starred);
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel source) {
            return new Exercise(source);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[0];
        }
    };

    public int getExerciseID(){
        return exerciseID;
    }

    public void setExerciseID(int exerciseID){
        this.exerciseID = exerciseID;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getExerciseDescription() {
        return exerciseDescription;
    }

    public void setExerciseDescription(String exerciseDescription) {
        this.exerciseDescription = exerciseDescription;
    }

    public String getBodyPart(){
        return this.bodyPart;
    }

    public void setBodyPart(String bodyPart){
        this.bodyPart = bodyPart;
    }

    public boolean getTimeBased(){
        return timeBased;
    }

    public void setTimeBased(boolean timeBased){
        this.timeBased = timeBased;
    }

    public int getVideoID() {
        return videoID;
    }

    public void setVideoID(int videoID) {
        this.videoID = videoID;
    }

    public String getImageThumb1() {
        return imageThumb1;
    }

    public void setImageThumb1(String imageThumb1) {
        this.imageThumb1 = imageThumb1;
    }

    public String getImageThumb2() {
        return imageThumb2;
    }

    public void setImageThumb2(String imageThumb2) {
        this.imageThumb2 = imageThumb2;
    }

    public String getGifImage(){
        return gifImage;
    }

    public void setGifImage(String gifImage){
        this.gifImage = gifImage;
    }

    public String getStarred(){
        return starred;
    }

    public void setStarred(String starredValue){
        this.starred = starredValue;
    }

    // Legger til data sånn at det kan bli lagt inn i recycler view
    public static List<Exercise> getData(){

        List<Exercise> exerciseList = new ArrayList<>();
        List<String> exerciseCategories = new ArrayList<>();
        exerciseCategories.add("Arms");
        exerciseCategories.add("Back");
        exerciseCategories.add("Chest");
        exerciseCategories.add("Legs");
        exerciseCategories.add("Shoulders");

        return exerciseList;
    }

}

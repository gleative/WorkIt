package com.example.gleative.workit.model;

import com.example.gleative.workit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gleative on 09.10.2017.
 */

public class Exercise {

    private String exerciseName;
    private String exerciseDescription;
    private int videoID;
    private int imageID; // Bildet som kommer til å være på høyre siden

    // Legger til data sånn at det kan bli lagt inn i recycler view
    public static ArrayList<Exercise> getData(){

        ArrayList<Exercise> exerciseList = new ArrayList<>();

        for(int i = 0; i <= 40; i++){
            Exercise exercise = new Exercise();
            exercise.setExerciseName("Exercise " + i);

            exerciseList.add(exercise);
        }

        return exerciseList;
    }

    public static int[] getImages(){

        int[] images = {
                R.drawable.e12_1, R.drawable.e12_2, R.drawable.e1621_1, R.drawable.e1621_2, R.drawable.e1_1,
                R.drawable.e1_2, R.drawable.e70_1, R.drawable.e70_2
        };

        return images;
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

    public int getVideoID() {
        return videoID;
    }

    public void setVideoID(int videoID) {
        this.videoID = videoID;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
}

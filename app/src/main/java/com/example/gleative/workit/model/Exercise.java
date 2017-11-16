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

    // Legger til data sånn at det kan bli lagt inn i recycler view
    public static List<Exercise> getData(){

        List<Exercise> exerciseList = new ArrayList<>();
        List<String> exerciseCategories = new ArrayList<>();
        exerciseCategories.add("Arms");
        exerciseCategories.add("Back");
        exerciseCategories.add("Chest");
        exerciseCategories.add("Legs");
        exerciseCategories.add("Shoulders");
//
//
//        int[] images = {
//                R.drawable.e12_1, R.drawable.e12_2, R.drawable.e1621_1, R.drawable.e1621_2, R.drawable.e1_1,
//                R.drawable.e1_2, R.drawable.e70_1, R.drawable.e70_2
//        };
//
//        String dummyDesc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas pretium maximus rhoncus. Morbi tempus lacus vitae enim dignissim vehicula. Mauris ut nibh eleifend, condimentum massa a, commodo risus. Fusce id magna faucibus, tempor ante tempor, eleifend lorem. Mauris rhoncus lacus libero, id aliquam enim faucibus id. Aliquam ac odio quis nibh ornare molestie sit amet consectetur magna. Etiam lacinia, dolor et sagittis tristique, turpis nisi bibendum augue, quis pellentesque velit nisi eget mauris. In ac bibendum odio. Sed efficitur sit amet enim eget fringilla. Mauris at elit euismod, placerat nisi a, porttitor urna.\n" +
//                "\n" +
//                "Nunc tristique, quam vel ornare scelerisque, est odio pretium purus, eu laoreet neque purus vel lacus. Integer a pellentesque dolor, luctus tincidunt quam. Integer tincidunt efficitur vestibulum. Aenean vel aliquam dolor. Phasellus eget odio massa. Vivamus sit amet nulla interdum, tincidunt ante non, pretium nibh. Nullam porttitor dolor eleifend tortor rutrum, et consectetur ligula feugiat. Proin dignissim consectetur massa, in ultrices nibh ornare et. Donec convallis eget lorem in sagittis. Aliquam sodales vulputate sem, a sagittis libero maximus non. Nam vel volutpat lacus, ac vestibulum metus. Duis varius eget quam ut varius.\n" +
//                "\n" +
//                "Vivamus aliquet magna at lacus tempus, vitae lacinia lorem placerat. Morbi massa purus, mattis at augue id, ullamcorper tristique libero. Nulla mattis lectus id maximus congue. Aenean eleifend dictum nisl vel semper. Donec sit amet vehicula enim. Mauris vitae metus leo. Nulla placerat et neque a molestie. Donec suscipit libero nunc, nec pulvinar nibh sagittis nec. Integer rutrum sem eget facilisis dapibus. Etiam feugiat dictum purus, in cursus tellus posuere molestie. Phasellus in pulvinar purus, eu mollis magna. Fusce at faucibus est, nec molestie felis. Vivamus elit tellus, dictum sit amet lorem at, venenatis iaculis nibh.\n" +
//                "\n" +
//                "Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec pretium lacus aliquet purus fermentum egestas. Aliquam nec elit metus. Etiam sagittis urna quis elit porttitor cursus. Nulla facilisi. Maecenas elementum dui nec finibus aliquam. Suspendisse lacus sapien, ornare id enim volutpat, accumsan ultrices metus. Curabitur id tempor dui. Sed tempor, nulla sit amet lobortis cursus, urna ante mollis elit, at feugiat tellus massa in enim. Nullam dignissim eros metus. Duis ullamcorper, libero non tincidunt euismod, ante neque hendrerit lectus, ut lacinia urna nulla at sem. Sed sed vulputate odio, nec posuere nunc. Quisque sed blandit diam. In massa nunc, ornare ac orci non, egestas semper sem.\n" +
//                "\n" +
//                "Curabitur tincidunt congue mi non pulvinar. Nullam rutrum ante id dignissim volutpat. Sed et augue vitae quam viverra rutrum. Donec lectus elit, rhoncus et augue ac, ornare pharetra sapien. Cras ut dolor consequat, varius leo et, pharetra ante. Nulla luctus lectus at felis feugiat convallis. Proin id ante sem. Praesent imperdiet nunc metus, a porttitor massa commodo vitae. Donec arcu nulla, porttitor at lacinia euismod, porttitor vel nunc. Praesent dapibus lectus nunc, non elementum tortor laoreet et. Fusce venenatis efficitur nunc. Mauris mattis est leo, in facilisis turpis luctus vitae.";
//
//        for(int i = 0; i <= 40; i++){
//            Exercise exercise = new Exercise();
//            exercise.setExerciseID(i);
//            exercise.setExerciseName("Exercise " + i);
//            exercise.setExerciseDescription("Description for exercise: " + i + "\n" + dummyDesc);
//
//            // Sets random bodypart to a exercise
//            exercise.setBodyPart(exerciseCategories.get(new Random().nextInt(exerciseCategories.size())));
//
//            exerciseList.add(exercise);
//        }



        return exerciseList;
    }

}

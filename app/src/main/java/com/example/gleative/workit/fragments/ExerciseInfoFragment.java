package com.example.gleative.workit.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gleative.workit.R;
import com.example.gleative.workit.adapter.ExercisePicturesAdapter;
import com.example.gleative.workit.model.Exercise;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gleative on 12.10.2017.
 *
 * Gets data from selected exercise, and sets the data into the correct views
 */

public class ExerciseInfoFragment extends Fragment{
    DatabaseReference dbReferenceExercise;
    DatabaseReference dbReferenceStarred;

    private List<Exercise> exerciseList;
    private List<Exercise> eList;

    public final static String EXERCISE_INDEX = "exerciseIndex";
    private static final int DEFAULT_EXERCISE_INDEX = 1;

    private TextView exerciseNameView, exerciseBodyPartView, exerciseDescView;
    private ImageView exercisePicture;
    private FloatingActionButton starredFab;
    private int exerciseIndex;
    private ViewPager exerciseViewPager; // Holds the pictures

    public ExerciseInfoFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        dbReferenceExercise = FirebaseDatabase.getInstance().getReference().child("exercises");
        dbReferenceStarred = FirebaseDatabase.getInstance().getReference().child("starred");

        exerciseIndex = savedInstanceState == null? DEFAULT_EXERCISE_INDEX : savedInstanceState.getInt(EXERCISE_INDEX, DEFAULT_EXERCISE_INDEX);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_exercise_info, container, false); // Layout file

        // Finds referance to the diffrent views in "fragment_exercise_info" layout
        exerciseNameView = fragmentView.findViewById(R.id.exercise_name_info);
        exerciseBodyPartView = fragmentView.findViewById(R.id.exercise_body_part_info);
        exercisePicture = fragmentView.findViewById(R.id.exercise_picture);
        exerciseDescView = fragmentView.findViewById(R.id.exercise_description);
        starredFab = fragmentView.findViewById(R.id.fab_exercise_starred);

        return fragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXERCISE_INDEX, exerciseIndex);
    }

    public void setDisplayedDetail(Exercise exercise){
        Glide.with(getActivity()).load(exercise.getImageThumb1()).into(exercisePicture);

        exerciseNameView.setText(exercise.getExerciseName());
        exerciseBodyPartView.setText("Body Part: " + exercise.getBodyPart());
        exerciseDescView.setText(exercise.getExerciseDescription());

        setImageFabButton(exercise.getStarred());
    }

    private void setImageFabButton(String starredValued){
        if(starredValued.equals("1")){
            starredFab.setImageResource(R.drawable.star);
        }
        else{
            starredFab.setImageResource(R.drawable.star_outline);
        }
    }

    public void starrExercise(String exerciseID, String starredValue){
        dbReferenceExercise.child(exerciseID).child("starred").setValue(starredValue);
        dbReferenceStarred.child(exerciseID).setValue(starredValue);
        setImageFabButton(starredValue);
    }


}

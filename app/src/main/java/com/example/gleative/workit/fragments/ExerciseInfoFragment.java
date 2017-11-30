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
import com.example.gleative.workit.ExerciseActivity;
import com.example.gleative.workit.R;
import com.example.gleative.workit.adapter.ExercisePicturesAdapter;
import com.example.gleative.workit.model.Exercise;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gleative on 12.10.2017.
 *
 * Gets data from selected exercise, and sets the data into the correct views
 */

public class ExerciseInfoFragment extends Fragment{
    DatabaseReference dbReferenceExercise;
    DatabaseReference dbReferenceStarred;

    private TextView exerciseNameView, exerciseBodyPartView, exerciseDescView;
    private FloatingActionButton starredFab;

    private ViewPager exerciseViewPager; // Holds the pictures
    ExercisePicturesAdapter exercisePicturesAdapter;
    private ArrayList<String> images;

    public ExerciseInfoFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        dbReferenceExercise = FirebaseDatabase.getInstance().getReference().child("exercises");
        dbReferenceStarred = FirebaseDatabase.getInstance().getReference().child("starred");

        images = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_exercise_info, container, false); // Layout file

        // Finds referance to the diffrent views in "fragment_exercise_info" layout
        exerciseNameView = fragmentView.findViewById(R.id.exercise_name_info);
        exerciseBodyPartView = fragmentView.findViewById(R.id.exercise_body_part_info);
        exerciseDescView = fragmentView.findViewById(R.id.exercise_description);
        starredFab = fragmentView.findViewById(R.id.fab_exercise_starred);

        exerciseViewPager = fragmentView.findViewById(R.id.view_pager);


        return fragmentView;
    }

    public void setDisplayedDetail(Exercise exercise){
        setUpViewPager(exercise);

        exerciseNameView.setText(exercise.getExerciseName());
        exerciseBodyPartView.setText("Body Part: " + exercise.getBodyPart());
        exerciseDescView.setText(exercise.getExerciseDescription());

        setImageFabButton(exercise.getStarred());
    }

    private void setUpViewPager(Exercise exercise){
        int gifPath = getResources().getIdentifier(exercise.getGifImage(), "drawable", getActivity().getPackageName());

        images.add(exercise.getImageThumb1());
        images.add(exercise.getImageThumb2());
        images.add(String.valueOf(gifPath)); // Convert to string because the list contains String values

        // Creates a new adapter, and sends the images
        exercisePicturesAdapter = new ExercisePicturesAdapter(getActivity(), images);
        exerciseViewPager.setAdapter(exercisePicturesAdapter);

    }

    // Gives a visual presentation for the user if the exercise is starred or not, dependent on the value
    private void setImageFabButton(String starredValued){
        if(starredValued.equals("1")){
            starredFab.setImageResource(R.drawable.star);
        }
        else{
            starredFab.setImageResource(R.drawable.star_outline);
        }
    }

    // Updates the current exercise if it is starred or unstarred
    public void starrExercise(String exerciseID, String starredValue){
        try{
            dbReferenceExercise.child(exerciseID).child("starred").setValue(starredValue);
        } catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(), "Failed to star the exercise.", Toast.LENGTH_SHORT).show();
        }
        setImageFabButton(starredValue);
    }


}

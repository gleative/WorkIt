package com.example.gleative.workit.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.gleative.workit.CreateCustomExerciseActivity;
import com.example.gleative.workit.MyWorkoutInfoActivity;
import com.example.gleative.workit.R;
import com.example.gleative.workit.model.CustomExercise;
import com.example.gleative.workit.model.Exercise;
import com.example.gleative.workit.model.Workout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by gleative on 07.11.2017.
 */

public class CreateCustomExerciseFragment extends Fragment{

    DatabaseReference dbReference;

    CustomExercise customExercise;
    Exercise selectedExercise;

    private TextView exerciseName;
    private TextView exerciseBodyPart;
    private ImageView exercisePicture1, exercisePicture2;
    private EditText sets, reps;

    public CreateCustomExerciseFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_create_custom_exercise, container, false); // Layout file

        exercisePicture1 = fragmentView.findViewById(R.id.selected_exercise_picture1);
        exercisePicture2 = fragmentView.findViewById(R.id.selected_exercise_picture2);
        exerciseBodyPart = fragmentView.findViewById(R.id.selected_exercise_body_part);
        sets = fragmentView.findViewById(R.id.sets);
        reps = fragmentView.findViewById(R.id.reps);

        return fragmentView;
    }

    // Displays the selected exercise information on the layout
    public void setDisplayedDetail(Exercise exercise){
        selectedExercise = exercise;

        try{
            Glide.with(getActivity()).load(exercise.getImageThumb1()).into(exercisePicture1);
            Glide.with(getActivity()).load(exercise.getImageThumb2()).into(exercisePicture2);
        } catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(), "Unable to load the pictures", Toast.LENGTH_SHORT).show();
        }

        // Displays what body part the exercise trains
        exerciseBodyPart.setText(exercise.getBodyPart());
    }

    // Adds the custome exercise to the created workout, unless the fields are empty.
    public void addCustomExerciseToWorkout(Workout workout){
        // If any of the edit text's are empty
        if(sets.getText().toString().isEmpty() || reps.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "You have to fill out both fields!", Toast.LENGTH_SHORT).show();
        }
        else {
            try{
                customExercise = createCustomExercise(workout, selectedExercise,
                        Integer.parseInt(sets.getText().toString()), Integer.parseInt(reps.getText().toString()));

                // Adds the custom exercise just created to the workouts list, so when the user is sendt to MyWorkoutInfoActivity, the custom exercise is displayed
                workout.addCustomExerciseToWorkout(customExercise);

                // This is where the user is sendt after adding a custom exercise to the workout
                Intent intent = new Intent(getActivity(), MyWorkoutInfoActivity.class);
                intent.putExtra("workout", workout);
                startActivity(intent);
            } catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getActivity(), "Error adding exercise to workout", Toast.LENGTH_SHORT).show();
            }

        }
    }

    // Creates the custom exercise and sends to database, and returns the created object
    private CustomExercise createCustomExercise(Workout workout, Exercise exercise, int sets, int reps){
        try{
            dbReference = FirebaseDatabase.getInstance().getReference("customExercises");

            // Creates a user node with random generated ID, and returns a unique key value (ID)
            String customExerciseID = dbReference.push().getKey();

            // IF THERE ARE SOME VALUES YOU DONT WANT IN THE CHILD, REMOVE GETTER AND SETTER FOR THAT VALUE, OR ELSE IT WILL DISPLAY!
            CustomExercise newCustomExercise = new CustomExercise(customExerciseID, workout.getWorkoutID(), exercise.getExerciseID(), exercise, sets, reps);

            // Adds the given values to the database
            dbReference.child(customExerciseID).setValue(newCustomExercise);

            return newCustomExercise;
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error creating a custom exercise", Toast.LENGTH_SHORT).show();
        }

        return null; // Return nothing, this means it failed to create a custom exercise
    }
}

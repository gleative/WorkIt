package com.example.gleative.workit;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gleative.workit.adapter.CustomExerciseRecyclerAdapter;
import com.example.gleative.workit.adapter.WorkoutsRecyclerAdapter;
import com.example.gleative.workit.fragments.ExerciseListFragment;
import com.example.gleative.workit.fragments.WorkoutCustomExercisesListFragment;
import com.example.gleative.workit.model.CustomExercise;
import com.example.gleative.workit.model.Exercise;
import com.example.gleative.workit.model.Workout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyWorkoutInfoActivity extends AppCompatActivity implements WorkoutCustomExercisesListFragment.OnCustomExerciseFragmentInteractionListener{

    DatabaseReference dbReferenceWorkout;
    DatabaseReference dbReferenceCustomExercise;

    Workout selectedWorkout;
    WorkoutCustomExercisesListFragment workoutCustomExercisesListFragment;

    Toolbar toolbar;
    EditText workoutNameView, workoutDescView;
    TextView dialogTitleView, dialogMessageView, dialogItemNameView;
    Button updateWorkoutButton, cancelUpdateWorkoutButton, dialogYesBtn, dialogNoBtn;
    FloatingActionButton fabEdit, fabAdd, fabDelete;

    boolean fabButtonIsOpen = false; // True if edit fab button is pressed
    boolean deleteExercise = false; // True when user wants to delete a exercise


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_workout_info);

        selectedWorkout = getIntent().getParcelableExtra("workout");

        toolbar = findViewById(R.id.toolbar_workout_name);
        setSupportActionBar(toolbar);

        workoutNameView = findViewById(R.id.selected_workout_name);
        workoutNameView.setSelected(true);
        workoutDescView = findViewById(R.id.selected_workout_desc);
        updateWorkoutButton = findViewById(R.id.update_workout_button);
        cancelUpdateWorkoutButton = findViewById(R.id.cancel_update_workout_button);
        fabEdit = findViewById(R.id.fab_edit_workout);
        fabAdd = findViewById(R.id.fab_add_exercise_to_workout);
        fabDelete = findViewById(R.id.fab_delete_exercise_from_workout);


        // Displays the update and cancel button if their is input from the user
        workoutNameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            // Displays the update and cancel button if their is input from the user
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!(selectedWorkout.getWorkoutName().equals(workoutNameView.getText().toString()))){
                    setVisibilityOnUpdateAndCancel(true); // Shows update and cancel button
                }
                else{
                    setVisibilityOnUpdateAndCancel(false);  // Hides update and cancel button
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Displays the update and cancel button if their is input from the user
        workoutDescView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            // Displays the update and cancel button if their is input from the user
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!(selectedWorkout.getWorkoutDescription().equals(workoutDescView.getText().toString()))){
                    setVisibilityOnUpdateAndCancel(true); // Shows update and cancel button
                }
                else{
                    setVisibilityOnUpdateAndCancel(false); // Hides update and cancel button
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        dbReferenceWorkout = FirebaseDatabase.getInstance().getReference().child("workouts");
        dbReferenceCustomExercise = FirebaseDatabase.getInstance().getReference().child("customExercises");

        // So we can access its methods
        workoutCustomExercisesListFragment = (WorkoutCustomExercisesListFragment) getSupportFragmentManager().findFragmentById(R.id.myWorkout_info_fragment);

        setDisplayedDetail(selectedWorkout);
    }

    // Displays the information about the workout to the user
    public void setDisplayedDetail(Workout workout){
        workoutNameView.setText(workout.getWorkoutName());
        workoutDescView.setText(workout.getWorkoutDescription());
        workoutCustomExercisesListFragment.getCustomExercisesFromWorkout(workout); // Sends the custom exercises list to the adapter, so it can display workouts custom exercises
    }

    // Displays the information about the chosen customExercise, or deletes it dependent on the value of "deleteExercise"
    @Override
    public void onCustomExerciseSelected(CustomExercise customExercise) {
        Exercise exercise = customExercise.getExercise();

        // See info about the chosen exercise
        if(!deleteExercise){
            Intent intent = new Intent(this, ExerciseInfoActivity.class);
            intent.putExtra("exercise", exercise);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); // Added because if you press fast two times, it goes into the activity twice
            startActivity(intent);
        }
        // Delete exercise
        else{
            try{
                showDialog(customExercise);

            }catch(Exception e){
                e.printStackTrace();
                Toast.makeText(this, "Failed to delete exercise.", Toast.LENGTH_SHORT).show();
            }

            // User no longer in delete exercise state
            deleteExercise = false;
            fabDelete.setImageResource(R.drawable.ic_delete);
        }
    }

    // Shows a dialog for the user when delete button on a workout is clicked, if yes, it deletes the workout, no is cancel
    private void showDialog(final CustomExercise customExercise){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_message_layout);

        // Finds the elements in the dialog layout, and sets text
        dialogYesBtn = dialog.findViewById(R.id.dialog_button_yes);
        dialogNoBtn = dialog.findViewById(R.id.dialog_button_no);
        dialogTitleView = dialog.findViewById(R.id.dialog_title);
        dialogMessageView = dialog.findViewById(R.id.dialog_message);
        dialogItemNameView = dialog.findViewById(R.id.dialog_item_name);
        dialogTitleView.setText("Delete Exercise");
        dialogMessageView.setText("Are you sure you want to delete ");
        dialogItemNameView.setText(customExercise.getExercise().getExerciseName() + "?");

        dialog.show();

        // Deletes workout and exits the dialog
        dialogYesBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                deleteExercise(customExercise);
                dialog.cancel();
            }
        });

        // Exits the dialog, if the user presses the no button
        dialogNoBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    // Deletes the exercise chosen, removes from list and database
    private void deleteExercise(CustomExercise customExercise){
        try{
            dbReferenceCustomExercise.child(customExercise.getCustomExerciseID()).removeValue();
            selectedWorkout.getCustomExercises().remove(customExercise);

            // Displays message to user showing it got deleted successfully
            Snackbar.make(findViewById(R.id.coord), "Exercise successfully deleted", Snackbar.LENGTH_SHORT).show();
        } catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Failed to delete exercise", Toast.LENGTH_SHORT).show();
        }

        // Updates the recycler view so it displays for the user it is gone
        workoutCustomExercisesListFragment.getCustomExercisesFromWorkout(selectedWorkout);
    }

    // Updates the workout with the new info to the database
    public void updateWorkout(View view) {
        String workoutID = selectedWorkout.getWorkoutID();
        String newWorkoutName = workoutNameView.getText().toString();
        String newWorkoutDesc = workoutDescView.getText().toString();

        try{
            dbReferenceWorkout.child(workoutID).child("workoutName").setValue(newWorkoutName);
            dbReferenceWorkout.child(workoutID).child("workoutDescription").setValue(newWorkoutDesc);

            // Update the objects values aswell. Fixes a bug if user updates value, and wants to update again and they press cancel.
            selectedWorkout.setWorkoutName(newWorkoutName);
            selectedWorkout.setWorkoutDescription(newWorkoutDesc);

            Toast.makeText(this, "Workout successfully updated", Toast.LENGTH_SHORT).show();
        } catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Failed to update workout!", Toast.LENGTH_SHORT).show();
        }

        // Hides the keyboard
        workoutNameView.clearFocus();
        workoutDescView.clearFocus();

        // Hides the update and cancel buttons
        setVisibilityOnUpdateAndCancel(false);

        hideKeyboard(view);

    }

    // Reverts the input to the origin name and description of the workout
    public void cancelUpdateWorkout(View view) {
        workoutNameView.setText(selectedWorkout.getWorkoutName());
        workoutDescView.setText(selectedWorkout.getWorkoutDescription());

        workoutNameView.clearFocus();
        workoutDescView.clearFocus();

        hideKeyboard(view);
    }

    // Displays the two buttons if true, or else removes them from the view
    private void setVisibilityOnUpdateAndCancel(boolean value){
        if(!value){
            updateWorkoutButton.setVisibility(View.GONE);
            cancelUpdateWorkoutButton.setVisibility(View.GONE);

        }
        else{
            updateWorkoutButton.setVisibility(View.VISIBLE);
            cancelUpdateWorkoutButton.setVisibility(View.VISIBLE);
        }
    }

    // Hides the keyboard
    private void hideKeyboard(View view){
        view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    // When the user press back, they will be sent to MyWorkoutsActivity, and not go through all the process they did when they created a workout
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MyWorkoutActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // So MyWorkoutActivity wont overlap on eachother when user presses back
        startActivity(intent);
    }

    // Delete icon is white if it is in delete state (everything clicked gets deleted) icon is black otherwise.
    public void selectExerciseToRemoveFromWorkout(View view){
        if(!deleteExercise){
            deleteExercise = true;
            fabDelete.setImageResource(R.drawable.ic_delete_white);
        }
        else{
            deleteExercise = false;
            fabDelete.setImageResource(R.drawable.ic_delete);
        }
    }

    // When user presses the FAB button
    public void addNewCustomExerciseToWorkout(View view) {
        Intent intent = new Intent(this, ExerciseActivity.class);
        intent.putExtra("workout", selectedWorkout);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); // So MyWorkoutActivity wont overlap on eachother when user presses back
        startActivity(intent);
    }

    public void displayOtherFabButtons(View view){
        if(!fabButtonIsOpen){
            hideFabButtons(false);
        }
        else{
            hideFabButtons(true);
        }
    }

    // Hides the two fab buttons, dependent on boolean value
    private void hideFabButtons(boolean value){
        // If they dont want to hide fab buttons
        if(!value){
            fabButtonIsOpen = true;
            fabEdit.setImageResource(R.drawable.ic_close_white);
            fabAdd.setVisibility(View.VISIBLE);
            fabDelete.setVisibility(View.VISIBLE);
        }
        else{
            fabButtonIsOpen = false;
            fabEdit.setImageResource(R.drawable.ic_edit_white);
            fabAdd.setVisibility(View.INVISIBLE);
            fabDelete.setVisibility(View.INVISIBLE);
        }
    }
}

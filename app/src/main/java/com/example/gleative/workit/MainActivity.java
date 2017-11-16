package com.example.gleative.workit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gleative.workit.fragments.NavigationDrawerFragment;
import com.example.gleative.workit.model.Exercise;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    NavigationDrawerFragment navigationDrawerFragment;

    TextView textView;

    DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar); // Finner toolbaren i Activity
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        textView = (TextView) findViewById(R.id.text);

        dbReference = FirebaseDatabase.getInstance().getReference().child("text"); // Gets reference of the the child "text"

        // Reads from the database
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String e = dataSnapshot.getValue().toString();

                textView.setText(e);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        setUpDrawer();
    }

    private void setUpDrawer() {
        navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationDrawerFragment.setUpDrawer(drawerLayout, toolbar, R.id.nav_home);
    }

    @Override
    protected void onStart() {
        navigationDrawerFragment.updateCheckedItem(R.id.nav_home);

        super.onStart();
    }

    // When user presses FAB button
    public void startWorkout(View view) {
        Intent intent = new Intent(this, MyWorkoutActivity.class);
        intent.putExtra("startWorkout", 1); // Sends value to MyWorkoutActivity that it wants to start the workout the user chooses.
        startActivity(intent);
    }

}

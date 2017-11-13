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
import android.widget.TextView;

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
        toolbar.setTitle("Home"); // Må sette title her av en eller annen grunn. Går ikke i manifest, ellers blir navn på appen Home
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


//    private class MyActionListener  implements View.OnClickListener{
//
//        @Override
//        public void onClick(View v) {
//            // Code to do the desired Action
//            Snackbar.make(v, "I'm dead! =(", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
//        }
//    }
}


//public class MainActivity extends AppCompatActivity
//        implements NavigationView.OnNavigationItemSelectedListener {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//    }
//
//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//        Intent intent;
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//            intent = new Intent(this, ExerciseActivity.class);
//            startActivity(intent);
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
//}

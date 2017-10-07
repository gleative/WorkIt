package com.example.gleative.workit.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gleative.workit.ExerciseActivity;
import com.example.gleative.workit.MainActivity;
import com.example.gleative.workit.R;

public class NavigationDrawerFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        navigationView = (NavigationView) view.findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        return view;
    }

    public void setUpDrawer(DrawerLayout drawerLayout, Toolbar toolbar, int menuItemId) {
        this.drawerLayout = drawerLayout;
        drawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, 0, 0) {};

        this.drawerLayout.addDrawerListener(drawerToggle);

        drawerToggle.syncState();

        navigationView.setCheckedItem(menuItemId);
    }

    public void updateCheckedItem(int menuItemId) {
        navigationView.setCheckedItem(menuItemId);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case R.id.nav_home:
                intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_profile:
//                intent = new Intent(getActivity(), ListActivity.class);
//                startActivity(intent);
//                break;
            case R.id.nav_exercises:
                intent = new Intent(getActivity(), ExerciseActivity.class);
                startActivity(intent);
                break;
//            case R.id.nav_help_feedback:
//                Toast.makeText(getActivity(), "Opening settings...", Toast.LENGTH_SHORT).show();
//                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}


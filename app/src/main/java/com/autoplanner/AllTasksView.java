package com.autoplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AllTasksView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /* create the array list to save the data */
    public static ArrayList<Task> taskList = new ArrayList<>();
    private final int InternetRequest = 0;
    private final int GoogleRequest = 93;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //used to load the saved tasks even when program is closed
        Type dataType = new TypeToken<ArrayList<Task>>() {}.getType();
        SharedPreferences prefs = this.getSharedPreferences("savedTasks",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("savedTasks", "");
        taskList = gson.fromJson(json, dataType);

        //if there is nothing in the task list, add a welcome message otherwise a null pointer exception will be thrown
        if (taskList == null){
            taskList = new ArrayList<>();
            Task t = new Task();
            t.setWhat("Welcome to auto planner! There is no task. Please add a new task to get started.");
            t.setDeadline("2017 01 30 23 59");
            t.setWhere("Made by Runze Zheng");
            t.setOrder(-1);
            taskList.add(t);

            SharedPreferences.Editor prefsEditor = prefs.edit();
            String jsonSave = gson.toJson(taskList, dataType);
            prefsEditor.putString("savedTasks", jsonSave);
            prefsEditor.apply();

            //try again to get task list
            json = prefs.getString("savedTasks", "");
            taskList = gson.fromJson(json, dataType);
        }

        //sort
        Sorter sr = new Sorter();
        taskList = sr.optimizedSort(taskList);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent addNewIntent = new Intent(context, NewTask.class);
                startActivity(addNewIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

         /* create the recycler view */
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(20, true));

        /* create the adapter itself */
        mAdapter = new RecyclerAdapter(taskList);
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.Callback callback = new TaskTouchHelper(mAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //parse a gson and save to shared preference
            SharedPreferences prefs = getSharedPreferences("savedTasks", MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = prefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(AllTasksView.taskList);
            prefsEditor.clear();
            prefsEditor.putString("savedTasks", json);
            prefsEditor.commit();
            this.finish();
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.all_tasks_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case InternetRequest: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            case GoogleRequest:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.findViewById(R.id.loginImageView).callOnClick();
                } else {
                    Log.i("get google failed", "Permissions not granted for Google sign-in. :(");
                }
        }
    }
}

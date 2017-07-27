package com.autoplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

public class NewTask extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private String deadlineYear = "2017", deadlineMonth = "01", deadlineDay = "01", deadlineHour = "00", deadlineMinute = "00";
    private boolean switchChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final Button submit = (Button) findViewById(R.id.addButton);
        final TextView what = (TextView) findViewById(R.id.whatText);
        final TextView duration = (TextView) findViewById(R.id.durationText);
        final TextView where = (TextView) findViewById(R.id.whereText);
        final Switch typeSwitch = (Switch) findViewById(R.id.typeSwitch);
        final TextView startingTimeTextView = (TextView) findViewById(R.id.startingTimeTextView);

        typeSwitch.setChecked(false);
        typeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    //stating time mode
                    startingTimeTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
                    typeSwitch.setTextColor(getResources().getColor(R.color.colorInactive));
                    switchChecked = true;
                }else{
                    //deadline mode
                    startingTimeTextView.setTextColor(getResources().getColor(R.color.colorInactive));
                    typeSwitch.setTextColor(getResources().getColor(R.color.colorPrimary));
                    switchChecked = false;
                }
            }
        });

        NumberPicker yearPicker = (NumberPicker) findViewById(R.id.numberPicker);
        yearPicker.setMinValue(2017);
        yearPicker.setMaxValue(2150);
        yearPicker.setWrapSelectorWheel(false);
        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                deadlineYear = ((Integer) newVal).toString();
            }
        });

        NumberPicker monthPicker = (NumberPicker) findViewById(R.id.numberPicker2);
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setWrapSelectorWheel(true);
        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                deadlineMonth = ((Integer) newVal).toString();
                if (deadlineMonth.length() < 2){
                    deadlineMonth = "0" + deadlineMonth;
                }
            }
        });

        NumberPicker dayPicker = (NumberPicker) findViewById(R.id.numberPicker3);
        dayPicker.setMinValue(1);
        dayPicker.setMaxValue(31);
        dayPicker.setWrapSelectorWheel(true);
        dayPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                deadlineDay = ((Integer) newVal).toString();
                if (deadlineDay.length() < 2){
                    deadlineDay = "0" + deadlineDay;
                }
            }
        });

        NumberPicker hourPicker = (NumberPicker) findViewById(R.id.numberPicker4);
        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(23);
        hourPicker.setWrapSelectorWheel(true);
        hourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                deadlineHour = ((Integer) newVal).toString();
                if (deadlineHour.length() < 2){
                    deadlineHour = "0" + deadlineHour;
                }
            }
        });

        NumberPicker minutePicker = (NumberPicker) findViewById(R.id.numberPicker5);
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);
        minutePicker.setWrapSelectorWheel(true);
        minutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                deadlineMinute = ((Integer) newVal).toString();
                if (deadlineMinute.length() < 2){
                    deadlineMinute = "0" + deadlineMinute;
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task t = new Task();
                try {
                    t.setWhat(what.getText().toString());
                    t.setDeadline(deadlineYear + "/" + deadlineMonth + "/" + deadlineDay + " " + deadlineHour + ":" + deadlineMinute);
                    t.setDuration(Double.parseDouble(duration.getText().toString()));
                    t.setWhere(where.getText().toString());
                    if (switchChecked){
                        t.setMode(true);
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), " WTF are you trying to create??? ", Toast.LENGTH_SHORT).show();
                    System.out.println(e);
                }
                Sorter Sorter = new Sorter();
                if (Sorter.doabilityCheck(t)) {
                    AllTasksView.taskList.add(t);

                    //parse a gson and save to shared preference
                    SharedPreferences prefs = getSharedPreferences("savedTasks", MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor = prefs.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(AllTasksView.taskList);
                    prefsEditor.clear();
                    prefsEditor.putString("savedTasks", json);
                    prefsEditor.commit();

                    //go back to main view
                    Context context = view.getContext();
                    Intent intent = new Intent(context, AllTasksView.class);
                    startActivity(intent);
                    finish();
                } else {
                    submit.setText("Task already overdue or don't have enough time to finish");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent mainIntent = new Intent(this.getApplicationContext(), AllTasksView.class);
            startActivity(mainIntent);
            finish();
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_task, menu);
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
}

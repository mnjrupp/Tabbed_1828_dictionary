package com.example.mnjru.tabbed_1828_dictionary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import Utilities.ClipboardUtil;
import Utilities.DicDatabaseHelper;
import Utilities.TaskHistory;
import Utilities.ThemeUtility;

public class DisplayTopicDef extends AppCompatActivity {

    private static int intTheme=0;
    public final static String TOPIC = "topic";
    TextView textViewDef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Need to get the theme from preference and set before
        // setContentView
        intTheme = getSharedTheme();
        if(ThemeApplication.currentPosition!=intTheme){
            ThemeUtility.changetoTheme(this,intTheme);
            ThemeApplication.currentPosition=intTheme;
        }
        ThemeUtility.onActivityCreateSetTheme(this);
        // -----------------------------------------------

        setContentView(R.layout.activity_display_topic_def);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        String topic = intent.getStringExtra(TOPIC);
        textViewDef = (TextView)findViewById(R.id.textViewDef);
        // Get font size from preferences
        textViewDef.setTextSize(TypedValue.COMPLEX_UNIT_SP, getSharedfontSize());

        // Set the title of the Activity to the topic
        if(!topic.isEmpty()) {
            setTitle(topic);
        }
        getCompleteDefinition(topic);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardUtil cm = new ClipboardUtil();
                cm.copyToClipboard(getApplicationContext(),textViewDef.getText().toString());
                Snackbar.make(view, "Copied to clipboard", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        intTheme = getSharedTheme();
        if(ThemeApplication.currentPosition!=intTheme){
            ThemeUtility.changetoTheme(this,intTheme);
            ThemeApplication.currentPosition=intTheme;
        }
    }

    private void getCompleteDefinition(String input)
    {

        DicDatabaseHelper db = new DicDatabaseHelper(this);
        // query the database based on the user input
        String definition = db.getDefnition(input);
        db.close();
        // if empty, we don't want to proceed
        if(!definition.isEmpty()) {
            textViewDef.setText(definition);
            // Scroll the TextView to the top
            textViewDef.scrollTo(0, 0);


            Date dNow = new Date();
            SimpleDateFormat ft =
                    new SimpleDateFormat("E yyyy.MM.dd");
            TaskHistory taskHistory = new TaskHistory(this);
            taskHistory.execute("add_hist", input, ft.format(dNow));
        }
    }
    private int getSharedfontSize() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int szText = Integer.parseInt(sharedPreferences.getString("textsize_list", "-1"));
        switch (szText) {
            case -1:
                // return default text size
                int szdefault = 14;
                return szdefault;
            default:
                // using the int array from Main Activity
                return MainActivity.fSize[szText];

        }
    };

    private int getSharedTheme(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int szTheme = Integer.parseInt(sharedPreferences.getString("theme_list", "0"));
        return szTheme;
    };
}

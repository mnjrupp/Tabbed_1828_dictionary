package com.example.mnjru.tabbed_1828_dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import Utilities.DicDatabaseHelper;

public class DisplayTopicDef extends AppCompatActivity {

    public final static String TOPIC = "topic";
    TextView textViewDef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_topic_def);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        String topic = intent.getStringExtra(TOPIC);
        textViewDef = (TextView)findViewById(R.id.textViewDef);

        // Set the title of the Activity to the topic
        if(!topic.isEmpty()) {
            setTitle(topic);
        }
        //make input proper case before searching database
        DicDatabaseHelper db = new DicDatabaseHelper(this);
        // query the database based on the user input
        textViewDef.setText(db.getDefnition(topic));
        // Scroll the TextView to the top
        textViewDef.scrollTo(0, 0);
        db.close();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}

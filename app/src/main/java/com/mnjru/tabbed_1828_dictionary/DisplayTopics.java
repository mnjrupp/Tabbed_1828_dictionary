package com.mnjru.tabbed_1828_dictionary;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import Utilities.TaskGetTopics;
import Utilities.ThemeUtility;

import static java.security.AccessController.getContext;


/**
 * Created by mnjru on 5/29/2017.
 */

public class DisplayTopics extends Activity {
    public final static String ALPHA = "alpha_message";
    public final static String TOPIC = "topic";
    ListView listView;
    TaskGetTopics taskGetTopics;
    private static int intTheme=0;

    public void onCreate(@Nullable Bundle savedInstanceState) {
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
        setContentView(R.layout.activity_topics_alpha);
        Intent intent = getIntent();
        String alpha = intent.getStringExtra(ALPHA);
        updateAlpha(alpha);
        listView = (ListView) findViewById(R.id.list_topics_alpha);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String topic = listView.getItemAtPosition(position).toString();
                Intent intent = new Intent(getApplicationContext(), DisplayTopicDef.class);
                intent.putExtra(TOPIC,topic);
                startActivity(intent);
            }

        });


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


    public void updateAlpha(String alpha)
    {
        taskGetTopics = new TaskGetTopics(this);
        taskGetTopics.execute("get_topics",alpha);

    }

    private int getSharedTheme(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int szTheme = Integer.parseInt(sharedPreferences.getString("theme_list", "0"));
        return szTheme;
    };

}

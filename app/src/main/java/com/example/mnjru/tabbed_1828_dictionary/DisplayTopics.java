package com.example.mnjru.tabbed_1828_dictionary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import Utilities.TaskGetTopics;

import static java.security.AccessController.getContext;


/**
 * Created by mnjru on 5/29/2017.
 */

public class DisplayTopics extends Activity {
    public final static String ALPHA = "alpha_message";
    public final static String TOPIC = "topic";
    ListView listView;
    TaskGetTopics taskGetTopics;


    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


   /* public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_topics_alpha, container, false);
        listView = (ListView) view.findViewById(R.id.list_topics_alpha);
        return view;
    }*/

    public void updateAlpha(String alpha)
    {
        taskGetTopics = new TaskGetTopics(this);
        taskGetTopics.execute("get_topics",alpha);

    }
}

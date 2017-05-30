package com.example.mnjru.tabbed_1828_dictionary;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by mnjru on 5/29/2017.
 */

public class DisplayTopicsAlpha extends Fragment {

    ListView listView;
    ArrayAdapter<String> listAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topics_alpha, container, false);
        listView = (ListView) view.findViewById(R.id.list_topics_alpha);
        return view;
    }
}
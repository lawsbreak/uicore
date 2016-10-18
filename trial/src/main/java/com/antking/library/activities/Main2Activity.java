package com.antking.library.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.antking.library.R;
import com.antking.library.uicore.SyncHorizontalScrollView;
import com.antking.library.uicore.SyncListView;
import com.antking.library.uicore.WrapperExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main2Activity extends AppCompatActivity {


    ExpandableListViewAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    SyncListView slvHeader, slvData;
    SyncHorizontalScrollView shsvHeader, shsvData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        slvHeader   = (SyncListView) findViewById(R.id.lv_header);
        slvData     = (SyncListView) findViewById(R.id.lv_data);

        shsvHeader  = (SyncHorizontalScrollView) findViewById(R.id.shsv_header);
        shsvData    = (SyncHorizontalScrollView) findViewById(R.id.shsv_data);

        shsvHeader.setViewPartner(shsvData);
        shsvData.setViewPartner(shsvHeader);

        slvHeader.setViewPartner(slvData);
        slvData.setViewPartner(slvHeader);

        prepareListData();

        listAdapter = new ExpandableListViewAdapter(this, listDataHeader, listDataChild);
        WrapperExpandableListAdapter wrapperAdapter = new WrapperExpandableListAdapter(listAdapter);
        slvHeader.setAdapter(wrapperAdapter);
        slvData.setAdapter(wrapperAdapter);

//        slvData.setVisibility(View.VISIBLE);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Top 100");
        listDataHeader.add("Top 250");
        listDataHeader.add("Now Showing");
        listDataHeader.add("Coming Soon..");

        // Adding child data
        List<String> top100 = new ArrayList<String>();
        top100.add("The Shawshank");
        top100.add("The Godfather");
        top100.add("The Dark Knight");
        top100.add("12 Angry Men");
        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank");
        top250.add("The Godfather");
        top250.add("The Godfather II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular");
        comingSoon.add("The Canyons");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), top100); // Header, Child data
        listDataChild.put(listDataHeader.get(1), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(2), nowShowing);
        listDataChild.put(listDataHeader.get(3), comingSoon);
    }
}

/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * {@link EarthquakeListViewAdapter} is an {@link ArrayAdapter} that can provide the layout for each list item
 * based on a data source, which is a list of {@link Earthquake} objects.
 */
public class EarthquakeListViewAdapter extends BaseAdapter {
    private LayoutInflater mInflator;
    private ArrayList<Earthquake> quakeList;
    public static final String TAG = "UASDeviceAdapter";

    public EarthquakeListViewAdapter(Context context, ArrayList<Earthquake> quakeList) {
           super();
        this.quakeList = quakeList;

    }


    @Override
    public int getCount() {
        return quakeList.size();
    }

    @Override
    public Earthquake getItem(int i) {
        return quakeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.quake_list_item, parent, false);
        }
        Earthquake earthquake = getItem(position);

        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude_text_view);
        magnitudeTextView.setText(earthquake.getMagnitude() + " ");

        TextView locationTextView = (TextView) listItemView.findViewById(R.id.location_text_view);
        locationTextView.setText(splitOffsetLocation(earthquake.getLocation()));

        TextView locationPrimaryTextView = (TextView) listItemView.findViewById(R.id.location_primary_text_view);
        locationPrimaryTextView.setText(splitPrimaryLocation(earthquake.getLocation()));

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_text_view);
        dateTextView.setText(formatDate(earthquake.getDate()));

        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time_text_view);
        timeTextView.setText(formatTime(earthquake.getDate()));

        return listItemView;
    }


    public void add(Earthquake earthquake) {

        quakeList.add(earthquake);


    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private String splitOffsetLocation(String location){

        String[] parts = location.split("(?<=of)");
        String part1 = parts[0]; // 004-
        //String part2 = parts[1]; // 034556
        if(parts.length == 1){
           return "";
        }
        return part1;
    }

    private String splitPrimaryLocation(String location){

        String[] parts = location.split("(?=of)");
        Log.d(TAG, "splitPrimaryLocation: " + parts.length);

        if(parts.length > 1){
            return parts[1];
        }
        return parts[0];
    }
}
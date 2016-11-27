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
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * {@link EarthquakeListViewAdapter} is an {@link ArrayAdapter} that can provide the layout for each list item
 * based on a data source, which is a list of {@link Earthquake} objects.
 */
public class EarthquakeListViewAdapter extends BaseAdapter {
    private static final String LOCATION_SEPARATOR = "of";
    private LayoutInflater mInflator;
    private ArrayList<Earthquake> quakeList;
    public static final String TAG = "UASDeviceAdapter";
    private Context context;

    public EarthquakeListViewAdapter(Context context, ArrayList<Earthquake> quakeList) {
           super();
        this.quakeList = quakeList;
        this.context = context;

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


        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude);
        magnitudeTextView.setText(formatMagnitude(earthquake.getMagnitude()));
        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(earthquake.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        TextView locationTextView = (TextView) listItemView.findViewById(R.id.location_offset);
        locationTextView.setText(splitOffsetLocation(earthquake.getLocation()));

        TextView locationPrimaryTextView = (TextView) listItemView.findViewById(R.id.primary_location);
        locationPrimaryTextView.setText(splitPrimaryLocation(earthquake.getLocation()));

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        dateTextView.setText(formatDate(earthquake.getDate()));

        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time);
        timeTextView.setText(formatTime(earthquake.getDate()));

        return listItemView;
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeLevel = (int) magnitude;
        int magnitudeColorResourceId;
        Log.d(TAG, "getMagnitudeColor: " +magnitudeLevel);
        if (magnitudeLevel > 10) {magnitudeLevel = 10;}
        switch(magnitudeLevel){
            case 1: magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2: magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3: magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4: magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5: magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6: magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7: magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8: magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9: magnitudeColorResourceId = R.color.magnitude9;
                break;
            case 10: magnitudeColorResourceId = R.color.magnitude10plus;
                break;
            default: magnitudeColorResourceId = R.color.magnitude1;
                break;
        }
        return ContextCompat.getColor(context, magnitudeColorResourceId);
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

        String[] parts = location.split("(?<=" + LOCATION_SEPARATOR + ")");
        if(parts.length == 1){
           return  context.getString(R.string.near_the);
        }
        return parts[0];
    }

    private String splitPrimaryLocation(String location){

        String[] parts = location.split("(?<=" + LOCATION_SEPARATOR + ")");
        Log.d(TAG, "splitPrimaryLocation: " + parts.length);
        if(parts.length > 1){
            return parts[1];
        }
        return parts[0];
    }

    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    public void clear(){
        quakeList.clear();
    }

    public void add(ArrayList<Earthquake> earthquakes) {
        quakeList.addAll(earthquakes);
    }
}
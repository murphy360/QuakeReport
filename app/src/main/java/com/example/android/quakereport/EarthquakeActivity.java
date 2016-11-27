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

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String TAG = "Earthquake Activity";

    ArrayList<Earthquake> earthquakes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        earthquakes = new ArrayList<>();

        new DownloadTask().execute("");
        updateUI();


    }

    private void updateUI() {
        // Find a reference to the {@link ListView} in the layout
        final ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        final EarthquakeListViewAdapter adapter = new EarthquakeListViewAdapter(
                this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

        earthquakeListView.setClickable(true);

        //When an Item in the list is clicked.
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Get SensorObject from listView
                Earthquake earthquake = (Earthquake) (earthquakeListView.getItemAtPosition(position));
                String url = earthquake.getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    /**
     * Implementation of AsyncTask, to fetch the data in the background away from
     * the UI thread.
     */
    private class DownloadTask extends AsyncTask<String, Void, ArrayList<Earthquake>> {

        @Override
        protected ArrayList<Earthquake> doInBackground(String... urls) {
            Log.d(TAG, "doInBackground: ");
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            return QueryUtils.extractEarthquakes();//TODO add urls[0]
        }

        /**
         * Uses the logging framework to display the output of the fetch
         * operation in the log fragment.
         */
        @Override
        protected void onPostExecute(ArrayList<Earthquake> result) {
            Log.d(TAG, "onPostExecute:");
            // If there is no result, do nothing.
            if (result == null) {
                return;
            }
            Log.i(TAG, result.toString());
            // Update the information displayed to the user.
            earthquakes = result;
            updateUI();
        }
    }
}

/**
 * Created by Catriona Crowe (S1511579)
 */

package com.catrionacrowe.earthquakeupdates;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ListView listEarthquakes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listEarthquakes = (ListView) findViewById(R.id.fullList);

//        Log.d(TAG, "onCreate method: starting Asynctask");
        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://quakes.bgs.ac.uk/feeds/MhSeismology.xml");
//        Log.d(TAG, "onCreate method: done");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.earthquake_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int optionID = item.getItemId();

        switch(optionID) {
            case R.id.element1:
                setContentView(R.layout.activity_main);
                Intent homePage = new Intent(this, MainActivity.class);
                startActivity(homePage);
                break;
            case R.id.element2:
                setContentView(R.layout.activity_maps);
                Intent mapsPage = new Intent(this, MapsActivity.class);
                startActivity(mapsPage);
                break;
            case R.id.element3:
                setContentView(R.layout.activity_search);
                break;
            case R.id.element4:
                finish();
                System.exit(0);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    /**
     * Handles the button click to create a new date picker fragment and
     * show it.
     *
     * @param view View that was clicked
     */
    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),
                getString(R.string.datepicker));
    }

    /**
     * Process the date picker result into strings that can be displayed in
     * a Toast.
     *
     * @param year Chosen year
     * @param month Chosen month
     * @param day Chosen day
     */
    public void processDatePickerResult(int year, int month, int day) {

//        String month_string = Integer.toString(month + 1);
//        String day_string = Integer.toString(day);
//        String year_string = Integer.toString(year);
//
//        String dateMessage = (month_string +
//                "/" + day_string +
//                "/" + year_string);

        String[] monthName = {"Jan", "Feb",
                "Mar", "Apr", "May", "Jun", "Jul",
                "Aug", "Sep", "Oct", "Nov",
                "Dec"};

        String chosenMonth = monthName[month];

        String dateSelected = day + " " + chosenMonth + " " + year;

 //       Log.d("Date Selected = " ,dateSelected);

        Toast.makeText(this, getString(R.string.date) + dateSelected,
                Toast.LENGTH_SHORT).show();

        DBHelper dbh = new DBHelper(getApplicationContext());

        ArrayList<String> dateMatches;

        dateMatches = dbh.selectStatementB(dateSelected);

        for (String results : dateMatches){
            Log.d("Results: ",results);
        }
    }

    private class DownloadData extends AsyncTask<String, Void, String> {
        private static final String TAG = "DownloadData";

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            Log.d(TAG, "onPostExecute method: parameter is " + s);
            EarthquakeParser earthquakeParser = new EarthquakeParser();
            earthquakeParser.parse(s);

            ToDB(EarthquakeParser.getEarthquakes());

            DBHelper dbh = new DBHelper(getApplicationContext());

            dbh.deleteStatement();

            List<String> check = dbh.selectStatementA();
            StringBuilder sb = new StringBuilder();
            sb.append(check.size() + " - Size of check\n");
            for (String checks : check){
                sb.append(checks + "\n");
            }

            check.toArray();
//          Log.e("STRING",check.toString());

            EarthquakeAdapter earthquakeAdapter= new EarthquakeAdapter(MainActivity.this, R.layout.list_record,
                    EarthquakeParser.getEarthquakes());
            listEarthquakes.setAdapter(earthquakeAdapter);
        }

        private void ToDB(ArrayList<EarthquakeItem> earthquakes){
            DBHelper dbh2 = new DBHelper (getApplicationContext());

            for (EarthquakeItem eq : earthquakes){
                String eqLoc = eq.getLocation();
                String eqMag = eq.getMagnitude();
                String eqDep = eq.getDepth();
                String eqLink = eq.getLink();
                String eqDate = eq.getDatePublished();
                String eqLat = eq.getGeoLat();
                String eqLong = eq.getGeoLong();
                dbh2.insertStatement(eqLoc,eqMag,eqDep,eqLink,eqDate,eqLat,eqLong);
            }
        }

        @Override
        protected String doInBackground(String... strings) {
//            Log.d(TAG, "doInBackground method: starts with " + strings[0]);
            String rssFeed = downloadXML(strings[0]);
            if (rssFeed == null) {
                Log.e(TAG, "doInBackground method: Error downloading");
            }
            return rssFeed;
        }

        private String downloadXML(String urlPath) {
            StringBuilder xmlResult = new StringBuilder();

            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
//                Log.d(TAG, "downloadXML method: The response code was " + response);
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                int charsRead;
                //reads 800 characters at a time
                char[] inputBuffer = new char[800];
                while (true) {
                    charsRead = reader.read(inputBuffer);
                    if (charsRead < 0) {
                        break;
                    }
                    if (charsRead > 0) {
                        xmlResult.append(String.copyValueOf(inputBuffer, 0, charsRead));
                    }
                }
                reader.close();

                return xmlResult.toString();
            } catch (MalformedURLException e) {
                Log.e(TAG, "downloadXML method: URL is invalid " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "downloadXML method: IO Exception reading data: " + e.getMessage());
            } catch (SecurityException e) {
                Log.e(TAG, "downloadXML method: Security Exception - permisson required" + e.getMessage());
            }

            return null;
        }
    }
}
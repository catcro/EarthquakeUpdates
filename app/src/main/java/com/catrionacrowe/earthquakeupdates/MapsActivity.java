package com.catrionacrowe.earthquakeupdates;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        ArrayList<EarthquakeItem> earthquakes = EarthquakeParser.getEarthquakes();

        //set the centre location for zoom purposes
        LatLng centreLocation = new LatLng(54.072701, -4.653230);

        for (EarthquakeItem eq : earthquakes) {

            String [] titlesplit = eq.getDescription().split(";",-1);

            //get lat
            double varlat = Double.parseDouble(eq.getGeoLat());

            //get long
            double varlong =  Double.parseDouble(eq.getGeoLong());

            //set position of earthquake
            LatLng position = new LatLng(varlat, varlong);

            // Add a marker using lat and long coordinates and move the camera to the location plotted
            mMap.addMarker(new MarkerOptions().position(position).title(titlesplit[1]));

            }

        //zoom to centre on GB by 5
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(centreLocation, 5), 5000, null);
        //change map type to hybrid
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

    }
}

/**
 * Created by Catriona Crowe  (S1511579)
 */

package com.catrionacrowe.earthquakeupdates;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter {

    private static final String TAG = "EarthquakeAdapter";
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private List<EarthquakeItem> earthquakes;

    public EarthquakeAdapter(Context context, int resource, List<EarthquakeItem> applications) {
        super(context, resource);
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.earthquakes = applications;
    }

    @Override
    public int getCount() {
        return earthquakes.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = layoutInflater.inflate(layoutResource, parent, false);
        }

        TextView earthquakeTitle = (TextView) convertView.findViewById(R.id.earthquakeTitle);
        TextView earthquakeDescription = (TextView) convertView.findViewById(R.id.earthquakeDescription);

        EarthquakeItem currentApp = earthquakes.get(position);

        earthquakeTitle.setText(currentApp.getTitle());
        earthquakeDescription.setText(currentApp.getDescription());

        return convertView;
    }

}

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
        ViewHolder viewHolder;

        if (convertView == null){
            convertView = layoutInflater.inflate(layoutResource, parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        EarthquakeItem currentApp = earthquakes.get(position);

        viewHolder.eqLocation.setText(currentApp.getLocation());
        viewHolder.eqDateTime.setText(currentApp.getDateTimePublished());
        viewHolder.eqMagnitude.setText(currentApp.getMagnitude());
        viewHolder.eqDepth.setText(currentApp.getDepth());

        return convertView;
    }

    private class ViewHolder {
        final TextView eqLocation;
        final TextView eqDateTime;
        final TextView eqMagnitude;
        final TextView eqDepth;

        ViewHolder(View v) {
            this.eqLocation = v.findViewById(R.id.earthquakeLocation);
            this.eqDateTime = v.findViewById(R.id.earthquakeDateTime);
            this.eqMagnitude = v.findViewById(R.id.earthquakeMagnitide);
            this.eqDepth = v.findViewById(R.id.earthquakeDepth);
        }
    }

}

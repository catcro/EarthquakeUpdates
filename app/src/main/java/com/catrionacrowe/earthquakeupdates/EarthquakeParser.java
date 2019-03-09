/**
 * Created by Catriona Crowe (S1511579)
 */

package com.catrionacrowe.earthquakeupdates;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class EarthquakeParser {
    private static final String TAG = "EarthquakeParser";
    private static ArrayList<EarthquakeItem> earthquakes;

    public EarthquakeParser() {
        this.earthquakes = new ArrayList<>();
    }

    public static ArrayList<EarthquakeItem> getEarthquakes() {
        return earthquakes;
    }

    public boolean parse(String xmlData) {
        boolean status = true;
        EarthquakeItem currentTag = null;
        boolean entry = false;
        String TagValue = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(xmlData));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xpp.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        Log.d(TAG, "parse: Starting tag for " + tagName);
                        if ("item".equalsIgnoreCase(tagName)) {
                            entry = true;
                            currentTag = new EarthquakeItem();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        TagValue = xpp.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        Log.d(TAG, "parse: Ending tag for " + tagName);
                        if (entry) {
                            if ("item".equalsIgnoreCase(tagName)) {
                                earthquakes.add(currentTag);
                                entry = false;
                            } else if ("title".equalsIgnoreCase(tagName)) {
                                currentTag.setTitle(TagValue);
                            } else if ("description".equalsIgnoreCase(tagName)) {
                                currentTag.setDescription(TagValue);
                            } else if ("link".equalsIgnoreCase(tagName)) {
                                currentTag.setLink(TagValue);
                            } else if ("pubdate".equalsIgnoreCase(tagName)) {
                                currentTag.setPubdate(TagValue);
                            } else if ("category".equalsIgnoreCase(tagName)) {
                                currentTag.setCategory(TagValue);
                            } else if ("lat".equalsIgnoreCase(tagName)) {
                                currentTag.setGeoLat(TagValue);
                            } else if ("long".equalsIgnoreCase(tagName)) {
                                currentTag.setGeoLong(TagValue);
                            }
                        }
                        break;

                    default:
                        // Nothing else to add
                }
                eventType = xpp.next();

            }
            for (EarthquakeItem eq : earthquakes) {
                Log.d(TAG, "--------Earthquake--------");
                Log.d(TAG, eq.toString());
            }

        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        }

        return status;
    }
}


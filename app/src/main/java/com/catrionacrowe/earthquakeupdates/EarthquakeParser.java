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
//                        Log.d(TAG, "parse: Starting tag for " + tagName);
                        if ("item".equalsIgnoreCase(tagName)) {
                            entry = true;
                            currentTag = new EarthquakeItem();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        TagValue = xpp.getText();
                        break;

                    case XmlPullParser.END_TAG:
//                        Log.d(TAG, "parse: Ending tag for " + tagName);
                        if (entry) {
                            if ("item".equalsIgnoreCase(tagName)) {
                                earthquakes.add(currentTag);
                                entry = false;
                            } else if ("title".equalsIgnoreCase(tagName)) {
                                currentTag.setTitle(TagValue);
                            } else if ("description".equalsIgnoreCase(tagName)) {
                                currentTag.setDescription(TagValue);

                                //extract location from description
                                String[] splitString1 = TagValue.split(";", -1);
                                currentTag.setLocation(splitString1[1]);

                                //extract magnitude from description
                                String[] splitString2 = TagValue.split(";", -1);
                                currentTag.setMagnitude(splitString2[4]);

                                //extract depth from description
                                String[] splitString3 = TagValue.split(";", -1);
                                currentTag.setDepth(splitString3[3]);

                            } else if ("link".equalsIgnoreCase(tagName)) {
                                currentTag.setLink(TagValue);
                            } else if ("pubdate".equalsIgnoreCase(tagName)) {
                                currentTag.setDateTimePublished(TagValue);
                                currentTag.setDatePublished(TagValue.substring(5,16));
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
  //           commented out the test below which was used to check that the data parsed correctly
//            for (EarthquakeItem eq : earthquakes) {
//                Log.d(TAG, "--------Earthquake--------");
//                Log.d(TAG, eq.toString());
//            }

        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        }

        return status;
    }
}
/**
 * Created by Catriona Crowe  (S1511579)
 */

package com.catrionacrowe.earthquakeupdates;

public class EarthquakeItem {

    private String title;
    private String description;
    private String link;
    private String datePublished;
    private String category;
    private String geoLat;
    private String geoLong;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGeoLat() {
        return geoLat;
    }

    public void setGeoLat(String geoLat) {
        this.geoLat = geoLat;
    }

    public String getGeoLong() {
        return geoLong;
    }

    public void setGeoLong(String geoLong) {
        this.geoLong = geoLong;
    }

    @Override
    public String toString() {
        return  "Title = " + title + '\n' +
                "Description = " + description + '\n' +
                "Link = " + link + '\n' +
                "Date Published = " + datePublished + '\n' +
                "Category = " + category + '\n' +
                "Latitude = " + geoLat + '\n' +
                "Longitude = " + geoLong + '\n';
    }
}

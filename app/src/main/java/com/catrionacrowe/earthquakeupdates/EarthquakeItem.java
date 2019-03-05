/**
 * Created by Catriona Crowe
 */

package com.catrionacrowe.earthquakeupdates;

public class EarthquakeItem {

    private String title;
    private String description;
    private String link;
    private String pubdate;
    private String category;

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

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "title=" + title + '\n' +
                ", description=" + description + '\n' +
                ", link='" + link + '\n' +
                ", pubdate=" + pubdate + '\n' +
                ", category=" + category + '\n';
    }
}

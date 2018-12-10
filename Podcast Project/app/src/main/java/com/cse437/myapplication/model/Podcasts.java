package com.cse437.myapplication.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Podcasts {
    @SerializedName("artistName")
    @Expose
    private String artistName;
    @SerializedName("feedUrl")
    @Expose
    private String feedUrl;
    @SerializedName("artworkUrl600")
    @Expose
    private String artworkUrl600;
    @SerializedName("trackCount")
    @Expose
    private Integer trackCount;
    @SerializedName("collectionName")
    @Expose
    private String collectionName;

    public Podcasts(String name, String feedURL){
        this.artistName = name;
        this.feedUrl = feedURL;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
    public String getFeedUrl() {
        return feedUrl;
    }

    public void setFeedUrl(String feedUrl) {
        this.feedUrl = feedUrl;
    }
    public String getArtworkUrl600() {
        return artworkUrl600;
    }

    public void setArtworkUrl600(String artworkUrl600) {
        this.artworkUrl600 = artworkUrl600;
    }

    public Integer getTrackCount() {
        return trackCount;
    }

    public void setTrackCount(Integer trackCount) {
        this.trackCount = trackCount;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }


}

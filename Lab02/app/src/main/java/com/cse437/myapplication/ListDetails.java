package com.cse437.myapplication;

public class ListDetails {

    String name;
    int score;
    String timestamp;

    public ListDetails(String name, int score, String timestamp){
        this.name = name;
        this.score = score;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return name + " - " + score + " - " + timestamp;
    }
}

package com.example.firebasecruead;

public class Track {
    private String trackid;
    private String trackname;
    private int trackrating;

    public Track(){}

    public Track(String trackid, String trackname, int trackrating) {
        this.trackid = trackid;
        this.trackname = trackname;
        this.trackrating = trackrating;
    }

    public String getTrackid() {
        return trackid;
    }

    public String getTrackname() {
        return trackname;
    }

    public int getTrackrating() {
        return trackrating;
    }
}

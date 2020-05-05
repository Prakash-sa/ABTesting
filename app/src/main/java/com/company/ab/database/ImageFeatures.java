package com.company.ab.database;

public class ImageFeatures {
    private String imageurl1;
    private String imageurl2;
    private String imagetext1;
    private String imagetext2;
    private String lastdate;
    private int vote;

    public ImageFeatures() {
    }

    public ImageFeatures(String imageurl1, String imageurl2, String lastdate, int vote, String imagetext1,String imagetext2) {
        this.imageurl1 = imageurl1;
        this.imageurl2 = imageurl2;
        this.lastdate = lastdate;
        this.vote = vote;
        this.imagetext1=imagetext1;
        this.imagetext2=imagetext2;
    }

    public String getImageurl1() {
        return imageurl1;
    }

    public void setImageurl1(String imageurl1) {
        this.imageurl1 = imageurl1;
    }

    public String getImageurl2() {
        return imageurl2;
    }

    public void setImageurl2(String imageurl2) {
        this.imageurl2 = imageurl2;
    }

    public String getLastdate() {
        return lastdate;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public String getImagetext1() {
        return imagetext1;
    }

    public void setImagetext1(String imagetext1) {
        this.imagetext1 = imagetext1;
    }

    public String getImagetext2() {
        return imagetext2;
    }

    public void setImagetext2(String imagetext2) {
        this.imagetext2 = imagetext2;
    }
}

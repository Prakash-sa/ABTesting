package com.company.ab.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ImageFeatures implements Serializable {
    private String imageurl1;
    private String imageurl2;
    private String imageDesciption;
    private int lastdate;
    private int upVote;
    private int downVote;
    private int aVote;
    private int bVote;
    private List<String>feedback=new ArrayList<>();
    private String uuid;

    public ImageFeatures() {
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

    public int getLastdate() {
        return lastdate;
    }

    public void setLastdate(int lastdate) {
        this.lastdate = lastdate;
    }

    public String getImageDesciption() {
        return imageDesciption;
    }

    public void setImageDesciption(String imageDesciption) {
        this.imageDesciption = imageDesciption;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getUpVote() {
        return upVote;
    }

    public void setUpVote(int upVote) {
        this.upVote = upVote;
    }

    public int getDownVote() {
        return downVote;
    }

    public void setDownVote(int downVote) {
        this.downVote = downVote;
    }

    public List<String> getFeedback() {
        return feedback;
    }

    public void setFeedback(List<String> feedback) {
        this.feedback = feedback;
    }

    public int getaVote() {
        return aVote;
    }

    public void setaVote(int aVote) {
        this.aVote = aVote;
    }

    public int getbVote() {
        return bVote;
    }

    public void setbVote(int bVote) {
        this.bVote = bVote;
    }
}

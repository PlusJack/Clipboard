package com.example.appdevelopment.clipboard;

import java.util.ArrayList;

public class Clip {
    private String mContents;
    public boolean mFavorite;
    private String mDate;

    public Clip(String contents, String date, boolean saved) {
        mContents = contents;
        mFavorite = saved;
        mDate = date;
    }

    public String getContents() {
        return mContents;
    }

    public boolean isSaved() {
        return mFavorite;
    }

    public String getDate() {
        return mDate;
    }

    public ArrayList<Clip> createClipsList(int numClips) {
        ArrayList<Clip> Clips = new ArrayList<Clip>();

        for (int i = 1; i <= numClips; i++) {
            Clips.add(new Clip(mContents, mDate, mFavorite));
        }

        return Clips;
    }
}
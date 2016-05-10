package com.example.appdevelopment.clipboard;

import java.util.ArrayList;

public class Clip {
    private String mContents;
    private boolean mSaved;
    private String mDate;

    public Clip(String contents, String date, boolean saved) {
        mContents = contents;
        mSaved = saved;
        mDate = date;
    }

    public String getContents() {
        return mContents;
    }

    public boolean isSaved() {
        return mSaved;
    }

    public ArrayList<Clip> createClipsList(int numClips) {
        ArrayList<Clip> Clips = new ArrayList<Clip>();

        for (int i = 1; i <= numClips; i++) {
            Clips.add(new Clip(mContents, mDate, mSaved));
        }

        return Clips;
    }
}
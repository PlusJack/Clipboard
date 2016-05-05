package com.example.appdevelopment.clipboard;

import java.util.ArrayList;

public class Clip {
    private String mContents;
    private boolean mSaved;

    public Clip(String contents, boolean saved) {
        mContents = contents;
        mSaved = saved;
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
            Clips.add(new Clip(mContents, mSaved));
        }

        return Clips;
    }
}
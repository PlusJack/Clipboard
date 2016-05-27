package com.example.appdevelopment.clipboard;

import android.util.Log;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;

/**
 * Created by 17jphillips on 5/27/2016.
 */
public class MyOnFavoriteChangeListener implements MaterialFavoriteButton.OnFavoriteChangeListener {

    private Clip clip;

    public MyOnFavoriteChangeListener(Clip c) {
        this.clip = c;
    }

//    public void reloadStars(){
//        Log.d(String.valueOf(clip), String.valueOf(clip.isSaved())); //literally what is happening here
//        MyAdapter.myViewHolder.favorite.setFavorite(mDataset.get(position).isSaved(), false);
//    }

    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite){
        if (favorite) {
            clip.mFavorite = true;
            Log.d(clip.toString(), " set to true.");
        } else {
            clip.mFavorite = false;
            Log.d(clip.toString(), " is now false");
        }
    }
}

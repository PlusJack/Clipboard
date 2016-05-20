package com.example.appdevelopment.clipboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    Clip clip;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public TextView mDate;
        public MaterialFavoriteButton favorite;
        public ViewHolder(View v) {
                super(v);
            mTextView = (TextView) v.findViewById(R.id.editText);
            mDate = (TextView) v.findViewById(R.id.date);
            favorite = (com.github.ivbaranov.mfb.MaterialFavoriteButton) v.findViewById(R.id.fav);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    private ArrayList<Clip> mDataset;
    public MyAdapter(ArrayList<Clip> myDataset) { // Back in the myActivity class you could do something like:
        mDataset = myDataset;
    }

    // Create new views (usedby the layout manager). See where it's linked in #1 above.
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.clip_object, parent, false);
        // set the view's size, margins, paddings and layout parameters as desired
        // ...
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager) See where it's linked in #1 above.
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        clip = mDataset.get(position);
        holder.mTextView.setEllipsize(TextUtils.TruncateAt.END);
        holder.mTextView.setMaxLines(2);
        holder.mTextView.setText(clip.getContents());

            Log.d(String.valueOf(clip), String.valueOf(mDataset.get(position).isSaved())); //literally what is happening here
                holder.favorite.setFavorite(mDataset.get(position).isSaved(), false);

        holder.mDate.setText(clip.getDate());


        holder.favorite.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        if (favorite) {
                            clip.mFavorite = true;
                            Log.d(clip.toString(), " set to true.");
                        } else {
                            clip.mFavorite = false;
                            Log.d(clip.toString(), " is now false");
                        }
                    }
                });

        holder.favorite.setOnFavoriteAnimationEndListener(
                new MaterialFavoriteButton.OnFavoriteAnimationEndListener() {
                    @Override
                    public void onAnimationEnd(MaterialFavoriteButton buttonView, boolean favorite) {
                        //
                    }
                });

    }

    public String getContents(int position){ //Method to return the contents of any given clip
        Clip clip = mDataset.get(position);
        return clip.getContents();
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
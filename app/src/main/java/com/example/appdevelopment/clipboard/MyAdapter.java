package com.example.appdevelopment.clipboard;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myViewHolder> {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class myViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public TextView mDate;
        public MaterialFavoriteButton favorite;
        public View line;
        public myViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.editText);
            mDate = (TextView) v.findViewById(R.id.date);
            favorite = (com.github.ivbaranov.mfb.MaterialFavoriteButton) v.findViewById(R.id.fav);
            line = v.findViewById(R.id.line);


        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    private ArrayList<Clip> mDataset;
    public MyAdapter(ArrayList<Clip> myDataset) { // Back in the myActivity class you could do something like:
        mDataset = myDataset;
    }

    // Create new views (usedby the layout manager). See where it's linked in #1 above.
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.clip_object, parent, false);
        // set the view's size, margins, paddings and layout parameters as desired
        // ...
        myViewHolder vh = new myViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager) See where it's linked in #1 above.
    @Override
    public void onBindViewHolder(final myViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Clip clip = mDataset.get(position);
        Clip newest =  mDataset.get(mDataset.size() -1);

        holder.mTextView.setEllipsize(TextUtils.TruncateAt.END);
        holder.mTextView.setMaxLines(2);
        holder.mTextView.setText(clip.getContents());

        // Only place logging happens. ==> This method is called for every position of the mDataset array - every time we pull/refresh.
        Log.d(String.valueOf(clip), String.valueOf(clip.isSaved())); //literally what is happening here
        holder.favorite.setFavorite(clip.isSaved(), false);
        holder.mDate.setText(clip.getDate());

        if (clip == newest) {
            holder.line.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        if (clip != newest){
            holder.line.setBackgroundColor(Color.parseColor("#EEEEEE"));
        }

        holder.favorite.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        Clip newClip = mDataset.get(holder.getAdapterPosition());

                        if (favorite) {
                            newClip.mFavorite = true;
                            Log.d(newClip.toString(), " set to true.");
                        } else {
                            newClip.mFavorite = false;
                            Log.d(newClip.toString(), " is now false");
                        }
                    }
                });

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
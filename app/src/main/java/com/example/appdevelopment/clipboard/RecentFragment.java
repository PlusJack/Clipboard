package com.example.appdevelopment.clipboard;

import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;;import java.util.ArrayList;
import java.util.List;

public class RecentFragment extends Fragment {

    public RecentFragment() {
        // Required empty public constructor
    }

    public static ArrayList<String> getRecentList() {
        return recentList;
    }

    private void addItem(String item) {
        recentList.add(item); //recentList is an ArrayList
        List<String> list = this.getRecentList(); //getRecentList returns recentList
        String joined = TextUtils.join(", ", list); //Converts strings to comma separated strings
        MyAdapter ma = new MyAdapter(new String[]{joined});
        ma.notifyDataSetChanged();
    }

    public void readFromClipboard(View v) { //Function(?) to get item currently on clipboard and make sure it's plain text
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard.hasPrimaryClip()) {
            android.content.ClipDescription description = clipboard.getPrimaryClipDescription();
            android.content.ClipData data = clipboard.getPrimaryClip();
            if (data != null && description != null && description.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                String contents = String.valueOf(data.getItemAt(0).getText());
                addItem(contents);
                Log.d("readFromClipboard", "Working");
            }
        }
    }



    public static ArrayList<String> recentList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_blank, container, false);

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);
        List<String> list = this.getRecentList();
        String joined = TextUtils.join(", ", list); //Converts the list into a String[]
        MyAdapter adapter = new MyAdapter(new String[]{joined}); //Connects the String[] to the adapter
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }

}

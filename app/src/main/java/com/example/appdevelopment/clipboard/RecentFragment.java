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
    public ArrayList<String> recentList;

    public RecentFragment() {
        // Required empty public constructor
    }

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
        rv.setHasFixedSize(false);
        final MyAdapter adapter = new MyAdapter();//Connects the String[] to the adapter
        recentList.add(MainActivity.readFromClipboard());
        adapter.addItem(recentList.get(0), 0);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        Log.d("onCreateRecentFragment", "Working");
        return rootView;

    }

}

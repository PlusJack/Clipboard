package com.example.appdevelopment.clipboard;

import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.annotation.UiThread;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

   private RecyclerView mRecyclerView;
   private RecyclerView.Adapter mAdapter;
   private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout refreshLayout;


    public ArrayList<Clip> clipList = new ArrayList<Clip>();

    public Boolean checkDuplicate(String toMatchString) {

        for (int i = 0; i < clipList.size(); i++) {
            Clip clip = clipList.get(i);
            if (clip.getContents().equals(toMatchString)) {
                return true;
            }
        }
        return false;
    }


    public void readFromClipboard() { //get item currently on clipboard and make sure it's plain text, then add to recentList & refresh Adapter
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard.hasPrimaryClip()) {
            android.content.ClipDescription description = clipboard.getPrimaryClipDescription();
            android.content.ClipData data = clipboard.getPrimaryClip();
            if (data != null && description != null && (description.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN) || description.hasMimeType(ClipDescription.MIMETYPE_TEXT_HTML))) {
                String contents = String.valueOf(data.getItemAt(0).getText());
                if (!checkDuplicate(contents)){ //make sure item isn't the same as the last item
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy, hh:mm");
                    String date = df.format(Calendar.getInstance().getTime());
                    Log.d("New clip ", contents);
                    clipList.add(0, new Clip(contents, date, false));
                    mAdapter.notifyDataSetChanged();
                }
                else{
                    Log.d(contents, " already exists in recentList.");
                }

            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        refreshLayout.setColorSchemeResources(R.color.refresh_1);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                readFromClipboard();
                refreshLayout.setRefreshing(false);
            }
        });

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        //ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        //viewPager.setAdapter(pagerAdapter);

        // Give the TabLayout the ViewPager
        //TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        //tabLayout.setupWithViewPager(viewPager);

        // Iterate over all tabs and set the custom view
        //for (int i = 0; i < tabLayout.getTabCount(); i++) {
        //    TabLayout.Tab tab = tabLayout.getTabAt(i);
        //    tab.setCustomView(pagerAdapter.getTabView(i));
        //}

        //getSupportActionBar().setElevation(0); //Removes the shadow below the actionbar

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recycler_view);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this); // Will visually update content received by RV.
        mRecyclerView.setLayoutManager(mLayoutManager); // Connects LayoutManager to RecyclerView

        // specify an adapter (see also next example)
            /* See suggestion in #3 below for example of setting up data source. */
        mAdapter = new MyAdapter(clipList); // Will bring in content from data source (e.g. String array) to local RV.
        mRecyclerView.setAdapter(mAdapter); // Connects custom MyAdapter to RecyclerView.

    }

    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        TextView v = (TextView) getLayoutInflater().from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        // set the view's size, margins, paddings and layout parameters as desired
        // ...
        MyAdapter.ViewHolder vh = new MyAdapter.ViewHolder(v);
        return vh;
    }
}



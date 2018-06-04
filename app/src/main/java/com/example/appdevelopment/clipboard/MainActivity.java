package com.example.appdevelopment.clipboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ViewGroup;
import android.widget.TextView;



import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity {

   private RecyclerView mRecyclerView;
   private RecyclerView.Adapter mAdapter;
   private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout refreshLayout;




    public ArrayList<Clip> clipList = new ArrayList<Clip>();
    private ArrayList<File> fileList = new ArrayList<File>();


    public void readFromClipboardCache() { //get item currently on clipboard and make sure it's plain text, then add to recentList & refresh Adapter
        File dir = new File(getFilesDir().toString()+"/ClipStorage");
        File listFiles[] = dir.listFiles();

        if (listFiles != null && listFiles.length > 0){ //if the file isn't empty and more than 0 files
            for (int i = 0; i < listFiles.length; i++){ //iterate through the list of files
                boolean isTwoEqual = false;
                for (int j = 0; j < fileList.size(); j++){ //iterate through the ArrayList of files, if the file is = to any of the files already in the ArrayList, ignore it
                    try {
                        File file1 = listFiles[i];
                        File file2 = fileList.get(j);
                        isTwoEqual = FileUtils.contentEquals(file1, file2);
                        if (isTwoEqual) {
                            break;
                        }
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }

                }
                if (!isTwoEqual){
                    fileList.add(listFiles[i]);
                }
            }
        }
        for (int i = 0; i < fileList.size(); i++){ //convert items in the ArrayList of files to Clip objects
            File curr = fileList.get(i);
            String dateTime = curr.getName();
            try {
                Scanner scn = new Scanner(curr);
                String contents = scn.nextLine();
                Boolean isFavorite = Boolean.valueOf(scn.nextLine());
                Boolean isCopy = false;
                if (clipList.size() > 0){
                    for (int j = 0; j < clipList.size(); j++){
                        if (clipList.get(j).getContents().equals(contents)){
                            isCopy = true;
                            break;
                        }
                    }
                    if (!isCopy){
                        clipList.add(new Clip(contents, dateTime, isFavorite));
                        mAdapter.notifyDataSetChanged();
                    }
                }
                else {
                    clipList.add(new Clip(contents, dateTime, isFavorite));
                    mAdapter.notifyDataSetChanged();
                }

            }
            catch (IOException e) {
                e.printStackTrace();
            }


        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, CBWatcherService.class));

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        refreshLayout.setColorSchemeResources(R.color.refresh_1);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                readFromClipboardCache();
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

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_recycler_view);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager (this); // Will visually update content received by RV.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));; // Connects LayoutManager to RecyclerView

        // specify an adapter (see also next example)
            /* See suggestion in #3 below for example of setting up data source. */
        mAdapter = new MyAdapter(clipList); // Will bring in content from data source (e.g. String array) to local RV.
        mRecyclerView.setAdapter(mAdapter); // Connects custom MyAdapter to RecyclerView.


        SwipeController swipeController = new SwipeController();
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(mRecyclerView);

        readFromClipboardCache();
    }

    public MyAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        TextView v = (TextView) getLayoutInflater().from(parent.getContext())
                .inflate(R.layout.clip_object, parent, false);
        // set the view's size, margins, paddings and layout parameters as desired
        // ...
        MyAdapter.myViewHolder vh = new MyAdapter.myViewHolder(v);
        return vh;
    }
}



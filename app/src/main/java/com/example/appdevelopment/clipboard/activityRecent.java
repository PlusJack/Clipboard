package com.example.appdevelopment.clipboard;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Jack on 2/7/2016.
 */

//Currently having issues dealing with ListViews within a fragment.

public class activityRecent extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) { //Once the activity is created it sets these items to the list and defines the adapter used
        super.onActivityCreated(savedInstanceState);
        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

    }

    public void onViewCreated(View view, Bundle savedInstanceState) { //*should* connect the listed items and the adapter to the listRecent ListView in recent_fragment.xml
        super.onViewCreated(view, savedInstanceState);
        ListView list = (ListView) getView().findViewById(R.id.listRecent);
    }

    }

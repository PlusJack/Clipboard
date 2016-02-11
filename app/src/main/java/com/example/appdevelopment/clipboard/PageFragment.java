package com.example.appdevelopment.clipboard;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

// In this case, the fragment displays simple text based on the page
public class PageFragment extends Activity {

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        switch (mPage) {
            case 1:
                ArrayList<String> clipboard = new ArrayList<>();
                clipboard.add("Thing1");
                clipboard.add("Thing2");
                View view1 = inflater.inflate(R.layout.recent_fragment, null);
                ListView listView = (ListView)view1.findViewById(R.id.recentList);
                listView.setAdapter(new UserItemAdapter(PageFragment.this, R.layout.recent_item, clipboard));
                ((ViewPager) collection).addView(view1, 0);
                return view1;
            case 2:
                View view2 = inflater.inflate(R.layout.starred_fragment, container, false);
                return view2;
            default:
                View view3 = inflater.inflate(R.layout.recent_fragment, container, false);
                return view3;
        }

        /*View view = inflater.inflate(R.layout.recent_fragment, container, false);
        TextView textView = (TextView) view;
        textView.setText("Fragment #" + mPage);
        return view;*/
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Recents, R.layout.recent_item);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }

}
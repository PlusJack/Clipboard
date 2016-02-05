package com.example.appdevelopment.clipboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

// In this case, the fragment displays simple text based on the page
public class PageFragment extends Fragment {
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
                View view1 = inflater.inflate(R.layout.recent_fragment, container, false);
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
}
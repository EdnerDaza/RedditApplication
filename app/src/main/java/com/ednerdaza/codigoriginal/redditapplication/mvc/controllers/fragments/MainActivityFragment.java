package com.ednerdaza.codigoriginal.redditapplication.mvc.controllers.fragments;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ednerdaza.codigoriginal.redditapplication.R;

/**
 * Created by administrador on 16/02/17.
 */
public class MainActivityFragment extends Fragment {

    private CoordinatorLayout mCoordinatorLayout;
    private RecyclerView mRecyclerView;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

       /* mCoordinatorLayout = (CoordinatorLayout)view.findViewById(R.id.coordinatorLayout);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.rv_root);
        mRecyclerView.setHasFixedSize(true);*/

        return view;
    }
}

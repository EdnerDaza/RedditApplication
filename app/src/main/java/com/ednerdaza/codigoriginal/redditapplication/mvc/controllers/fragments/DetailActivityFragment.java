package com.ednerdaza.codigoriginal.redditapplication.mvc.controllers.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ednerdaza.codigoriginal.redditapplication.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    //region CONSTRUCTOR

        public DetailActivityFragment() {
        }

    //endregion

    //region METODOS FRAGMENT

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_detail, container, false);
        }

    //endregion

}

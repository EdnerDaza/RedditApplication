package com.ednerdaza.codigoriginal.redditapplication.mvc.controllers.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ednerdaza.codigoriginal.redditapplication.R;

/**
 * Created by administrador on 16/02/17.
 */
public class MainActivityFragment extends Fragment {

    //region CONSTRUCTOR

        public MainActivityFragment() {
        }

    //endregion

    //region METODOS DEL FRAGMENT

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_main, container, false);
            return view;
        }

    //endregion

}

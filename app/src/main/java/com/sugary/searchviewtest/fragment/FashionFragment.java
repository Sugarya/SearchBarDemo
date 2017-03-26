package com.sugary.searchviewtest.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sugary.searchviewtest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FashionFragment extends Fragment {


    public FashionFragment() {
        // Required empty public constructor
    }

    public static FashionFragment newInstance() {

        Bundle args = new Bundle();

        FashionFragment fragment = new FashionFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fashion, container, false);
    }

}

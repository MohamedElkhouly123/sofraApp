package com.example.sofra.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;


import com.example.sofra.R;

import butterknife.ButterKnife;

public class ContactUsFragment extends BaSeFragment {




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_main, container, false);
        ButterKnife.bind(this, root);
//
        return root;
    }



}
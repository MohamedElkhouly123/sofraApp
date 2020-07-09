package com.example.sofra.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sofra.utils.MyApplication;
import com.example.sofra.view.activity.BaseActivity;
import com.example.sofra.view.activity.HomeCycleActivity;

public class BaSeFragment extends Fragment implements MyApplication.IMemoryInfo{


    public BaseActivity baseActivity;
    public HomeCycleActivity homeCycleActivity;
    protected BaSeFragment child2;


    public void setUpActivity() {
        baseActivity = (BaseActivity) getActivity();
        baseActivity.baseFragment = this;
        try {
            homeCycleActivity = (HomeCycleActivity) getActivity();
        } catch (Exception e) {

        }
    }
//onBack​
    public void onBack() {
        baseActivity.superBackPressed();
    }
//​ onStart
    @Override
    public void onStart() {
        super.onStart();
        setUpActivity();
    }
//​
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setUpActivity();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            if (child2 != null)
                MyApplication.unregisterMemoryListener(child2);
        } catch (Exception e) {

        }
    }

    @Override
    public void goodTimeToReleaseMemory() {

    }
}

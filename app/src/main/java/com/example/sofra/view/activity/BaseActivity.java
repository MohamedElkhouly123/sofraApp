package com.example.sofra.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sofra.utils.MyApplication;
import com.example.sofra.view.fragment.BaSeFragment;

public class BaseActivity extends AppCompatActivity implements MyApplication.IMemoryInfo {

    public BaSeFragment baseFragment;
    protected BaseActivity child;

//    ​

    @Override
    public void onBackPressed() {
        baseFragment.onBack();
    }
//​


    public void superBackPressed() {
        super.onBackPressed();
    }



    @Override
    protected void onStop() {
        super.onStop();
        try {
            if (child != null)
                MyApplication.unregisterMemoryListener(child);
        } catch (Exception e) {

        }
    }
    @Override
    public void goodTimeToReleaseMemory() {

    }
}


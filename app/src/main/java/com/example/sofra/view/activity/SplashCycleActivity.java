package com.example.sofra.view.activity;

import android.os.Bundle;
import android.os.Handler;

import com.example.sofra.R;
import com.example.sofra.utils.MyApplication;
import com.example.sofra.view.fragment.splashCycle.SplashFragment;

import static com.example.sofra.utils.HelperMethod.replaceFragment;

public class SplashCycleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_cycle);
        child = this;
        replaceFragment(getSupportFragmentManager(), R.id.splash_activity_fram, new SplashFragment());

    }

    @Override
    protected void onPause() {
        MyApplication.registerMemoryListener(this);
        super.onPause();
    }

    @Override
    public void goodTimeToReleaseMemory() {
        super.goodTimeToReleaseMemory();
//remove your Cache etc here
    }
    //--NO Need because parent implementation will be called first, just for the sake of clarity
    @Override
    protected void onStop() {
        super.onStop();
        try {
            if (null != child)
                MyApplication.unregisterMemoryListener(child);
        } catch (Exception e) {

        }}



}

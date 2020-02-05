package com.example.sofra.view.activity;

import android.os.Bundle;
import android.os.Handler;

import com.example.sofra.R;
import com.example.sofra.view.fragment.splashCycle.SplashFragment;

import static com.example.sofra.utils.HelperMethod.replaceFragment;

public class SplashCycleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_cycle);

                            replaceFragment(getSupportFragmentManager(), R.id.splash_activity_fram, new SplashFragment());


    }





}

package com.example.sofra.view.activity;

import android.os.Bundle;


import com.example.sofra.R;
import com.example.sofra.view.fragment.userCycle.RestaurantAndClientLoginFragment;

import static com.example.sofra.utils.HelperMethod.replaceFragment;


public class UserCycleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cycle);
        replaceFragment(getSupportFragmentManager(), R.id.user_activity_fram, new RestaurantAndClientLoginFragment());
    }

}

package com.example.sofra.view.activity;

import android.os.Bundle;


import com.example.sofra.R;
import com.example.sofra.utils.MyApplication;
import com.example.sofra.view.fragment.splashCycle.SplashFragment;
import com.example.sofra.view.fragment.userCycle.RestaurantAndClientLoginFragment;

import static com.example.sofra.data.local.SharedPreferencesManger.CLIENT;
import static com.example.sofra.data.local.SharedPreferencesManger.LoadData;
import static com.example.sofra.utils.HelperMethod.replaceFragment;


public class UserCycleActivity extends BaseActivity {
    public String ISCLIENT = SplashFragment.getClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cycle);
        child = this;
        if(ISCLIENT==null){
            ISCLIENT = LoadData(this, CLIENT);
        }
        replaceFragment(getSupportFragmentManager(), R.id.user_activity_fram, new RestaurantAndClientLoginFragment());
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

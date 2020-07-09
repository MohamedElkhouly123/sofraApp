package com.example.sofra.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.sofra.R;
import com.example.sofra.data.model.clientLogin.ClientData;
import com.example.sofra.utils.MyApplication;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.home.FoodMenueFragment;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.home.HomeFragment;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.home.RestaurantCommissionFragment;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.more.MoreFragment;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.notificationsMenues.MenuesFragment;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.notificationsMenues.NotificationsFragment;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.update_my_info.RestaurantAndClientEditProfileFragment;
import com.example.sofra.view.fragment.splashCycle.SplashFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.sofra.data.local.SharedPreferencesManger.CLIENT;
import static com.example.sofra.data.local.SharedPreferencesManger.LoadData;
import static com.example.sofra.data.local.SharedPreferencesManger.LoadUserData;
import static com.example.sofra.utils.HelperMethod.replaceFragment;
import static com.example.sofra.utils.HelperMethod.showToast;


public class HomeCycleActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    public HomeFragment homeFragment;
    @BindView(R.id.toolbar_notification)
    ImageButton toolbarNotification;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_shopping_cart)
    ImageButton toolbarShoppingCart;
    @BindView(R.id.toolbar_calculator)
    ImageButton toolbarCalculator;
    public BottomNavigationView navView;
    public String ISCLIENT = SplashFragment.getClient();
    private ClientData clientData;
    private boolean goLogin=false;
    private boolean backFromLogin=false;
    public HomeCycleActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cycle);
        ButterKnife.bind(this);
        child = this;
        homeFragment=new HomeFragment();
        clientData = LoadUserData(this);
        replaceFragment(getSupportFragmentManager(), R.id.home_activity_fram,new HomeFragment());
        navView = (BottomNavigationView) findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);
        if (ISCLIENT=="true") {
            toolbarShoppingCart.setVisibility(View.VISIBLE);
        }  if(ISCLIENT=="false"){
            toolbarCalculator.setVisibility(View.VISIBLE);

        }

    }

    private void displayView(int position) {
        switch (position) {
            case 0:
                Intent intentHome = new Intent(HomeCycleActivity.this,HomeCycleActivity.class);
                startActivity(intentHome);
                break;
            case 1:
                Intent intentHome2 = new Intent(HomeCycleActivity.this,HomeCycleActivity.class);
                startActivity(intentHome2);
                break;}}
    public void setNavigation(String visibility) {
        navView = (BottomNavigationView) findViewById(R.id.nav_view);
        if (visibility.equals("v")) {
            navView.setVisibility(View.VISIBLE);
        } else if (visibility.equals("g")) {
            navView.setVisibility(View.GONE);
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navigation_home) {
            replaceFragment(getSupportFragmentManager(), R.id.home_activity_fram,new HomeFragment());
        } else if (id == R.id.navigation_update_my_info) {
            if(clientData!=null) {
                replaceFragment(getSupportFragmentManager(), R.id.home_activity_fram, new RestaurantAndClientEditProfileFragment());
            }else {
                goToRegisterFirst(this);
                goLogin = true;
                backFromLogin=true;
            }
        } else if (id == R.id.navigation_menues) {
            replaceFragment(getSupportFragmentManager(), R.id.home_activity_fram, new MenuesFragment());

        } else if (id == R.id.navigation_more_setting) {
            replaceFragment(getSupportFragmentManager(), R.id.home_activity_fram, new MoreFragment());
        }


        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        MyApplication.registerMemoryListener(this);
        if (goLogin && LoadUserData(this) != null) {
              goLogin = false;
            replaceFragment(getSupportFragmentManager(), R.id.home_activity_fram, new RestaurantAndClientEditProfileFragment());
        }
        else if(backFromLogin){
            backFromLogin=false;
            replaceFragment(getSupportFragmentManager(), R.id.home_activity_fram, new HomeFragment());
            navView.setSelectedItemId(R.id.navigation_home);

        }
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

    public void goToRegisterFirst(Activity activity){
        showToast(activity, "Go To Register or Login First");
        Intent intent2 = new Intent(HomeCycleActivity.this, UserCycleActivity.class);
        startActivity(intent2);
    }

//    @Override
//    public void onBackPressed() {
//        if (navView.getSelectedItemId() == R.id.navigation_menues) {
//            navView.setSelectedItemId(R.id.na);
//        }    }

    @OnClick({R.id.toolbar_notification, R.id.toolbar_shopping_cart, R.id.toolbar_calculator})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_notification:
                replaceFragment(getSupportFragmentManager(), R.id.home_activity_fram, new NotificationsFragment());
                navView.getMenu().getItem(0).setChecked(true);
                break;
            case R.id.toolbar_shopping_cart:
                replaceFragment(getSupportFragmentManager(), R.id.home_activity_fram, new FoodMenueFragment());
                navView.getMenu().getItem(0).setChecked(true);
                break;
            case R.id.toolbar_calculator:
                replaceFragment(getSupportFragmentManager(), R.id.home_activity_fram, new RestaurantCommissionFragment());
                navView.getMenu().getItem(0).setChecked(true);
                break;
        }
    }
}

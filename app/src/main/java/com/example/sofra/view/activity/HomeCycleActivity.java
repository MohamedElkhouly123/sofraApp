package com.example.sofra.view.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sofra.R;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.home.HomeFragment;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.more.MoreFragment;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.notificationsMenues.MenuesFragment;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.update_my_info.ProfileFragment;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.update_my_info.RestaurantAndClientEditProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.sofra.data.local.SharedPreferencesManger.CLIENT;
import static com.example.sofra.data.local.SharedPreferencesManger.LoadData;
import static com.example.sofra.utils.HelperMethod.replaceFragment;


public class HomeCycleActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.toolbar_notification)
    ImageButton toolbarNotification;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_shopping_cart)
    ImageButton toolbarShoppingCart;
    @BindView(R.id.toolbar_calculator)
    ImageButton toolbarCalculator;
    private BottomNavigationView navView;
    public String ISCLIENT = LoadData(this, CLIENT);

    public HomeCycleActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cycle);
        ButterKnife.bind(this);
        replaceFragment(getSupportFragmentManager(), R.id.home_activity_fram, new HomeFragment());

        navView = (BottomNavigationView) findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);
        if (ISCLIENT=="true") {
            toolbarShoppingCart.setVisibility(View.VISIBLE);
        } else {
            toolbarCalculator.setVisibility(View.VISIBLE);

        }

    }

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
            replaceFragment(getSupportFragmentManager(), R.id.home_activity_fram, new HomeFragment());
        } else if (id == R.id.navigation_update_my_info) {
            replaceFragment(getSupportFragmentManager(), R.id.home_activity_fram, new RestaurantAndClientEditProfileFragment());
        } else if (id == R.id.navigation_menues) {
            replaceFragment(getSupportFragmentManager(), R.id.home_activity_fram, new MenuesFragment());

        } else if (id == R.id.navigation_more_setting) {
            replaceFragment(getSupportFragmentManager(), R.id.home_activity_fram, new MoreFragment());
        }


        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @OnClick({R.id.toolbar_notification, R.id.toolbar_shopping_cart, R.id.toolbar_calculator})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_notification:
                break;
            case R.id.toolbar_shopping_cart:
                break;
            case R.id.toolbar_calculator:
                break;
        }
    }
}

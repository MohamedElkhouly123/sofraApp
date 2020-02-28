package com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.notificationsMenues;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.example.sofra.R;
import com.example.sofra.adapter.MyTabbedAdapter;
import com.example.sofra.view.fragment.BaSeFragment;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.home.HomeFragment;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.more.MoreFragment;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.sofra.utils.HelperMethod.replaceFragment;


public class MenuesFragment extends BaSeFragment {


    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
//    public String ISCLIENT = LoadData(getActivity(), CLIENT);
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_menues, container, false);
        ButterKnife.bind(this, root);
        setUpActivity();
//        if (ISCLIENT=="true") {
            tabLayout.addTab(tabLayout.newTab().setText("طلبات جديدة"));
            tabLayout.addTab(tabLayout.newTab().setText("طلبات حالية"));
            tabLayout.addTab(tabLayout.newTab().setText("طلبات سابقة"));
//        } else {
//            tabLayout.addTab(tabLayout.newTab().setText("طلبات جديدة"));
//            tabLayout.addTab(tabLayout.newTab().setText("طلبات حالية"));
//            tabLayout.addTab(tabLayout.newTab().setText("طلبات سابقة"));
//        }

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        MyTabbedAdapter adapter = new MyTabbedAdapter(this, getChildFragmentManager(),
                tabLayout.getTabCount());
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {


            }
        });
        return root;
    }

    @Override
    public void onBack() {
        homeCycleActivity.navView.getMenu().getItem(0).setChecked(true);
        super.onBack();
    }


}
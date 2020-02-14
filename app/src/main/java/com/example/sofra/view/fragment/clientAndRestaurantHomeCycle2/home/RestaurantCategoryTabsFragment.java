package com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.example.sofra.R;
import com.example.sofra.adapter.CategoryTabbedAdapter;
import com.example.sofra.view.fragment.BaSeFragment;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantCategoryTabsFragment extends BaSeFragment {


    @BindView(R.id.restaurant_category_tabLayout)
    TabLayout restaurantCategoryTabLayout;
    @BindView(R.id.restaurant_category_viewPager)
    ViewPager restaurantCategoryViewPager;
//    restaurantCategoryViewPager
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home_retaurant_category_tabs, container, false);
        ButterKnife.bind(this, root);
        restaurantCategoryTabLayout.addTab(restaurantCategoryTabLayout.newTab().setText("قائمة الطعام"));
        restaurantCategoryTabLayout.addTab(restaurantCategoryTabLayout.newTab().setText("التعليقات والتقييم"));
        restaurantCategoryTabLayout.addTab(restaurantCategoryTabLayout.newTab().setText("معلومات المتجر"));


        restaurantCategoryTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        CategoryTabbedAdapter adapter = new CategoryTabbedAdapter(this, getChildFragmentManager(),
                restaurantCategoryTabLayout.getTabCount());
//        restaurantCategoryTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        restaurantCategoryViewPager.setAdapter(adapter);
        restaurantCategoryViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(restaurantCategoryTabLayout));
        restaurantCategoryTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                restaurantCategoryViewPager.setCurrentItem(tab.getPosition());
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


}
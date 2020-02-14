package com.example.sofra.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.home.FoodMenueFragment;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.home.HomeFragment;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.home.RatingAndCommentsFragment;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.home.RestaurantCategoryTabsFragment;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.home.RestaurantInformationsFragment;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.notificationsMenues.MenuesFragment;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.notificationsMenues.SubMenuesFragment;

public class CategoryTabbedAdapter extends FragmentPagerAdapter {
    private RestaurantCategoryTabsFragment context2;
    private int totalTabs;
    private FoodMenueFragment foodMenueFragment= new FoodMenueFragment();
    public CategoryTabbedAdapter(RestaurantCategoryTabsFragment c, FragmentManager fm, int totalTabs) {
        super(fm);
        context2 = c;
        this.totalTabs = totalTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return foodMenueFragment;

            case 1:
                return new RatingAndCommentsFragment();
            case 2:
                return new RestaurantInformationsFragment();

            default:
                return new RestaurantCategoryTabsFragment();
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}

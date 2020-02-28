package com.example.sofra.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.notificationsMenues.MenuesFragment;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.notificationsMenues.SubMenuesFragment;


public class MyTabbedAdapter extends FragmentPagerAdapter {
    private MenuesFragment context;
    private int totalTabs;
    private SubMenuesFragment subMenuesFragment= new SubMenuesFragment();
    public MyTabbedAdapter(MenuesFragment c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                subMenuesFragment.SUBMENUE= 1;
                return new SubMenuesFragment();

            case 1:
                subMenuesFragment.SUBMENUE= 2;
                return new SubMenuesFragment();
            case 2:
                subMenuesFragment.SUBMENUE= 3;
                return new SubMenuesFragment();

            default:
                return new MenuesFragment();
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}

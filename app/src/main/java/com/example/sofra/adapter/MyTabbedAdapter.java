package com.example.sofra.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.home.HomeFragment;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.notificationsMenues.MenuesFragment;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.notificationsMenues.SubMenuesFragment;

import static com.example.sofra.data.local.SharedPreferencesManger.SaveData;

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
                return subMenuesFragment;

            case 1:
                subMenuesFragment.SUBMENUE= 2;
                return subMenuesFragment;
            case 2:
                subMenuesFragment.SUBMENUE= 3;
                return subMenuesFragment;

            default:
                return new MenuesFragment();
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}

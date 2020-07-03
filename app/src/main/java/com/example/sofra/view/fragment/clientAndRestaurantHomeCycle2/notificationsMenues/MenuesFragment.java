package com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.notificationsMenues;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.sofra.R;
import com.example.sofra.adapter.MyTabbedAdapter;
import com.example.sofra.view.activity.UserCycleActivity;
import com.example.sofra.view.fragment.BaSeFragment;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.home.HomeFragment;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.more.MoreFragment;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.sofra.data.local.SharedPreferencesManger.CLIENT;
import static com.example.sofra.data.local.SharedPreferencesManger.LoadData;
import static com.example.sofra.data.local.SharedPreferencesManger.LoadUserData;
import static com.example.sofra.utils.HelperMethod.replaceFragment;
import static com.example.sofra.utils.HelperMethod.showToast;


public class MenuesFragment extends BaSeFragment {


//    @BindView(R.id.tabLayout)
    public static TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private boolean goLogin = false;

//    public String ISCLIENT = LoadData(getActivity(), CLIENT);

    private SubMenuesFragment subMenuesFragment = new SubMenuesFragment();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_menues, container, false);
        ButterKnife.bind(this, root);
        setUpActivity();
        if (LoadUserData(getActivity()) == null) {
            goToRegisterFirst(getActivity());
            goLogin = true;
        }else {
            tabLayout = (TabLayout) root.findViewById(R.id.tabLayout);
//        getActivity().getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
//            @Override
//            public void onBackStackChanged() {
//                if (homeCycleActivity.navView.getSelectedItemId() == R.id.navigation_menues&&onBack) {
//                    homeCycleActivity.navView.setSelectedItemId(R.id.navigation_home);
//        }
//            }
//        });
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
//        showToast(getActivity(), String.valueOf(tabLayout.getSelectedTabPosition()));

            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
//                showToast(getActivity(), String.valueOf(tab.getPosition()));
//                showToast(getActivity(), String.valueOf(tabLayout.getSelectedTabPosition()));

//                subMenuesFragment.SUBMENUE=tab.getPosition();

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {


                }
            });
        }
        return root;
    }


    private void goToRegisterFirst(Activity activity) {
        showToast(activity, "Go To Register or Login First");
        Intent intent2 = new Intent(getActivity(), UserCycleActivity.class);
        getActivity().startActivity(intent2);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (goLogin && LoadUserData(getActivity()) != null) {
            goLogin = false;
            replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, new MenuesFragment());
            homeCycleActivity.navView.setSelectedItemId(R.id.navigation_menues);
        }
    }



    @Override
    public void onBack() {
        replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, new HomeFragment());
        homeCycleActivity.navView.getMenu().getItem(0).setChecked(true);
    }


}
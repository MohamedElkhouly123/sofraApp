package com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.sofra.R;
import com.example.sofra.view.fragment.BaSeFragment;

import butterknife.ButterKnife;

import static com.example.sofra.utils.HelperMethod.replaceFragment;

public class FoodMenueFragment extends BaSeFragment {




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home_food_menu, container, false);
        ButterKnife.bind(this, root);
//
        return root;
    }


    @Override
    public void onBack() {
        replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, homeCycleActivity.homeFragment);
        homeCycleActivity.navView.getMenu().getItem(0).setChecked(true);
    }
}
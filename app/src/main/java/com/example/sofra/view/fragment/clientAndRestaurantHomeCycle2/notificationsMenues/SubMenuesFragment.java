package com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.notificationsMenues;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.sofra.R;
import com.example.sofra.view.fragment.BaSeFragment;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.home.HomeFragment;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.more.ContactWithUsFragment;

import butterknife.ButterKnife;

import static com.example.sofra.utils.HelperMethod.replaceFragment;
import static com.example.sofra.utils.HelperMethod.replaceFragmentWithAnimation;

public class SubMenuesFragment extends BaSeFragment {


    public static int SUBMENUE ;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_submenues, container, false);
        ButterKnife.bind(this, root);
//

        return root;
    }
    @Override
    public void onBack() {
        replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, new HomeFragment());
        homeCycleActivity.navView.getMenu().getItem(0).setChecked(true);
    }


}
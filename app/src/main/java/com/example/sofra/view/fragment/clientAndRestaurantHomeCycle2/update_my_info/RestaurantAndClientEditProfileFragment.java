package com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.update_my_info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.sofra.R;
import com.example.sofra.view.fragment.BaSeFragment;
import com.example.sofra.view.fragment.userCycle.RestaurantAndClientLoginFragment;

import butterknife.ButterKnife;

import static com.example.sofra.utils.HelperMethod.replaceFragment;


public class RestaurantAndClientEditProfileFragment extends BaSeFragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_restaurant_and_client_edit_profile, container, false);
        ButterKnife.bind(this, root);


        return root;
    }


    @Override
    public void onBack() {
        replaceFragment(getActivity().getSupportFragmentManager(), R.id.user_activity_fram, new RestaurantAndClientLoginFragment());

    }



}
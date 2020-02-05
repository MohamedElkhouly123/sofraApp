package com.example.sofra.view.fragment.userCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.sofra.R;
import com.example.sofra.view.fragment.BaSeFragment;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.sofra.utils.HelperMethod.replaceFragment;


public class RestaurantAndClientForgetPasswordFragment extends BaSeFragment {


    @BindView(R.id.client_and_restaurant_forget_password_fragment_til_pin_code)
    TextInputLayout forgetPasswordFragmentTilEmail;
    private String phoneStr;
    private String passwordStr;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_client_and_restaurant_forget_password, container, false);
        ButterKnife.bind(this, root);


        return root;
    }


    @Override
    public void onBack() {
        replaceFragment(getActivity().getSupportFragmentManager(), R.id.user_activity_fram, new RestaurantAndClientLoginFragment());

    }


    @OnClick(R.id.client_and_restaurant_forget_password_next_btn)
    public void onViewClicked() {
    }
}
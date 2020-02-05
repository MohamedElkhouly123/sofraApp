package com.example.sofra.view.fragment.splashCycle;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.sofra.R;
import com.example.sofra.view.activity.HomeCycleActivity;
import com.example.sofra.view.activity.UserCycleActivity;
import com.example.sofra.view.fragment.BaSeFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.sofra.data.local.SharedPreferencesManger.CLIENT;

import static com.example.sofra.data.local.SharedPreferencesManger.LoadBoolean;
import static com.example.sofra.data.local.SharedPreferencesManger.LoadUserData;
import static com.example.sofra.data.local.SharedPreferencesManger.REMEMBER_ME;
import static com.example.sofra.data.local.SharedPreferencesManger.SaveData;


public class SplashFragment extends BaSeFragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_splash, container, false);
        ButterKnife.bind(this, root);
//        if (LoadUserData(getActivity()) != null && LoadBoolean(getActivity(), REMEMBER_ME)) {
//            startActivity(new Intent(getActivity(), HomeCycleActivity.class));
//            getActivity().finish();
//        }
        return root;
    }


    @Override
    public void onBack() {
        getActivity().finish();
    }

    @OnClick({R.id.splash_food_client_btn, R.id.splash_food_restaurant_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.splash_food_client_btn:
                SaveData(getActivity(), CLIENT, "true");
                Intent intent = new Intent(getActivity(), HomeCycleActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
                break;
            case R.id.splash_food_restaurant_btn:
                SaveData(getActivity(), CLIENT, "false");
//                Boolean CLIENTV = LoadBoolData(getActivity(), CLIENT);
                Intent intent2 = new Intent(getActivity(), UserCycleActivity.class);
                getActivity().startActivity(intent2);
                getActivity().finish();
                break;
        }
    }
}
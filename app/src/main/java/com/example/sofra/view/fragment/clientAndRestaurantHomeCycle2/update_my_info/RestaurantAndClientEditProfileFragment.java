package com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.update_my_info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;

import com.example.sofra.R;
import com.example.sofra.view.fragment.BaSeFragment;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.home.HomeFragment;
import com.example.sofra.view.fragment.splashCycle.SplashFragment;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.sofra.data.local.SharedPreferencesManger.CLIENT;
import static com.example.sofra.data.local.SharedPreferencesManger.LoadData;
import static com.example.sofra.utils.HelperMethod.replaceFragment;
import static com.example.sofra.utils.HelperMethod.showToast;


public class RestaurantAndClientEditProfileFragment extends BaSeFragment {


    @BindView(R.id.restaurant_and_client_edit_profile_conect_data_image_botton)
    CircleImageView restaurantAndClientEditProfileConectDataImageBotton;
    @BindView(R.id.restaurant_and_client_edit_profile_fragment_til_restrant_name)
    TextInputLayout restaurantAndClientEditProfileFragmentTilRestrantName;
    @BindView(R.id.restaurant_and_client_edit_profile_fragment_til_email)
    TextInputLayout restaurantAndClientEditProfileFragmentTilEmail;
    @BindView(R.id.restaurant_and_client_edit_profile_fragment_til_order_phone)
    TextInputLayout restaurantAndClientEditProfileFragmentTilOrderPhone;
    @BindView(R.id.restaurant_and_client_edit_profile_fragment_sp_city)
    Spinner restaurantAndClientEditProfileFragmentSpCity;
    @BindView(R.id.restaurant_and_client_edit_profile_fragment_til_city)
    TextInputLayout restaurantAndClientEditProfileFragmentTilCity;
    @BindView(R.id.rrestaurant_and_client_edit_profile_fragment_sp_neighborhood)
    Spinner rrestaurantAndClientEditProfileFragmentSpNeighborhood;
    @BindView(R.id.restaurant_and_client_edit_profile_fragment_til_neighborhood)
    TextInputLayout restaurantAndClientEditProfileFragmentTilNeighborhood;
    @BindView(R.id.restaurant_and_client_edit_profile_fragment_til_least_range_of_order)
    TextInputLayout restaurantAndClientEditProfileFragmentTilLeastRangeOfOrder;
    @BindView(R.id.restaurant_and_client_edit_profile_fragment_til_order_time)
    TextInputLayout restaurantAndClientEditProfileFragmentTilOrderTime;
    @BindView(R.id.restaurant_and_client_edit_profile_fragment_til_delevery_price)
    TextInputLayout restaurantAndClientEditProfileFragmentTilDeleveryPrice;
    @BindView(R.id.restaurant_and_client_edit_profile_switch1)
    Switch restaurantAndClientEditProfileSwitch1;
    @BindView(R.id.restaurant_and_client_edit_profile_conect_data_fragment_til_phone)
    TextInputLayout restaurantAndClientEditProfileConectDataFragmentTilPhone;
    @BindView(R.id.restaurant_and_client_edit_profile_conect_data_fragment_til_whats_app)
    TextInputLayout restaurantAndClientEditProfileConectDataFragmentTilWhatsApp;
    @BindView(R.id.restaurant_and_client_edit_profile_addtion_restaurant_part)
    LinearLayout restaurantAndClientEditProfileAddtionRestaurantPart;
//    public String ISCLIENT = LoadData(getActivity(), CLIENT);
     public String ISCLIENT = SplashFragment.getClient();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_restaurant_and_client_edit_profile, container, false);
        ButterKnife.bind(this, root);
        setUpActivity();
        if (ISCLIENT=="true") {

            restaurantAndClientEditProfileFragmentTilOrderPhone.setVisibility(View.VISIBLE);
        }  if(ISCLIENT=="false"){
            restaurantAndClientEditProfileAddtionRestaurantPart.setVisibility(View.VISIBLE);

        }
        return root;
    }


    @Override
    public void onBack() {
//        replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, homeCycleActivity.homeFragment);
        super.onBack();
        homeCycleActivity.navView.getMenu().getItem(0).setChecked(true);

    }


    @OnClick({R.id.restaurant_and_client_edit_profile_conect_data_image_botton, R.id.restaurant_and_client_edit_profile_update_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.restaurant_and_client_edit_profile_conect_data_image_botton:
                break;
            case R.id.restaurant_and_client_edit_profile_update_btn:
                break;
        }
    }
}
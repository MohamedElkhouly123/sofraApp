package com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.more;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.example.sofra.R;
import com.example.sofra.data.model.clientLogin.ClientData;
import com.example.sofra.utils.LogOutDialog;
import com.example.sofra.utils.RestaurantAddAndUpdateCategoryDialog;
import com.example.sofra.view.activity.HomeCycleActivity;
import com.example.sofra.view.activity.UserCycleActivity;
import com.example.sofra.view.fragment.BaSeFragment;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.home.HomeFragment;
import com.example.sofra.view.fragment.splashCycle.SplashFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.sofra.data.local.SharedPreferencesManger.CLIENT;
import static com.example.sofra.data.local.SharedPreferencesManger.LoadData;
import static com.example.sofra.data.local.SharedPreferencesManger.LoadUserData;
import static com.example.sofra.utils.HelperMethod.replaceFragment;
import static com.example.sofra.utils.HelperMethod.showToast;
import static com.example.sofra.utils.RestaurantAddAndUpdateCategoryDialog.showDialog;

public class MoreFragment extends BaSeFragment {


    @BindView(R.id.more_my_put_coments_rate_on_store_lay)
    LinearLayout moreMyPutComentsRateOnStoreLay;
    @BindView(R.id.more_my_put_coments_rate_on_store_view_line)
    ImageView moreMyPutComentsRateOnStoreViewLine;
    public String ISCLIENT = SplashFragment.getClient();
    private ClientData clientData;
    private boolean goLogin=false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_more, container, false);
        ButterKnife.bind(this, root);
        setUpActivity();
        clientData = LoadUserData(getActivity());
        if(ISCLIENT=="false"){
            moreMyPutComentsRateOnStoreLay.setVisibility(View.VISIBLE);
            moreMyPutComentsRateOnStoreViewLine.setVisibility(View.VISIBLE);

        }
        homeCycleActivity= (HomeCycleActivity) getActivity();
        homeCycleActivity.setNavigation("v");
        return root;
    }

   public void goToRegisterFirst(Activity activity){
       showToast(activity, "Go To Register or Login First");
       Intent intent2 = new Intent(getActivity(), UserCycleActivity.class);
       getActivity().startActivity(intent2);
   }

    @OnClick({R.id.more_my_Offers_lay, R.id.more_contact_us_lay, R.id.more_about_app_lay, R.id.more_my_put_coments_rate_on_store_lay, R.id.more_change_password_lay, R.id.more_sign_out_lay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.more_my_Offers_lay:
                if(clientData!=null) {
                    replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, new RestaurantAndClientOffersFragment());
                    homeCycleActivity.setNavigation("g");
                }else {
                    goToRegisterFirst(getActivity());
                    goLogin = true;
                }

                break;
            case R.id.more_contact_us_lay:
                if(clientData!=null) {
                    replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, new ContactWithUsFragment());
                homeCycleActivity.setNavigation("g");
                }else {
                    goToRegisterFirst(getActivity());
                    goLogin = true;
                }

//                showDialog(getActivity(),getContext(),"add");
                break;
            case R.id.more_about_app_lay:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, new AboutAppFragment());
                homeCycleActivity.setNavigation("g");
                break;
            case R.id.more_my_put_coments_rate_on_store_lay:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, new RestaurantCommentsFragment());
                homeCycleActivity.setNavigation("g");
                break;
            case R.id.more_change_password_lay:
                if(clientData!=null) {
                    replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, new ChangePasswordFragment());
                homeCycleActivity.setNavigation("g");
        }else {
            goToRegisterFirst(getActivity());
                    goLogin = true;
                }
                break;
            case R.id.more_sign_out_lay:
                if(clientData!=null) {
                    new  LogOutDialog().showDialog(getActivity());
                }else {
                    goToRegisterFirst(getActivity());
                    goLogin = true;
                }
                break;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (goLogin && LoadUserData(getActivity()) != null) {
              goLogin = false;
            replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, new MoreFragment());
            homeCycleActivity.navView.setSelectedItemId(R.id.navigation_more_setting);
        }
    }
    @Override
    public void onBack() {
        replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, homeCycleActivity.homeFragment);
        homeCycleActivity.navView.getMenu().getItem(0).setChecked(true);
    }
}
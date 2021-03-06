package com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.sofra.R;
import com.example.sofra.utils.SmileDialog.DialogCallback;
import com.example.sofra.utils.SmileDialog.GlobalShowDialog;
import com.example.sofra.view.fragment.BaSeFragment;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.more.MoreFragment;
import com.example.sofra.view.fragment.splashCycle.SplashFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.sofra.utils.HelperMethod.replaceFragment;

public class RatingAndCommentsFragment extends BaSeFragment {


    public String ISCLIENT = SplashFragment.getClient();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home_rating_and_comments, container, false);
        ButterKnife.bind(this, root);
//
        return root;
    }

    @Override
    public void onBack() {
        if (ISCLIENT == "true") {
            replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, homeCycleActivity.homeFragment);
            homeCycleActivity.navView.getMenu().getItem(0).setChecked(true);
        }
        if (ISCLIENT == "false") {
            replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, new MoreFragment());
        }

    }

    public void showDialog() {
        // create and show dialog
        GlobalShowDialog.showDiallog(getActivity(), new DialogCallback() {
            @Override
            public void callback(String rating) {
                Toast.makeText(getActivity(), "Thanks For Review", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.client_restaurant_review_btn_add_review)
    public void onViewClicked() {
        showDialog();
    }
}
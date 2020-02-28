package com.example.sofra.view.fragment.userCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sofra.R;
import com.example.sofra.data.model.clientResetPassword.ClientResetPasswordResponse;
import com.example.sofra.view.fragment.BaSeFragment;
import com.example.sofra.view.fragment.splashCycle.SplashFragment;
import com.example.sofra.view.viewModel.ViewModelClient;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

import static com.example.sofra.data.api.ApiClient.getApiClient;
import static com.example.sofra.data.local.SharedPreferencesManger.CLIENT;
import static com.example.sofra.data.local.SharedPreferencesManger.LoadData;
import static com.example.sofra.utils.HelperMethod.disappearKeypad;
import static com.example.sofra.utils.HelperMethod.replaceFragment;
import static com.example.sofra.utils.HelperMethod.showToast;
import static com.example.sofra.utils.validation.Validation.cleanError;
import static com.example.sofra.utils.validation.Validation.validationEmail;
import static com.example.sofra.utils.validation.Validation.validationPassword;
import static com.example.sofra.utils.validation.Validation.validationTextInputLayoutListEmpty;


public class RestaurantAndClientForgetPasswordFragment extends BaSeFragment {


    @BindView(R.id.client_and_restaurant_forget_password_fragment_til_pin_code)
    TextInputLayout forgetPasswordFragmentTilEmail;
    List<TextInputLayout> textInputLayoutList = new ArrayList<>();
    private String email;
    private ViewModelClient viewModel;
    public String ISCLIENT = SplashFragment.getClient();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_client_and_restaurant_forget_password, container, false);
        ButterKnife.bind(this, root);
        textInputLayoutList.add(forgetPasswordFragmentTilEmail);


        return root;
    }
    private void initListener() {
        viewModel = ViewModelProviders.of(this).get(ViewModelClient.class);
        viewModel.makeResetAndNewPasswordAndTokenResponse().observe(this, new Observer<ClientResetPasswordResponse>() {
            @Override
            public void onChanged(@Nullable ClientResetPasswordResponse response) {
                if(response!=null) {
                    if (response.getStatus() == 1) {
                        replaceFragment(getActivity().getSupportFragmentManager(), R.id.user_activity_fram, new RestaurantAndClientForgetPasswordFragment());
                        showToast(getActivity(), "success");

                    }
                }
            }
        });
    }

    @Override
    public void onBack() {
        replaceFragment(getActivity().getSupportFragmentManager(), R.id.user_activity_fram, new RestaurantAndClientLoginFragment());

    }


    @OnClick(R.id.client_and_restaurant_forget_password_next_btn)
    public void onViewClicked() {

        disappearKeypad(getActivity(), getView());
        onValidData();

    }

    private void onValidData() {

        cleanError(textInputLayoutList);

        if (!validationTextInputLayoutListEmpty(textInputLayoutList, getString(R.string.empty))) {
            return;
        }

        if (!validationEmail(getActivity(), forgetPasswordFragmentTilEmail)) {

            return;
        }


        onCall();
    }

    private void onCall() {
        email = forgetPasswordFragmentTilEmail.getEditText().getText().toString();


        Call<ClientResetPasswordResponse> resetPasswordCall = null;

        if (ISCLIENT=="true") {
            resetPasswordCall = getApiClient().clientResetPassword(email);
        }  if(ISCLIENT=="false"){

            resetPasswordCall = getApiClient().restaurantResetPassword(email);
        }
        viewModel.makeResetAndNewPasswordAndToken(getActivity(),resetPasswordCall);


    }
}
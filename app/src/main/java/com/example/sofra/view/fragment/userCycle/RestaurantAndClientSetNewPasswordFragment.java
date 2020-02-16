package com.example.sofra.view.fragment.userCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sofra.R;
import com.example.sofra.data.model.clientLogin.ClientGeneralResponse;
import com.example.sofra.data.model.clientResetPassword.ClientResetPasswordResponse;
import com.example.sofra.view.fragment.BaSeFragment;
import com.example.sofra.view.viewModel.ViewModelClient;
import com.google.android.material.textfield.TextInputLayout;

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
import static com.example.sofra.utils.ToastCreator.onCreateErrorToast;
import static com.example.sofra.utils.validation.Validation.validationConfirmPassword;
import static com.example.sofra.utils.validation.Validation.validationLength;
import static com.example.sofra.utils.validation.Validation.validationPassword;


public class RestaurantAndClientSetNewPasswordFragment extends BaSeFragment {


    @BindView(R.id.client_and_restaurant_set_New_password_fragment_til_pin_code)
    TextInputLayout clientAndRestaurantSetNewPasswordFragmentTilPinCode;
    @BindView(R.id.client_and_restaurant_set_New_password_fragment_til_password)
    TextInputLayout clientAndRestaurantSetNewPasswordFragmentTilPassword;
    @BindView(R.id.client_and_restaurant_set_New_forget_password_fragment_til_confirm_password)
    TextInputLayout clientAndRestaurantSetNewForgetPasswordFragmentTilConfirmPassword;
    @BindView(R.id.client_and_restaurant_set_new_password_next_btn)
    Button clientAndRestaurantSetNewPasswordNextBtn;
    private String pinCode;
    private String password;
    private String passwordConfirm;
    private ViewModelClient viewModel;
    public String ISCLIENT = LoadData(getActivity(), CLIENT);

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_client_and_restaurant_set_new_password, container, false);
        ButterKnife.bind(this, root);
        initListener();

        return root;
    }

    private void initListener() {
        viewModel = ViewModelProviders.of(this).get(ViewModelClient.class);
        viewModel.makeGeneralRegisterationAndEdit().observe(this, new Observer<ClientGeneralResponse>() {
            @Override
            public void onChanged(@Nullable ClientGeneralResponse response) {
                if (response.getStatus() == 1) {
                    replaceFragment(getActivity().getSupportFragmentManager(), R.id.user_activity_fram, new RestaurantAndClientLoginFragment());
                    showToast(getActivity(),"success");

                }
            }
        });
    }

    @Override
    public void onBack() {
        replaceFragment(getActivity().getSupportFragmentManager(), R.id.user_activity_fram, new RestaurantAndClientForgetPasswordFragment());

    }


    @OnClick(R.id.client_and_restaurant_set_new_password_next_btn)
    public void onViewClicked() {
        disappearKeypad(getActivity(), getView());
        onCall();
    }

    private void onCall() {
        pinCode = clientAndRestaurantSetNewPasswordFragmentTilPinCode.getEditText().getText().toString();
        password =clientAndRestaurantSetNewPasswordFragmentTilPassword.getEditText().getText().toString();
        passwordConfirm = clientAndRestaurantSetNewForgetPasswordFragmentTilConfirmPassword.getEditText().getText().toString();


        if (pinCode.isEmpty() && password.isEmpty() && passwordConfirm.isEmpty()) {

            return;
        }
        if (pinCode.isEmpty()) {
            onCreateErrorToast(getActivity(), getString(R.string.enter_pin_code));

            return;
        }
        if (password.isEmpty()) {
            onCreateErrorToast(getActivity(), getString(R.string.enter_password));

            return;
        }
//        if (!validationLength(getActivity(), password, getString(R.string.weak_password), 5)) {
//
//            return;
//        }
        if (passwordConfirm.isEmpty()) {
            onCreateErrorToast(getActivity(), getString(R.string.enter_password_confirm));

            return;
        }
        if (!validationPassword(clientAndRestaurantSetNewPasswordFragmentTilPassword, 6, getString(R.string.invalid_password))) {
            return;
        }


        if (!validationConfirmPassword(getActivity(), password, passwordConfirm)) {

            return;
        }

        Call<ClientResetPasswordResponse> resetPasswordCall;

        if (ISCLIENT=="true") {
            resetPasswordCall = getApiClient().clientNewPassword(pinCode,password,passwordConfirm);
        } else {

            resetPasswordCall = getApiClient().restaurantNewPassword(pinCode,password,passwordConfirm);
        }
        viewModel.makeResetAndNewPasswordAndToken(getActivity(),resetPasswordCall);

    }

    }
package com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.more;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sofra.R;
import com.example.sofra.data.model.clientLogin.ClientData;
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
import static com.example.sofra.data.local.SharedPreferencesManger.LoadUserData;
import static com.example.sofra.utils.HelperMethod.replaceFragment;
import static com.example.sofra.utils.HelperMethod.showToast;
import static com.example.sofra.utils.validation.Validation.cleanError;
import static com.example.sofra.utils.validation.Validation.validationConfirmPassword;
import static com.example.sofra.utils.validation.Validation.validationPassword;
import static com.example.sofra.utils.validation.Validation.validationTextInputLayoutListEmpty;

public class ChangePasswordFragment extends BaSeFragment {


    @BindView(R.id.change_password_fragment_til_password_old)
    TextInputLayout changePasswordFragmentTilPasswordOld;
    @BindView(R.id.change_password_fragment_til_new_password)
    TextInputLayout changePasswordFragmentTilNewPassword;
    @BindView(R.id.change_password_fragment_til_new_confirm_password)
    TextInputLayout changePasswordFragmentTilNewConfirmPassword;
    private ViewModelClient viewModel;
    private ClientData clientData;
    private String oldPassword;
    private String getOldPassword;
    private String newPassword;
    private String newConfirmPassword;
    public String ISCLIENT = SplashFragment.getClient();
    private boolean goLogin=false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_more_change_password, container, false);
        ButterKnife.bind(this, root);
        initListener();
        clientData = LoadUserData(getActivity());
        if(clientData!=null){
            oldPassword= clientData.getUser().getPassword();
        changePasswordFragmentTilPasswordOld.getEditText().setText(oldPassword);}
        changePasswordFragmentTilPasswordOld.getEditText().setTypeface(Typeface.DEFAULT);
        changePasswordFragmentTilPasswordOld.getEditText().setTransformationMethod(new PasswordTransformationMethod());
        changePasswordFragmentTilNewPassword.getEditText().setTypeface(Typeface.DEFAULT);
        changePasswordFragmentTilNewPassword.getEditText().setTransformationMethod(new PasswordTransformationMethod());
        changePasswordFragmentTilNewConfirmPassword.getEditText().setTypeface(Typeface.DEFAULT);
        changePasswordFragmentTilNewConfirmPassword.getEditText().setTransformationMethod(new PasswordTransformationMethod());
        return root;
    }

    private void initListener() {
        viewModel = ViewModelProviders.of(this).get(ViewModelClient.class);
        viewModel.makeResetAndNewPasswordAndTokenAndChangeStatusResponse().observe(this, new Observer<ClientResetPasswordResponse>() {
            @Override
            public void onChanged(@Nullable ClientResetPasswordResponse response) {
                if(response!=null){
                    if (response.getStatus() == 1) {
                        showToast(getActivity(),"success");
                        new  MoreFragment().goToRegisterFirst(getActivity());
                        goLogin = true;

                    }  }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (goLogin && LoadUserData(getActivity()) != null) {
            //  goLogin = false;
            replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, new MoreFragment());
        }
    }

    @Override
    public void onBack() {
        replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, new MoreFragment());
    }

    @OnClick(R.id.change_password_fragment_til_new_change_btn)
    public void onViewClicked() {
        onValidation();

    }

    private void onValidation() {
        List<TextInputLayout> textInputLayouts = new ArrayList<>();
        textInputLayouts.add(changePasswordFragmentTilPasswordOld);
        textInputLayouts.add(changePasswordFragmentTilNewPassword);
        textInputLayouts.add(changePasswordFragmentTilNewConfirmPassword);
        cleanError(textInputLayouts);
        if (!validationTextInputLayoutListEmpty(textInputLayouts, getString(R.string.empty))) {
            return;
        }
        if (!validationPassword(changePasswordFragmentTilPasswordOld, 6, getString(R.string.invalid_password))) {
            return;
        }
        if ( changePasswordFragmentTilPasswordOld.getEditText().getText().toString()!=oldPassword) {
            showToast(getActivity(),"old password must not correct");
            return;
        }
        if (!validationPassword(changePasswordFragmentTilNewPassword, 6, getString(R.string.invalid_password))) {
            return;
        }
        if (!validationConfirmPassword(getActivity(), changePasswordFragmentTilNewPassword, changePasswordFragmentTilNewConfirmPassword)) {
            return;
        }
        onCall();

    }

    private void onCall() {
        getOldPassword = changePasswordFragmentTilPasswordOld.getEditText().getText().toString();
        newPassword = changePasswordFragmentTilNewPassword.getEditText().getText().toString();
        newConfirmPassword = changePasswordFragmentTilNewConfirmPassword.getEditText().getText().toString();
        Call<ClientResetPasswordResponse> changePasswordCall = null;

        boolean remember = true;
        boolean auth=false;
        if (ISCLIENT=="true") {
            changePasswordCall = getApiClient().clientChangePassword(clientData.getApiToken(),getOldPassword,newPassword,newConfirmPassword);

        }  if(ISCLIENT=="false"){

            changePasswordCall = getApiClient().restaurantChangePassword(clientData.getApiToken(),getOldPassword,newPassword,newConfirmPassword);
        }
        viewModel.getAndMakeResetAndNewPasswordAndTokenAndChangeStatus(getActivity(),changePasswordCall);


    }
}
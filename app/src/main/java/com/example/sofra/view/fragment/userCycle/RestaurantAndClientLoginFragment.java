package com.example.sofra.view.fragment.userCycle;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sofra.R;
import com.example.sofra.data.model.clientGetAllNotofications.ClientFireBaseToken;
import com.example.sofra.data.model.clientLogin.ClientData;
import com.example.sofra.data.model.clientLogin.ClientGeneralResponse;
import com.example.sofra.data.model.clientResetPassword.ClientResetPasswordResponse;
import com.example.sofra.utils.HelperMethod;
import com.example.sofra.utils.KeyboardUtils;
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
import static com.example.sofra.data.local.SharedPreferencesManger.LoadUserData;
import static com.example.sofra.utils.HelperMethod.replaceFragment;
import static com.example.sofra.utils.HelperMethod.replaceFragmentWithAnimation;
import static com.example.sofra.utils.HelperMethod.showToast;
import static com.example.sofra.utils.validation.Validation.cleanError;
import static com.example.sofra.utils.validation.Validation.validationEmail;
import static com.example.sofra.utils.validation.Validation.validationPassword;
import static com.example.sofra.utils.validation.Validation.validationTextInputLayoutListEmpty;


public class RestaurantAndClientLoginFragment extends BaSeFragment {


    @BindView(R.id.client_and_restaurant_login_fragment_til_email)
    TextInputLayout loginFragmentTilEmail;
    @BindView(R.id.client_and_restaurant_login_fragment_til_password)
    TextInputLayout loginFragmentTilPassword;
    @BindView(R.id.client_and_restaurant_login_creat_new_account_txt)
    TextView loginCreatNewAccountTxt;
    List<TextInputLayout> textInputLayoutList = new ArrayList<>();
    private String email;
    private String password;
    private String apiToken;
    private String token;
//    private ClientData clientData;

    public String ISCLIENT = SplashFragment.getClient();
    private ViewModelClient viewModel;
//    public String ISCLIENT = LoadData(getActivity(), CLIENT);

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_client_and_restaurant_login, container, false);
        ButterKnife.bind(this, root);
        addKeyboardToggleListener();
        textInputLayoutList.add(loginFragmentTilEmail);
        textInputLayoutList.add(loginFragmentTilPassword);
//        clientData = LoadUserData(getActivity());
        loginFragmentTilPassword.getEditText().setTypeface(Typeface.DEFAULT);
        loginFragmentTilPassword.getEditText().setTransformationMethod(new PasswordTransformationMethod());

        initListener();

        return root;
    }

    private void initListener() {
        viewModel = ViewModelProviders.of(this).get(ViewModelClient.class);
        viewModel.makeGeneralRegisterationAndEdit().observe(this, new Observer<ClientGeneralResponse>() {
            @Override
            public void onChanged(@Nullable ClientGeneralResponse response) {
                if(response!=null){
                    if (response.getStatus() == 1) {
                        showToast(getActivity(),"success");

                    }  }
            }
        });
    }

    private void addKeyboardToggleListener() {
        KeyboardUtils.addKeyboardToggleListener(getActivity(), new KeyboardUtils.SoftKeyboardToggleListener() {
            @Override
            public void onToggleSoftKeyboard(boolean isVisible) {
                try {
                    if (!isVisible) {
                        loginCreatNewAccountTxt.setVisibility(View.VISIBLE);
                    } else {
                        loginCreatNewAccountTxt.setVisibility(View.GONE);
                    }
                } catch (Exception e) {

                }
            }
        });
    }

    @OnClick({R.id.client_and_restaurant_login_forget_id, R.id.client_and_restaurant_login_buer_next_btn, R.id.client_and_restaurant_login_creat_new_account_txt})
    public void onViewClicked(View view) {
        HelperMethod.disappearKeypad(getActivity(), view);
        switch (view.getId()) {
            case R.id.client_and_restaurant_login_forget_id:
                replaceFragmentWithAnimation(getActivity().getSupportFragmentManager(), R.id.user_activity_fram, new RestaurantAndClientForgetPasswordFragment(),"r");

                break;
            case R.id.client_and_restaurant_login_buer_next_btn:
                onValidData();

                break;
            case R.id.client_and_restaurant_login_creat_new_account_txt:
                replaceFragmentWithAnimation(getActivity().getSupportFragmentManager(), R.id.user_activity_fram, new RestaurantAndClientRegisterFragment(),"b");

                break;
        }
    }

    private void onValidData() {

        cleanError(textInputLayoutList);

        if (!validationTextInputLayoutListEmpty(textInputLayoutList, getString(R.string.empty))) {
            return;
        }

        if (!validationEmail(getActivity(), loginFragmentTilEmail)) {

            return;
        }

        if (!validationPassword(loginFragmentTilPassword, 6, getString(R.string.invalid_password))) {
            return;
        }

        onCall();
    }

    private void onCall() {
         email = loginFragmentTilEmail.getEditText().getText().toString();
         password = loginFragmentTilPassword.getEditText().getText().toString();
//         apiToken=clientData.getApiToken();
//         token=new ClientFireBaseToken().getToken();
        boolean remember = true;

//        Call<ClientGeneralResponse> clientCall = getApiClient().clientLogin(email, password);


        Call<ClientGeneralResponse> loginCall = null;
//        Call<ClientResetPasswordResponse> tokenCall;


        if (ISCLIENT=="true") {
            loginCall = getApiClient().clientLogin(email, password);
//            tokenCall = getApiClient().clientSignUpToken(token, "android",apiToken);

        }  if(ISCLIENT=="false"){

            loginCall = getApiClient().restaurantLogin(email, password);
//            tokenCall = getApiClient().restaurantSignUpToken(token, "android",apiToken);
        }
        viewModel.makeGeneralRegisterationAndEditToServer(getActivity(),loginCall, password, remember, true);


    }

    @Override
    public void onBack() {
        getActivity().finish();
    }

}
package com.example.sofra.view.fragment.userCycle;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sofra.R;
import com.example.sofra.data.model.clientLogin.ClientGeneralResponse;
import com.example.sofra.utils.ToastCreator;
import com.example.sofra.view.fragment.BaSeFragment;
import com.example.sofra.view.viewModel.ViewModelClient;
import com.google.android.material.textfield.TextInputLayout;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

import static android.app.Activity.RESULT_OK;
import static com.example.sofra.data.api.ApiClient.getApiClient;
import static com.example.sofra.utils.HelperMethod.openGallery;
import static com.example.sofra.utils.HelperMethod.replaceFragment;
import static com.example.sofra.utils.HelperMethod.showToast;
import static com.example.sofra.utils.validation.Validation.cleanError;
import static com.example.sofra.utils.validation.Validation.validationPhone;
import static com.example.sofra.utils.validation.Validation.validationTextInputLayoutListEmpty;


public class ContactDataFragment extends BaSeFragment {


    public String name;
    public String email;
    public String password;
    public String passwordConfirmation;
    public String phone;
    public String whatsApp;
    public int regionId;
    public String deleveryPrice;
    public String leastRangeOfOrder;
    public String orderTime;
    @BindView(R.id.conect_data_fragment_til_phone)
    TextInputLayout conectDataFragmentTilPhone;
    @BindView(R.id.client_conect_data_img_restraunt_image_botton)
    ImageView clientConectDataImgRestrauntImageBotton;
    @BindView(R.id.conect_data_buer_register_btn)
    Button conectDataBuerRegisterBtn;
    @BindView(R.id.conect_data_fragment_til_whats_app)
    TextInputLayout conectDataFragmentTilWhatsApp;
    private ViewModelClient viewModel;
    private String mPath;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_contact_data, container, false);
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
                    showToast(getActivity(), "success");

                }
            }
        });
    }

    @Override
    public void onBack() {
        replaceFragment(getActivity().getSupportFragmentManager(), R.id.user_activity_fram, new RestaurantAndClientRegisterFragment());

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            mPath = data.getStringExtra(ImagePicker.EXTRA_IMAGE_PATH);
            //Your Code
        }
    }

    @OnClick({R.id.client_conect_data_img_restraunt_image_botton, R.id.conect_data_buer_register_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.client_conect_data_img_restraunt_image_botton:
                openGallery(getActivity());
                break;
            case R.id.conect_data_buer_register_btn:
                onValidation();
                break;
        }
    }

    private void onValidation() {

        List<TextInputLayout> textInputLayouts = new ArrayList<>();

        textInputLayouts.add(conectDataFragmentTilPhone);
        textInputLayouts.add(conectDataFragmentTilWhatsApp);


        cleanError(textInputLayouts);

        if (!validationTextInputLayoutListEmpty(textInputLayouts, getString(R.string.empty))) {

            ToastCreator.onCreateErrorToast(getActivity(), getString(R.string.empty));
            return;
        }

        if (mPath.equals("")) {
            ToastCreator.onCreateErrorToast(getActivity(), getString(R.string.select_image));
//                showToast(getActivity(),"Please Select Image First");
            return;
        }

        if (!validationPhone(getActivity(), conectDataFragmentTilPhone)) {

            return;
        }

        if (!validationPhone(getActivity(), conectDataFragmentTilWhatsApp)) {

            return;
        }




        onCall();
    }

    private void onCall() {


        String phone = conectDataFragmentTilPhone.getEditText().getText().toString();
        String orderTime = conectDataFragmentTilWhatsApp.getEditText().getText().toString();

        String restaurantProfilePhoto = mPath;

        Call<ClientGeneralResponse> clientCall;


            clientCall = getApiClient().restaurantRegistration(name, email, password, passwordConfirmation, phone,whatsApp, regionId, deleveryPrice,leastRangeOfOrder,restaurantProfilePhoto,orderTime);
            viewModel.makeGeneralRegisterationAndEditToServer(getActivity(), clientCall, password, true, true);



    }
}
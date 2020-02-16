package com.example.sofra.view.fragment.userCycle;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sofra.R;
import com.example.sofra.adapter.SpinnerAdapter;
import com.example.sofra.data.model.clientGetAllNotofications.ClientFireBaseToken;
import com.example.sofra.data.model.clientLogin.ClientData;
import com.example.sofra.data.model.clientLogin.ClientGeneralResponse;
import com.example.sofra.data.model.clientResetPassword.ClientResetPasswordResponse;
import com.example.sofra.data.model.generalRespose.GeneralRespose;
import com.example.sofra.utils.ToastCreator;
import com.example.sofra.view.fragment.BaSeFragment;
import com.example.sofra.view.viewModel.ViewModelClient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

import static android.app.Activity.RESULT_OK;
import static com.example.sofra.data.api.ApiClient.getApiClient;
import static com.example.sofra.data.local.SharedPreferencesManger.CLIENT;
import static com.example.sofra.data.local.SharedPreferencesManger.LoadData;
import static com.example.sofra.data.local.SharedPreferencesManger.LoadUserData;
import static com.example.sofra.utils.GeneralRequest.getSpinnerData;
import static com.example.sofra.utils.HelperMethod.convertFileToMultipart;
import static com.example.sofra.utils.HelperMethod.convertToRequestBody;
import static com.example.sofra.utils.HelperMethod.disappearKeypad;
import static com.example.sofra.utils.HelperMethod.openGallery;
import static com.example.sofra.utils.HelperMethod.replaceFragment;
import static com.example.sofra.utils.HelperMethod.showToast;
import static com.example.sofra.utils.validation.Validation.cleanError;
import static com.example.sofra.utils.validation.Validation.validationAllEmpty;
import static com.example.sofra.utils.validation.Validation.validationConfirmPassword;
import static com.example.sofra.utils.validation.Validation.validationEmail;
import static com.example.sofra.utils.validation.Validation.validationLength;
import static com.example.sofra.utils.validation.Validation.validationPassword;
import static com.example.sofra.utils.validation.Validation.validationPhone;


public class RestaurantAndClientRegisterFragment extends BaSeFragment {


    @BindView(R.id.restaurant_registers_buyer_fragment_til_restrant_name)
    TextInputLayout clientRegistersBuyerFragmentTilRestrantName;
    @BindView(R.id.restaurant_registers_buyer_fragment_til_email)
    TextInputLayout clientRegistersBuyerFragmentTilEmail;
    @BindView(R.id.restaurant_registers_buyer_fragment_til_order_time)
    TextInputLayout clientRegistersBuyerFragmentTilOrderTime;
    @BindView(R.id.restaurant_registers_buyer_fragment_sp_city)
    Spinner clientRegistersBuyerFragmentSpCity;
//    @BindView(R.id.restaurant_registers_buyer_fragment_til_city)
//    TextInputLayout clientRegistersBuyerFragmentTilCity;
    @BindView(R.id.restaurant_registers_buyer_fragment_sp_neighborhood)
    Spinner clientRegistersBuyerFragmentSpNeighborhood;
    @BindView(R.id.restaurant_registers_buyer_fragment_til_neighborhood)
    TextInputLayout clientRegistersBuyerFragmentTilNeighborhood;
    @BindView(R.id.restaurant_registers_buyer_fragment_til_password)
    TextInputLayout clientRegistersBuyerFragmentTilPassword;
    @BindView(R.id.restaurant_rigister_fragment_til_confirm_password)
    TextInputLayout clientRigisterFragmentTilConfirmPassword;
    @BindView(R.id.restaurant_registers_buyer_fragment_til_least_range_of_order)
    TextInputLayout registersBuyerFragmentTilLeastRangeOfOrder;
    @BindView(R.id.restaurant_registers_buyer_fragment_til_delevery_price)
    TextInputLayout registersBuyerFragmentTilDeleveryPrice;
    @BindView(R.id.client_conect_data_img_restraunt_image_botton)
    ImageView clientConectDataImgRestrauntImageBotton;
    @BindView(R.id.client_registers_buyer_fragment_til_order_phone)
    TextInputLayout clientRegistersBuyerFragmentTilOrderPhone;
    @BindView(R.id.restaurant_register_buer_next_btn)
    Button restaurantRegisterBuerNextBtn;
    @BindView(R.id.restaurant_registers_buyer_restrant_name_etxt)
    TextInputEditText restaurantRegistersBuyerRestrantNameEtxt;
    private String ISCLIENT = LoadData(getActivity(), CLIENT);
//    public  boolean EDITPROFILE=false;
    private SpinnerAdapter neighborhoodsAdapter, citiesAdapter;
    private int  neighborhoodSelectedId = 0, citiesSelectedId = 0;
    private AdapterView.OnItemSelectedListener listener;
    private ContactDataFragment contactData= new ContactDataFragment();
    private ViewModelClient viewModel;
    private String mPath;
    private static final String CLIENTPROFILEIMAGE ="CLIENTPROFILEIMAGE" ;
    private String apiToken;
    private String token;
    private ClientData clientData;


    public RestaurantAndClientRegisterFragment() {
        // Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_restaurant_and_client_rigister, container, false);
        ButterKnife.bind(this, root);
        isClient();
        initListener();
        setSpinner();
        clientData = LoadUserData(getActivity());
        clientRegistersBuyerFragmentTilPassword.getEditText().setTypeface(Typeface.DEFAULT);
        clientRegistersBuyerFragmentTilPassword.getEditText().setTransformationMethod(new PasswordTransformationMethod());
        clientRigisterFragmentTilConfirmPassword.getEditText().setTypeface(Typeface.DEFAULT);
        clientRigisterFragmentTilConfirmPassword.getEditText().setTransformationMethod(new PasswordTransformationMethod());

        return root;
    }

    private void initListener() {
        viewModel = ViewModelProviders.of(this).get(ViewModelClient.class);
        viewModel.makeGeneralRegisterationAndEdit().observe(this, new Observer<ClientGeneralResponse>() {
            @Override
            public void onChanged(@Nullable ClientGeneralResponse response) {
                if (response.getStatus() == 1) {
                    showToast(getActivity(),"success");

                }
            }
        });

        viewModel.makegetSpinnerData().observe(this, new Observer<GeneralRespose>() {
            @Override
            public void onChanged(@Nullable GeneralRespose response) {
                if (response.getStatus() == 1) {
                    showToast(getActivity(),"success");

                } else {
                    showToast(getActivity(),"error");

                }
            }
        });
    }

    private void isClient() {
        if (ISCLIENT == "true") {
            clientConectDataImgRestrauntImageBotton.setVisibility(View.VISIBLE);
            clientRegistersBuyerFragmentTilOrderPhone.setVisibility(View.VISIBLE);
            clientRegistersBuyerFragmentTilRestrantName.setHint("الاسم");
            restaurantRegisterBuerNextBtn.setText(R.string.contact_data_register);

        } else {
            clientRegistersBuyerFragmentTilOrderTime.setVisibility(View.VISIBLE);
            registersBuyerFragmentTilLeastRangeOfOrder.setVisibility(View.VISIBLE);
            registersBuyerFragmentTilDeleveryPrice.setVisibility(View.VISIBLE);
            clientRegistersBuyerFragmentTilRestrantName.setHint("اسم المطعم");
            restaurantRegisterBuerNextBtn.setText(R.string.register_buer_next);

        }
    }
    private void setSpinner() {


        citiesAdapter = new SpinnerAdapter(getActivity());
        listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) {
                    clientRegistersBuyerFragmentTilNeighborhood.setVisibility(View.GONE);

                } else {
                    int cityId = i;
                    neighborhoodsAdapter = new SpinnerAdapter(getActivity());
                    viewModel.getSpinnerData(getActivity(), clientRegistersBuyerFragmentSpNeighborhood, neighborhoodsAdapter, getString(R.string.select_region),
                            getApiClient().getRegion(cityId), neighborhoodSelectedId, null);
                    clientRegistersBuyerFragmentTilNeighborhood.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };

        viewModel.getSpinnerData(getActivity(), clientRegistersBuyerFragmentSpCity, citiesAdapter, getString(R.string.select_city),
                getApiClient().getAllCities(), citiesSelectedId, listener);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
             mPath = data.getStringExtra(ImagePicker.EXTRA_IMAGE_PATH);
            //Your Code
        }
    }

    @Override
    public void onBack() {
        replaceFragment(getActivity().getSupportFragmentManager(), R.id.user_activity_fram, new RestaurantAndClientLoginFragment());

    }


    @OnClick({R.id.client_conect_data_img_restraunt_image_botton, R.id.restaurant_register_buer_next_btn})
    public void onViewClicked(View view) {
        disappearKeypad(getActivity(), getView());
        switch (view.getId()) {
            case R.id.client_conect_data_img_restraunt_image_botton:
                openGallery(getActivity());
                break;
            case R.id.restaurant_register_buer_next_btn:
                onValidation();
                break;
        }
    }

    private void onValidation() {

        List<EditText> editTexts = new ArrayList<>();
        List<TextInputLayout> textInputLayouts = new ArrayList<>();
        List<Spinner> spinners = new ArrayList<>();

        textInputLayouts.add(clientRegistersBuyerFragmentTilRestrantName);
        textInputLayouts.add(clientRegistersBuyerFragmentTilEmail);
        textInputLayouts.add(clientRegistersBuyerFragmentTilPassword);
        textInputLayouts.add(clientRigisterFragmentTilConfirmPassword);

        spinners.add(clientRegistersBuyerFragmentSpCity);
        spinners.add(clientRegistersBuyerFragmentSpNeighborhood);

        if (ISCLIENT=="true") {
            // photo
            textInputLayouts.add(clientRegistersBuyerFragmentTilOrderPhone);
        } else {
            textInputLayouts.add(clientRegistersBuyerFragmentTilOrderTime);
            textInputLayouts.add(registersBuyerFragmentTilLeastRangeOfOrder);
            textInputLayouts.add(registersBuyerFragmentTilDeleveryPrice);
        }




        cleanError(textInputLayouts);

        if (!validationAllEmpty(editTexts, textInputLayouts, spinners, getString(R.string.empty))) {

            ToastCreator.onCreateErrorToast(getActivity(), getString(R.string.empty));
            return;
        }

        if (!validationLength(clientRegistersBuyerFragmentTilRestrantName, getString(R.string.invalid_user_name), 3)) {
            return;
        }

        if (!validationEmail(getActivity(), clientRegistersBuyerFragmentTilEmail)) {

            return;
        }

        if (clientRegistersBuyerFragmentSpCity.getSelectedItemPosition() == 0) {
            ToastCreator.onCreateErrorToast(getActivity(), getString(R.string.select_city));
            return;
        }

        if (clientRegistersBuyerFragmentSpNeighborhood.getSelectedItemPosition() == 0) {
            ToastCreator.onCreateErrorToast(getActivity(), getString(R.string.select_region));
            return;
        }



        if (!validationPassword(clientRegistersBuyerFragmentTilPassword, 6, getString(R.string.invalid_password))) {
            return;
        }

        if (!validationConfirmPassword(getActivity(), clientRegistersBuyerFragmentTilPassword, clientRigisterFragmentTilConfirmPassword)) {
            return;
        }

        if (ISCLIENT=="true") {

            if(mPath.equals("")){
                ToastCreator.onCreateErrorToast(getActivity(), getString(R.string.select_image));
//                showToast(getActivity(),"Please Select Image First");
                return;
            }

            if (!validationPhone(getActivity(), clientRegistersBuyerFragmentTilOrderPhone)) {

                return;
            }

        } else {
            if (!validationLength(clientRegistersBuyerFragmentTilOrderTime, getString(R.string.invalid_order_time), 3)) {
                return;
            }

            if (!validationLength(registersBuyerFragmentTilLeastRangeOfOrder, getString(R.string.invalid_least_range_of_order), 3)) {
                return;
            }

            if (!validationLength(registersBuyerFragmentTilDeleveryPrice, getString(R.string.invalid_delevery_price), 3)) {
                return;
            }

        }


        onCall();
    }

    private void onCall() {
        RequestBody name = convertToRequestBody(clientRegistersBuyerFragmentTilRestrantName.getEditText().getText().toString());
        RequestBody email = convertToRequestBody(clientRegistersBuyerFragmentTilEmail.getEditText().getText().toString());
        RequestBody phone =convertToRequestBody(clientRegistersBuyerFragmentTilOrderTime.getEditText().getText().toString());
        RequestBody orderTime = convertToRequestBody(clientRegistersBuyerFragmentTilOrderTime.getEditText().getText().toString());
        RequestBody password = convertToRequestBody(clientRegistersBuyerFragmentTilPassword.getEditText().getText().toString());
        RequestBody passwordConfirmation = convertToRequestBody(clientRigisterFragmentTilConfirmPassword.getEditText().getText().toString());
        RequestBody leastRangeOfOrder =convertToRequestBody(registersBuyerFragmentTilLeastRangeOfOrder.getEditText().getText().toString());
        RequestBody deleveryPrice = convertToRequestBody(registersBuyerFragmentTilDeleveryPrice.getEditText().getText().toString());
        MultipartBody.Part clientProfilePhoto= convertFileToMultipart(mPath,CLIENTPROFILEIMAGE);
        RequestBody regionId = convertToRequestBody(String.valueOf(neighborhoodsAdapter.selectedId));
        String passwordSave = clientRegistersBuyerFragmentTilPassword.getEditText().getText().toString();
        apiToken=clientData.getApiToken();
        token=new ClientFireBaseToken().getToken();
        Call<ClientGeneralResponse> clientCall;
        Call<ClientResetPasswordResponse> tokenCall;

        if (ISCLIENT=="true") {

            clientCall = getApiClient().clientRegistration(name, email,  password, passwordConfirmation,phone, regionId, clientProfilePhoto);
            tokenCall = getApiClient().clientSignUpToken(token, "android",apiToken);
            viewModel.makeGeneralRegisterationAndEditToServer(getActivity(),clientCall,tokenCall, passwordSave, true, true);

        } else {
            contactData.name=name;
            contactData.email=email;
            contactData.orderTime=orderTime;
            contactData.password=password;
            contactData.passwordSave=passwordSave;
            contactData.passwordConfirmation=passwordConfirmation;
            contactData.leastRangeOfOrder=leastRangeOfOrder;
            contactData.deleveryPrice=deleveryPrice;

            replaceFragment(getActivity().getSupportFragmentManager(), R.id.user_activity_fram, contactData);

        }

    }
}
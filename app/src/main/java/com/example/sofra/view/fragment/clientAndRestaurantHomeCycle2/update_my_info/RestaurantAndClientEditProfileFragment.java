package com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.update_my_info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sofra.R;
import com.example.sofra.adapter.SpinnerAdapter;
import com.example.sofra.data.model.clientLogin.ClientData;
import com.example.sofra.data.model.clientLogin.ClientGeneralResponse;
import com.example.sofra.data.model.clientResetPassword.ClientResetPasswordResponse;
import com.example.sofra.data.model.generalRespose.GeneralRespose;
import com.example.sofra.utils.ToastCreator;
import com.example.sofra.view.activity.HomeCycleActivity;
import com.example.sofra.view.fragment.BaSeFragment;
import com.example.sofra.view.fragment.splashCycle.SplashFragment;
import com.example.sofra.view.viewModel.ViewModelClient;
import com.google.android.material.textfield.TextInputLayout;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

import static com.example.sofra.data.api.ApiClient.getApiClient;
import static com.example.sofra.data.local.SharedPreferencesManger.LoadUserData;
import static com.example.sofra.utils.HelperMethod.convertFileToMultipart;
import static com.example.sofra.utils.HelperMethod.convertToRequestBody;
import static com.example.sofra.utils.HelperMethod.onLoadCirImageFromUrl;
import static com.example.sofra.utils.HelperMethod.openGalleryِAlpom;
import static com.example.sofra.utils.HelperMethod.replaceFragment;
import static com.example.sofra.utils.HelperMethod.showToast;
import static com.example.sofra.utils.validation.Validation.cleanError;
import static com.example.sofra.utils.validation.Validation.validationAllEmpty;
import static com.example.sofra.utils.validation.Validation.validationConfirmPassword;
import static com.example.sofra.utils.validation.Validation.validationEmail;
import static com.example.sofra.utils.validation.Validation.validationLength;
import static com.example.sofra.utils.validation.Validation.validationPassword;
import static com.example.sofra.utils.validation.Validation.validationPhone;


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
    private ClientData clientData;
    private int citySelectedId;
    private int regionSelectedId;
    private ViewModelClient viewModel;
    private static String mPath;
    private static ArrayList<AlbumFile> alpom= new ArrayList<>();
    private SpinnerAdapter citiesAdapter;
    private AdapterView.OnItemSelectedListener listener;
    private SpinnerAdapter neighborhoodsAdapter;
    private String CLIENTPROFILEIMAGE="photo";
    private boolean goLogin=false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_restaurant_and_client_edit_profile, container, false);
        ButterKnife.bind(this, root);
        setUpActivity();
        homeCycleActivity= (HomeCycleActivity) getActivity();
        clientData = LoadUserData(getActivity());
//        if(clientData!=null) {
        if (ISCLIENT=="true") {

            restaurantAndClientEditProfileFragmentTilOrderPhone.setVisibility(View.VISIBLE);
        }  if(ISCLIENT=="false"){
            restaurantAndClientEditProfileAddtionRestaurantPart.setVisibility(View.VISIBLE);

        }
        initListener();
        setUserData();
        setSpinner();
//        }else {
//            homeCycleActivity.goToRegisterFirst(getActivity());
//            goLogin = true;
//        }
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
        viewModel.makeResetAndNewPasswordAndTokenAndChangeStatusResponse().observe(this, new Observer<ClientResetPasswordResponse>() {
            @Override
            public void onChanged(@Nullable ClientResetPasswordResponse response) {
                if(response!=null){
                    if (response.getStatus() == 1) {
                        showToast(getActivity(),"success");

                    }  }
            }
        });
        viewModel.makegetSpinnerData().observe(this, new Observer<GeneralRespose>() {
            @Override
            public void onChanged(@Nullable GeneralRespose response) {
                if(response!=null){
                    if (response.getStatus() == 1) {
                        showToast(getActivity(),"success");

                    } else {
                        showToast(getActivity(),"error");

                    }
                }}
        });
    }
    private void setUserData() {


        restaurantAndClientEditProfileFragmentTilRestrantName.getEditText().setText(clientData.getUser().getName());
        restaurantAndClientEditProfileFragmentTilEmail.getEditText().setText(clientData.getUser().getEmail());
        onLoadCirImageFromUrl(restaurantAndClientEditProfileConectDataImageBotton, clientData.getUser().getPhotoUrl(), getContext());
        citySelectedId = clientData.getUser().getRegion().getCity().getId();
        regionSelectedId = clientData.getUser().getRegion().getId();
        restaurantAndClientEditProfileFragmentSpCity.setSelection(citySelectedId);
        rrestaurantAndClientEditProfileFragmentSpNeighborhood.setSelection(regionSelectedId);

        if (ISCLIENT=="true") {
            restaurantAndClientEditProfileFragmentTilOrderPhone.getEditText().setText(clientData.getUser().getPhone());
        }  if(ISCLIENT=="false"){
            restaurantAndClientEditProfileFragmentTilLeastRangeOfOrder.getEditText().setText(clientData.getUser().getMinimumCharger());
            restaurantAndClientEditProfileFragmentTilOrderTime.getEditText().setText(clientData.getUser().getDeliveryTime());
            restaurantAndClientEditProfileFragmentTilDeleveryPrice.getEditText().setText(clientData.getUser().getDeliveryCost());
            restaurantAndClientEditProfileConectDataFragmentTilPhone.getEditText().setText(clientData.getUser().getPhoto());
            restaurantAndClientEditProfileConectDataFragmentTilWhatsApp.getEditText().setText(clientData.getUser().getWhatsapp());
            if(clientData.getUser().getActivated()=="1"){
            restaurantAndClientEditProfileSwitch1.setChecked(true);}

        }
//        registersAndEditProfileFragmentTvTitle.setText(getString(R.string.profile));
//        registersAndEditProfileFragmentBtnStartCall.setText(getString(R.string.save));

    }

    private void setSpinner() {


        citiesAdapter = new SpinnerAdapter(getActivity());
        listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) {
                    rrestaurantAndClientEditProfileFragmentSpNeighborhood.setVisibility(View.GONE);

                } else {
                    int cityId = i;
                    neighborhoodsAdapter = new SpinnerAdapter(getActivity());
                    viewModel.getSpinnerData(getActivity(), rrestaurantAndClientEditProfileFragmentSpNeighborhood, neighborhoodsAdapter, getString(R.string.select_region),
                            getApiClient().getRegion(cityId), regionSelectedId, null);
                    rrestaurantAndClientEditProfileFragmentSpNeighborhood.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };

        viewModel.getSpinnerData(getActivity(), restaurantAndClientEditProfileFragmentSpCity, citiesAdapter, getString(R.string.select_city),
                getApiClient().getAllCities(), citySelectedId, listener);
    }
        @Override
    public void onBack() {
//        replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, homeCycleActivity.homeFragment);
        super.onBack();
        homeCycleActivity.navView.getMenu().getItem(0).setChecked(true);

    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        if (goLogin && LoadUserData(getActivity()) != null) {
//            goLogin = false;
//            replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, new RestaurantAndClientEditProfileFragment());
//        }
//    }

    @OnClick({R.id.restaurant_and_client_edit_profile_conect_data_image_botton, R.id.restaurant_and_client_edit_profile_update_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.restaurant_and_client_edit_profile_conect_data_image_botton:
                openGalleryِAlpom(getActivity(), alpom, new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        mPath=result.get(0).getPath();
                    }
                }, 1);
                break;
            case R.id.restaurant_and_client_edit_profile_update_btn:
                onValidation();
                break;
        }
    }

    private void onValidation() {

        List<EditText> editTexts = new ArrayList<>();
        List<TextInputLayout> textInputLayouts = new ArrayList<>();
        List<Spinner> spinners = new ArrayList<>();

        textInputLayouts.add(restaurantAndClientEditProfileFragmentTilRestrantName);
        textInputLayouts.add(restaurantAndClientEditProfileFragmentTilEmail);
        spinners.add(restaurantAndClientEditProfileFragmentSpCity);
        spinners.add(rrestaurantAndClientEditProfileFragmentSpNeighborhood);

        if (ISCLIENT=="true") {
            // photo
            textInputLayouts.add(restaurantAndClientEditProfileFragmentTilOrderPhone);
        }  if(ISCLIENT=="false"){

            textInputLayouts.add(restaurantAndClientEditProfileConectDataFragmentTilPhone);
            textInputLayouts.add(restaurantAndClientEditProfileFragmentTilDeleveryPrice);
            textInputLayouts.add(restaurantAndClientEditProfileFragmentTilLeastRangeOfOrder);
            textInputLayouts.add(restaurantAndClientEditProfileConectDataFragmentTilWhatsApp);
            textInputLayouts.add(restaurantAndClientEditProfileConectDataFragmentTilWhatsApp);
        }




        cleanError(textInputLayouts);

        if (!validationAllEmpty(editTexts, textInputLayouts, spinners, getString(R.string.empty))) {

            ToastCreator.onCreateErrorToast(getActivity(), getString(R.string.empty));
            return;
        }

        if (!validationLength(restaurantAndClientEditProfileFragmentTilRestrantName, getString(R.string.invalid_user_name), 3)) {
            return;
        }

        if (!validationEmail(getActivity(), restaurantAndClientEditProfileFragmentTilEmail)) {

            return;
        }

        if (restaurantAndClientEditProfileFragmentSpCity.getSelectedItemPosition() == 0) {
            ToastCreator.onCreateErrorToast(getActivity(), getString(R.string.select_city));
            return;
        }

        if (rrestaurantAndClientEditProfileFragmentSpNeighborhood.getSelectedItemPosition() == 0) {
            ToastCreator.onCreateErrorToast(getActivity(), getString(R.string.select_region));
            return;
        }

        if(mPath.equals("")){
            ToastCreator.onCreateErrorToast(getActivity(), getString(R.string.select_image));
//                showToast(getActivity(),"Please Select Image First");
            return;
        }

        if (ISCLIENT=="true") {



            if (!validationPhone(getActivity(), restaurantAndClientEditProfileConectDataFragmentTilPhone)) {

                return;
            }

        }  if(ISCLIENT=="false"){
            if (!validationLength(restaurantAndClientEditProfileFragmentTilOrderTime, getString(R.string.invalid_order_time), 3)) {
                return;
            }

            if (!validationLength(restaurantAndClientEditProfileFragmentTilLeastRangeOfOrder, getString(R.string.invalid_least_range_of_order), 3)) {
                return;
            }

            if (!validationLength(restaurantAndClientEditProfileFragmentTilDeleveryPrice, getString(R.string.invalid_delevery_price), 3)) {
                return;
            }
            if (!validationPhone(getActivity(), restaurantAndClientEditProfileConectDataFragmentTilPhone)) {

                return;
            }

            if (!validationPhone(getActivity(), restaurantAndClientEditProfileConectDataFragmentTilWhatsApp)) {

                return;
            }

        }


        onCall();
    }

    private void onCall() {
        RequestBody name = convertToRequestBody(restaurantAndClientEditProfileFragmentTilRestrantName.getEditText().getText().toString());
        RequestBody email = convertToRequestBody(restaurantAndClientEditProfileFragmentTilEmail.getEditText().getText().toString());
        RequestBody phone = convertToRequestBody(restaurantAndClientEditProfileFragmentTilOrderPhone.getEditText().getText().toString());
        RequestBody deleveryTime = convertToRequestBody(restaurantAndClientEditProfileFragmentTilOrderTime.getEditText().getText().toString());
        RequestBody leastRangeOfOrder = convertToRequestBody(restaurantAndClientEditProfileFragmentTilLeastRangeOfOrder.getEditText().getText().toString());
        RequestBody deleveryPrice = convertToRequestBody(restaurantAndClientEditProfileFragmentTilDeleveryPrice.getEditText().getText().toString());
        MultipartBody.Part ProfilePhoto = convertFileToMultipart(mPath, CLIENTPROFILEIMAGE);
        RequestBody regionId = convertToRequestBody(String.valueOf(neighborhoodsAdapter.selectedId));
        RequestBody apiToken = convertToRequestBody(clientData.getApiToken());
        RequestBody restaurantPhone = convertToRequestBody(restaurantAndClientEditProfileConectDataFragmentTilPhone.getEditText().getText().toString());
        RequestBody availabilty;
        String state;
        if (restaurantAndClientEditProfileSwitch1.isChecked()){
             availabilty = convertToRequestBody("opened");
             state="opened";
    }else {
            availabilty = convertToRequestBody("closed");
            state="closed";
        }
        String passwordSave = clientData.getUser().getPassword();
        Call<ClientGeneralResponse> clientCall=null;
        Call<ClientResetPasswordResponse> statusCall = null;

        if (ISCLIENT=="true") {

            clientCall = getApiClient().editClientProfile(apiToken, name,  email, phone,regionId, ProfilePhoto);

        }  if(ISCLIENT=="false"){

            clientCall = getApiClient().editRestaurantProfile(email, name,  restaurantPhone, regionId,deleveryPrice, leastRangeOfOrder,availabilty,ProfilePhoto,apiToken,deleveryTime);
            statusCall = getApiClient().restaurantChangeState(state,clientData.getApiToken());


        }
        viewModel.makeGeneralRegisterationAndEditToServer(getActivity(),clientCall, passwordSave, true, false);
        viewModel.getAndMakeResetAndNewPasswordAndTokenAndChangeStatus(getActivity(),statusCall,true);


    }
}
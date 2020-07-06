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
import com.example.sofra.data.model.clientGetAllNotofications.ClientFireBaseToken;
import com.example.sofra.data.model.clientLogin.ClientData;
import com.example.sofra.data.model.clientLogin.ClientGeneralResponse;
import com.example.sofra.data.model.clientResetPassword.ClientResetPasswordResponse;
import com.example.sofra.utils.ToastCreator;
import com.example.sofra.view.fragment.BaSeFragment;
import com.example.sofra.view.viewModel.ViewModelClient;
import com.google.android.material.textfield.TextInputLayout;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

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
import static com.example.sofra.data.local.SharedPreferencesManger.LoadUserData;
import static com.example.sofra.utils.HelperMethod.convertFileToMultipart;
import static com.example.sofra.utils.HelperMethod.convertToRequestBody;
import static com.example.sofra.utils.HelperMethod.onLoadImageFromUrl;
import static com.example.sofra.utils.HelperMethod.openGallery;
import static com.example.sofra.utils.HelperMethod.openGalleryِAlpom;
import static com.example.sofra.utils.HelperMethod.replaceFragment;
import static com.example.sofra.utils.HelperMethod.showToast;
import static com.example.sofra.utils.validation.Validation.cleanError;
import static com.example.sofra.utils.validation.Validation.validationPhone;
import static com.example.sofra.utils.validation.Validation.validationTextInputLayoutListEmpty;


public class ContactDataFragment extends BaSeFragment {


    private static final String RESTAURANTPROFILEIMAGE ="photo" ;
    public RequestBody name;
    public RequestBody email;
    public RequestBody password;
    public String passwordSave;
    public RequestBody passwordConfirmation;
    public RequestBody phone;
    public RequestBody whatsApp;
    public RequestBody regionId;
    public RequestBody deleveryPrice;
    public RequestBody leastRangeOfOrder;
    public RequestBody orderTime;
    @BindView(R.id.conect_data_fragment_til_phone)
    TextInputLayout conectDataFragmentTilPhone;
    @BindView(R.id.client_conect_data_img_restraunt_image_botton)
    ImageView clientConectDataImgRestrauntImageBotton;
    @BindView(R.id.conect_data_buer_register_btn)
    Button conectDataBuerRegisterBtn;
    @BindView(R.id.conect_data_fragment_til_whats_app)
    TextInputLayout conectDataFragmentTilWhatsApp;
    private ViewModelClient viewModel;
    private static ArrayList<AlbumFile> alpom = new ArrayList<>();
    private static String mPath;
    private static final String CLIENTPROFILEIMAGE = "photo3";//    private String apiToken;
//    private String token;
//    private ClientData clientData;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_contact_data, container, false);
        ButterKnife.bind(this, root);
        initListener();
//        clientData = LoadUserData(getActivity());


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

    @Override
    public void onBack() {
        replaceFragment(getActivity().getSupportFragmentManager(), R.id.user_activity_fram, new RestaurantAndClientRegisterFragment());

    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
//            mPath = data.getStringExtra(ImagePicker.EXTRA_IMAGE_PATH);
//            onLoadImageFromUrl(clientConectDataImgRestrauntImageBotton, mPath, getContext());
//
//            //Your Code
//        }
//    }

    @OnClick({R.id.client_conect_data_img_restraunt_image_botton, R.id.conect_data_buer_register_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.client_conect_data_img_restraunt_image_botton:
//                openGallery(getActivity());
                openGalleryِAlpom(getActivity(), alpom, new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        mPath = result.get(0).getPath();
                        onLoadImageFromUrl(clientConectDataImgRestrauntImageBotton, mPath, getContext());
                    }
                }, 1);
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

//        if (mPath.equalsIgnoreCase("")) {
//            ToastCreator.onCreateErrorToast(getActivity(), getString(R.string.select_image));
////                showToast(getActivity(),"Please Select Image First");
//            return;
//        }

        if (!validationPhone(getActivity(), conectDataFragmentTilPhone)) {

            return;
        }

        if (!validationPhone(getActivity(), conectDataFragmentTilWhatsApp)) {

            return;
        }




        onCall();
    }

    private void onCall() {


        phone =convertToRequestBody(conectDataFragmentTilPhone.getEditText().getText().toString());
        whatsApp = convertToRequestBody(conectDataFragmentTilWhatsApp.getEditText().getText().toString());
        MultipartBody.Part restaurantProfilePhoto = convertFileToMultipart(mPath,RESTAURANTPROFILEIMAGE);
//        apiToken=clientData.getApiToken();
//        token=new ClientFireBaseToken().getToken();
        Call<ClientGeneralResponse> clientCall;
        Call<ClientResetPasswordResponse> tokenCall;
//        tokenCall = getApiClient().restaurantSignUpToken(token, "android",apiToken);
        clientCall = getApiClient().restaurantRegistration(name, email, password, passwordConfirmation, phone,whatsApp, regionId, deleveryPrice,leastRangeOfOrder,restaurantProfilePhoto,orderTime);
        viewModel.makeGeneralRegisterationAndEditToServer(getActivity(), clientCall, passwordSave, true, true);



    }
}
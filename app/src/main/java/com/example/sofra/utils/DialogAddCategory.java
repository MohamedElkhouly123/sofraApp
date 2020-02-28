package com.example.sofra.utils;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.sofra.R;
import com.example.sofra.data.model.clientLogin.ClientData;
import com.example.sofra.data.model.restaurantCategoryResponse.RestaurantCategoryResponse;
import com.google.android.material.textfield.TextInputLayout;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

import static com.example.sofra.data.api.ApiClient.getApiClient;
import static com.example.sofra.data.local.SharedPreferencesManger.LoadUserData;
import static com.example.sofra.utils.GeneralRequest.deleteAndUpdateItemCallBack;
import static com.example.sofra.utils.HelperMethod.alertDialog;
import static com.example.sofra.utils.HelperMethod.convertFileToMultipart;
import static com.example.sofra.utils.HelperMethod.convertToRequestBody;
import static com.example.sofra.utils.HelperMethod.openGalleryِAlpom;
import static com.example.sofra.utils.HelperMethod.showToast;

public class DialogAddCategory extends Dialog {



    private Activity activity;
    private Context context;
    private boolean Cancelable;
    private ClientData clientData;
    private static final String CLIENTPROFILEIMAGE ="CLIENTPROFILEIMAGE" ;
    private String mPath;
    private ArrayList<AlbumFile> alpom= new ArrayList<>();
    @BindView(R.id.restaurant_add_category_dialog_img_add_photo)
    CircleImageView restaurantAddCategoryDialogImgAddPhoto;
    @BindView(R.id.set_new_password_bin_code_etxt)
    TextInputLayout restaurantAddCategoryDialogTilCategoryName;
    String addOrUpdate;

    private static final int RESULT_LOAD_IMAGE = 1;

    public DialogAddCategory(Context context, Activity activity, boolean Cancelable) {
        super(context);
        this.activity = activity;
        this.context = context;
        this.Cancelable = Cancelable;
        onCreate();
    }

    public void onCreate() {
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_restaurant_add_category);
            ButterKnife.bind(this);

            DialogAddCategory.this.setCancelable(Cancelable);

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
            getWindow().setGravity(Gravity.CENTER);
            show();
//            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
////            window.setBackgroundDrawableResource(R.color.colorTransparent);
//            window.setGravity(Gravity.CENTER);
//            dialog.show();
        } catch (Exception e) {

        }

    }


    @OnClick({R.id.restaurant_add_category_dialog_img_add_photo, R.id.restaurant_add_category_dialog_add_and_update_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.restaurant_add_category_dialog_img_add_photo:
                openGalleryِAlpom(activity, alpom, new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        mPath=result.get(0).getPath();
                    }
                }, 1);
                break;
            case R.id.restaurant_add_category_dialog_add_and_update_btn:
                showToast(activity, "here");
                clientData = LoadUserData(activity);
                RequestBody updatedCaegoryName=convertToRequestBody(restaurantAddCategoryDialogTilCategoryName.getEditText().getText().toString());
                RequestBody updatedCategoryApiToken=convertToRequestBody(clientData.getApiToken());
                MultipartBody.Part updatedCategoryPhoto=convertFileToMultipart(mPath,CLIENTPROFILEIMAGE);
                Call<RestaurantCategoryResponse> updateItemCal = getApiClient().restaurantNewCategory( updatedCaegoryName,updatedCategoryPhoto,updatedCategoryApiToken);
                deleteAndUpdateItemCallBack(activity,updateItemCal);
                alertDialog.dismiss();
                break;
        }
    }
}


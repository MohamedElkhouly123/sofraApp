package com.example.sofra.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.example.sofra.R;
import com.example.sofra.data.model.clientLogin.ClientData;
import com.example.sofra.data.model.restaurantCategoryResponse.RestaurantCategoryData;
import com.example.sofra.data.model.restaurantCategoryResponse.RestaurantCategoryResponse;
import com.example.sofra.view.activity.BaseActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.yanzhenjie.album.Action;
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
import static com.example.sofra.utils.HelperMethod.onLoadImageFromUrl;
import static com.example.sofra.utils.HelperMethod.openGalleryِAlpom;
import static com.example.sofra.utils.HelperMethod.showToast;


public class RestaurantAddAndUpdateCategoryDialog  {

    private Activity activity;
    private static ClientData clientData;
    private static final String CLIENTPROFILEIMAGE ="photo" ;
    private static String mPath;
    private static ArrayList<AlbumFile> alpom= new ArrayList<>();
    static CircleImageView restaurantAddCategoryDialogImgAddPhoto;
    static TextInputLayout restaurantAddCategoryDialogTilCategoryName;
    static Button restaurantAddCategoryDialogAddBtn;
    String addOrUpdate;
//    static AlertDialog alertDialog;
    private Context context;
//    private boolean Cancelable;





    public static void showDialog(@NonNull Activity activity,@NonNull Context context, @Nullable String addOrUpdate) {
//        final RestaurantCategoryData restaurantDataListOfPossision
        try {
//            ButterKnife.bind(activity);
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_restaurant_add_category, null);
            alertDialogBuilder.setView(view);
            alertDialogBuilder.setCancelable(true);
            final AlertDialog dialog = alertDialogBuilder.create();
            dialog.show();

             restaurantAddCategoryDialogTilCategoryName = (TextInputLayout) view.findViewById(R.id.restaurant_add_category_dialog_til_category_name);
             restaurantAddCategoryDialogAddBtn = (Button) view.findViewById(R.id.restaurant_add_category_dialog_add_and_update_btn);
             restaurantAddCategoryDialogImgAddPhoto = (CircleImageView) view.findViewById(R.id.restaurant_add_category_dialog_img_add_photo);
            if (addOrUpdate.equals("update")) {
                restaurantAddCategoryDialogAddBtn.setText(R.string.update_dialog);
//                restaurantAddCategoryDialogTilCategoryName.getEditText().setText(restaurantDataListOfPossision.getName());
//                onLoadImageFromUrl(restaurantAddCategoryDialogImgAddPhoto, restaurantDataListOfPossision.getPhotoUrl(), activity);
            }else {
                restaurantAddCategoryDialogAddBtn.setText(R.string.add);

            }
            clientData = LoadUserData(activity);
            restaurantAddCategoryDialogAddBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mPath != null&&restaurantAddCategoryDialogTilCategoryName.getEditText().getText().toString()!=""){
                       RequestBody updatedCaegoryName=convertToRequestBody(restaurantAddCategoryDialogTilCategoryName.getEditText().getText().toString());
                       RequestBody updatedCategoryApiToken=convertToRequestBody(clientData.getApiToken());
//                        RequestBody updatedCategoryApiToken=convertToRequestBody("Jptu3JVmDXGpJEaQO9ZrjRg5RuAVCo45OC2AcOKqbVZPmu0ZJPN3T1sm0cWx");
                       MultipartBody.Part updatedCategoryPhoto=convertFileToMultipart(mPath,CLIENTPROFILEIMAGE);
                        showToast(activity, String.valueOf(updatedCategoryPhoto));
                        Call<RestaurantCategoryResponse> updateItemCal = getApiClient().restaurantNewCategory( updatedCaegoryName,updatedCategoryPhoto,updatedCategoryApiToken);
                       deleteAndUpdateItemCallBack(activity,updateItemCal);
                        dialog.dismiss();
                }else {

                    }

                }
            });
            restaurantAddCategoryDialogImgAddPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openGalleryِAlpom(activity, alpom, new Action<ArrayList<AlbumFile>>() {
                        @Override
                        public void onAction(@NonNull ArrayList<AlbumFile> result) {
                            mPath=result.get(0).getPath();
                        }
                    }, 1);
//                    showToast(activity, restaurantAddCategoryDialogTilCategoryName.getEditText().getText().toString());
                }
            });


        } catch (Exception e) {

        }
    }

}

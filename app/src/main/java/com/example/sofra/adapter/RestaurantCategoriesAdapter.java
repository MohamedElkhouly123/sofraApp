package com.example.sofra.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.sofra.R;
import com.example.sofra.data.model.clientLogin.ClientData;
import com.example.sofra.data.model.restaurantCategoryResponse.RestaurantCategoriesListData;
import com.example.sofra.data.model.restaurantCategoryResponse.RestaurantCategoryResponse;
import com.example.sofra.utils.HelperMethod;
import com.example.sofra.utils.ToastCreator;
import com.example.sofra.view.activity.BaseActivity;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.home.RestaurantCategoryMenuSubCategorysFragment;
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
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

import static com.example.sofra.data.api.ApiClient.getApiClient;
import static com.example.sofra.data.local.SharedPreferencesManger.LoadUserData;
import static com.example.sofra.utils.HelperMethod.alertDialog;
import static com.example.sofra.utils.HelperMethod.convertFileToMultipart;
import static com.example.sofra.utils.HelperMethod.convertToRequestBody;
import static com.example.sofra.utils.HelperMethod.dismissProgressDialog;
import static com.example.sofra.utils.HelperMethod.onLoadImageFromUrl;
import static com.example.sofra.utils.HelperMethod.openGallery;
import static com.example.sofra.utils.HelperMethod.openGalleryِAlpom;
import static com.example.sofra.utils.HelperMethod.progressDialog;
import static com.example.sofra.utils.HelperMethod.replaceFragment;
import static com.example.sofra.utils.ToastCreator.onCreateErrorToast;


public class RestaurantCategoriesAdapter extends RecyclerView.Adapter<RestaurantCategoriesAdapter.ViewHolder> {


    private Context context;
    private BaseActivity activity;
    private List<RestaurantCategoriesListData> restaurantDataList = new ArrayList<>();
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private ClientData clientData;
    private String lang;
    private static final String CLIENTPROFILEIMAGE ="CLIENTPROFILEIMAGE" ;
    private String mPath;
    private ArrayList<AlbumFile> alpom= new ArrayList<>();
    public RestaurantCategoriesAdapter(Activity activity, List<RestaurantCategoriesListData> restaurantDataList) {
        this.context = activity;
        this.activity = (BaseActivity) activity;
        this.restaurantDataList = restaurantDataList;
        viewBinderHelper.setOpenOnlyOne(true);
        clientData = LoadUserData(activity);
        lang = "eg";
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_item_restaurant_category,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setSwipe(holder, position);
        setAction(holder, position);

    }

    @SuppressLint("SetTextI18n")
    private void setData(ViewHolder holder, int position) {


        try {
            holder.position = position;
            holder.itemRestaurantCategoryTvName.setText(
                    restaurantDataList.get(position).getName());

            onLoadImageFromUrl(holder.itemRestaurantCategoryImgPhoto, restaurantDataList.get(position).getPhotoUrl(), context);


        } catch (Exception e) {

        }


    }

    private void setAction(ViewHolder holder, int position) {

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                replaceFragment(activity.getSupportFragmentManager(), R.id.home_activity_fram, new RestaurantCategoryMenuSubCategorysFragment());
            }
        });
    }

    private void setSwipe(final ViewHolder holder, final int position) {
        holder.itemRestaurantCategorySwipeLayout.computeScroll();
        if (lang.equals("ar")) {
            holder.itemRestaurantCategorySwipeLayout.setDragEdge(SwipeRevealLayout.DRAG_EDGE_LEFT);
        } else {
            holder.itemRestaurantCategorySwipeLayout.setDragEdge(SwipeRevealLayout.DRAG_EDGE_RIGHT);
        }

        viewBinderHelper.bind(holder.itemRestaurantCategorySwipeLayout, String.valueOf(restaurantDataList.get(position).getId()));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewBinderHelper.openLayout(String.valueOf(restaurantDataList.get(position).getId()));
                holder.itemRestaurantCategorySwipeLayout.computeScroll();
            }
        });

    }

    @Override
    public int getItemCount() {
        return restaurantDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_restaurant_category_img_edit)
        ImageView itemRestaurantCategoryImgEdit;
        @BindView(R.id.item_restaurant_category_img_remove)
        ImageView itemRestaurantCategoryImgRemove;
        @BindView(R.id.item_restaurant_category_img_photo)
        ImageView itemRestaurantCategoryImgPhoto;
        @BindView(R.id.item_restaurant_category_tv_name)
        TextView itemRestaurantCategoryTvName;
        @BindView(R.id.item_restaurant_category_swipe_layout)
        SwipeRevealLayout itemRestaurantCategorySwipeLayout;
        @BindView(R.id.restaurant_add_category_dialog_img_add_photo)
        CircleImageView restaurantAddCategoryDialogImgAddPhoto;
        @BindView(R.id.set_new_password_bin_code_etxt)
        TextInputLayout restaurantAddCategoryDialogTilCategoryName;
        @BindView(R.id.restaurant_add_category_dialog_add_and_update_btn)
        Button restaurantAddCategoryDialogAddBtn;
        private View view;
        private int position;

        private ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }


        @OnClick({R.id.item_restaurant_category_img_edit, R.id.item_restaurant_category_img_remove,R.id.restaurant_add_category_dialog_img_add_photo, R.id.restaurant_add_category_dialog_add_and_update_btn})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.item_restaurant_category_img_edit:
                    restaurantAddCategoryDialogAddBtn.setText("تعديل");
                    showDialog();
                    break;
                case R.id.item_restaurant_category_img_remove:
                    Call<RestaurantCategoryResponse> deletItemCal = getApiClient().restaurantDeleteCategory(clientData.getApiToken(), restaurantDataList.get(position).getId());
                    deleteAndUpdateItemCallBack(deletItemCal);
                    restaurantDataList.remove(position);
                    notifyItemRemoved(position);
                    break;
                case R.id.restaurant_add_category_dialog_img_add_photo:
//                    openGallery(activity);
                    openGalleryِAlpom(context, alpom, new Action<ArrayList<AlbumFile>>() {
                        @Override
                        public void onAction(@NonNull ArrayList<AlbumFile> result) {
                            mPath=result.get(0).getPath();
                        }
                    }, 1);

                    break;
                case R.id.restaurant_add_category_dialog_add_and_update_btn:
                    RequestBody updatedCaegoryName=convertToRequestBody(restaurantAddCategoryDialogTilCategoryName.getEditText().getText().toString());
                    RequestBody updatedCategoryApiToken=convertToRequestBody(clientData.getApiToken());
                    RequestBody updatedCategoryId= convertToRequestBody(String.valueOf(restaurantDataList.get(position).getId()));
                    MultipartBody.Part updatedCategoryPhoto=convertFileToMultipart(mPath,CLIENTPROFILEIMAGE);
                    Call<RestaurantCategoryResponse> updateItemCal = getApiClient().restaurantUpdateCategory( updatedCaegoryName,updatedCategoryPhoto,updatedCategoryApiToken,updatedCategoryId);
                    deleteAndUpdateItemCallBack(updateItemCal);

                    break;

            }
        }

        private void showDialog() {

            try {
                final View view = activity.getLayoutInflater().inflate(R.layout.dialog_restaurant_add_category, null);
//            alertDialog = new AlertDialog.Builder(HomeFragment.this).create();
                alertDialog = new AlertDialog.Builder(context).create();
                restaurantAddCategoryDialogTilCategoryName.getEditText().setText(restaurantDataList.get(position).getName());
                onLoadImageFromUrl(restaurantAddCategoryDialogImgAddPhoto, restaurantDataList.get(position).getPhotoUrl(), context);
                alertDialog.setCancelable(false);
                alertDialog.setView(view);
                alertDialog.show();

            } catch (Exception e) {

            }

        }

        private void deleteAndUpdateItemCallBack(final Call<RestaurantCategoryResponse> method) {
            if (progressDialog == null) {
                HelperMethod.showProgressDialog(activity, activity.getString(R.string.wait));
            } else {
                if (!progressDialog.isShowing()) {
                    HelperMethod.showProgressDialog(activity, activity.getString(R.string.wait));
                }
            }

            method.enqueue(new Callback<RestaurantCategoryResponse>() {
                @Override
                public void onResponse(Call<RestaurantCategoryResponse> call, Response<RestaurantCategoryResponse> response) {

                    if (response.body() != null) {
                        try {
                            dismissProgressDialog();

                            if (response.body().getStatus() == 1) {

                                ToastCreator.onCreateSuccessToast(activity, response.body().getMsg());
                            } else {
                                onCreateErrorToast(activity, response.body().getMsg());
                            }
                        } catch (Exception e) {

                        }
                    }
                }

                @Override
                public void onFailure(Call<RestaurantCategoryResponse> call, Throwable t) {
                    dismissProgressDialog();
                    onCreateErrorToast(activity, activity.getString(R.string.error));
                }
            });
        }



    }
}

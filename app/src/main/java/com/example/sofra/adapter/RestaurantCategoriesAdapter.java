package com.example.sofra.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.sofra.R;
import com.example.sofra.data.model.clientLogin.ClientData;
import com.example.sofra.data.model.restaurantCategoryResponse.RestaurantCategoryFiltterData;
import com.example.sofra.data.model.restaurantCategoryResponse.RestaurantCategoryResponse;
import com.example.sofra.utils.RestaurantAddAndUpdateCategoryDialog;
import com.example.sofra.view.activity.BaseActivity;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.home.RestaurantCategoryMenuSubCategorysFragment;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

import static com.example.sofra.data.api.ApiClient.getApiClient;
import static com.example.sofra.data.local.SharedPreferencesManger.LoadUserData;
import static com.example.sofra.utils.GeneralRequest.deleteAndUpdateItemCallBack;
import static com.example.sofra.utils.HelperMethod.onLoadImageFromUrl;
import static com.example.sofra.utils.HelperMethod.replaceFragment;
import static com.example.sofra.utils.HelperMethod.showToast;
import static com.example.sofra.utils.RestaurantAddAndUpdateCategoryDialog.showDialog;
import static com.example.sofra.utils.ToastCreator.onCreateErrorToast;
import static com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.home.HomeFragment.isDialogDataAddSuccess;


public class RestaurantCategoriesAdapter extends RecyclerView.Adapter<RestaurantCategoriesAdapter.ViewHolder> {


    private Context context;
    private BaseActivity activity;
    private List<RestaurantCategoryFiltterData> restaurantDataList = new ArrayList<>();
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private ClientData clientData;
    private String lang;
    private int lastPosition = -1;
    private static final String CLIENTPROFILEIMAGE ="CLIENTPROFILEIMAGE" ;
    public static String dialogCategoryPath;
    public static String dialogCategoryName;
    private ArrayList<AlbumFile> alpom= new ArrayList<>();
    public RestaurantCategoriesAdapter(Activity activity, List<RestaurantCategoryFiltterData> restaurantDataList) {
        this.context = activity;
        this.activity = (BaseActivity) activity;
        this.restaurantDataList.clear();
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
        setAnimation(holder.itemView, position, holder);

    }

    @SuppressLint("SetTextI18n")
    private void setData(ViewHolder holder, int position) {


        try {
            holder.position = position;
            holder.itemRestaurantCategoryTvName.setText(restaurantDataList.get(position).getName());

            onLoadImageFromUrl(holder.itemRestaurantCategoryImgPhoto, restaurantDataList.get(position).getPhotoUrl(), context);


        } catch (Exception e) {

        }


    }

    private void setAnimation(View viewToAnimate, int position, ViewHolder holder) {
        Animation animation = null;
//        if (position > lastPosition) {
            animation = AnimationUtils.loadAnimation(activity, R.anim.rv_animation_down_to_up);
//            lastPosition = position;
//        }
//        else if (position < lastPosition) {
//
//            animation = AnimationUtils.loadAnimation(activity,R.anim.rv_animation_up_to_down);
//            lastPosition = -1;
//
//        }
        viewToAnimate.startAnimation(animation);

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


        @BindView(R.id.item_restaurant_category_img_photo)
        ImageView itemRestaurantCategoryImgPhoto;
        @BindView(R.id.item_restaurant_category_tv_name)
        TextView itemRestaurantCategoryTvName;
        @BindView(R.id.item_restaurant_category_swipe_layout)
        SwipeRevealLayout itemRestaurantCategorySwipeLayout;

        private View view;
        private int position;

        private ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }


        @OnClick({R.id.item_restaurant_category_img_edit, R.id.item_restaurant_category_img_remove})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.item_restaurant_category_img_edit:
                    isDialogDataAddSuccess = true;
                    RestaurantAddAndUpdateCategoryDialog restaurantAddAndUpdateCategoryDialog = new RestaurantAddAndUpdateCategoryDialog();
                    restaurantAddAndUpdateCategoryDialog.restaurantDataListOfPossision = restaurantDataList.get(position);
                    showDialog(activity, context, "update");
                    showToast(activity, dialogCategoryName + "\n" + dialogCategoryPath);
//                    if (dialogCategoryName != null && isDialogDataAddSuccess) {
                        restaurantDataList.get(position).setName(dialogCategoryName);
                        restaurantDataList.get(position).setPhotoUrl(dialogCategoryPath);
                        notifyItemChanged(position);
//                    }
                    break;
                case R.id.item_restaurant_category_img_remove:
                    showDeleteDialog();

                    break;
//                case R.id.restaurant_add_category_dialog_img_add_photo:
////                    openGallery(activity);
//                    openGalleryِAlpom(context, alpom, new Action<ArrayList<AlbumFile>>() {
//                        @Override
//                        public void onAction(@NonNull ArrayList<AlbumFile> result) {
//                            mPath=result.get(0).getPath();
//                        }
//                    }, 1);
//
//                    break;
//                case R.id.restaurant_add_category_dialog_add_and_update_btn:
//                    RequestBody updatedCaegoryName=convertToRequestBody(restaurantAddCategoryDialogTilCategoryName.getEditText().getText().toString());
//                    RequestBody updatedCategoryApiToken=convertToRequestBody(clientData.getApiToken());
//                    RequestBody updatedCategoryId= convertToRequestBody(String.valueOf(restaurantDataList.get(position).getId()));
//                    MultipartBody.Part updatedCategoryPhoto=convertFileToMultipart(mPath,CLIENTPROFILEIMAGE);
//                    Call<RestaurantCategoryResponse> updateItemCal = getApiClient().restaurantUpdateCategory( updatedCaegoryName,updatedCategoryPhoto,updatedCategoryApiToken,updatedCategoryId);
//                    deleteAndUpdateItemCallBack(activity,updateItemCal);
//                    restaurantDataList.get(position).setName(restaurantAddCategoryDialogTilCategoryName.getEditText().getText().toString());
//                    restaurantDataList.get(position).setPhotoUrl(mPath);
//                    notifyItemChanged(position);
//                    break;

            }
        }

        private void showDeleteDialog(){
            try {
//                final View view = activity.getLayoutInflater().inflate(R.layout.dialog_restaurant_add_category, null);
//            alertDialog = new AlertDialog.Builder(HomeFragment.this).create();
                AlertDialog alertDialog;
                 alertDialog = new AlertDialog.Builder(activity).create();
                alertDialog.setTitle("Delete");
                alertDialog.setMessage("هل انت متاكد من الحذف ؟");
                alertDialog.setCancelable(false);

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "تاكيد", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Call<RestaurantCategoryResponse> deletItemCal = getApiClient().restaurantDeleteCategory(clientData.getApiToken(), restaurantDataList.get(position).getId());
                        deleteAndUpdateItemCallBack(activity, deletItemCal);
                        restaurantDataList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, restaurantDataList.size());

                    }
                });


                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "الغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss() ;
                    }
                });

//                alertDialog.setView(view);
                alertDialog.setOnShowListener( new DialogInterface.OnShowListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onShow(DialogInterface arg0) {
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.pink);
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.pink);

                    }
                });

                                alertDialog.show();

            } catch (Exception e) {

            }
        }



    }
}

//    public void removeItem(int position){
//        mData.remove(position);
//        notifyItemRemoved(position);
//        notifyItemRangeChanged(position, mData.size());
//    }
//    and then I would add the item at that particular position as shown below:
//
//public void addItem(int position, Landscape landscape){
//        mData.add(position, landscape);
//        notifyItemInserted(position);
//        notifyItemRangeChanged(position, mData.size());
//        }
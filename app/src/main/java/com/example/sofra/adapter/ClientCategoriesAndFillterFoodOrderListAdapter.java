package com.example.sofra.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.data.model.clientLogin.ClientData;
import com.example.sofra.data.model.restaurantCategoryResponse.RestaurantCategoryFiltterData;
import com.example.sofra.utils.RVAdapterCallback;
import com.example.sofra.view.activity.HomeCycleActivity;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.home.FoodMenueFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.sofra.data.local.SharedPreferencesManger.LoadUserData;
import static com.example.sofra.utils.HelperMethod.onLoadImageFromUrl;
import static com.example.sofra.utils.HelperMethod.showToast;

public class ClientCategoriesAndFillterFoodOrderListAdapter extends RecyclerView.Adapter<ClientCategoriesAndFillterFoodOrderListAdapter.ViewHolder> {



    private Context context;
    private Activity activity;
    private List<RestaurantCategoryFiltterData> clientCategoriesAndFillterFoodOrderDataList = new ArrayList<>();
    private ClientData clientData;
    private RVAdapterCallback rvAdapterCallback;
//    private ApiService apiService;

    public ClientCategoriesAndFillterFoodOrderListAdapter(Context context,
                                                          Activity activity,RVAdapterCallback callback,
                                                          List<RestaurantCategoryFiltterData> clientCategoriesAndFillterFoodOrderDataList
    ) {
        this.context = context;
        this.activity = activity;
        this.rvAdapterCallback=callback;
        this.clientCategoriesAndFillterFoodOrderDataList = clientCategoriesAndFillterFoodOrderDataList;
        clientData = LoadUserData(activity);
//        showToast(activity, "addapter"+this.clientCategoriesAndFillterFoodOrderDataList.size());


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_item_client_category_food_orders_restaurant,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
        setAnimation(holder.itemView, position, holder);
    }

    private void setData(ViewHolder holder, int position) {
        try {
            holder.position = position;
            onLoadImageFromUrl(holder.itemClientCategoryFillterRestaurantImgRestaurantLogo, clientCategoriesAndFillterFoodOrderDataList.get(position).getPhotoUrl(), context);
            holder.itemClientCategoryFillterTvCategoryName.setText(clientCategoriesAndFillterFoodOrderDataList.get(position).getName());

        } catch (Exception e) {

        }

    }
    private void setAnimation(View viewToAnimate, int position, ViewHolder holder) {
        Animation animation = null;
        animation = AnimationUtils.loadAnimation(activity, R.anim.rv_animation_left_to_right);
        viewToAnimate.startAnimation(animation);

    }
    private void setAction(final ViewHolder holder, final int position) {

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodMenueFragment foodMenueFragment=new FoodMenueFragment();
//                foodMenueFragment.clientGetRestaurantsItemsListByFilter(1, String.valueOf(clientCategoriesAndFillterFoodOrderDataList.get(position).getId()),context,activity);
//                foodMenueFragment.useFilter=true;
//                showToast(activity, "true");
                foodMenueFragment.categoryId=String.valueOf(clientCategoriesAndFillterFoodOrderDataList.get(position).getId());
                rvAdapterCallback.onMethodCallback();

//                HomeCycleActivity navigationActivity = (HomeCycleActivity) activity;
//                postDetails.postsData = postsDataList.get(position);
//                HelperMethod.replaceFragment(navigationActivity.getSupportFragmentManager(), R.id.home_activity_fram, new RestaurantCategoryTabsFragment());

            }
        });

    }


    @Override
    public int getItemCount() {
        return clientCategoriesAndFillterFoodOrderDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_client_category_fillter_restaurant_img_restaurant_logo)
        CircleImageView itemClientCategoryFillterRestaurantImgRestaurantLogo;
        @BindView(R.id.item_client_category_fillter_tv_category_name)
        TextView itemClientCategoryFillterTvCategoryName;
        private View view;
        private int position;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}

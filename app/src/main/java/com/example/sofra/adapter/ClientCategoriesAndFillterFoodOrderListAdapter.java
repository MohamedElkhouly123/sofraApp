package com.example.sofra.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.data.model.clientLogin.ClientData;
import com.example.sofra.data.model.restaurantCategoryResponse.RestaurantCategoriesListResponse;
import com.example.sofra.data.model.restaurantCategoryResponse.RestaurantCategoryData;
import com.example.sofra.data.model.restaurantsListAndDetailsResponce.RestaurantsListData;
import com.example.sofra.utils.HelperMethod;
import com.example.sofra.view.activity.HomeCycleActivity;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.home.RestaurantCategoryTabsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.sofra.data.local.SharedPreferencesManger.LoadUserData;
import static com.example.sofra.utils.HelperMethod.onLoadImageFromUrl;

public class ClientCategoriesAndFillterFoodOrderListAdapter extends RecyclerView.Adapter<ClientCategoriesAndFillterFoodOrderListAdapter.ViewHolder> {



    private Context context;
    private Activity activity;
    private List<RestaurantCategoryData> clientCategoriesAndFillterFoodOrderDataList = new ArrayList<>();
    private ClientData clientData;
//    private ApiService apiService;

    public ClientCategoriesAndFillterFoodOrderListAdapter(Context context,
                                                          Activity activity,
                                                          List<RestaurantCategoryData> clientCategoriesAndFillterFoodOrderDataList
    ) {
        this.context = context;
        this.activity = activity;
        this.clientCategoriesAndFillterFoodOrderDataList = clientCategoriesAndFillterFoodOrderDataList;
        clientData = LoadUserData(activity);

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
    }

    private void setData(ViewHolder holder, int position) {
        try {
            holder.position = position;
            onLoadImageFromUrl(holder.itemClientCategoryFillterRestaurantImgRestaurantLogo, clientCategoriesAndFillterFoodOrderDataList.get(position).getPhotoUrl(), context);
            holder.itemClientCategoryFillterTvCategoryName.setText(clientCategoriesAndFillterFoodOrderDataList.get(position).getName());

        } catch (Exception e) {

        }

    }

    private void setAction(final ViewHolder holder, final int position) {

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomeCycleActivity navigationActivity = (HomeCycleActivity) activity;
//                postDetails.postsData = postsDataList.get(position);
                HelperMethod.replaceFragment(navigationActivity.getSupportFragmentManager(), R.id.home_activity_fram, new RestaurantCategoryTabsFragment());

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

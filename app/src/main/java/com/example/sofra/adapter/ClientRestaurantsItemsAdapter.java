package com.example.sofra.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sofra.R;
import com.example.sofra.data.model.restaurantSubCategoriesItemsListResponce.RestaurantSubCategoriesItemsListData;
import com.example.sofra.view.activity.BaseActivity;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.more.ContactWithUsFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.sofra.utils.HelperMethod.onLoadImageFromUrl;
import static com.example.sofra.utils.HelperMethod.replaceFragmentWithAnimation;

public class ClientRestaurantsItemsAdapter extends RecyclerView.Adapter<ClientRestaurantsItemsAdapter.ViewHolder> {


    private BaseActivity activity;
    private Context context;
    private List<RestaurantSubCategoriesItemsListData> foodListData;

    public ClientRestaurantsItemsAdapter(Activity activity, Context context, List<RestaurantSubCategoriesItemsListData> foodListData) {
        this.activity = (BaseActivity) activity;
        this.context = context;
        this.foodListData = foodListData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item_client_food_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
        setAnimation(holder.itemView, position, holder);

    }

    private void setData(ViewHolder holder, int position) {
        RestaurantSubCategoriesItemsListData foodList = foodListData.get(position);
        holder.cardviewItemClientFoodOrderMenuTvMealName.setText(foodList.getName());
        holder.cardviewItemClientFoodOrderMenuTvMealDetails.setText(foodList.getDescription());
        holder.cardviewItemClientFoodOrderMenuTvMealPrice.setText(foodList.getPrice());
//        Glide.with(context).load(foodList.getPhotoUrl()).asBitmap().override(1080, 600).into(holder.cardviewItemClientFoodOrderMenuImg);
        onLoadImageFromUrl(holder.cardviewItemClientFoodOrderMenuImg, foodList.getPhotoUrl(), context);
    }

    private void setAnimation(View viewToAnimate, int position,ViewHolder holder) {
        Animation animation = null;
        animation = AnimationUtils.loadAnimation(context, R.anim.rv_animation_up_to_down);
        viewToAnimate.startAnimation(animation);

    }
    private void setAction(ViewHolder holder, int position) {
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AddOrderFragment addOrderFragment = new AddOrderFragment();
//                addOrderFragment.item = foodListData.get(position);
//                replaceFragmentWithAnimation(activity.getSupportFragmentManager(), R.id.home_activity_fram, new ContactWithUsFragment(),"t");

            }
        });
    }

    @Override
    public int getItemCount() {
        return foodListData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cardview_item_client_food_order_menu_img)
        ImageView cardviewItemClientFoodOrderMenuImg;
        @BindView(R.id.cardview_item_client_food_order_menu_tv_meal_name)
        TextView cardviewItemClientFoodOrderMenuTvMealName;
        @BindView(R.id.cardview_item_client_food_order_menu_tv_meal_details)
        TextView cardviewItemClientFoodOrderMenuTvMealDetails;
        @BindView(R.id.cardview_item_client_food_order_menu_tv_meal_price)
        TextView cardviewItemClientFoodOrderMenuTvMealPrice;
        View view;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}

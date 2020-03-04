package com.example.sofra.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sofra.R;
import com.example.sofra.data.model.clientLogin.ClientData;
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
import static com.example.sofra.utils.HelperMethod.onLoadCirImageFromUrl;
import static com.example.sofra.utils.HelperMethod.onLoadImageFromUrl;
import static com.example.sofra.utils.HelperMethod.showToast;

public class ClientRestaurantsAdapter extends RecyclerView.Adapter<ClientRestaurantsAdapter.ViewHolder> {


    private Context context;
    private Activity activity;
    private List<RestaurantsListData> clientRestaurantsDataList = new ArrayList<>();
    private ClientData clientData;
//    private ApiService apiService;

    public ClientRestaurantsAdapter(Context context,
                                    Activity activity,
                                    List<RestaurantsListData> clientRestaurantsDataList
                                    ) {
        this.context = context;
        this.activity = activity;
        this.clientRestaurantsDataList.clear();
        this.clientRestaurantsDataList = clientRestaurantsDataList;
        clientData = LoadUserData(activity);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_item_client_home_restaurant_list,
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
//            Glide.with(context)
//                    .load(clientRestaurantsDataList.get(position).getPhotoUrl())
//                    .into(holder.itemClientRestaurantsListImgRestaurantCrimg);
//            Glide.with(activity).load(clientRestaurantsDataList.get(position).getPhotoUrl()).into(holder.itemClientRestaurantsListImgRestaurantCrimg);
//            Picasso.get().load(clientRestaurantsDataList.get(position).getPhotoUrl()).placeholder(holder.position).into(holder.itemClientRestaurantsListImgRestaurantCrimg);
            onLoadCirImageFromUrl(holder.itemClientRestaurantsListImgRestaurantCrimg, clientRestaurantsDataList.get(position).getPhotoUrl(), context);
            holder.itemClientRestaurantsListTvRestaurantName.setText(clientRestaurantsDataList.get(position).getName());
            holder.itemClientRestaurantsListRbRating.setRating(clientRestaurantsDataList.get(position).getRate());
            holder.itemClientRestaurantsListTvMinimumOrder.setText(clientRestaurantsDataList.get(position).getMinimumCharger());
            holder.itemClientRestaurantsListTvDeliveryFees.setText(clientRestaurantsDataList.get(position).getDeliveryCost());
            holder.itemClientRestaurantsListTvOpened.setText(clientRestaurantsDataList.get(position).getAvailability());

            if (clientRestaurantsDataList.get(position).getActivated().equals("1")) {
                holder.itemClientRestaurantsListImgOpened.setImageResource(R.drawable.shape_green_circle);
            } else {
                holder.itemClientRestaurantsListImgOpened.setImageResource(R.drawable.shape_red_circle);
            }

        } catch (Exception e) {

        }

    }

    private void setAnimation(View viewToAnimate, int position, ViewHolder holder) {
        Animation animation = null;
        animation = AnimationUtils.loadAnimation(activity, R.anim.rv_animation_down_to_up);
        viewToAnimate.startAnimation(animation);

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
        return clientRestaurantsDataList.size();
    }

//    @OnClick(R.id.item_client_restaurants_list_rb_rating)
//    public void onViewClicked() {
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_client_restaurants_list_tv_restaurant_name)
        TextView itemClientRestaurantsListTvRestaurantName;
        @BindView(R.id.item_client_restaurants_list_img_opened)
        ImageView itemClientRestaurantsListImgOpened;
        @BindView(R.id.item_client_restaurants_list_tv_opened)
        TextView itemClientRestaurantsListTvOpened;
        @BindView(R.id.item_client_restaurants_list_rb_rating)
        RatingBar itemClientRestaurantsListRbRating;
        @BindView(R.id.item_client_restaurants_list_tv_minimum_order)
        TextView itemClientRestaurantsListTvMinimumOrder;
        @BindView(R.id.item_client_restaurants_list_tv_delivery_fees)
        TextView itemClientRestaurantsListTvDeliveryFees;
        @BindView(R.id.item_client_restaurants_list_img_restaurant_crimg)
        CircleImageView itemClientRestaurantsListImgRestaurantCrimg;
        private View view;
        private int position;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}

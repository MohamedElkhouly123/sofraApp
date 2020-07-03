package com.example.sofra.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.data.model.clientLogin.ClientData;
import com.example.sofra.data.model.orderResponse.OrderData;
import com.example.sofra.data.model.orderResponse.OrderResponse;
import com.example.sofra.data.model.restaurantCategoryResponse.RestaurantCategoryResponse;
import com.example.sofra.view.activity.BaseActivity;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.notificationsMenues.SubMenuesFragment;
import com.example.sofra.view.fragment.splashCycle.SplashFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

import static com.example.sofra.data.api.ApiClient.getApiClient;
import static com.example.sofra.data.local.SharedPreferencesManger.LoadUserData;
import static com.example.sofra.utils.GeneralRequest.deleteAndUpdateItemCallBack;
import static com.example.sofra.utils.GeneralRequest.genralOrderCallBack;
import static com.example.sofra.utils.HelperMethod.onLoadImageFromUrl;
import static com.example.sofra.utils.HelperMethod.onPermission;

public class GeneralOrderAdapter extends RecyclerView.Adapter<GeneralOrderAdapter.ViewHolder> {


    private BaseActivity activity;
    private Context context;
    private List<OrderData> orderListData;
    public String ISCLIENT = SplashFragment.getClient();
    private SubMenuesFragment subMenuesFragment = new SubMenuesFragment();
    public static boolean clientIsCompleted = true;
    private ClientData clientData;
    private int SUBMENUE;

    public GeneralOrderAdapter(Activity activity, Context context, List<OrderData> orderListData,int SUBMENUE) {
        this.activity = (BaseActivity) activity;
        this.context = context;
        this.SUBMENUE = SUBMENUE;
        this.orderListData = orderListData;
        clientData = LoadUserData(activity);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item_client_orders, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(orderListData.size()!=0){
        setData(holder, position);}
        setAction(holder, position);
        setAnimation(holder.itemView, position, holder);

    }

    private void setData(ViewHolder holder, int position) {
        OrderData orderList = orderListData.get(position);
        holder.position = position;

        if (ISCLIENT.equalsIgnoreCase("true")) {

            holder.itemRestaurantOrdersTvRestaurantName.setText(orderList.getClient().getName());
            onLoadImageFromUrl(holder.itemRestaurantOrdersImgClient, orderList.getClient().getPhotoUrl(), context);
            if (SUBMENUE == 0) {
                holder.itemUserNewOrderBtnCancelOrSubmitOrder.setVisibility(View.VISIBLE);
                holder.itemUserNewOrderBtnCancelOrSubmitOrder.setBackgroundColor(R.drawable.blue_shape);
                holder.itemUserNewOrderTvCancelOrder.setText(R.string.client_cancel);

            }
            if (SUBMENUE == 1) {
                holder.itemUserNewOrderBtnCancelOrSubmitOrder.setVisibility(View.VISIBLE);
                holder.itemUserNewOrderBtnCancelOrSubmitOrder.setBackgroundColor(R.drawable.green_button);
                holder.itemUserNewOrderTvCancelOrder.setText(R.string.client_submmit);

            }
            if (SUBMENUE == 2) {
                setThirdBotton(holder);
            }
        } else if (ISCLIENT.equalsIgnoreCase("false")) {
            holder.itemRestaurantOrdersTvRestaurantName.setVisibility(View.GONE);
            holder.itemRestaurantOrdersTvClientName.setText(orderList.getRestaurant().getName());
            onLoadImageFromUrl(holder.itemRestaurantOrdersImgClient, orderList.getRestaurant().getPhotoUrl(), context);
            if (SUBMENUE == 0) {
                holder.itemRestaurantOrdersBottomsLay1.setVisibility(View.VISIBLE);

            }
            if (SUBMENUE == 1) {
                holder.itemRestaurantOrdersBottomsLay2.setVisibility(View.VISIBLE);
            }
            if (SUBMENUE == 2) {
                setThirdBotton(holder);
                if (!clientIsCompleted) {
                    holder.itemUserNewOrderBtnCompletedOrCanceledOrder.setText(R.string.client_order_rejected);
                }
            }
        }
        holder.itemRestaurantOrdersTvOrderNumber.setText(orderList.getId());
        holder.itemRestaurantOrdersTvTotalAmount.setText(orderList.getTotal() + " ريال ");
        holder.itemRestaurantOrdersTvAddress.setText(orderList.getAddress());

    }


    private void setAnimation(View viewToAnimate, int position, ViewHolder holder) {
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

    private void setThirdBotton(ViewHolder holder){
        holder.itemUserNewOrderBtnCompletedOrCanceledOrder.setVisibility(View.VISIBLE);
        if (clientIsCompleted) {
            holder.itemUserNewOrderBtnCompletedOrCanceledOrder.setBackgroundColor(R.drawable.green_button);
            holder.itemUserNewOrderBtnCompletedOrCanceledOrder.setText(R.string.client_order_completed);
        }
        if (!clientIsCompleted) {
            holder.itemUserNewOrderBtnCompletedOrCanceledOrder.setBackgroundColor(R.drawable.pink_shape);
            holder.itemUserNewOrderBtnCompletedOrCanceledOrder.setText(R.string.client_order_canceled);
        }
    }

    @Override
    public int getItemCount() {
        return orderListData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_restaurant_orders_img_client)
        ImageView itemRestaurantOrdersImgClient;
        @BindView(R.id.lin_client_img)
        LinearLayout linClientImg;
        @BindView(R.id.item_restaurant_orders_tv_client_name)
        TextView itemRestaurantOrdersTvClientName;
        @BindView(R.id.item_restaurant_orders_bottoms_lay1)
        LinearLayout itemRestaurantOrdersBottomsLay1;
        @BindView(R.id.item_restaurant_orders_tv_restaurant_name)
        TextView itemRestaurantOrdersTvRestaurantName;
        @BindView(R.id.lin_restaurant_name)
        LinearLayout linRestaurantName;
        @BindView(R.id.item_restaurant_orders_bottoms_lay2)
        LinearLayout itemRestaurantOrdersBottomsLay2;
        @BindView(R.id.item_restaurant_orders_tv_order_number)
        TextView itemRestaurantOrdersTvOrderNumber;
        @BindView(R.id.item_restaurant_orders_tv_total_amount)
        TextView itemRestaurantOrdersTvTotalAmount;
        @BindView(R.id.item_restaurant_orders_tv_address)
        TextView itemRestaurantOrdersTvAddress;
        @BindView(R.id.item_user_new_order_tv_cancel_order)
        TextView itemUserNewOrderTvCancelOrder;
        @BindView(R.id.item_user_new_order_btn_cancel_or_submit_order)
        LinearLayout itemUserNewOrderBtnCancelOrSubmitOrder;
        @BindView(R.id.item_user_new_order_btn_completed_or_canceled_order)
        Button itemUserNewOrderBtnCompletedOrCanceledOrder;
        @BindView(R.id.item_restaurant_orders_btn_cancel_order)
        LinearLayout itemRestaurantOrdersBtnCancelOrder;
        @BindView(R.id.item_restaurant_orders_btn_cancel2_order)
        LinearLayout itemRestaurantOrdersBtnCancel2Order;
        View view;
        private int position;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.item_user_new_order_btn_cancel_or_submit_order, R.id.item_user_new_order_btn_completed_or_canceled_order, R.id.item_restaurant_orders_btn_call, R.id.item_restaurant_orders_btn_accept, R.id.item_restaurant_orders_btn_cancel_order, R.id.item_restaurant_orders_btn_cancel2_order, R.id.item_restaurant_orders_btn_confirm2})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.item_user_new_order_btn_cancel_or_submit_order:
                    if (SUBMENUE == 0) {
                        Call<OrderResponse> cancelOrderCal = getApiClient().clientDeclineOrder(String.valueOf(orderListData.get(position).getId()),clientData.getApiToken());
                        genralOrderCallBack(activity, cancelOrderCal);
                    }
                    if (SUBMENUE == 1) {
                        Call<OrderResponse> confirmOrderCal = getApiClient().clientConfirmOrder(String.valueOf(orderListData.get(position).getId()),clientData.getApiToken());
                        genralOrderCallBack(activity, confirmOrderCal);
                    }
                    break;
                case R.id.item_user_new_order_btn_completed_or_canceled_order:
                    break;
                case R.id.item_restaurant_orders_btn_call:
                    onPermission(activity);

                    activity.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", orderListData.get(position).getClient().getPhone(), null)));

                    break;
                case R.id.item_restaurant_orders_btn_accept:
                    Call<OrderResponse> acceptOrderCal = getApiClient().acceptOrder(clientData.getApiToken(), String.valueOf(orderListData.get(position).getId()));
                    genralOrderCallBack(activity, acceptOrderCal);
                    break;
                case R.id.item_restaurant_orders_btn_cancel_order:
                    showCancelDialog();
                    break;
                case R.id.item_restaurant_orders_btn_cancel2_order:
                    showCancelDialog();
                    break;
                case R.id.item_restaurant_orders_btn_confirm2:
                    Call<OrderResponse> confirmOrderCal = getApiClient().confirmOrder(String.valueOf(orderListData.get(position).getId()),clientData.getApiToken());
                    genralOrderCallBack(activity, confirmOrderCal);
                    break;
            }
        }

        private void showCancelDialog(){
            try {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.dialog_restaurant_cancel_reason, null);
                alertDialogBuilder.setView(view);
                alertDialogBuilder.setCancelable(true);
                final AlertDialog dialog = alertDialogBuilder.create();
                TextInputLayout restaurantAddCategoryDialogTilCategoryName = (TextInputLayout) view.findViewById(R.id.item_restaurant_cancel_reason_dialog_et_cancel_reason);
                Button dialogCancelReasonBtn = (Button) view.findViewById(R.id.dialog_restaurant_cancel_resoan_btn);
                dialog.show();
                dialogCancelReasonBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String cancelOrderReason =restaurantAddCategoryDialogTilCategoryName.getEditText().getText().toString();
                        Call<OrderResponse> cancelOrderCal = getApiClient().rejectOrder(clientData.getApiToken(), String.valueOf(orderListData.get(position).getId()),cancelOrderReason);
                        genralOrderCallBack(activity, cancelOrderCal);
                        dialog.dismiss();
                    }
                });

            } catch (Exception e) {

            }
        }
    }
}

package com.example.sofra.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.sofra.R;
import com.example.sofra.adapter.SpinnerAdapter;
import com.example.sofra.data.model.clientLogin.ClientGeneralResponse;
import com.example.sofra.data.model.clientResetPassword.ClientResetPasswordResponse;
import com.example.sofra.data.model.generalRespose.GeneralRespose;
import com.example.sofra.data.model.orderResponse.OrderResponse;
import com.example.sofra.data.model.restaurantCategoryResponse.RestaurantCategoryResponse;
import com.example.sofra.view.activity.HomeCycleActivity;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.home.HomeFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static com.example.sofra.utils.HelperMethod.dismissProgressDialog;
import static com.example.sofra.utils.HelperMethod.progressDialog;
import static com.example.sofra.utils.ToastCreator.onCreateErrorToast;
import static com.example.sofra.utils.network.InternetState.isConnected;

public class GeneralRequest {

    public static void deleteAndUpdateItemCallBack(final Activity activity,final Call<RestaurantCategoryResponse> method) {
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
                new HomeFragment().isDialogDataAddSuccess=false;
                onCreateErrorToast(activity, activity.getString(R.string.error));
            }
        });
    }

    public static void genralOrderCallBack(final Activity activity,final Call<OrderResponse> method) {
        if (progressDialog == null) {
            HelperMethod.showProgressDialog(activity, activity.getString(R.string.wait));
        } else {
            if (!progressDialog.isShowing()) {
                HelperMethod.showProgressDialog(activity, activity.getString(R.string.wait));
            }
        }

        method.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {

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
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                dismissProgressDialog();
                new HomeFragment().isDialogDataAddSuccess=false;
                onCreateErrorToast(activity, activity.getString(R.string.error));
            }
        });
    }

    public static void getAndMakeRegisterToken(final Activity activity, final Call<ClientResetPasswordResponse> method) {

            if (progressDialog == null) {
                HelperMethod.showProgressDialog(activity, activity.getString(R.string.wait));
            } else {
                if (!progressDialog.isShowing()) {
                    HelperMethod.showProgressDialog(activity, activity.getString(R.string.wait));
                }
            }

            method.enqueue(new Callback<ClientResetPasswordResponse>() {
                @Override
                public void onResponse(Call<ClientResetPasswordResponse> call, Response<ClientResetPasswordResponse> response) {

                    if (response.body() != null) {
                        try {

                            if (response.body().getStatus() == 1) {
                                    dismissProgressDialog();
                                ToastCreator.onCreateSuccessToast(activity, response.body().getMsg());
                            } else {
                                onCreateErrorToast(activity, response.body().getMsg());

                            }
                        } catch (Exception e) {

                        }
                    }
                }

                @Override
                public void onFailure(Call<ClientResetPasswordResponse> call, Throwable t) {
                    dismissProgressDialog();
                    onCreateErrorToast(activity, activity.getString(R.string.error));
                }
            });
        }

}

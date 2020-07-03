package com.example.sofra.view.repository;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.lifecycle.MutableLiveData;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.sofra.R;
import com.example.sofra.data.model.orderResponse.OrderResponse;
import com.example.sofra.utils.ToastCreator;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.home.HomeFragment;
import com.facebook.shimmer.ShimmerFrameLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.utils.ToastCreator.onCreateErrorToast;
import static com.example.sofra.utils.network.InternetState.isConnected;

public class OrderRepository {

    private MutableLiveData<OrderResponse> generalOrderResponse = new MutableLiveData<>();

//    public MutableLiveData<RestaurantSubCategoriesItemsListResponce> makeClientRestaurantSubCategoriesItemsDataList() {
//        return generalOrderResponse;
//    }

    public MutableLiveData<OrderResponse> getGeneralOrderDataList(final Activity activity, final LinearLayout errorSubView, final Call<OrderResponse> method, final SwipeRefreshLayout clientAndRestaurantHomeFragmentSrRefreshRv, final RelativeLayout loadMore, final ShimmerFrameLayout clientAndRestaurantHomeFragmentSFlShimmer) {
        if (isConnected(activity)) {


            method.enqueue(new Callback<OrderResponse>() {
                @Override
                public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
//                    showToast(activity, "here4");

                    if (response.body() != null) {
                        try {

                            clientAndRestaurantHomeFragmentSFlShimmer.stopShimmer();
                            clientAndRestaurantHomeFragmentSFlShimmer.setVisibility(View.GONE);
                            loadMore.setVisibility(View.GONE);
                            clientAndRestaurantHomeFragmentSrRefreshRv.setRefreshing(false);
                            if (response.body().getStatus() == 1) {
                                generalOrderResponse.postValue(response.body());

                                ToastCreator.onCreateSuccessToast(activity, response.body().getMsg());
                            } else {
                                onCreateErrorToast(activity, response.body().getMsg());
                                new HomeFragment().setError(String.valueOf(R.string.error_list));
                            }

                        } catch(Exception e){

                        }
                    }
                }

                @Override
                public void onFailure(Call<OrderResponse> call, Throwable t) {
                    try {
                        clientAndRestaurantHomeFragmentSFlShimmer.stopShimmer();
                        clientAndRestaurantHomeFragmentSFlShimmer.setVisibility(View.GONE);
                        loadMore.setVisibility(View.GONE);
                        clientAndRestaurantHomeFragmentSrRefreshRv.setRefreshing(false);
                        new HomeFragment().setError(String.valueOf(R.string.error_list));
                        generalOrderResponse.postValue(null);
                    } catch (Exception e) {

                    }

                }
            });
        } else {
            try {
                clientAndRestaurantHomeFragmentSFlShimmer.stopShimmer();
                clientAndRestaurantHomeFragmentSFlShimmer.setVisibility(View.GONE);
                loadMore.setVisibility(View.GONE);
                clientAndRestaurantHomeFragmentSrRefreshRv.setRefreshing(false);
                errorSubView.setVisibility(View.VISIBLE);
//                errorTitle.setText(errorTitleTxt);
                new HomeFragment().setError(String.valueOf(R.string.error_inter_net));
                onCreateErrorToast(activity, activity.getString(R.string.error_inter_net));
            } catch (Exception e) {

            }

        }
                return generalOrderResponse;
    }
}

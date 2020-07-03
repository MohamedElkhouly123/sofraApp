package com.example.sofra.view.viewModel;



import android.app.Activity;
import android.app.Application;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.sofra.data.model.orderResponse.OrderResponse;
import com.example.sofra.view.repository.OrderRepository;
import com.facebook.shimmer.ShimmerFrameLayout;

import retrofit2.Call;


public class ViewModelOtherWayForOrder extends AndroidViewModel {

    private OrderRepository orderRepository;
    private MutableLiveData<OrderResponse> getOrderResponse = new MutableLiveData<>();

    public ViewModelOtherWayForOrder(@NonNull Application application) {
        super(application);
        orderRepository=new OrderRepository();
    }

        public MutableLiveData<OrderResponse> getGeneralOrderData() {
        return getOrderResponse;
    }
    public LiveData<OrderResponse> returnGeneralOrder(final Activity activity, final LinearLayout errorSubView, final Call<OrderResponse> method, final SwipeRefreshLayout clientAndRestaurantHomeFragmentSrRefreshRv, final RelativeLayout loadMore, final ShimmerFrameLayout clientAndRestaurantHomeFragmentSFlShimmer) {
              getOrderResponse= orderRepository.getGeneralOrderDataList(activity,errorSubView,method,clientAndRestaurantHomeFragmentSrRefreshRv,loadMore,clientAndRestaurantHomeFragmentSFlShimmer);
              return getOrderResponse;
    }



}

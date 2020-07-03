package com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.notificationsMenues;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.sofra.R;
import com.example.sofra.adapter.GeneralOrderAdapter;
import com.example.sofra.data.model.clientLogin.ClientData;
import com.example.sofra.data.model.orderResponse.OrderData;
import com.example.sofra.data.model.orderResponse.OrderResponse;
import com.example.sofra.utils.OnEndLess;
import com.example.sofra.view.fragment.BaSeFragment;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.home.HomeFragment;
import com.example.sofra.view.fragment.splashCycle.SplashFragment;
import com.example.sofra.view.viewModel.ViewModelOtherWayForOrder;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;

import static com.example.sofra.data.api.ApiClient.getApiClient;
import static com.example.sofra.data.local.SharedPreferencesManger.CLIENT;
import static com.example.sofra.data.local.SharedPreferencesManger.LoadData;
import static com.example.sofra.data.local.SharedPreferencesManger.LoadUserData;
import static com.example.sofra.utils.HelperMethod.replaceFragment;
import static com.example.sofra.utils.HelperMethod.showToast;

public class Sub3MenuesFragment extends BaSeFragment {


    @BindView(R.id.notifications_fragment_s_fl_shimmer_submenues)
    ShimmerFrameLayout submenuesFragmentSFlShimmerSubmenues;
    @BindView(R.id.submenues_recycler_view)
    RecyclerView submenuesRecyclerView;
    @BindView(R.id.load_more)
    RelativeLayout loadMore;
    @BindView(R.id.error_image)
    GifImageView errorImage;
    @BindView(R.id.error_title)
    TextView errorTitle;
    @BindView(R.id.error_sub_view)
    LinearLayout errorSubView;
    @BindView(R.id.submenues_list_Fragment_sr_refresh_submenues)
    SwipeRefreshLayout submenuesListFragmentSrRefreshSubmenues;
    @BindView(R.id.no_result_error_title)
    TextView noResultErrorTitle;
    private LinearLayoutManager linearLayout;
    public List<OrderData> generalOrderListData = new ArrayList<OrderData>();
    public GeneralOrderAdapter generalOrderAdapter;
    private ViewModelOtherWayForOrder viewModel;
    public Integer maxPage = 0;
    private OnEndLess onEndLess;
    private ClientData clientData;
    public String ISCLIENT ;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_submenues, container, false);
        ButterKnife.bind(this, root);
        ISCLIENT = SplashFragment.getClient();
        clientData = LoadUserData(getActivity());
        if(ISCLIENT==null){
            ISCLIENT = LoadData(getActivity(), CLIENT);
        }
//        showToast(getActivity(), String.valueOf(tabLayout.getSelectedTabPosition()));

//        showToast(getActivity(), String.valueOf(SUBMENUE));
        setUpActivity();
        initLisener();
        init();

        return root;
    }

    private void initLisener() {
        viewModel = ViewModelProviders.of(this).get(ViewModelOtherWayForOrder.class);

        viewModel.getGeneralOrderData().observe(this, new Observer<OrderResponse>() {
            @Override
            public void onChanged(@Nullable OrderResponse response) {
                try {
                    if (response != null) {
                        if (response.getStatus() == 1) {
                            maxPage = response.getData().getLastPage();
//                                showToast(getActivity(), "max="+maxPage);

                            if (response.getData() != null && response.getData().getTotal() != 0) {
                                generalOrderListData.clear();
                                generalOrderListData.addAll(response.getData().getData());
//                                showToast(getActivity(), "list="+clientrestaurantsListData.get(1));
                                noResultErrorTitle.setVisibility(View.GONE);
                                generalOrderAdapter.notifyDataSetChanged();

                            } else {
                                noResultErrorTitle.setVisibility(View.VISIBLE);
                            }
//                                showToast(getActivity(), "success1");

                        }
                    } else {
                        showToast(getActivity(), "null");

                    }

                } catch (Exception e) {
                }
            }
        });
    }



    private void init() {
        linearLayout = new LinearLayoutManager(getActivity());
        submenuesRecyclerView.setLayoutManager(linearLayout);
        submenuesRecyclerView.setHasFixedSize(true);

        onEndLess = new OnEndLess(linearLayout, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        loadMore.setVisibility(View.VISIBLE);
                        callMethod(current_page);


                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }
            }
        };
        submenuesRecyclerView.addOnScrollListener(onEndLess);

        if (ISCLIENT == "true") {
            generalOrderAdapter = new GeneralOrderAdapter(getActivity(), getContext(), generalOrderListData,2);
            submenuesRecyclerView.setAdapter(generalOrderAdapter);
//            showToast(getActivity(), "success adapter");
        }

        if (generalOrderListData.size() == 0) {
            callMethod(1);
        } else {
            submenuesFragmentSFlShimmerSubmenues.stopShimmer();
            submenuesFragmentSFlShimmerSubmenues.setVisibility(View.GONE);
        }

        submenuesListFragmentSrRefreshSubmenues.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                    callMethod(1);
            }
        });
    }

    private void callMethod(int page) {

//        String restaurantId = String.valueOf(restaurantsListData.getId());
//        String categoryId2 = null;
        Call<OrderResponse> clientGetOrderCompleted = null;
        Call<OrderResponse> restaurantGetOrderCompleted = getApiClient().restaurantGetMyOrders(clientData.getApiToken(), "current", page);



        Sub3MenuesFragment subMenuesFragment = new Sub3MenuesFragment();
        startShimmer(page);

        if (ISCLIENT.equalsIgnoreCase("true")) {



                 clientGetOrderCompleted = getApiClient().clientGetMyOrder(clientData.getApiToken(), "completed", page);
                viewModel.returnGeneralOrder(getActivity(), errorSubView, clientGetOrderCompleted, submenuesListFragmentSrRefreshSubmenues, loadMore, submenuesFragmentSFlShimmerSubmenues);
                showToast(getActivity(), "pend 3");


        } else if (ISCLIENT.equalsIgnoreCase("false")) {


                viewModel.returnGeneralOrder(getActivity(), errorSubView, restaurantGetOrderCompleted, submenuesListFragmentSrRefreshSubmenues, loadMore, submenuesFragmentSFlShimmerSubmenues);

//
        }
//            showToast(getActivity(), "success without fillter");
    }


    private void startShimmer(int page) {
        errorSubView.setVisibility(View.GONE);
        if (page == 1) {
            reInit();
            errorSubView.setVisibility(View.GONE);
            submenuesFragmentSFlShimmerSubmenues.startShimmer();
            submenuesFragmentSFlShimmerSubmenues.setVisibility(View.VISIBLE);
        }
    }


    private void reInit() {
        onEndLess.previousTotal = 0;
        onEndLess.current_page = 1;
        onEndLess.previous_page = 1;
//        if ("add".equalsIgnoreCase(called_from)) {
//        if (ISCLIENT.equalsIgnoreCase("true")) {
            generalOrderListData = new ArrayList<>();
            generalOrderAdapter = new GeneralOrderAdapter(getActivity(), getContext(), generalOrderListData,2);
            submenuesRecyclerView.setAdapter(generalOrderAdapter);

//        }

    }

    @Override
    public void onBack() {
        replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, new HomeFragment());
        homeCycleActivity.navView.getMenu().getItem(0).setChecked(true);
    }


}
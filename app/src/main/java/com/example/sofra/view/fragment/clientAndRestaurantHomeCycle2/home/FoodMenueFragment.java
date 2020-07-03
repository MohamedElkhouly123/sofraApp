package com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import com.example.sofra.adapter.ClientCategoriesAndFillterFoodOrderListAdapter;
import com.example.sofra.adapter.ClientRestaurantsAdapter;
import com.example.sofra.adapter.ClientRestaurantsItemsAdapter;
import com.example.sofra.adapter.RestaurantCategoriesAdapter;
import com.example.sofra.data.model.clientLogin.ClientData;
import com.example.sofra.data.model.restaurantCategoryResponse.RestaurantCategoriesListResponse;
import com.example.sofra.data.model.restaurantCategoryResponse.RestaurantCategoryFiltterData;
import com.example.sofra.data.model.restaurantCategoryResponse.RestaurantCategoryResponse;
import com.example.sofra.data.model.restaurantSubCategoriesItemsListResponce.RestaurantSubCategoriesItemsListData;
import com.example.sofra.data.model.restaurantSubCategoriesItemsListResponce.RestaurantSubCategoriesItemsListResponce;
import com.example.sofra.data.model.restaurantsListAndDetailsResponce.RestaurantsListData;
import com.example.sofra.utils.HelperMethod;
import com.example.sofra.utils.OnEndLess;
import com.example.sofra.utils.RVAdapterCallback;
import com.example.sofra.view.fragment.BaSeFragment;
import com.example.sofra.view.fragment.splashCycle.SplashFragment;
import com.example.sofra.view.viewModel.ViewModelClient;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getApiClient;
import static com.example.sofra.utils.HelperMethod.dismissProgressDialog;
import static com.example.sofra.utils.HelperMethod.progressDialog;
import static com.example.sofra.utils.HelperMethod.replaceFragment;
import static com.example.sofra.utils.HelperMethod.showToast;

public class FoodMenueFragment extends BaSeFragment implements RVAdapterCallback {


    @BindView(R.id.food_order_fragment_s_fl_shimmer_donations)
    ShimmerFrameLayout foodOrderFragmentSFlShimmer;
    @BindView(R.id.food_order_fragment_recycler_view_horizental)
    RecyclerView foodOrderFragmentRecyclerViewHorizental;
    @BindView(R.id.food_order_fragment_articles_recycler_view)
    RecyclerView foodOrderFragmentArticlesRecyclerView;
    @BindView(R.id.load_more)
    RelativeLayout loadMore;
    @BindView(R.id.error_image)
    GifImageView errorImage;
    @BindView(R.id.error_title)
    TextView errorTitle;
    @BindView(R.id.error_sub_view)
    LinearLayout errorSubView;
    @BindView(R.id.food_order_Fragment_sr_refresh_donations)
    SwipeRefreshLayout foodOrderFragmentSrRefresh;
    @BindView(R.id.no_result_error_title)
    TextView noResultErrorTitle;
    private LinearLayoutManager linearLayout;
    private LinearLayoutManager linearLayoutHorizental;
    private List<RestaurantSubCategoriesItemsListData> clientRestaurantItemsDataList = new ArrayList<>();
    public List<RestaurantCategoryFiltterData> clientRestaurantCategoryFiltterListData = new ArrayList<>();
    public ClientCategoriesAndFillterFoodOrderListAdapter clientCategoriesAndFillterFoodOrderListAdapter;
    public ClientRestaurantsItemsAdapter clientRestaurantsItemsAdapter;
    private static ViewModelClient viewModel;
    public Integer maxPage = 0;
    private OnEndLess onEndLess;
    private static boolean Filter = false;
    private ClientData clientData;
    public String ISCLIENT ;
    public static RestaurantsListData restaurantsListData = new RestaurantsListData();
    public static String categoryId;
    static View root;

    public FoodMenueFragment(){

    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

         root = inflater.inflate(R.layout.fragment_home_food_menu, container, false);
        ButterKnife.bind(this, root);
        Filter=false;
        ISCLIENT = SplashFragment.getClient();
//        homeCycleActivity= (HomeCycleActivity) getActivity();
//        homeCycleActivity.setNavigation("v");
        setUpActivity();
        initListener();
//        if(restaurantsListData.getId()!=null){
        initHozental();
        init();
//        }
        return root;
    }


    private void initListener() {
        viewModel = ViewModelProviders.of(this).get(ViewModelClient.class);

        viewModel.makeClientRestaurantCategoryFiltterDataList().observe(this, new Observer<RestaurantCategoriesListResponse>() {
            @Override
            public void onChanged(@Nullable RestaurantCategoriesListResponse response) {
                {
                    try {
                        if (response != null) {
                            if (response.getStatus() == 1) {
//                                maxPage = response.getData().getLastPage();
//                                showToast(getActivity(), "max=" + maxPage);

                                if (response.getData() != null && response.getData().size() != 0) {
                                    clientRestaurantCategoryFiltterListData.clear();
                                    clientRestaurantCategoryFiltterListData.addAll(response.getData());
//                                    showToast(getActivity(), "list=" + clientRestaurantCategoryFiltterListData.get(1));

                                    clientCategoriesAndFillterFoodOrderListAdapter.notifyDataSetChanged();

                                } else {
//                                    showToast(getActivity(), "addapter" + clientRestaurantCategoryFiltterListData.size());

                                    noResultErrorTitle.setVisibility(View.VISIBLE);
                                }
//                                showToast(getActivity(), "success 2");

                            }
                        } else {
                            showToast(getActivity(), "null");

                        }

                    } catch (Exception e) {
                    }
                }
            }
        });

        viewModel.makeClientRestaurantSubCategoriesItemsDataList().observe(this, new Observer<RestaurantSubCategoriesItemsListResponce>() {
            @Override
            public void onChanged(@Nullable RestaurantSubCategoriesItemsListResponce response) {
                {
                    try {
                        if (response != null) {
                            if (response.getStatus() == 1) {
                                maxPage = response.getData().getLastPage();
//                                showToast(getActivity(), "max="+maxPage);

                                if (response.getData() != null && response.getData().getTotal() != 0) {
                                    clientRestaurantItemsDataList.clear();
                                    clientRestaurantItemsDataList.addAll(response.getData().getData());
//                                showToast(getActivity(), "list="+clientRestaurantItemsDataList.get(1));
                                    noResultErrorTitle.setVisibility(View.GONE);
                                    clientRestaurantsItemsAdapter.notifyDataSetChanged();

                                } else {
                                    noResultErrorTitle.setVisibility(View.VISIBLE);
                                }
//                                showToast(getActivity(), "success1");

                            }
                        } else {
//                            showToast(getActivity(), "null");

                        }

                    } catch (Exception e) {
                    }
                }
            }
        });


    }

    private void initHozental() {
        linearLayoutHorizental = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        foodOrderFragmentRecyclerViewHorizental.setLayoutManager(linearLayoutHorizental);
        foodOrderFragmentRecyclerViewHorizental.setHasFixedSize(true);
        clientGetRestaurantsFiltterList(0);

//        onEndLess = new OnEndLess(linearLayoutHorizental, 1) {
//            @Override
//            public void onLoadMore(int current_page) {
//                if (current_page <= maxPage) {
//                    if (maxPage != 0 && current_page != 1) {
//                        onEndLess.previous_page = current_page;
//                        loadMore.setVisibility(View.VISIBLE);
//                        clientGetRestaurantsFiltterList(current_page);
//
//                    } else {
//                        onEndLess.current_page = onEndLess.previous_page;
//                    }
//                } else {
//                    onEndLess.current_page = onEndLess.previous_page;
//                }
//            }
//        };
//        foodOrderFragmentArticlesRecyclerView.addOnScrollListener(onEndLess);

        if (ISCLIENT == "true") {
            clientCategoriesAndFillterFoodOrderListAdapter = new ClientCategoriesAndFillterFoodOrderListAdapter(getContext(), getActivity(),this, clientRestaurantCategoryFiltterListData);
            foodOrderFragmentRecyclerViewHorizental.setAdapter(clientCategoriesAndFillterFoodOrderListAdapter);
//            showToast(getActivity(), "success adapter");
        }

//        if (clientRestaurantCategoryFiltterListData.size() == 0) {
//            clientGetRestaurantsFiltterList(1);
//        } else {
//            foodOrderFragmentSFlShimmer.stopShimmer();
//            foodOrderFragmentSFlShimmer.setVisibility(View.GONE);
//        }
    }

    private void clientGetRestaurantsFiltterList(int page) {
        if (restaurantsListData.getId() != null) {

            String restaurantId = String.valueOf(restaurantsListData.getId());

            Call<RestaurantCategoriesListResponse> clientCategoryFiltterCall;
            clientCategoryFiltterCall = getApiClient().getCategories(restaurantId);
            startShimmer(page);
            viewModel.getClientRestaurantCategoryFiltterDataList(getActivity(), errorSubView, clientCategoryFiltterCall, foodOrderFragmentSrRefresh, loadMore, foodOrderFragmentSFlShimmer);
//            showToast(getActivity(), "success no fillter " + restaurantsListData.getId());
        }

    }

    private void init() {
        linearLayout = new LinearLayoutManager(getActivity());
        foodOrderFragmentArticlesRecyclerView.setLayoutManager(linearLayout);
        foodOrderFragmentArticlesRecyclerView.setHasFixedSize(true);

        onEndLess = new OnEndLess(linearLayout, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        loadMore.setVisibility(View.VISIBLE);
                        if (Filter) {
                            clientGetRestaurantsItemsListByFilter(current_page);
                        } else {
                            clientGetRestaurantsItemsListWithoutFilter(current_page);
                        }

                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }
            }
        };
        foodOrderFragmentArticlesRecyclerView.addOnScrollListener(onEndLess);

        if (ISCLIENT == "true") {
            clientRestaurantsItemsAdapter = new ClientRestaurantsItemsAdapter(getActivity(), getContext(), clientRestaurantItemsDataList);
            foodOrderFragmentArticlesRecyclerView.setAdapter(clientRestaurantsItemsAdapter);
//            showToast(getActivity(), "success adapter");
        }

        if (clientRestaurantItemsDataList.size() == 0) {
            clientGetRestaurantsItemsListWithoutFilter(1);
        } else {
            foodOrderFragmentSFlShimmer.stopShimmer();
            foodOrderFragmentSFlShimmer.setVisibility(View.GONE);
        }

        foodOrderFragmentSrRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Filter) {
                    clientGetRestaurantsItemsListByFilter(1);
//                    showToast(getActivity(), "success refresh fillter " + categoryId);

                } else {
                    clientGetRestaurantsItemsListWithoutFilter(1);

                }
                clientGetRestaurantsFiltterList(1);


            }
        });
    }

    private void clientGetRestaurantsItemsListWithoutFilter(int page) {
        Call<RestaurantSubCategoriesItemsListResponce> clientRestaurantsItemsCall;
        String restaurantId = String.valueOf(restaurantsListData.getId());
        String categoryId2 = null;
        clientRestaurantsItemsCall = getApiClient().getRestaurantSubCategoriesItemsList(restaurantId, categoryId2, page);
        startShimmer(page);
        viewModel.getClientRestaurantSubCategoriesItemsDataList(getActivity(), errorSubView, clientRestaurantsItemsCall, foodOrderFragmentSrRefresh, loadMore, foodOrderFragmentSFlShimmer);
//            showToast(getActivity(), "success without fillter");


    }
    public void clientGetRestaurantsItemsListByFilter(int page) {
        Filter = true;
        Call<RestaurantSubCategoriesItemsListResponce> clientRestaurantsItemsCall;
        String restaurantId = String.valueOf(restaurantsListData.getId());
        clientRestaurantsItemsCall = getApiClient().getRestaurantSubCategoriesItemsList(restaurantId, categoryId, page);
        startShimmer(page);
        viewModel.getClientRestaurantSubCategoriesItemsDataList(getActivity(), errorSubView, clientRestaurantsItemsCall, foodOrderFragmentSrRefresh, loadMore, foodOrderFragmentSFlShimmer);
//            showToast(getActivity(), "success without fillter");

    }
//    public void clientGetRestaurantsItemsListByFilter(int page, String categoryIdOut, Context context, Activity activity) {
//        Filter = true;
////        foodOrderFragmentArticlesRecyclerView=(RecyclerView) root.findViewById(R.id.food_order_fragment_articles_recycler_view);
////        loadMore=(RelativeLayout) root.findViewById(R.id.load_more);
//        String restaurantId = String.valueOf(restaurantsListData.getId());
////        categoryId = categoryIdOut;
////        init();
//        clientRestaurantItemsDataList = new ArrayList<>();
//        clientRestaurantsItemsAdapter = new ClientRestaurantsItemsAdapter(getActivity(), context, clientRestaurantItemsDataList);
//        foodOrderFragmentArticlesRecyclerView.setAdapter(clientRestaurantsItemsAdapter);
//        if (progressDialog == null) {
//            HelperMethod.showProgressDialog(activity, activity.getString(R.string.wait));
//        } else {
//            if (!progressDialog.isShowing()) {
//                HelperMethod.showProgressDialog(activity, activity.getString(R.string.wait));
//            }
//        }
//        Call<RestaurantSubCategoriesItemsListResponce> clientRestaurantsItemsCall;
//        getApiClient().getRestaurantSubCategoriesItemsList(restaurantId, categoryId, page).enqueue(new Callback<RestaurantSubCategoriesItemsListResponce>() {
//
//            @Override
//            public void onResponse(Call<RestaurantSubCategoriesItemsListResponce> call, Response<RestaurantSubCategoriesItemsListResponce> response) {
//                try {
//                    dismissProgressDialog();
//                    loadMore.setVisibility(View.GONE);
//                    if (response.body().getStatus() == 1) {
//                        if (response.body().getData() != null && response.body().getData().getTotal() != 0) {
//
//                            clientRestaurantItemsDataList.clear();
//                        clientRestaurantItemsDataList.addAll(response.body().getData().getData());
//                        clientRestaurantsItemsAdapter.notifyDataSetChanged();
//
//                    }
//                        showToast(getActivity(), "rv num" + clientRestaurantCategoryFiltterListData.size());
//
//                    }
//                } catch (Exception e) {
////                    Log.d(TAG, e.getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RestaurantSubCategoriesItemsListResponce> call, Throwable t) {
//                dismissProgressDialog();
//                loadMore.setVisibility(View.VISIBLE);
//
//
//            }
//        });  }

    private void startShimmer(int page) {
        errorSubView.setVisibility(View.GONE);
        if (page == 1) {
            reInit();
            errorSubView.setVisibility(View.GONE);
            foodOrderFragmentSFlShimmer.startShimmer();
            foodOrderFragmentSFlShimmer.setVisibility(View.VISIBLE);
        }
    }


    private void reInit() {
        onEndLess.previousTotal = 0;
        onEndLess.current_page = 1;
        onEndLess.previous_page = 1;
//        if ("add".equalsIgnoreCase(called_from)) {
        if (ISCLIENT.equalsIgnoreCase("true")) {
            clientRestaurantItemsDataList = new ArrayList<>();
            clientRestaurantsItemsAdapter = new ClientRestaurantsItemsAdapter(getActivity(), getContext(), clientRestaurantItemsDataList);
            foodOrderFragmentArticlesRecyclerView.setAdapter(clientRestaurantsItemsAdapter);

            clientRestaurantCategoryFiltterListData = new ArrayList<>();
            clientCategoriesAndFillterFoodOrderListAdapter = new ClientCategoriesAndFillterFoodOrderListAdapter(getContext(), getActivity(), this,clientRestaurantCategoryFiltterListData);
            foodOrderFragmentRecyclerViewHorizental.setAdapter(clientCategoriesAndFillterFoodOrderListAdapter);
        }

    }

//    private void reInit2() {
//        foodOrderFragmentArticlesRecyclerView=(RecyclerView) root.findViewById(R.id.food_order_fragment_articles_recycler_view);
//
//        if (ISCLIENT.equals("true")) {
////            initListener();
//            clientRestaurantItemsDataList = new ArrayList<>();
//            clientRestaurantsItemsAdapter = new ClientRestaurantsItemsAdapter(getActivity(), getContext(), clientRestaurantItemsDataList);
//            foodOrderFragmentArticlesRecyclerView.setAdapter(clientRestaurantsItemsAdapter);
//
////            clientRestaurantCategoryFiltterListData = new ArrayList<>();
////            clientCategoriesAndFillterFoodOrderListAdapter = new ClientCategoriesAndFillterFoodOrderListAdapter(getContext(), getActivity(), clientRestaurantCategoryFiltterListData);
////            foodOrderFragmentRecyclerViewHorizental.setAdapter(clientCategoriesAndFillterFoodOrderListAdapter);
//        }
//
//    }

    @Override
    public void onBack() {
        replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, new HomeFragment());
        homeCycleActivity.navView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void onMethodCallback() {
        clientGetRestaurantsFiltterList(1);
        clientGetRestaurantsItemsListByFilter(1);
//        showToast(getActivity(), "yes");
    }
}
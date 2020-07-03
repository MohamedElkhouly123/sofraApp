package com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.sofra.R;
import com.example.sofra.adapter.ClientRestaurantsAdapter;
import com.example.sofra.adapter.RestaurantCategoriesAdapter;
import com.example.sofra.adapter.SpinnerAdapter;
import com.example.sofra.data.model.clientLogin.ClientData;
import com.example.sofra.data.model.generalRespose.GeneralRespose;
import com.example.sofra.data.model.restaurantCategoryResponse.RestaurantCategoryFiltterData;
import com.example.sofra.data.model.restaurantCategoryResponse.RestaurantCategoryResponse;
import com.example.sofra.data.model.restaurantsListAndDetailsResponce.RestaurantsListData;
import com.example.sofra.data.model.restaurantsListAndDetailsResponce.RestaurantsListResponce;
import com.example.sofra.utils.OnEndLess;
import com.example.sofra.view.activity.HomeCycleActivity;
import com.example.sofra.view.fragment.BaSeFragment;
import com.example.sofra.view.fragment.splashCycle.SplashFragment;
import com.example.sofra.view.viewModel.ViewModelClient;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;

import static com.example.sofra.data.api.ApiClient.getApiClient;
import static com.example.sofra.data.local.SharedPreferencesManger.CLIENT;
import static com.example.sofra.data.local.SharedPreferencesManger.LoadData;
import static com.example.sofra.data.local.SharedPreferencesManger.LoadUserData;
import static com.example.sofra.utils.HelperMethod.showToast;
import static com.example.sofra.utils.RestaurantAddAndUpdateCategoryDialog.showDialog;

public class HomeFragment extends BaSeFragment {


    @BindView(R.id.client_home_fillter_spin_id)
    Spinner clientHomeFillterSpinId;
    @BindView(R.id.client_home_fillter_search_keyword_etxt)
    EditText clientHomeFillterSearchKeyWordEtxt;
    @BindView(R.id.client_home_fillter_lyout)
    LinearLayout clientHomeFillterLyout;
    @BindView(R.id.client_home_shimmer_appear_lyout)
    LinearLayout clientHomeShimmerAppearLyout;
    @BindView(R.id.item_restaurant_category_tv_name)
    TextView itemRestaurantCategoryTvName;
    @BindView(R.id.client_and_restaurant_home_fragment_s_fl_shimmer)
    ShimmerFrameLayout clientAndRestaurantHomeFragmentSFlShimmer;
    @BindView(R.id.client_and_restaurant_home_recycler_view)
    RecyclerView clientAndRestaurantHomeRecyclerView;
    @BindView(R.id.load_more)
    RelativeLayout loadMore;
    @BindView(R.id.error_image)
    GifImageView errorImage;
    @BindView(R.id.error_title)
    TextView errorTitle;
    @BindView(R.id.error_sub_view)
    LinearLayout errorSubView;
    @BindView(R.id.client_and_restaurant_home_Fragment_sr_refresh)
    SwipeRefreshLayout clientAndRestaurantHomeFragmentSrRefreshRv;
    @BindView(R.id.restaurant_home_shimmer_appear_lyout)
    LinearLayout restaurantHomeShimmerAppearLyout;
    @BindView(R.id.restaurant_home_add_category_floating_action_btn)
    FloatingActionButton restaurantHomeAddCategoryFloatingActionBtn;
    @BindView(R.id.restaurant_home_category_tv)
    TextView restaurantHomeCategoryTv;
//    public String ISCLIENT = LoadData(getActivity(), CLIENT);

    public static boolean isDialogDataAddSuccess = true;
    public RestaurantCategoryFiltterData restaurantDataListOfPossision = new RestaurantCategoryFiltterData();
    public String ISCLIENT = SplashFragment.getClient();
    @BindView(R.id.no_result_error_title)
    TextView noResultErrorTitle;

    private SpinnerAdapter cityFilterAdapter;
    private LinearLayoutManager linearLayout;
    public List<RestaurantCategoryFiltterData> restaurantCategoriesListData = new ArrayList<RestaurantCategoryFiltterData>();
    public List<RestaurantsListData> clientrestaurantsListData = new ArrayList<>();
    public RestaurantCategoriesAdapter restaurantCategoriesAdapter;
    public ClientRestaurantsAdapter clientrestaurantsAdapter;
    private ViewModelClient viewModel;
    public Integer maxPage = 0;
    private OnEndLess onEndLess;
    private boolean Filter = false;
    private ClientData clientData;
    private int citiesSelectedId = 0;
    private String keyword;
    private AdapterView.OnItemSelectedListener listener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);
        homeCycleActivity = (HomeCycleActivity) getActivity();
        homeCycleActivity.setNavigation("v");
        if(ISCLIENT==null){
            ISCLIENT = LoadData(getActivity(), CLIENT);
        }
        setRestaurantOrClientView();

        setUpActivity();
        initListener();
        init();
        if (ISCLIENT == "false") {
        floatHidden();}
        return root;
    }

    @SuppressLint("RestrictedApi")
    private void setRestaurantOrClientView() {
        if (ISCLIENT.equalsIgnoreCase("true")) {
            clientHomeFillterLyout.setVisibility(View.VISIBLE);
            clientHomeShimmerAppearLyout.setVisibility(View.VISIBLE);
            restaurantHomeAddCategoryFloatingActionBtn.setVisibility(View.GONE);

        }
        if (ISCLIENT == "false") {
            restaurantHomeShimmerAppearLyout.setVisibility(View.VISIBLE);
            restaurantHomeCategoryTv.setVisibility(View.VISIBLE);
            restaurantHomeAddCategoryFloatingActionBtn.setVisibility(View.VISIBLE);

        }
    }

    private void initListener() {

        viewModel = ViewModelProviders.of(this).get(ViewModelClient.class);
        if (ISCLIENT == "true") {

            viewModel.makegetSpinnerData().observe(this, new Observer<GeneralRespose>() {
                @Override
                public void onChanged(@Nullable GeneralRespose response) {
                    if (response != null) {
                        if (response.getStatus() == 1) {
//                        showToast(getActivity(), "success2");

                        } else {
//                        showToast(getActivity(), "error");

                        }
                    }
                }
            });

            setSpinner();

            viewModel.makeClientHomeRestaurantsDataList().observe(this, new Observer<RestaurantsListResponce>() {
                @Override
                public void onChanged(@Nullable RestaurantsListResponce response) {
                    try {
                        if (response != null) {
                            if (response.getStatus() == 1) {
                                maxPage = response.getData().getLastPage();
//                                showToast(getActivity(), "max="+maxPage);

                                if (response.getData() != null && response.getData().getTotal() != 0) {
                                    clientrestaurantsListData.clear();
                                    clientrestaurantsListData.addAll(response.getData().getData());
//                                showToast(getActivity(), "list="+clientrestaurantsListData.get(1));

                                    clientrestaurantsAdapter.notifyDataSetChanged();

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
        if (ISCLIENT == "false") {
            viewModel.makeRestaurantHomeCategoriesDataList().observe(this, new Observer<RestaurantCategoryResponse>() {
                @Override
                public void onChanged(@Nullable RestaurantCategoryResponse response) {
                    try {
                        if (response != null) {
                            if (response.getStatus() == 1) {
                                maxPage = response.getData().getLastPage();
//                                        showToast(getActivity(), "max="+maxPage);

//                                        if (response.getData().getTotal() != 0) {
                                if (response.getData() != null && response.getData().getTotal() != 0) {
//                                            showToast(getActivity(), "list=");
//                                            restaurantCategoriesListData = response.getData().getData();
                                    restaurantCategoriesListData.clear();
                                    restaurantCategoriesListData.addAll(response.getData().getData());
//                                            init();
                                    restaurantCategoriesAdapter.notifyDataSetChanged();

                                } else {
                                    noResultErrorTitle.setVisibility(View.VISIBLE);
                                }
//                                        showToast(getActivity(), "success3");

                            }
                        } else {
                            showToast(getActivity(), "null");

                        }

                    } catch (Exception e) {

                    }
                }
            });

        }


    }

    private void setSpinner() {

        cityFilterAdapter = new SpinnerAdapter(getActivity());

        viewModel.getSpinnerData(getActivity(), clientHomeFillterSpinId, cityFilterAdapter, getString(R.string.select_region),
                getApiClient().getAllCities(), citiesSelectedId, null);
    }

    private void floatHidden() {
        clientAndRestaurantHomeRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && restaurantHomeAddCategoryFloatingActionBtn.getVisibility() == View.VISIBLE) {
                    restaurantHomeAddCategoryFloatingActionBtn.hide();
                } else if (dy < 0 && restaurantHomeAddCategoryFloatingActionBtn.getVisibility() != View.VISIBLE) {
                    restaurantHomeAddCategoryFloatingActionBtn.show();
                }

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        restaurantHomeAddCategoryFloatingActionBtn.show();
    }

    private void init() {
        linearLayout = new LinearLayoutManager(getActivity());
        clientAndRestaurantHomeRecyclerView.setLayoutManager(linearLayout);

        onEndLess = new OnEndLess(linearLayout, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        loadMore.setVisibility(View.VISIBLE);
                        if (Filter) {
                            clientGetRestaurantsListByFilter(current_page);
                        } else {
                            getRestaurantOrClientHomeList(current_page);
                        }

                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }
            }
        };
        clientAndRestaurantHomeRecyclerView.addOnScrollListener(onEndLess);

        if (ISCLIENT == "true") {
            clientrestaurantsAdapter = new ClientRestaurantsAdapter(getContext(), getActivity(), clientrestaurantsListData);
            clientAndRestaurantHomeRecyclerView.setAdapter(clientrestaurantsAdapter);
//            showToast(getActivity(), "success adapter");

        }
        if (ISCLIENT == "false") {

            restaurantCategoriesAdapter = new RestaurantCategoriesAdapter(getActivity(), restaurantCategoriesListData);
            clientAndRestaurantHomeRecyclerView.setAdapter(restaurantCategoriesAdapter);
        }

        if (restaurantCategoriesListData.size() == 0) {
            getRestaurantOrClientHomeList(1);
        } else {
            clientAndRestaurantHomeFragmentSFlShimmer.stopShimmer();
            clientAndRestaurantHomeFragmentSFlShimmer.setVisibility(View.GONE);
        }

        clientAndRestaurantHomeFragmentSrRefreshRv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (Filter) {
                    clientGetRestaurantsListByFilter(1);
                } else {
                    getRestaurantOrClientHomeList(1);
                }

            }
        });
    }

    private void clientGetRestaurantsListByFilter(int page) {

        Filter = true;
        keyword = clientHomeFillterSearchKeyWordEtxt.getText().toString().trim();
        Call<RestaurantsListResponce> clientRestaurantsCall;
        clientRestaurantsCall = getApiClient().getRestaurantsWithFiltter(keyword, cityFilterAdapter.selectedId, page);
        startShimmer(page);
        viewModel.getClientHomeRestaurantsDataList(getActivity(), errorSubView, clientRestaurantsCall, clientAndRestaurantHomeFragmentSrRefreshRv, loadMore, clientAndRestaurantHomeFragmentSFlShimmer);


    }

    private void getRestaurantOrClientHomeList(int page) {
        Call<RestaurantsListResponce> clientRestaurantsCall;

        Call<RestaurantCategoryResponse> restaurantCategoriesCall;

        if (ISCLIENT.equals("true")) {
            reInit();
            clientRestaurantsCall = getApiClient().getRestaurantsWithFiltter(keyword, cityFilterAdapter.selectedId, page);

//            clientRestaurantsCall = getApiClient().getRestaurantsWithoutFiltter(page);
            startShimmer(page);
            viewModel.getClientHomeRestaurantsDataList(getActivity(), errorSubView, clientRestaurantsCall, clientAndRestaurantHomeFragmentSrRefreshRv, loadMore, clientAndRestaurantHomeFragmentSFlShimmer);
//            showToast(getActivity(), "success without fillter");

        }
        if (ISCLIENT == "false") {
            clientData = LoadUserData(getActivity());
            restaurantCategoriesCall = getApiClient().getRestaurantCategories(clientData.getApiToken());

            startShimmer(page);
            viewModel.getRestaurantHomeCategoriesDataList(getActivity(), errorSubView, restaurantCategoriesCall, clientAndRestaurantHomeFragmentSrRefreshRv, loadMore, clientAndRestaurantHomeFragmentSFlShimmer);

        }


    }

    private void startShimmer(int page) {
        errorSubView.setVisibility(View.GONE);
        if (page == 1) {
            reInit();
            errorSubView.setVisibility(View.GONE);
            clientAndRestaurantHomeFragmentSFlShimmer.startShimmer();
            clientAndRestaurantHomeFragmentSFlShimmer.setVisibility(View.VISIBLE);
        }
    }

    private void reInit() {
        onEndLess.previousTotal = 0;
        onEndLess.current_page = 1;
        onEndLess.previous_page = 1;
        if (ISCLIENT.equals("true")) {
            clientrestaurantsListData = new ArrayList<>();
            clientrestaurantsAdapter = new ClientRestaurantsAdapter(getContext(), getActivity(), clientrestaurantsListData);
            clientAndRestaurantHomeRecyclerView.setAdapter(clientrestaurantsAdapter);
        }
        if (ISCLIENT == "false") {

            restaurantCategoriesListData = new ArrayList<>();
            restaurantCategoriesAdapter = new RestaurantCategoriesAdapter(getActivity(), restaurantCategoriesListData);
            clientAndRestaurantHomeRecyclerView.setAdapter(restaurantCategoriesAdapter);
        }

    }


    public void setError(String errorTitleTxt) {
        View.OnClickListener action = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Filter) {
                    clientGetRestaurantsListByFilter(1);
                } else {
                    getRestaurantOrClientHomeList(1);
                }

            }
        };
        errorSubView.setVisibility(View.VISIBLE);
        errorTitle.setText(errorTitleTxt);

    }


    @OnClick({R.id.client_home_fillter_search_img_btn, R.id.restaurant_home_add_category_floating_action_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.client_home_fillter_search_img_btn:
                if (cityFilterAdapter.selectedId == 0 && clientHomeFillterSearchKeyWordEtxt.getText().toString().trim().equals("") && Filter) {
                    Filter = false;
                    getRestaurantOrClientHomeList(1);
                } else {
                    clientGetRestaurantsListByFilter(1);
                }
                break;
            case R.id.restaurant_home_add_category_floating_action_btn:
                isDialogDataAddSuccess = true;
//                new DialogNormal(getContext(),getActivity(),false);
                showDialog(getActivity(), getContext(), "add");
//                if (Filter) {
//                    clientGetRestaurantsListByFilter(1);
//                } else {
                if (restaurantDataListOfPossision.getName() != null && isDialogDataAddSuccess) {
                    restaurantCategoriesListData.add(restaurantDataListOfPossision);
                    restaurantCategoriesAdapter.notifyItemInserted(restaurantCategoriesListData.size());
                }
//                }
                break;
        }
    }

    public void onBack() {
        getActivity().finish();
    }

}
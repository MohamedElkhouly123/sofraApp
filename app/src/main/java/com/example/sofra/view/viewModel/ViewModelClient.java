package com.example.sofra.view.viewModel;


import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.sofra.R;
import com.example.sofra.adapter.SpinnerAdapter;
import com.example.sofra.data.model.clientGetAllNotofications.ClientFireBaseToken;
import com.example.sofra.data.model.clientLogin.ClientGeneralResponse;
import com.example.sofra.data.model.clientResetPassword.ClientResetPasswordResponse;
import com.example.sofra.data.model.generalRespose.GeneralRespose;
import com.example.sofra.data.model.restaurantCategoryResponse.RestaurantCategoryResponse;
import com.example.sofra.data.model.restaurantsListAndDetailsResponce.RestaurantsListResponce;
import com.example.sofra.utils.HelperMethod;
import com.example.sofra.utils.ToastCreator;
import com.example.sofra.view.activity.HomeCycleActivity;
import com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.home.HomeFragment;
import com.example.sofra.view.fragment.splashCycle.SplashFragment;
import com.facebook.shimmer.ShimmerFrameLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.PendingIntent.getActivity;
import static com.example.sofra.data.api.ApiClient.getApiClient;
import static com.example.sofra.data.local.SharedPreferencesManger.REMEMBER_ME;
import static com.example.sofra.data.local.SharedPreferencesManger.SaveData;
import static com.example.sofra.data.local.SharedPreferencesManger.USER_DATA;
import static com.example.sofra.data.local.SharedPreferencesManger.USER_PASSWORD;
import static com.example.sofra.utils.HelperMethod.dismissProgressDialog;
import static com.example.sofra.utils.HelperMethod.progressDialog;
import static com.example.sofra.utils.HelperMethod.showToast;
import static com.example.sofra.utils.ToastCreator.onCreateErrorToast;
import static com.example.sofra.utils.network.InternetState.isConnected;


public class ViewModelClient extends ViewModel {

//    private UserRepository userRepository;
    private MutableLiveData<ClientGeneralResponse> generalRegisterationAndEditResponse = new MutableLiveData<>();
    private MutableLiveData<GeneralRespose> getSpinnerDataResponce = new MutableLiveData<>();
    private MutableLiveData<ClientResetPasswordResponse> newResetAndPasswordAndTokenResponse = new MutableLiveData<>();
    private MutableLiveData<RestaurantCategoryResponse> restaurantHomeCategoriesDataListResponse = new MutableLiveData<>();
    private MutableLiveData<RestaurantsListResponce> clientHomeRestaurantsDataListResponse = new MutableLiveData<>();

    private String token;
    private String apiToken;
    public String ISCLIENT = SplashFragment.getClient();

    public MutableLiveData<ClientGeneralResponse> makeGeneralRegisterationAndEdit() {
        return generalRegisterationAndEditResponse;
    }

    public void makeGeneralRegisterationAndEditToServer(final Activity activity,final Call<ClientGeneralResponse> method, final String password, final boolean remember, final boolean auth) {
        if (isConnected(activity)) {

            if (progressDialog == null) {
                HelperMethod.showProgressDialog(activity, activity.getString(R.string.wait));
            } else {
                if (!progressDialog.isShowing()) {
                    HelperMethod.showProgressDialog(activity, activity.getString(R.string.wait));
                }
            }
//            userRepository = new UserRepository();

//            UserRepository.getInstance().clientLogin(activity, email, password, remember, auth)

                method.enqueue(new Callback<ClientGeneralResponse>() {
                @Override
                public void onResponse(Call<ClientGeneralResponse> call, Response<ClientGeneralResponse> response) {

                    if (response.body() != null) {
                        try {
                            dismissProgressDialog();

                            if (response.body().getStatus() == 1) {
                                SaveData(activity, USER_PASSWORD, password);
                                SaveData(activity, USER_DATA, response.body().getData());
                                if (auth) {
                                    SaveData(activity, REMEMBER_ME, remember);
                                    Call<ClientResetPasswordResponse> tokenCall = null;
                                    token=new ClientFireBaseToken().getToken();
                                    apiToken=response.body().getData().getApiToken();
                                    if (ISCLIENT.equals("true")) {

                                        tokenCall = getApiClient().clientSignUpToken(token, "android",apiToken);
                                    }  if(ISCLIENT=="false") {
                                        tokenCall = getApiClient().restaurantSignUpToken(token, "android",apiToken);
                                    }
                                        getAndMakeResetAndNewPasswordAndTokenAndChangeStatus(activity,tokenCall);
                                    Intent intent = new Intent(activity, HomeCycleActivity.class);
                                    activity.startActivity(intent);
                                    activity.finish();
                                }
                                generalRegisterationAndEditResponse.postValue(response.body());

                                ToastCreator.onCreateSuccessToast(activity, response.body().getMsg());
                            } else {
//                                onCreateErrorToast(activity, response.body().getMsg());
                            }
                        } catch (Exception e) {

                        }
                    }
                }

                @Override
                public void onFailure(Call<ClientGeneralResponse> call, Throwable t) {
                    dismissProgressDialog();
                    onCreateErrorToast(activity, activity.getString(R.string.error));
                    generalRegisterationAndEditResponse.postValue(null);
                }
            });
        } else {
            try {
                onCreateErrorToast(activity, activity.getString(R.string.error_inter_net));
            } catch (Exception e) {

            }

        }

    }

    public MutableLiveData<ClientResetPasswordResponse> makeResetAndNewPasswordAndTokenAndChangeStatusResponse() {
        return newResetAndPasswordAndTokenResponse;
    }

    public void getAndMakeResetAndNewPasswordAndTokenAndChangeStatus(final Activity activity, final Call<ClientResetPasswordResponse> method) {
        if (isConnected(activity)) {

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
                            dismissProgressDialog();

                            if (response.body().getStatus() == 1) {

                                newResetAndPasswordAndTokenResponse.postValue(response.body());
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
                    newResetAndPasswordAndTokenResponse.postValue(null);
                }
            });
        } else {
            try {
                onCreateErrorToast(activity, activity.getString(R.string.error_inter_net));
            } catch (Exception e) {

            }

        }

    }

    public MutableLiveData<GeneralRespose> makegetSpinnerData() {
        return getSpinnerDataResponce;
    }

    public void getSpinnerData(final Activity activity, final Spinner spinner, final SpinnerAdapter adapter, final String hint
            , final Call<GeneralRespose> method, final int selectedId1, final AdapterView.OnItemSelectedListener listener) {
        if (isConnected(activity)) {
        if (progressDialog == null) {
            HelperMethod.showProgressDialog(activity, activity.getString(R.string.wait));
        } else {
            if (!progressDialog.isShowing()) {
                HelperMethod.showProgressDialog(activity, activity.getString(R.string.wait));
            }
        }

        method.enqueue(new Callback<GeneralRespose>() {
            @Override
            public void onResponse(Call<GeneralRespose> call, Response<GeneralRespose> response) {

                if (response.body() != null) {
                    try {

                        dismissProgressDialog();
                        if (response.body().getStatus() == 1) {
//                            showToast(activity, "hereSpinner");

                            adapter.setData(response.body().getData().getData(), hint);

                            spinner.setAdapter(adapter);

                            spinner.setSelection(selectedId1);

                            if (listener != null) {
                                spinner.setOnItemSelectedListener(listener);
                            }

                            getSpinnerDataResponce.postValue(response.body());

                            ToastCreator.onCreateSuccessToast(activity, response.body().getMsg());
                        } else {
                            onCreateErrorToast(activity, response.body().getMsg());
                        }

                    } catch(Exception e){

                    }
                }
            }

            @Override
            public void onFailure(Call<GeneralRespose> call, Throwable t) {
                dismissProgressDialog();
                getSpinnerDataResponce.postValue(null);

            }
        });
        } else {
            try {
                onCreateErrorToast(activity, activity.getString(R.string.error_inter_net));
            } catch (Exception e) {

            }

        }
    }

    public MutableLiveData<RestaurantCategoryResponse> makeRestaurantHomeCategoriesDataList() {
        return restaurantHomeCategoriesDataListResponse;
    }

    public void getRestaurantHomeCategoriesDataList(final Activity activity,final TextView noResultErrorTitle, final Call<RestaurantCategoryResponse> method,final SwipeRefreshLayout clientAndRestaurantHomeFragmentSrRefreshRv, final RelativeLayout loadMore, final ShimmerFrameLayout clientAndRestaurantHomeFragmentSFlShimmer) {
        if (isConnected(activity)) {



            method.enqueue(new Callback<RestaurantCategoryResponse>() {
                @Override
                public void onResponse(Call<RestaurantCategoryResponse> call, Response<RestaurantCategoryResponse> response) {

                    if (response.body() != null) {
                        try {
                            clientAndRestaurantHomeFragmentSFlShimmer.stopShimmer();
                            clientAndRestaurantHomeFragmentSFlShimmer.setVisibility(View.GONE);
                            loadMore.setVisibility(View.GONE);
                            clientAndRestaurantHomeFragmentSrRefreshRv.setRefreshing(false);
                            if (response.body().getStatus() == 1) {
//                                    new HomeFragment().maxPage = response.body().getData().getLastPage();
//
//                                    if (response.body().getData().getTotal() != 0) {
//                                        new HomeFragment().restaurantCategoriesListData.addAll(response.body().getData().getData());
//                                        new HomeFragment().restaurantCategoriesAdapter.notifyDataSetChanged();
//
//                                    } else {
//                                        noResultErrorTitle.setVisibility(View.VISIBLE);
//                                    }
                                restaurantHomeCategoriesDataListResponse.postValue(response.body());

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
                public void onFailure(Call<RestaurantCategoryResponse> call, Throwable t) {
                    try {
                        clientAndRestaurantHomeFragmentSFlShimmer.stopShimmer();
                        clientAndRestaurantHomeFragmentSFlShimmer.setVisibility(View.GONE);
                        loadMore.setVisibility(View.GONE);
                        clientAndRestaurantHomeFragmentSrRefreshRv.setRefreshing(false);
                        new HomeFragment().setError(String.valueOf(R.string.error_list));
                        restaurantHomeCategoriesDataListResponse.postValue(null);
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
                new HomeFragment().setError(String.valueOf(R.string.error_inter_net));
                onCreateErrorToast(activity, activity.getString(R.string.error_inter_net));
            } catch (Exception e) {

            }

        }
    }

    public MutableLiveData<RestaurantsListResponce> makeClientHomeRestaurantsDataList() {
        return clientHomeRestaurantsDataListResponse;
    }

    public void getClientHomeRestaurantsDataList(final Activity activity,final TextView noResultErrorTitle, final Call<RestaurantsListResponce> method, final SwipeRefreshLayout clientAndRestaurantHomeFragmentSrRefreshRv, final RelativeLayout loadMore, final ShimmerFrameLayout clientAndRestaurantHomeFragmentSFlShimmer) {
        if (isConnected(activity)) {


            method.enqueue(new Callback<RestaurantsListResponce>() {
                @Override
                public void onResponse(Call<RestaurantsListResponce> call, Response<RestaurantsListResponce> response) {
                    showToast(activity, "here4");

                    if (response.body() != null) {
                        try {

                            clientAndRestaurantHomeFragmentSFlShimmer.stopShimmer();
                            clientAndRestaurantHomeFragmentSFlShimmer.setVisibility(View.GONE);
                            loadMore.setVisibility(View.GONE);
                            clientAndRestaurantHomeFragmentSrRefreshRv.setRefreshing(false);
                            if (response.body().getStatus() == 1) {
//                               new HomeFragment().maxPage = response.body().getData().getLastPage();
//                                showToast(activity, "max="+new HomeFragment().maxPage);
//
//                                if (response.body().getData().getTotal() != 0) {
//                                    new HomeFragment().clientrestaurantsListData.addAll(response.body().getData().getData());
//                                    showToast(activity, "list="+new HomeFragment().clientrestaurantsListData.get(1));
//
//                                    new HomeFragment().clientrestaurantsAdapter.notifyDataSetChanged();
//
//                                } else {
//                                    noResultErrorTitle.setVisibility(View.VISIBLE);
//                                }
                                clientHomeRestaurantsDataListResponse.postValue(response.body());

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
                public void onFailure(Call<RestaurantsListResponce> call, Throwable t) {
                    try {
                        clientAndRestaurantHomeFragmentSFlShimmer.stopShimmer();
                        clientAndRestaurantHomeFragmentSFlShimmer.setVisibility(View.GONE);
                        loadMore.setVisibility(View.GONE);
                        clientAndRestaurantHomeFragmentSrRefreshRv.setRefreshing(false);
                        new HomeFragment().setError(String.valueOf(R.string.error_list));
                        clientHomeRestaurantsDataListResponse.postValue(null);
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
                new HomeFragment().setError(String.valueOf(R.string.error_inter_net));
                onCreateErrorToast(activity, activity.getString(R.string.error_inter_net));
            } catch (Exception e) {

            }

        }
    }



}

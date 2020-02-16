package com.example.sofra.view.viewModel;


import android.app.Activity;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sofra.R;
import com.example.sofra.adapter.SpinnerAdapter;
import com.example.sofra.data.model.clientLogin.ClientGeneralResponse;
import com.example.sofra.data.model.clientResetPassword.ClientResetPasswordResponse;
import com.example.sofra.data.model.generalRespose.GeneralRespose;
import com.example.sofra.utils.HelperMethod;
import com.example.sofra.utils.ToastCreator;
import com.example.sofra.view.activity.HomeCycleActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.PendingIntent.getActivity;
import static com.example.sofra.data.local.SharedPreferencesManger.REMEMBER_ME;
import static com.example.sofra.data.local.SharedPreferencesManger.SaveData;
import static com.example.sofra.data.local.SharedPreferencesManger.USER_DATA;
import static com.example.sofra.data.local.SharedPreferencesManger.USER_PASSWORD;
import static com.example.sofra.utils.HelperMethod.dismissProgressDialog;
import static com.example.sofra.utils.HelperMethod.progressDialog;
import static com.example.sofra.utils.ToastCreator.onCreateErrorToast;
import static com.example.sofra.utils.network.InternetState.isConnected;


public class ViewModelClient extends ViewModel {

//    private UserRepository userRepository;
    private MutableLiveData<ClientGeneralResponse> generalRegisterationAndEditResponse = new MutableLiveData<>();
    private MutableLiveData<GeneralRespose> getSpinnerDataResponce = new MutableLiveData<>();
    private MutableLiveData<ClientResetPasswordResponse> newResetAndPasswordAndTokenResponse = new MutableLiveData<>();
    private MutableLiveData<ClientGeneralResponse> restaurantContactDataResponse = new MutableLiveData<>();

    public MutableLiveData<ClientGeneralResponse> makeGeneralRegisterationAndEdit() {
        return generalRegisterationAndEditResponse;
    }

    public void makeGeneralRegisterationAndEditToServer(final Activity activity,final Call<ClientGeneralResponse> method,final Call<ClientResetPasswordResponse> tokenMethod, final String password, final boolean remember, final boolean auth) {
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
                                    makeResetAndNewPasswordAndToken(activity,tokenMethod);
                                    Intent intent = new Intent(activity, HomeCycleActivity.class);
                                    activity.startActivity(intent);
                                    activity.finish();
                                }
                                generalRegisterationAndEditResponse.postValue(response.body());

                                ToastCreator.onCreateSuccessToast(activity, response.body().getMsg());
                            } else {
                                onCreateErrorToast(activity, response.body().getMsg());
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

    public MutableLiveData<ClientResetPasswordResponse> makeResetAndNewPasswordAndTokenResponse() {
        return newResetAndPasswordAndTokenResponse;
    }

    public void makeResetAndNewPasswordAndToken(final Activity activity, final Call<ClientResetPasswordResponse> method) {
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


}

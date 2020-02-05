package com.example.sofra.data.repository;


import android.app.Activity;
import android.content.Intent;

import androidx.lifecycle.MutableLiveData;

import com.example.sofra.R;
import com.example.sofra.data.model.clientLogin.ClientGeneralResponse;
import com.example.sofra.utils.HelperMethod;
import com.example.sofra.utils.ToastCreator;
import com.example.sofra.view.activity.HomeCycleActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getApiClient;
import static com.example.sofra.data.local.SharedPreferencesManger.REMEMBER_ME;
import static com.example.sofra.data.local.SharedPreferencesManger.SaveData;
import static com.example.sofra.data.local.SharedPreferencesManger.USER_DATA;
import static com.example.sofra.data.local.SharedPreferencesManger.USER_PASSWORD;

public class UserRepository {
//    private final UserProfileDao userProfileDao;
//    private static DataBase db;
//    private static UserRepository sInstance;
//    private LiveData<List<Client>> mSubjects;

//    public UserRepository(Application application) {
//        db = DataBase.getInstance(application);
//        userProfileDao = db.userProfileDao();
//        mSubjects = userProfileDao.getusers();
//
//    }

    // region singleton implementation
    private static class Loader {
        static UserRepository INSTANCE = new UserRepository();
    }


    public static UserRepository getInstance() {
        return Loader.INSTANCE;
    }


    public Call<ClientGeneralResponse> clientLogin(final Activity activity, final String email, final String password, final boolean remember, final boolean auth) {
        final MutableLiveData<ClientGeneralResponse> data = new MutableLiveData<>();
        getApiClient().clientLogin(email, password)
                .enqueue(new Callback<ClientGeneralResponse>() {


                    @Override
                    public void onResponse(Call<ClientGeneralResponse> call, Response<ClientGeneralResponse> response) {

//                        Client response2=response.body().getData().getUser();

                        if (response.body() != null) {
                            data.postValue(response.body());
                            try {
                                HelperMethod.dismissProgressDialog();

                                if (response.body().getStatus() == 1) {

                                    SaveData(activity, USER_PASSWORD, password);
                                    SaveData(activity, USER_DATA, response.body().getData());
//                                    DataBase.getInstance(activity).userProfileDao().insert();
//                                    saveDataInDataBase(response2.getRegionId(), String email, String deliveryTime,
//                                            String deliveryCost, String minimumCharger, String phone, String whatsapp,
//                                            String photo, String availability, String password, String password_confirmation,
//                                            String activated, int rate, String photoUrl)
                                    if (auth) {
                                        SaveData(activity, REMEMBER_ME, remember);
                                        Intent intent = new Intent(activity, HomeCycleActivity.class);
                                        activity.startActivity(intent);
                                        activity.finish();
                                    }

                                }

                                ToastCreator.onCreateErrorToast(activity, response.body().getMsg());

                            } catch (Exception e) {

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ClientGeneralResponse> call, Throwable t) {
                        HelperMethod.dismissProgressDialog();
                        ToastCreator.onCreateErrorToast(activity, activity.getString(R.string.error));
                        data.postValue(null);
                    }
                });
        return (Call<ClientGeneralResponse>) data;
    }

//    private Client saveDataInDataBase(String regionId, String email, String deliveryTime,
//                                      String deliveryCost, String minimumCharger, String phone, String whatsapp,
//                                      String photo, String availability, String password, String password_confirmation,
//                                      String activated, int rate, String photoUrl) {
//
//
//        Client userData = new Client();
//        userData.setRegionId(regionId);
//        userData.setEmail(email);
//        userData.setDeliveryTime(deliveryTime);
//        userData.setDeliveryCost(deliveryCost);
//        userData.setMinimumCharger(minimumCharger);
//        userData.setPhone(phone);
//        userData.setWhatsapp(whatsapp);
//        userData.setPhoto(photo);
//        userData.setAvailability(availability);
//        userData.setPassword(password);
//        userData.setPassword_confirmation(password_confirmation);
//        userData.setActivated(activated);
//        userData.setRate(rate);
//        userData.setPhotoUrl(photoUrl);
//
//        return userData;
//    }
//    public void insert(final Client[] subjectEntity) {
//        Executors.newSingleThreadExecutor().execute(() ->{
//                UserProfileDao.insert(subjectEntity);
//   });
//    }

//    public void insert(final Client subjectEntity) {
//        AppExecutors.getInstance().diskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//                UserProfileDao.insert(Client);
//            }
//        });
//    }


}


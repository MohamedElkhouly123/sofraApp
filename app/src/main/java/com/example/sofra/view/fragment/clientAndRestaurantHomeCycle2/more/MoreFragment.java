package com.example.sofra.view.fragment.clientAndRestaurantHomeCycle2.more;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.sofra.R;
import com.example.sofra.data.local.DataBase;
import com.example.sofra.data.model.clientLogin.ClientData;
import com.example.sofra.data.model.clientMakeNewOrder.ClientMakeNewOrderItemForRoom;
import com.example.sofra.utils.LogOutDialog;
import com.example.sofra.view.activity.HomeCycleActivity;
import com.example.sofra.view.activity.UserCycleActivity;
import com.example.sofra.view.fragment.BaSeFragment;
import com.example.sofra.view.fragment.splashCycle.SplashFragment;

import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.ContentValues.TAG;
import static com.example.sofra.data.local.SharedPreferencesManger.LoadUserData;
import static com.example.sofra.utils.HelperMethod.replaceFragment;
import static com.example.sofra.utils.HelperMethod.replaceFragmentWithAnimation;
import static com.example.sofra.utils.HelperMethod.showToast;

public class MoreFragment extends BaSeFragment {


    @BindView(R.id.more_my_put_coments_rate_on_store_lay)
    LinearLayout moreMyPutComentsRateOnStoreLay;
    @BindView(R.id.more_my_put_coments_rate_on_store_view_line)
    ImageView moreMyPutComentsRateOnStoreViewLine;
    public String ISCLIENT = SplashFragment.getClient();
    private ClientData clientData;
    private boolean goLogin = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_more, container, false);
        ButterKnife.bind(this, root);
        setUpActivity();
        clientData = LoadUserData(getActivity());
        if (ISCLIENT == "false") {
            moreMyPutComentsRateOnStoreLay.setVisibility(View.VISIBLE);
            moreMyPutComentsRateOnStoreViewLine.setVisibility(View.VISIBLE);

        }
        homeCycleActivity = (HomeCycleActivity) getActivity();
        homeCycleActivity.setNavigation("v");
        return root;
    }

    public void goToRegisterFirst(Activity activity) {
        showToast(activity, "Go To Register or Login First");
        Intent intent2 = new Intent(getActivity(), UserCycleActivity.class);
        getActivity().startActivity(intent2);
    }

    @OnClick({R.id.more_my_Offers_lay, R.id.more_contact_us_lay, R.id.more_about_app_lay, R.id.more_my_put_coments_rate_on_store_lay, R.id.more_change_password_lay, R.id.more_sign_out_lay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.more_my_Offers_lay:
//                if(clientData!=null) {
                DataBase dataBase = DataBase.getInstance(getContext());

                LiveData<List<ClientMakeNewOrderItemForRoom>> items2;
                String[] name = new String[10];
//                Executors.newSingleThreadExecutor().execute(() -> {
//
//                });
                Executors.newSingleThreadExecutor().execute(

                        new Runnable() {
                            @Override
                            public void run() {
//                            orderItem = new OrderItem(item.getId(), Integer.parseInt(item.getRestaurantId()), Double.parseDouble(item.getPrice()),
//                                    quantity, "ahmed", note, item.getPhotoUrl(),
//                                    item.getName());
//                            roomDao.addItem(orderItem);
                                ClientMakeNewOrderItemForRoom clientMakeNewOrderItemForRoom = new ClientMakeNewOrderItemForRoom("mohamed","good","77");
//                                clientMakeNewOrderItemForRoo z z  s m.setCategoryId("2");
//                                clientMakeNewOrderItemForRoom.setDescription("good2");
//                                clientMakeNewOrderItemForRoom.setName("checolete2");
//                                Log.i(TAG, clientMakeNewOrderItemForRoom.getName()+"yes");
                                dataBase.addNewOrderItemDao().deletAll();

                                dataBase.addNewOrderItemDao().insert(clientMakeNewOrderItemForRoom);
//                                dataBase.addNewOrderItemDao().add(clientMakeNewOrderItemForRoom);
//                                dataBase.addNewOrderItemDao().add(clientMakeNewOrderItemForRoom);

//                    DataBase.getInstance(getContext()).addNewOrderItemDao().update(clientMakeNewOrderItemForRoom);
                                List<ClientMakeNewOrderItemForRoom> items = dataBase.addNewOrderItemDao().getAllItems();
                                Log.i(TAG, items.get(0).getName()+"yes");
//                                Log.i(TAG, items.get(7).getName()+"yes");

                                for (int i = 0; i > items.size(); i++) {
                                    name[i] = items.get(i).getDescription();
                                }
                            }
                        });


//                for (int i=0;i>name.length;i++) {

                showToast(getActivity(), name[0] + name[1]);
//                }

                replaceFragmentWithAnimation(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, new RestaurantAndClientOffersFragment(), "t");
                homeCycleActivity.setNavigation("g");
//                }else {
//                    goToRegisterFirst(getActivity());
//                    goLogin = true;
//                }

                break;
            case R.id.more_contact_us_lay:
                if (clientData != null) {
                    replaceFragmentWithAnimation(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, new ContactWithUsFragment(), "t");
                    homeCycleActivity.setNavigation("g");
                } else {
                    goToRegisterFirst(getActivity());
                    goLogin = true;
                }

//                showDialog(getActivity(),getContext(),"add");
                break;
            case R.id.more_about_app_lay:
                replaceFragmentWithAnimation(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, new AboutAppFragment(), "t");
                homeCycleActivity.setNavigation("g");
                break;
            case R.id.more_my_put_coments_rate_on_store_lay:
                replaceFragmentWithAnimation(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, new RestaurantCommentsFragment(), "t");
                homeCycleActivity.setNavigation("g");
                break;
            case R.id.more_change_password_lay:
                if (clientData != null) {
                    replaceFragmentWithAnimation(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, new ChangePasswordFragment(), "t");
                    homeCycleActivity.setNavigation("g");
                } else {
                    goToRegisterFirst(getActivity());
                    goLogin = true;
                }
                break;
            case R.id.more_sign_out_lay:
                if (clientData != null) {
                    new LogOutDialog().showDialog(getActivity());
                } else {
                    goToRegisterFirst(getActivity());
                    goLogin = true;
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (goLogin && LoadUserData(getActivity()) != null) {
            goLogin = false;
            replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, new MoreFragment());
            homeCycleActivity.navView.setSelectedItemId(R.id.navigation_more_setting);
        }
    }

    @Override
    public void onBack() {
        replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, homeCycleActivity.homeFragment);
        homeCycleActivity.navView.getMenu().getItem(0).setChecked(true);
    }
}
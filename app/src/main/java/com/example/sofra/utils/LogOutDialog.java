package com.example.sofra.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sofra.R;
import com.example.sofra.data.model.clientGetAllNotofications.ClientFireBaseToken;
import com.example.sofra.data.model.clientLogin.ClientData;
import com.example.sofra.data.model.clientResetPassword.ClientResetPasswordResponse;
import com.example.sofra.data.model.restaurantCategoryResponse.RestaurantCategoryResponse;
import com.example.sofra.view.activity.SplashCycleActivity;
import com.example.sofra.view.activity.UserCycleActivity;
import com.example.sofra.view.fragment.splashCycle.SplashFragment;

import retrofit2.Call;

import static com.example.sofra.data.api.ApiClient.getApiClient;
import static com.example.sofra.data.local.SharedPreferencesManger.CLIENT;
import static com.example.sofra.data.local.SharedPreferencesManger.LoadData;
import static com.example.sofra.data.local.SharedPreferencesManger.LoadUserData;
import static com.example.sofra.data.local.SharedPreferencesManger.clean;
import static com.example.sofra.utils.GeneralRequest.deleteAndUpdateItemCallBack;


public class LogOutDialog {
    public String ISCLIENT = SplashFragment.getClient();
    private ClientData clientData;

    public void showDialog(final Activity activity) {
        if(ISCLIENT==null){
            ISCLIENT = LoadData(activity, CLIENT);
        }
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setContentView(R.layout.dialog_sign_out);
        dialog.setCanceledOnTouchOutside(true);
//        TextView text = (TextView) dialog.findViewById(R.id.text);

        TextView dialogImageOk = (TextView) dialog.findViewById(R.id.item_sign_out_dialog_btn_yes);
        dialogImageOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Call
                clientData = LoadUserData(activity);
                clean(activity);
                if (clientData!=null){
                Call<RestaurantCategoryResponse> removetTokenCall = null;
                String token=new ClientFireBaseToken().getToken();
                String apiToken=clientData.getApiToken();
                if (ISCLIENT.equalsIgnoreCase("true")) {

                    removetTokenCall = getApiClient().clientRemoveToken(token,apiToken);
                }  if(ISCLIENT=="false") {
                    removetTokenCall = getApiClient().restaurantRemoveToken(token,apiToken);
                }
                deleteAndUpdateItemCallBack(activity,removetTokenCall);}
                Intent i = new Intent(activity, SplashCycleActivity.class);

                activity.startActivity(i);
                // close this activity
                activity.finish();
            }
        });
        dialog.setCanceledOnTouchOutside(true);
        TextView dialogImageNo = (TextView) dialog.findViewById(R.id.item_sign_out_dialog_btn_no);
        dialogImageNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });


        dialog.show();

    }
}

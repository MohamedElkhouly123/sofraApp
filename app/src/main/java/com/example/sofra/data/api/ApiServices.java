package com.example.sofra.data.api;


import com.example.sofra.data.model.clientGetAllNotofications.ClientGetAllNotoficationsResponse;
import com.example.sofra.data.model.clientLogin.ClientGeneralResponse;
import com.example.sofra.data.model.clientMakeNewOrder.ClientMakeNewOrderResponse;
import com.example.sofra.data.model.clientMyOrder.ClientMyOrderResponse;
import com.example.sofra.data.model.clientResetPassword.ClientResetPasswordResponse;
import com.example.sofra.data.model.clientRestaurantReview.ClientRestaurantReviewResponse;
import com.example.sofra.data.model.contactUs.ContactUsResponce;
import com.example.sofra.data.model.generalRespose.GeneralRespose;
import com.example.sofra.data.model.getSetting.GetSettingData;
import com.example.sofra.data.model.paymentMethods.PaymentMethodsResponce;
import com.example.sofra.data.model.restaurantChangeState.RestaurantChangeStateResponse;
import com.example.sofra.data.model.restaurantGetAllCommisions.RestaurantGetAllCommisionsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServices {




    @POST("client/login")
    @FormUrlEncoded
    Call<ClientGeneralResponse> clientLogin(@Field("email") String email,
                                            @Field("password") String password);

    @FormUrlEncoded
    @POST("client/sign-up")
    Call<ClientGeneralResponse> clientRegistration(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("password_confirmation") String passwordConfirmation,
            @Field("phone") String phone,
            @Field("region_id") int region_id,
            @Field("profile_image") String profileImage
    );
    @POST("client/reset-password")
    @FormUrlEncoded
    Call<ClientResetPasswordResponse> clientResetPassword(
            @Field("email") String email
    );

    @POST("client/new-password")
    @FormUrlEncoded
    Call<ClientResetPasswordResponse> clientNewPassword(
            @Field("code") String code,
            @Field("password") String password,
            @Field("password_confirmation") String passwordConfirmation
    );

    @POST("client/change-password")
    @FormUrlEncoded
    Call<ClientResetPasswordResponse> clientChangePassword(
            @Field("api_token") String apiToken,
            @Field("old_password") String oldPassword,
            @Field("password") String password,
            @Field("password_confirmation") String passwordConfirmation
    );

    @POST("client/profile")
    @FormUrlEncoded
    Call<ClientGeneralResponse> getClientProfile(
            @Field("api_token") String apiToken
    );

    @POST("client/profile")
    @FormUrlEncoded
    Call<ClientGeneralResponse> editClientProfile(
            @Field("api_token") String apiToken,
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("region_id") String region_id,
            @Field("profile_image") String profileImage
            );

    @POST("client/signup-token")
    @FormUrlEncoded
    Call<ClientResetPasswordResponse> clientSignUpToken(
            @Field("token") String token,
            @Field("type") String type,
            @Field("api_token") String api_token
    );

    @POST("client/remove-token")
    @FormUrlEncoded
    Call<ClientResetPasswordResponse> clientRemoveToken(
            @Field("token") String token,
            @Field("type") String type,
            @Field("api_token") String api_token
    );

    @POST("client/new-order")
    @FormUrlEncoded
    Call<ClientMakeNewOrderResponse> clientNewOrder(
            @Field("restaurant_id") String restaurantId,
            @Field("note") String note,
            @Field("address") String address,
            @Field("payment_method_id") String paymentMethodId,
            @Field("phone") String phone,
            @Field("name") String name,
            @Field("api_token") String apiToken,
            @Field("profile_image") String profileImage,
            @Field("items[]") List<Integer> items,
            @Field("quantities[]") List<Integer> quantities,
            @Field("notes[]") List<Integer> notes


    );

    @POST("client/confirm-order")
    @FormUrlEncoded
    Call<ClientResetPasswordResponse> clientConfirmOrder(
            @Field("order_id") String orderId,
            @Field("api_token") String api_token
    );

    @POST("client/decline-order")
    @FormUrlEncoded
    Call<ClientResetPasswordResponse> clientDeclineOrder(
            @Field("order_id") String order_id,
            @Field("api_token") String api_token
    );

    @POST("client/restaurant/review")
    @FormUrlEncoded
    Call<ClientRestaurantReviewResponse> clientRestaurantReview(
            @Field("rate") String rate,
            @Field("comment") String comment,
            @Field("restaurant_id") String restaurantId,
            @Field("api_token") String api_token
    );

    @GET("client/notifications")
    Call<ClientGetAllNotoficationsResponse> clientGetAllNotofications(
            @Query("api_token") String apiToken

    );

    @GET("client/my-orders")
    Call<ClientMyOrderResponse> getMyOrder(
            @Query("api_token") String apiToken,
            @Query("state") String state

    );

    @GET("client/show-order")
    Call<ClientMakeNewOrderResponse> showOrder(
            @Query("api_token") String apiToken,
            @Query("order_id") String orderId

    );

    @POST("restaurant/login")
    @FormUrlEncoded
    Call<ClientGeneralResponse> restaurantLogin(@Field("email") String email,
                                                @Field("password") String password);

    @FormUrlEncoded
    @POST("restaurant/sign-up")
    Call<ClientGeneralResponse> restaurantRegistration(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("password_confirmation") String passwordConfirmation,
            @Field("phone") String phone,
            @Field("whatsapp") String whatsapp,
            @Field("region_id") int regionId,
            @Field("delivery_cost") String deliveryCost,
            @Field("minimum_charger") String minimumCharger,
            @Field("photo") String photo,
            @Field("delivery_time") String deliveryTime

            );
    @POST("restaurant/reset-password")
    @FormUrlEncoded
    Call<ClientResetPasswordResponse> restaurantResetPassword(
            @Field("email") String email
    );

    @POST("restaurant/new-password")
    @FormUrlEncoded
    Call<ClientResetPasswordResponse> restaurantNewPassword(
            @Field("code") String code,
            @Field("password") String password,
            @Field("password_confirmation") String passwordConfirmation
    );

    @POST("restaurant/change-password")
    @FormUrlEncoded
    Call<ClientResetPasswordResponse> restaurantChangePassword(
            @Field("api_token") String apiToken,
            @Field("old_password") String oldPassword,
            @Field("password") String password,
            @Field("password_confirmation") String passwordConfirmation
    );

    @POST("restaurant/register-token")
    @FormUrlEncoded
    Call<ClientResetPasswordResponse> restaurantSignUpToken(
            @Field("token") String token,
            @Field("type") String type,
            @Field("api_token") String api_token
    );

    @POST("restaurant/remove-token")
    @FormUrlEncoded
    Call<ClientResetPasswordResponse> restaurantRemoveToken(
            @Field("token") String token,
            @Field("api_token") String api_token
    );


    @POST("client/profile")
    @FormUrlEncoded
    Call<ClientGeneralResponse> editRestaurantProfile(
            @Field("email") String email,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("region_id") String region_id,
            @Field("delivery_cost") String deliveryCost,
            @Field("minimum_charger") String minimumCharger,
            @Field("availability") String availability,
            @Field("photo") String photo,
            @Field("api_token") String apiToken,
            @Field("delivery_time") String deliveryTime
    );

    @POST("restaurant/change-state")
    @FormUrlEncoded
    Call<RestaurantChangeStateResponse> restaurantChangeState(
            @Field("state") String state,
            @Field("api_token") String api_token
    );

    @POST("restaurant/profile")
    @FormUrlEncoded
    Call<ClientGeneralResponse> restaurantGetUserProfile(
            @Field("api_token") String api_token
    );
    @POST("test-notification")
    @FormUrlEncoded
    Call<ClientGetAllNotoficationsResponse> testNotification(
            @Field("title") String title,
            @Field("token") String token,
            @Field("body") String body
    );

    @POST("contact")
    @FormUrlEncoded
    Call<ContactUsResponce> contactUs(
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("type") String type,
            @Field("content") String content
    );

    @GET("restaurant/notifications")
    Call<ClientGetAllNotoficationsResponse> restaurantGetAllNotofications(
            @Query("api_token") String apiToken

    );

    @GET("restaurant/commissions")
    Call<RestaurantGetAllCommisionsResponse> restaurantGetCommisions(
            @Query("api_token") String apiToken

    );

    @GET("cities")
    Call<GeneralRespose> getAllCities(

    );

    @GET("regions")
    Call<GeneralRespose> getRegion(
            @Query("city_id") int cityId

    );

    @GET("cities-not-paginated")
    Call<GeneralRespose> getAllCitiesNoPagination(
            @Query("name") String name

    );

    @GET("regions-not-paginated")
    Call<GeneralRespose> getRegionNoPagination(
            @Query("city_id") int cityId

    );

    @GET("payment-methods")
    Call<PaymentMethodsResponce> paymentMethods(

    );

    @GET("settings")
    Call<GetSettingData> getSetting(
            @Query("email") String email,
            @Query("password") String password


    );
}

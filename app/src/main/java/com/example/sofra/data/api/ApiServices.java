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
import com.example.sofra.data.model.listRestaurantItem.FoodItemsResponse;
import com.example.sofra.data.model.orderResponse.OrderResponse;
import com.example.sofra.data.model.paymentMethods.PaymentMethodsResponce;
import com.example.sofra.data.model.restaurantCategoryResponse.RestaurantCategoriesListResponse;
import com.example.sofra.data.model.restaurantCategoryResponse.RestaurantCategoryResponse;
import com.example.sofra.data.model.restaurantChangeState.RestaurantChangeStateResponse;
import com.example.sofra.data.model.restaurantCommission.RestaurantCommissionResponse;
import com.example.sofra.data.model.restaurantOffer.RestaurantOfferResponce;
import com.example.sofra.data.model.restaurantSubCategoriesItemsListResponce.RestaurantSubCategoriesItemsListResponce;
import com.example.sofra.data.model.restaurantsListAndDetailsResponce.RestaurantsListResponce;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiServices {




    @POST("client/login")
    @FormUrlEncoded
    Call<ClientGeneralResponse> clientLogin(@Field("email") String email,
                                            @Field("password") String password);

    @Multipart
    @POST("client/sign-up")
    Call<ClientGeneralResponse> clientRegistration(
            @Part("name") RequestBody name,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("password_confirmation") RequestBody passwordConfirmation,
            @Part("phone") RequestBody phone,
            @Part("region_id") RequestBody region_id,
            @Part("profile_image") MultipartBody.Part profileImage
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
    @Multipart
    Call<ClientGeneralResponse> editClientProfile(
            @Part("api_token") RequestBody apiToken,
            @Part("name") RequestBody name,
            @Part("email") RequestBody email,
            @Part("phone") RequestBody phone,
            @Part("region_id") RequestBody region_id,
            @Part("profile_image") MultipartBody.Part profileImage
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

//    Notice here>>>>>>>>>>>>>>>>>>>

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
//            @Part("profile_image") MultipartBody.Part profileImage,
            @Field("items[]") List<Integer> items,
            @Field("quantities[]") List<Integer> quantities,
            @Field("notes[]") List<Integer> notes

//             List<RequestBody> notes



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
    Call<ClientGeneralResponse> restaurantLogin(

            @Field("email") String email,
            @Field("password") String password);

    @Multipart
    @POST("restaurant/sign-up")

    Call<ClientGeneralResponse> restaurantRegistration(

            @Part("name") RequestBody name,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("password_confirmation") RequestBody passwordConfirmation,
            @Part("phone") RequestBody phone,
            @Part("whatsapp") RequestBody whatsapp,
            @Part("region_id") RequestBody regionId,
            @Part("delivery_cost") RequestBody deliveryCost,
            @Part("minimum_charger") RequestBody minimumCharger,
            @Part("photo") MultipartBody.Part photo,
            @Part("delivery_time") RequestBody deliveryTime

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

    @Multipart
    @POST("restaurant/new-offer")
    Call<RestaurantOfferResponce> restaurantNewOffers(

            @Part("description") RequestBody description,
            @Part("price") RequestBody price,
            @Part("starting_at") RequestBody starting_at,
            @Part("name") RequestBody name,
            @Part("photo") MultipartBody.Part photo,
            @Part("ending_at") RequestBody ending_at,
            @Part("api_token") RequestBody api_token,
            @Part("offer_price") RequestBody offer_price

    );

    @Multipart
    @POST("restaurant/update-offer")
    Call<RestaurantOfferResponce> editRestaurantOffers(

            @Part("description") RequestBody description,
            @Part("price") RequestBody price,
            @Part("starting_at") RequestBody starting_at,
            @Part("name") RequestBody name,
            @Part("photo") MultipartBody.Part photo,
            @Part("ending_at") RequestBody ending_at,
            @Part("offer_price") RequestBody offer_price,
            @Part("api_token") RequestBody api_token

    );

    @POST("client/profile")
    @Multipart
    Call<ClientGeneralResponse> editRestaurantProfile(
            @Part("email") RequestBody email,
            @Part("name") RequestBody name,
            @Part("phone") RequestBody phone,
            @Part("region_id") RequestBody region_id,
            @Part("delivery_cost") RequestBody deliveryCost,
            @Part("minimum_charger") RequestBody minimumCharger,
            @Part("availability") RequestBody availability,
            @Part("photo") MultipartBody.Part photo,
            @Part("api_token") RequestBody apiToken,
            @Part("delivery_time") RequestBody deliveryTime
    );

    @POST("restaurant/delete-offer")
    @FormUrlEncoded
    Call<RestaurantOfferResponce> deleteOffer(
            @Field("offer_id") String offer_id,
            @Field("api_token") String api_token
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
    Call<RestaurantCommissionResponse> restaurantGetCommisions(
            @Query("api_token") String apiToken

    );

    @GET("restaurant/my-offers")
    Call<RestaurantOfferResponce> getMyOrders(
            @Query("api_token") String apiToken,
            @Query("page") String page

    );

    @GET("cities")
    Call<GeneralRespose> getAllCities(

    );


    @GET("offers")
    Call<RestaurantOfferResponce> getListOfOffers(
            @Query("restaurant_id") int restaurant_id

    );

    @GET("offer")
    Call<RestaurantOfferResponce> getOfferDetails(
            @Query("offer_id") String offer_id

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

//  restaurant  food items

    @POST("restaurant/new-item")
    @Multipart
    Call<FoodItemsResponse> makeNewFoodItem(
            @Part("description") RequestBody description,
            @Part("price") RequestBody price,
            @Part("preparing_time") RequestBody preparingTime,
            @Part("name") RequestBody name,
            @Part("photo") MultipartBody.Part photo,
            @Part("api_token") RequestBody apiToken,
            @Part("offer_price") RequestBody offerPrice,
            @Part("category_id") RequestBody categoryId
              );

    @POST("restaurant/update-item")
    @Multipart
    Call<FoodItemsResponse> updateFoodItem(
            @Part("description") RequestBody description,
            @Part("price") RequestBody price,
            @Part("preparing_time") RequestBody preparingTime,
            @Part("name") RequestBody name,
            @Part("photo") MultipartBody.Part photo,
            @Part("item_id") RequestBody itemId,
            @Part("api_token") RequestBody apiToken,
            @Part("offer_price") RequestBody offerPrice
    );

    @POST("restaurant/delete-item")
    @FormUrlEncoded
    Call<FoodItemsResponse> deleteFoodItem(
            @Field("item_id") String item_id,
            @Field("api_token") String api_token
    );
    @GET("restaurant/my-items")
    Call<OrderResponse> getMyFooditems(
            @Query("api_token") String apiToken,
            @Query("category_id") String categoryId


    );

//    Order Apis


    @GET("restaurant/my-orders")
    Call<OrderResponse> getMyFoodOrders(
            @Query("api_token") String apiToken,
            @Query("state") String state,
            @Query("page") String page


    );


    @GET("restaurant/show-order")
    Call<OrderResponse> showMyFoodOrder(
            @Query("api_token") String apiToken,
            @Query("order_id") String orderId


    );
    @POST("restaurant/accept-order")
    @FormUrlEncoded
    Call<OrderResponse> acceptOrder(
            @Field("api_token") String apiToken,
            @Field("order_id") String orderId
    );

    @POST("restaurant/reject-order")
    @FormUrlEncoded
    Call<OrderResponse> rejectOrder(
            @Field("api_token") String apiToken,
            @Field("order_id") String orderId,
            @Field("refuse_reason") String refuseReason
    );

    @POST("restaurant/confirm-order")
    @FormUrlEncoded
    Call<OrderResponse> confirmOrder(
            @Field("order_id") String orderId,
            @Field("api_token") String apiToken
    );

    @GET("restaurant/commissions")
    Call<RestaurantCommissionResponse> commisionsOrder(
            @Query("api_token") String apiToken
    );

//    Category Apis

    @POST("restaurant/new-category")
    @Multipart
    Call<RestaurantCategoryResponse> restaurantNewCategory(
            @Part("name") RequestBody name,
            @Part("photo") MultipartBody.Part photo,
            @Part("api_token") RequestBody apiToken
    );

    @GET("restaurant/my-categories")
    Call<RestaurantCategoryResponse> getRestaurantCategories(
            @Query("api_token") String apiToken
    );

    @POST("restaurant/update-category")
    @Multipart
    Call<RestaurantCategoryResponse> restaurantUpdateCategory(
            @Part("name") RequestBody name,
            @Part("photo") MultipartBody.Part photo,
            @Part("api_token") RequestBody apiToken,
            @Part("category_id") RequestBody categoryId

    );

    @POST("restaurant/delete-category")
    @FormUrlEncoded
    Call<RestaurantCategoryResponse> restaurantDeleteCategory(
            @Field("api_token") String apiToken,
            @Field("category_id") Integer categoryId

    );

//    Genrals

    @GET("restaurants")
    Call<RestaurantsListResponce> getRestaurantsWithoutFiltter(
            @Query("page") int page
    );

    @GET("restaurants")
    Call<RestaurantsListResponce> getRestaurantsWithFiltter(
            @Query("keyword") String keyword,
            @Query("region_id") String regionId
            );

    @GET("restaurant")
    Call<RestaurantsListResponce> getRestaurantDetails(
            @Query("restaurant_id") String restaurantId
    );

    @GET("items")
    Call<RestaurantSubCategoriesItemsListResponce> getRestaurantSubCategoriesItemsList(
            @Query("restaurant_id") String restaurantId,
            @Query("category_id") String categoryId
    );

    @GET("categories")
    Call<RestaurantCategoriesListResponse> getRestaurantCategoriesList(
            @Query("restaurant_id") String restaurantId,
            @Query("category_id") String categoryId
    );
}


package com.example.sofra.data.model.clientRestaurantReview;

import com.example.sofra.data.model.clientLogin.Client;
import com.example.sofra.data.model.clientMakeNewOrder.ClientMakeNewOrderRestaurant;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientRestaurantReviewReview {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("client_id")
    @Expose
    private Integer clientId;
    @SerializedName("client")
    @Expose
    private Client client;
    @SerializedName("restaurant")
    @Expose
    private ClientMakeNewOrderRestaurant restaurant;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ClientMakeNewOrderRestaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(ClientMakeNewOrderRestaurant restaurant) {
        this.restaurant = restaurant;
    }

}

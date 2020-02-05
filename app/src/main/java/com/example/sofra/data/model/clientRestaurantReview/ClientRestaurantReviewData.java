
package com.example.sofra.data.model.clientRestaurantReview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientRestaurantReviewData {

    @SerializedName("review")
    @Expose
    private ClientRestaurantReviewReview review;

    public ClientRestaurantReviewReview getReview() {
        return review;
    }

    public void setReview(ClientRestaurantReviewReview review) {
        this.review = review;
    }

}

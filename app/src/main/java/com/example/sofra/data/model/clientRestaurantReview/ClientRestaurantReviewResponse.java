
package com.example.sofra.data.model.clientRestaurantReview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientRestaurantReviewResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private ClientRestaurantReviewData data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ClientRestaurantReviewData getData() {
        return data;
    }

    public void setData(ClientRestaurantReviewData data) {
        this.data = data;
    }

}

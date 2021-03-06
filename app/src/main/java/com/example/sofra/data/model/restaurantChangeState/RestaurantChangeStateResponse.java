
package com.example.sofra.data.model.restaurantChangeState;

import com.example.sofra.data.model.clientLogin.Client;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantChangeStateResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private Client data;

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

    public Client getData() {
        return data;
    }

    public void setData(Client data) {
        this.data = data;
    }

}

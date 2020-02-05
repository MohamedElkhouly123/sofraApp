
package com.example.sofra.data.model.clientMyOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientMyOrderResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private ClientMakeNewOrderPagination data;

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

    public ClientMakeNewOrderPagination getData() {
        return data;
    }

    public void setData(ClientMakeNewOrderPagination data) {
        this.data = data;
    }

}

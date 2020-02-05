
package com.example.sofra.data.model.clientGetAllNotofications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientGetAllNotoficationsResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("send")
    @Expose
    private Object send;
    @SerializedName("data")
    @Expose
    private ClientNotoficationPagination data;

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

    public ClientNotoficationPagination getData() {
        return data;
    }

    public void setData(ClientNotoficationPagination data) {
        this.data = data;
    }

    public Object getSend() {
        return send;
    }

    public void setSend(Object send) {
        this.send = send;}

}

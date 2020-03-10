
package com.example.sofra.data.model.restaurantCategoryResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantCategoriesListResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<RestaurantCategoryFiltterData> data = null;

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

    public List<RestaurantCategoryFiltterData> getData() {
        return data;
    }

    public void setData(List<RestaurantCategoryFiltterData> data) {
        this.data = data;
    }

}

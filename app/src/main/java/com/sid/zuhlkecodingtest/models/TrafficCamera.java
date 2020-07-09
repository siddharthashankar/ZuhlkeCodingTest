package com.sid.zuhlkecodingtest.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TrafficCamera {

    @SerializedName("items")
    @Expose
    private List<Items> items = null;
    @SerializedName("api_info")
    @Expose
    private ApiInfo apiInfo;

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public ApiInfo getApiInfo() {
        return apiInfo;
    }

    public void setApiInfo(ApiInfo apiInfo) {
        this.apiInfo = apiInfo;
    }

}

 class ApiInfo {

    @SerializedName("status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

 class ImageMetadata {

    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("md5")
    @Expose
    private String md5;

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

}




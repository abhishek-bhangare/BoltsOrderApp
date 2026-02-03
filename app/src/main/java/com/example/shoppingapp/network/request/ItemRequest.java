package com.example.shoppingapp.network.request;

import com.google.gson.annotations.SerializedName;

public class ItemRequest {

    @SerializedName("com_id")
    private String comId;

    @SerializedName("main_cateid")
    private String mainCateId;

    @SerializedName("sub_cateid")
    private String subCateId;

    public ItemRequest(String comId, String mainCateId, String subCateId) {
        this.comId = comId;
        this.mainCateId = mainCateId;
        this.subCateId = subCateId;
    }
}

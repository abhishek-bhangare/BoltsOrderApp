package com.example.shoppingapp.network.response;

import com.google.gson.annotations.SerializedName;

public class ItemModel {

    @SerializedName("item_id")
    private String itemId;

    @SerializedName("part_no")
    private String partNo;

    @SerializedName("part_name")
    private String partName;

    @SerializedName("v_companyid")
    private String vCompanyId;

    @SerializedName("maincategory_name")
    private String mainCategoryName;

    @SerializedName("v_modelid")
    private String vModelId;

    @SerializedName("subcategory_name")
    private String subCategoryName;

    @SerializedName("mrp")
    private String mrp;

    @SerializedName("sale_rate")
    private String saleRate;

    @SerializedName("gst_id")
    private String gstId;

    @SerializedName("gst_per")
    private String gstPer;

    @SerializedName("unit_id")
    private String unitId;

    @SerializedName("unit_nm")
    private String unitName;

    @SerializedName("img_path")
    private String imgPath;

    @SerializedName("avl_stock")
    private String avlStock;

    // ===== GETTERS =====

    public String getItemId() {
        return itemId;
    }

    public String getPartNo() {
        return partNo;
    }

    public String getPartName() {
        return partName;
    }

    public String getvCompanyId() {
        return vCompanyId;
    }

    public String getMainCategoryName() {
        return mainCategoryName;
    }

    public String getvModelId() {
        return vModelId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public String getMrp() {
        return mrp;
    }

    public String getSaleRate() {
        return saleRate;
    }

    public String getGstId() {
        return gstId;
    }

    public String getGstPer() {
        return gstPer;
    }

    public String getUnitId() {
        return unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public String getAvlStock() {
        return avlStock;
    }
}

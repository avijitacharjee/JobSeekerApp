package com.avijit.jobseeker.retroclient.com.avijit.jobseeker.retroclient.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Avijit on 11/29/2019.
 */
/*

id
category_name
image
row_order
*/

public class Category {

    @SerializedName("id")
    private String id;

    @SerializedName("category_name")
    private String category_name;

    @SerializedName("image")
    private String image;

    @SerializedName("row_order")
    private String row_order;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRow_order() {
        return row_order;
    }

    public void setRow_order(String row_order) {
        this.row_order = row_order;
    }
}

package com.avijit.jobseeker.retroclient.com.avijit.jobseeker.retroclient;

import com.avijit.jobseeker.retroclient.com.avijit.jobseeker.retroclient.models.Category;
import com.avijit.jobseeker.retroclient.com.avijit.jobseeker.retroclient.models.User;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Avijit on 11/29/2019.
 */

public interface ApiInterface {

    @Headers({
            "Content-Type: application/json",
            "Content-Type: application/x-www-form-urlencoded"
    })
    @POST("/")
    Call<List<Object>> getCategories(@Body JSONObject body);


}

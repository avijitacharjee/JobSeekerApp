package com.avijit.jobseeker.retroclient.com.avijit.jobseeker.retroclient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Avijit on 11/28/2019.
 */

public class ApiClient {
    public static final String BASE_URL="https://androstar.tk/jobseeker/api-v2.php/";
    //public static final String BASE_URL="";
    public static Retrofit retrofit=null;

    public static Retrofit getApiClient()
    {
        if(retrofit==null)
        {
            retrofit= new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}

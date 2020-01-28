package com.example.myapplication.Interfaces;

import com.example.myapplication.POJO.Hits;
import com.example.myapplication.POJO.MyPojo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Fetchlist_Interfaces {

    @GET("search_by_date")
    Call<ArrayList<Hits>>getsearch_by_date(@Query("tags")String tagss,
                                           @Query("page")String pages);
}

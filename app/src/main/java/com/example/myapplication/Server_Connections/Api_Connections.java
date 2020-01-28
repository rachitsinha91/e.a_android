package com.example.myapplication.Server_Connections;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api_Connections {

    public static String BASEURL=null;
    public static Retrofit retrofit=null;


    public static  Retrofit getClient(Context context){

        BASEURL="https://hn.algolia.com/api/v1/";
        if(retrofit==null){
            retrofit=new
                    Retrofit.Builder().baseUrl(BASEURL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }return retrofit;
    }
}

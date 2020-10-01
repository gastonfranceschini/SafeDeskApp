package com.ort.myapplication.utils;


public class ApiUtils {

    private ApiUtils() {}

    //public static final String BASE_URL = "http://jsonplaceholder.typicode.com/";
    public static final String BASE_URL =  "http://35.190.67.223"; // http://10.0.2.2:3000/";

    public static Object getAPI(Class IClass) {

        return RetrofitClient.getClient(BASE_URL).create(IClass);
    }
}
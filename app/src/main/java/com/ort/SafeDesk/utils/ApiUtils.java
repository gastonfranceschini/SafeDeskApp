package com.ort.SafeDesk.utils;


public class ApiUtils {

    private ApiUtils() {}

    //"https://safedesk.apiexperta.com.ar";
    //"http://35.190.67.223";
    //"http://10.0.2.2:3000/api/reportes/test";

    public static final String BASE_URL =   "https://safedesk.apiexperta.com.ar";

    public static Object getAPI(Class IClass) {
        return RetrofitClient.getClient(BASE_URL).create(IClass);
    }

    public static Object getAPISinToken(Class IClass) {
        return RetrofitClient.getClientSinToken(BASE_URL).create(IClass);
    }
}
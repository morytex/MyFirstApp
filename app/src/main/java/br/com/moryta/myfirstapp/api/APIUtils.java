package br.com.moryta.myfirstapp.api;

import br.com.moryta.myfirstapp.api.retrofit.RetrofitClient;

/**
 * Created by moryta on 02/07/2017.
 */

public class APIUtils {
    private final static String MOCKY_API_BASE_URL = "http://www.mocky.io/v2/";

    public static MockyAPI getMockyAPI() {
        return RetrofitClient.getClient(MOCKY_API_BASE_URL)
                .create(MockyAPI.class);
    }
}

package br.com.moryta.myfirstapp.api;

import br.com.moryta.myfirstapp.model.LoginDTO;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by moryta on 02/07/2017.
 */

public interface MockyAPI {
    @GET("58b9b1740f0000b614f09d2f")
    Observable<LoginDTO> getDefaultLogin();
}

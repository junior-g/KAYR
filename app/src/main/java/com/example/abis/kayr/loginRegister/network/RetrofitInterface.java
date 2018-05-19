package com.example.abis.kayr.loginRegister.network;

/**
 * Created by abis.
 */

import com.example.abis.kayr.loginRegister.model.Response;
import com.example.abis.kayr.loginRegister.model.User;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

public interface RetrofitInterface {

    //register
    @POST("users")
    Observable<Response> register(@Body User user);

    //login
    @POST("authenticate")
    Observable<Response> login();
    //fetch user details
    @GET("users/{email}")
    Observable<User> getProfile(@Path("email") String email);
    //password change when old password known
    @PUT("users/{email}")
    Observable<Response> changePassword(@Path("email") String email, @Body User user);
    //reset password with mail
    @POST("users/{email}/password")
    Observable<Response> resetPasswordInit(@Path("email") String email);
    //veryfing token
    @POST("users/{email}/password")
    Observable<Response> resetPasswordFinish(@Path("email") String email, @Body User user);
}

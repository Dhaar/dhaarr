package com.example.boopathyraj.lims;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Boopathyraj on 2/3/2017.
 */
public interface ApiService {

    @FormUrlEncoded
    @POST("/signup.php")
    Call<ResponseBody> onSignUp(@Field("name") String name,@Field("rollno") String rollno,@Field("email") String email,@Field("password") String password,@Field("confirm") String confirm);

    @FormUrlEncoded
    @POST("/signin.php")
    Call<ResponseBody> onSignIn(@Field("email") String email,@Field("password") String password);


    @FormUrlEncoded
    @POST("/search.php")
    Call<ResponseBody> onSearch1(@Field("name") String name,@Field("category") String category);


    @FormUrlEncoded
    @POST("/password.php")
    Call<ResponseBody> onPass(@Field("email") String email,@Field("current") String current,@Field("newpass") String newpass);


    @FormUrlEncoded
    @POST("/renew.php")
    Call<List<BookResponse>> getBooks(@Field("rollno") String rollno);

    @FormUrlEncoded
    @POST("/renewal.php")
    Call<ResponseBody> onsend(@Field("rolno") String rolno,@Field("book_id") String book_id);

    @FormUrlEncoded
    @POST("/forrenew.php")
    Call<ResponseBody> fordate(@Field("rollno") String rollno,@Field("bid") String bid);

    // first paste
    @FormUrlEncoded
    @POST("/fcm/sendMultiplePush.php")
    Call<ResponseBody> sendChat(@Field("token") String token,
                                @Field("title") String title,
                                @Field("message") String message);

}

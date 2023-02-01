package com.rathalove.kunaphepshop.API


import com.rathalove.kunaphepshop.DataModel.*
import com.rathalove.kunaphepshop.DataModel.PojoAllProduct.AllProduct
import com.rathalove.kunaphepshop.DataPojo_AllCategory.AllCategoryData
import retrofit2.Call
import retrofit2.http.*

interface APIUserInterface {

    //login
    @FormUrlEncoded
    @POST("/user/login")
    fun doLogIn(
        @Field("user_username") username: String?,
        @Field("user_password") password: String?
    ): Call<LoginResponse?>?

    //user log in
    @POST("/user/login")
    fun signIn(@Body info: SignInBody): retrofit2.Call<LoginResponse>

    //get user information by user token
    @GET("/user/me/{username}")
    fun getUser(
        @Path("username") username: String,
        @Header("Authorization") authToken: String
    ): Call<UserDataResponse>

    //user sign up
    @POST("/user/signup")
    fun signUp(@Body info: SignUpBody): Call<SignUpResponse>

    //get all category of the product
    @GET("/category/allcategory")
    fun getCategoryData(): Call<ArrayList<AllCategoryData>>
    //get all product
    @GET("/product/getAllProduct")
    fun getAllProduct(): Call<ArrayList<AllProduct>>
    //view product



}
package com.ratha.kunapheapmobile.DataModel

data class UserDataResponse(
    val user_id :String,
    val user_firstname: String,
    val user_lastname: String,
    val user_username: String,
    val user_password: String,
    val user_gender: String,
    val user_phone_number: String,
    val user_image_link: String,
    val user_email: String,
    val user_created_date :String,
    val role_id: String
)
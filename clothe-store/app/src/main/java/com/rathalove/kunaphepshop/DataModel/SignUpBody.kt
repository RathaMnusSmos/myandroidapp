package com.ratha.kunapheapmobile.DataModel

data class SignUpBody(
    val user_firstname: String,
    val user_lastname: String,
    val user_username: String,
    val user_password: String,
    val user_gender: String,
    val user_phone_number: String,
    val user_email: String,
)

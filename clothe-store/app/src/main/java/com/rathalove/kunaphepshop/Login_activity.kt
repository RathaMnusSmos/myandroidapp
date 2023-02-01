package com.rathalove.kunaphepshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.rathalove.kunaphepshop.API.APIUserInterface
import com.rathalove.kunaphepshop.API.RetrofitClientInstance
import com.rathalove.kunaphepshop.DataModel.LoginResponse
import com.rathalove.kunaphepshop.DataModel.SignInBody
import com.rathalove.kunaphepshop.DataModel.UserDataResponse
import com.rathalove.kunaphepshop.RoomDataBase.UserLogIn.UserData
import com.rathalove.kunaphepshop.RoomDataBase.UserLogIn.UserDatabase
import com.rathalove.kunaphepshop.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


import java.util.regex.Pattern

class Login_activity : AppCompatActivity() {
    private val userDatabase: UserDatabase by lazy { UserDatabase.getDatabase(this) }
    lateinit var USERNAME: String
    private lateinit var USER_TOKEN : String
    private var token : String = "Bearer"
    private lateinit var binding : ActivityLoginBinding
    private var PASSWORD_PARTTERN: Pattern = Pattern.compile(
        "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //  "(?=.*[a-z])" +         //at least 1 lower case letter
                //  "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                //  "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{6,}" +               //at least 6 characters
                "$"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.loginActivity = this

        binding.register.setOnClickListener {
            var intent = Intent(this, Sigup_Activity::class.java)
            startActivity(intent)
            overridePendingTransition(com.google.android.material.R.anim.abc_fade_in, com.google.android.material.R.anim.abc_fade_out)
        }
        binding.btnLogIn.setOnClickListener {
            validatePass()
            validateUsername()

            if (validatePass().equals(false) || validateUsername().equals(false)){
                Toast.makeText(baseContext, "Log In fail", Toast.LENGTH_LONG).show()
            }
            else{
                val username = binding.usernameEdt.text.toString()
                val password = binding.passwordEdt.text.toString()
                sigIn(username,password)
            }

        }

    }

    private fun getUserInfo(username: String, token: String){
        val retrofit = RetrofitClientInstance.getRetrofitInstance().create(APIUserInterface::class.java)
        retrofit.getUser(USERNAME,token).enqueue(object : Callback<UserDataResponse>{
            override fun onResponse(call: Call<UserDataResponse>, response: Response<UserDataResponse>) {
                if(response.isSuccessful){
                    Toast.makeText(this@Login_activity, "Login success!", Toast.LENGTH_SHORT).show()
                    Log.d("api1", "This is user information = ${response.body()}")

                    var username = response.body()!!.user_username
                    var password = response.body()!!.user_password
                    var userData = UserData(username, password,response.body()!!.user_phone_number, response.body()!!.user_image_link)
                    //set username and password to database
                    GlobalScope.launch(Dispatchers.IO){
                        val id = userDatabase.userDao().insertNewUser(userData)
                        val usersIn = userDatabase.userDao().getAllUser().toString()
                        launch (Dispatchers.Main){
                            Log.d("RoomActivity", "id: = $id")
                            Log.d("RoomActivity", "User object = $usersIn")
                        }
                    }


                }
                else{
                    Toast.makeText(this@Login_activity, "Login failed!", Toast.LENGTH_SHORT).show()
                    Log.d("apiErr", "token = ${token}")

                }
            }

            override fun onFailure(call: Call<UserDataResponse>, t: Throwable) {
                Toast.makeText(this@Login_activity, t.message, Toast.LENGTH_SHORT).show()
                Log.d("api1", " error ${t.message}")
            }

        })
    }


    private fun sigIn(username: String, password: String){
        val retIn = RetrofitClientInstance.getRetrofitInstance().create(APIUserInterface::class.java)
        val signInInfo = SignInBody(username, password)
        retIn.signIn(signInInfo).enqueue(object : Callback<LoginResponse>{
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.code() == 200){
                    Toast.makeText(this@Login_activity, "Login success!", Toast.LENGTH_SHORT).show()
                    Log.d("api", "This = ${response.body().toString()}")
                    USERNAME = response.body()!!.username
                    USER_TOKEN = response.body()!!.token
                    token = "Bearer $USER_TOKEN"
                    Log.d("api", "username = ${USERNAME} token = ${USER_TOKEN}")
                    Log.d("api", "token = ${token}")
                    getUserInfo(USERNAME,token)
                    var intent = Intent(this@Login_activity, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                }
                else {
                    Toast.makeText(this@Login_activity, "Login failed!", Toast.LENGTH_SHORT).show()
                    Log.d("api", "error hx")
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@Login_activity, t.message, Toast.LENGTH_SHORT).show()
                Log.d("api", " error ${t.message}")

            }

        })
    }

    private fun validateUsername(): Boolean {
        var username = binding.usernameEdt.text.toString()

        if (username.length < 4) {
            binding.usernameInp.error = "Invalid Username"
            return false
        } else if (username.isEmpty()) {
            binding.usernameInp.error = "Field can not empty"
            return false
        } else {
            binding.usernameInp.error = null
            binding.usernameInp.isErrorEnabled = false
            binding.usernameInp.isHelperTextEnabled = false
            return true
        }
    }
    private fun validatePass(): Boolean {
        val passTxt = binding.passwordEdt.text.toString()
        if (passTxt.isEmpty()) {
            binding.passwordInp.error = "Field can not be empty"
            return false
        } else if (!PASSWORD_PARTTERN.matcher(passTxt).matches()) {
            binding.passwordInp.error = "Invalid Password"
            return false
        } else {
            binding.passwordInp.error = null
            binding.passwordInp.isErrorEnabled = false
            binding.passwordInp.isHelperTextEnabled = false
            return true
        }
    }
}


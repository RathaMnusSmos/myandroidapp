package com.rathalove.kunaphepshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rathalove.kunaphepshop.API.APIUserInterface
import com.rathalove.kunaphepshop.API.RetrofitClientInstance
import com.rathalove.kunaphepshop.DataModel.LoginResponse
import com.rathalove.kunaphepshop.DataModel.SignInBody
import com.rathalove.kunaphepshop.RoomDataBase.UserLogIn.UserDatabase
import com.rathalove.kunaphepshop.databinding.ActivityMainBinding
import com.rathalove.kunaphepshop.fragment.HomeFragmmenrt
import com.rathalove.kunaphepshop.fragment.ItemFragment
import com.rathalove.kunaphepshop.fragment.UserAccountFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    lateinit var USERNAME: String
    private lateinit var USER_TOKEN : String
    private val userDatabase: UserDatabase by lazy { UserDatabase.getDatabase(this) }
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainActivity = this
        var bottomNav : BottomNavigationView = binding.bottomNav
        replaceFragment(HomeFragmmenrt())
        bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.account ->{
                    GlobalScope.launch(Dispatchers.IO){
                        var userdata = userDatabase.userDao().getAllUser()
                        launch(Dispatchers.Main){
                            if (userdata.isEmpty()){
                                var intent = Intent(this@MainActivity, Login_activity::class.java)
                                startActivity(intent)
                                overridePendingTransition(androidx.appcompat.R.anim.abc_fade_in, androidx.appcompat.R.anim.abc_fade_out)
                            }
                            else{
                                var username = userdata[0].username
                                var password = userdata[0].password
                                sigIn(username, password)
                            }
                        }
                    }

                }
                R.id.home_menu ->{
                    replaceFragment(HomeFragmmenrt())
                }
                R.id.shoping ->{
                    replaceFragment(ItemFragment())
                }
            }
            true

        }
    }


    private fun sigIn(username: String, password: String){
        val retIn = RetrofitClientInstance.getRetrofitInstance().create(APIUserInterface::class.java)
        val signInInfo = SignInBody(username, password)
        retIn.signIn(signInInfo).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.code() == 200){

                    Log.d("api", "This = ${response.body().toString()}")
                    USERNAME = response.body()!!.username
                    USER_TOKEN = response.body()!!.token
                    Log.d("api", "username = ${USERNAME} token = ${USER_TOKEN}")

                    replaceFragment(UserAccountFragment())

                }
                else {
                    Toast.makeText(this@MainActivity, "Login failed!", Toast.LENGTH_SHORT).show()
                    Log.d("api", "error hx")
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                Log.d("api", " error ${t.message}")

            }

        })
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManger = supportFragmentManager
        val fragmentTransaction = fragmentManger.beginTransaction()
        fragmentTransaction.replace((R.id.frameLayout),fragment)
        fragmentTransaction.commit()
    }


}
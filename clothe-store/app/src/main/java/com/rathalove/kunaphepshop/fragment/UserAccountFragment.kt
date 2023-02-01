package com.ratha.kunapheapmobile.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.ratha.kunapheapmobile.API.APIUserInterface
import com.ratha.kunapheapmobile.API.RetrofitClientInstance
import com.ratha.kunapheapmobile.DataModel.LoginResponse
import com.ratha.kunapheapmobile.DataModel.SignInBody
import com.ratha.kunapheapmobile.DataModel.UserDataResponse
import com.ratha.kunapheapmobile.Login_activity
import com.ratha.kunapheapmobile.MainActivity
import com.ratha.kunapheapmobile.R
import com.ratha.kunapheapmobile.RoomDataBase.UserLogIn.UserData
import com.ratha.kunapheapmobile.RoomDataBase.UserLogIn.UserDatabase
import com.ratha.kunapheapmobile.databinding.FragmentUserAccountBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserAccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserAccountFragment : Fragment(R.layout.fragment_user_account) {

    lateinit var USERNAME: String
    private lateinit var USER_TOKEN : String
    private var token : String = "Bearer"
    private lateinit var roomUsername: String
    private lateinit var roomUserPass: String
    private lateinit var fragmentBinding: FragmentUserAccountBinding
    private val userDatabase: UserDatabase by lazy { UserDatabase.getDatabase(requireContext()) }

    private var phoneNum: String = ""
    private lateinit var imgLink : String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentUserAccountBinding.bind(view)
        fragmentBinding = binding
        var username : String
        var phone : String
        var imgUrl: String

        GlobalScope.launch (Dispatchers.IO){

            var user = userDatabase.userDao().getAllUser()
            launch (Dispatchers.Main) {
                username = user[0].username
                phone = user[0].phone
                imgUrl = user[0].imageUrl
                phoneNum = phone
                Log.d("test", "1: $phoneNum")
                binding.username.text = username
                binding.phoneNumber.text = phone
                Glide.with(this@UserAccountFragment).load(imgUrl).into(binding.profile)

            }

            Log.d("test", "2: $phoneNum")
        }

        Log.d("test", "3: $phoneNum")
        binding.navView.setNavigationItemSelectedListener{
            when(it.itemId){
                R.id.logout ->{
                    GlobalScope.launch (Dispatchers.IO){
                        var user = userDatabase.userDao().getAllUser()
                        var dele = userDatabase.userDao().deleteUser(user[0])
                        launch (Dispatchers.Main){
                            var intent = Intent(view.getContext(), MainActivity::class.java);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent)
                            activity?.finish()

                        }
                    }
                }
            }
            true
        }

    }

    override fun onDestroyView() {
        // Consider not storing the binding instance in a field
        // if not needed.
        super.onDestroyView()
    }









}
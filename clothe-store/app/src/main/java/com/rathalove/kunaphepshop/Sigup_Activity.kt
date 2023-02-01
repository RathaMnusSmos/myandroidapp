package com.ratha.kunapheapmobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.ratha.kunapheapmobile.API.APIUserInterface
import com.ratha.kunapheapmobile.API.RetrofitClientInstance
import com.ratha.kunapheapmobile.DataModel.SignUpBody
import com.ratha.kunapheapmobile.DataModel.SignUpResponse
import com.ratha.kunapheapmobile.databinding.ActivitySigupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class Sigup_Activity : AppCompatActivity() {
    private lateinit var binding: ActivitySigupBinding
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
    private lateinit var gender :String
    private var check: String = "\\A\\w{4,30}\\z"       //no white spaces
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_sigup)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sigup)
        binding.signUpActivity = this

        binding.btnSigup.setOnClickListener {
            if(binding.maleRbtn.isChecked)
            {
                gender = "male"
                Log.d("ratha", "${gender} is Select.")
            }
            else if ( binding.feMaleRbtn.isChecked){
                gender = "female"
                Log.d("ratha", "${gender} is select.")
            }
            validatePass()
            validateConfirm()
            validateLastname()
            validateFirstname()
            validateEml()
            validatePhone()
            validateUsername()
            var firstname:String = binding.firstnameEdt.text.toString()
            var lastname :String = binding.lasstnameEdt.text.toString()
            var username :String = binding.usernameEdt.text.toString()
            var email :String = binding.emailEdt.text.toString()
            var phone : String = binding.phoneEdt.text.toString()
            var password : String =  binding.passwordEdt.text.toString()

            val retIn = RetrofitClientInstance.getRetrofitInstance().create(APIUserInterface::class.java)
            var signUpBody = SignUpBody(firstname,lastname,username,password,gender,phone,email)
            retIn.signUp(signUpBody).enqueue(object : Callback<SignUpResponse>{
                override fun onResponse(
                    call: Call<SignUpResponse>,
                    response: Response<SignUpResponse>
                ) {
                    if(response.isSuccessful){
                        Toast.makeText(this@Sigup_Activity,"Success", Toast.LENGTH_SHORT).show()
                        Log.d("signUP", "username = ${response.body()!!.username}  token = ${response.body()!!.token}")
                    }
                    else{
                        Toast.makeText(this@Sigup_Activity, "Error", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                    Toast.makeText(this@Sigup_Activity, "Sign Up Fail", Toast.LENGTH_SHORT).show()
                    Log.d("signUP", t.message.toString())
                }

            })

        }

    }





    fun OnclickItem(view: View) {
        when(view.id){
            R.id.maleRbtn ->{
                Log.d("ratha", "Male is Select.")
                gender = "male"
            }
            R.id.feMaleRbtn ->{
                Log.d("ratha", "Female is select.")
                gender = "female"
            }
        }
    }
    private fun validateFirstname(): Boolean {
        var firsTxt = binding.firstnameEdt.text.toString()
        if (firsTxt.isEmpty()) {
            binding.firstnameInp.error = "Field can not empty"
            return false
        } else if (!firsTxt.matches(check.toRegex())) {
            binding.firstnameInp.error = "Field can not with space"
            return false
        } else {
            binding.firstnameInp.error = null
            binding.firstnameInp.isErrorEnabled = false
            binding.firstnameInp.isHelperTextEnabled = false
            return true
        }
    }
    private fun validateLastname(): Boolean {
        var lastTxt = binding.lasstnameEdt.text.toString()
        if (lastTxt.isEmpty()) {
            binding.lastnameInp.error = "Field can not empty"
            return false
        } else if (!lastTxt.matches(check.toRegex())) {
            binding.lastnameInp.error = "Field can not with space1"
            return false
        } else {
            binding.lastnameInp.error = null
            binding.lastnameInp.isErrorEnabled = false
            binding.lastnameInp.isHelperTextEnabled = false
            return true
        }
    }

    private fun validateUsername(): Boolean {
        var username = binding.usernameEdt.text.toString()

        if (username.length < 4) {
            binding.usernameInp.error = "Invalid Username"
            return false
        } else if (username.isEmpty()) {
            binding.usernameInp.error = "Field can not empty"
            return false
        }else if(!username.matches(check.toRegex())){
            binding.usernameInp.error = "Field can not with space"
            return false
        }
        else {
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
    private fun validateConfirm(): Boolean {
        val passTxt = binding.passwordEdt.text.toString()
        val conTxt = binding.ConfirmpasswordEdt.text.toString()
        if (conTxt.isEmpty()) {
            binding.ConfirmpasswordInp.error = "Field can not empty"
            return false
        } else if (!conTxt.equals(passTxt)) {
            binding.ConfirmpasswordInp.error = "Password not match"
            return false
        } else {
            binding.ConfirmpasswordInp.error = null
            binding.ConfirmpasswordInp.isErrorEnabled = false
            binding.ConfirmpasswordInp.isHelperTextEnabled = false
            return true
        }

    }
    private fun validatePhone(): Boolean {
        var phNum = binding.phoneEdt.text.toString()
        if (phNum.length < 10) {
            binding.phoneInp.error = "Invalid Phone Number"
            return false
        } else if (phNum.isEmpty()) {
            binding.phoneInp.error = "Field can not empty"
            return false
        } else {
            binding.phoneInp.error = null
            binding.phoneInp.isErrorEnabled = false
            binding.phoneInp.isHelperTextEnabled = false
            return true
        }
    }

    private fun validateEml(): Boolean{
        var emlTxt = binding.emailEdt.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(emlTxt).matches()){
            binding.emailInp.error = "Please enter valid email"
            return false
        }
        if(emlTxt.isEmpty()){
            binding.emailInp.error = "Field can not empty"
            return false
            return true
        }
        else{
            binding.emailInp.error = null
            binding.emailInp.isErrorEnabled = false;
            binding.emailInp.isHelperTextEnabled = false
            return true
        }
    }


}
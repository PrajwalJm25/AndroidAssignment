package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener, View.OnFocusChangeListener, View.OnKeyListener {

    private lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))

        mBinding.loginBtn.setOnClickListener(this)
        setContentView(mBinding.root)
        mBinding.UsernameET.onFocusChangeListener = this
        mBinding.PasswordET.onFocusChangeListener = this
    }

    private fun validateUserName(): Boolean{
        var errorMessage: String? = null
        val value: String = mBinding.UsernameET.text.toString()
        if(value.isEmpty()){
            errorMessage = "Username is Required"
        }
        if(errorMessage != null){
            mBinding.UsernameTIL.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    private fun validatePassword(): Boolean{
        var errorMessage: String? = null
        val value = mBinding.PasswordET.text.toString()
        if(value.isEmpty()){
            errorMessage = "Password is required"
        }
        if(errorMessage != null){
            mBinding.PasswordTIL.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return errorMessage == null
    }

    override fun onClick(v: View?) {
        if(v!=null){
            when(v.id){
                R.id.loginBtn -> {
                    submitForm()
                }
            }
        }

    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if(v!=null){
            when(v.id){
                R.id.UsernameET -> {
                    if(hasFocus){
                        if(mBinding.UsernameTIL.isErrorEnabled){
                            mBinding.UsernameTIL.isErrorEnabled = false
                        }
                    } else {
                        validateUserName()
                    }
                }
                R.id.PasswordET -> {
                    if(hasFocus){
                        if(mBinding.PasswordTIL.isErrorEnabled){
                            mBinding.PasswordTIL.isErrorEnabled = false
                        }
                    }
                    else {
                        validatePassword()
                    }
                }
            }
        }
    }

    private fun load(){
        val intent = Intent(this, LoadingActivity::class.java)
        startActivity(intent)
    }

    val timer = object : CountDownTimer(3000,5000){
        override fun onTick(millisUntilFinished: Long) {
            load()
        }

        override fun onFinish() {
            result()
        }
    }
    private fun submitForm(){
        if(validateUserName() && validatePassword()){
            timer.start()
        }
    }
    private fun result(){
        if(mBinding.UsernameET.text.toString() == "bob" && mBinding.PasswordET.text.toString() == "1234"){
            val success = Intent(this, SuccessActivity::class.java)
            startActivity(success)
        }
        else{
            val wrong = Intent(this, FailureActivity::class.java)
            startActivity(wrong)
        }
    }
    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        return false
    }
}
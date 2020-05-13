package com.example.lecture19homework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lecture19homework.Request.LOGIN
import kotlinx.android.synthetic.main.activity_log_in.*

class ActivityLogIn : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        init()

    }

    private fun init() {
        logInButton.setOnClickListener {
            checkEmpty()
        }
        signUpTextView.setOnClickListener {
            val intent = Intent(this, ActivityRegister::class.java)
            startActivity(intent)
        }

    }

    private fun logIn() {

        val parameters = mutableMapOf<String, String>()
        parameters["email"] = emailField.text.toString()
        parameters["password"] = passwordField.text.toString()
        Request.postRequest(LOGIN, parameters, object : CustomCallback {

            override fun onSuccess(response: String, message:String) {
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
                        .show()
                messageTextView.text = message
            }

            override fun onError(response: String, message:String) {
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
                        .show()
                messageTextView.text = message
            }

            override fun onFailure(response: String) {
                Toast.makeText(applicationContext, "No Internet Connection", Toast.LENGTH_SHORT)
                        .show()

            }


        })

    }

    private fun checkEmpty() {
        if (emailField.text.isNotEmpty() && passwordField.text.isNotEmpty()) {
            logIn()
        } else {
            Toast.makeText(applicationContext, "All Fields must be filled!", Toast.LENGTH_SHORT).show()
        }
    }
}
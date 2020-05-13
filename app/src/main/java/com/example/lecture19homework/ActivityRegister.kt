package com.example.lecture19homework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lecture19homework.Request.REGISTRATION
import kotlinx.android.synthetic.main.activity_register.*

class ActivityRegister : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()
    }

    private fun init() {
        signUpButton.setOnClickListener {
            checkEmpty()
        }
        logInTextView.setOnClickListener {
            val intent = Intent(this, ActivityLogIn::class.java)
            startActivity(intent)
        }
    }

    private fun signUp() {

        val parameters = mutableMapOf<String, String>()
        parameters["email"] = emailField.text.toString()
        parameters["password"] = passwordField2.text.toString()
        Request.postRequest(REGISTRATION, parameters, object : CustomCallback {

            override fun onSuccess(response: String, message: String) {
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
                    .show()
                messageTextView2.text = message
            }

            override fun onError(response: String, message: String) {
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
                    .show()
                messageTextView2.text = message
            }

            override fun onFailure(response: String) {
                Toast.makeText(applicationContext, "No Internet Connection", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun checkEmpty() {
        if (emailField.text.isNotEmpty() && passwordField2.text.isNotEmpty() && repeatPasswordField.text.isNotEmpty()) {
            checkPasswords()
        } else {
            Toast.makeText(applicationContext, "All fields must be filled!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkPasswords() {

        if (passwordField2.text.toString() != repeatPasswordField.text.toString()) {
            Toast.makeText(
                applicationContext, "Passwords don't match", Toast.LENGTH_SHORT
            ).show()
        } else {
            signUp()
        }

    }
}

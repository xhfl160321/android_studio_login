package com.hsj.login

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity :AppCompatActivity() {

    lateinit var emailEt:EditText
    lateinit var passwordEt:EditText
    lateinit var emailLoginBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEt=findViewById(R.id.email_et)
        passwordEt=findViewById(R.id.password_et)
        emailLoginBtn=findViewById(R.id.email_login_btn)

        emailLoginBtn.setOnClickListener{ emailLogin() }

    }

    fun emailLogin(){
        Toast.makeText(this,"로그인",Toast.LENGTH_SHORT).show()
    }
}
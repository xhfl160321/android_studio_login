package com.hsj.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var emailTv:TextView
    lateinit var logoutBtn:Button
    lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailTv=findViewById(R.id.email_tv)
        logoutBtn=findViewById(R.id.logout_btn)
        auth=FirebaseAuth.getInstance()

        emailTv.text=auth.currentUser?.email  //인증 되어있는 사용자의 이메일을 가져옴

        logoutBtn.setOnClickListener{ logout() } //로그아웃 버튼 클릭 시 함수 호출
    }

    fun logout(){
        auth.signOut()
        var intent= Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}

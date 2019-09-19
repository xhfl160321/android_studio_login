package com.hsj.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlin.math.sign

class LoginActivity :AppCompatActivity() {

    lateinit var emailEt:EditText
    lateinit var passwordEt:EditText
    lateinit var emailLoginBtn: Button
    lateinit var googleLoginBtn:Button
    lateinit var auth:FirebaseAuth
    lateinit var googleSignInClient:GoogleSignInClient

    val GOOGLE_LOGIN=10000 //따로 타입 지정x

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEt=findViewById(R.id.email_et)
        passwordEt=findViewById(R.id.password_et)
        emailLoginBtn=findViewById(R.id.email_login_btn)
        googleLoginBtn=findViewById(R.id.google_login_btn)
        auth=FirebaseAuth.getInstance()
        moveMainPage(auth.currentUser)  //앱이 꺼져도 로그인 되어있는 상태일 경우 사용자의 정보를 담아둠

        var gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient=GoogleSignIn.getClient(this,gso) //구글 로그인 클릭 시 구글 아이디로 로그인

        emailLoginBtn.setOnClickListener{ emailLogin() } //이메일 로그인 버튼 클릭 시 함수 호출
        googleLoginBtn.setOnClickListener{ googleLogin() } //구글 로그인 버튼 클릭 시 함수 호출
    }

    fun googleLogin(){
        var intent=googleSignInClient.signInIntent
        startActivityForResult(intent,GOOGLE_LOGIN)
    }

    fun emailLogin(){
        auth.createUserWithEmailAndPassword(emailEt.text.toString(),passwordEt.text.toString())
            .addOnCompleteListener{ task->
                Log.d("LoginActivity","${task.isSuccessful} ${task.exception.toString()}")
                if(task.isSuccessful){
                    moveMainPage(auth.currentUser)
                }else{
                    signInEmail()
                }
            }
    }

    fun signInEmail(){
        auth.signInWithEmailAndPassword(emailEt.text.toString(), passwordEt.text.toString())
            .addOnCompleteListener{task ->
                if(task.isSuccessful){
                    moveMainPage(auth.currentUser)
                }else{
                    Toast.makeText(this,"비밀번호가 일치하지 않습니다",Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    fun moveMainPage(user:FirebaseUser?){
        if(user==null){
            return
        }
        //intent : 다른 activity에서 넘어갈 때 사용
        var intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun firebaseAuthWithGoogle(account: GoogleSignInAccount){
        var credential=GoogleAuthProvider.getCredential(account.idToken,null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener{task ->
                if(task.isSuccessful){
                    moveMainPage(auth.currentUser)
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==GOOGLE_LOGIN){
            var result= Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if(result.isSuccess){
                firebaseAuthWithGoogle(result.signInAccount!!)
            }
        }
    }
}
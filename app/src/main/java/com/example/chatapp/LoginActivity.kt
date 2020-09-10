package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_welcome.*

class LoginActivity : AppCompatActivity() {

    //menginisialisasi immutable tanpa value
    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        setSupportActionBar(toolbar_login)
//        supportActionBar!!.title = "Login"
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mAuth = FirebaseAuth.getInstance()

        toolbar_login.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        tv_to_sign_up_1.setOnClickListener {
            val intentToSignUp = Intent(this, RegisterActivity::class.java)
            startActivity(intentToSignUp)
            finish()
        }

        button_summit_login.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val email : String = email_login.text.toString()
        val password : String = password_login.text.toString()

        if (email == ""){
            Toast.makeText(this, getString(R.string.not_null), Toast.LENGTH_LONG).show()
        } else if (password == ""){
            Toast.makeText(this, getString(R.string.not_null), Toast.LENGTH_LONG).show()
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val intent = Intent(this, MainActivity::class.java)
                    //akan ngereplace task lama dengan task baru
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, getString(R.string.err) + task.exception!!.message.toString(),
                        Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

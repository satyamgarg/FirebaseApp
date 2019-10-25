package com.app.firebasesampleapp

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance()

        initUI()
    }

    private fun initUI() {
        val btnSignIn = findViewById<Button>(R.id.sign_in_button)
        val inputEmail = findViewById<EditText>(R.id.email)
        val inputPassword = findViewById<EditText>(R.id.password)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        btnSignIn.setOnClickListener {
            val email = inputEmail.text.toString()
            val password = inputPassword.text.toString()

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(applicationContext, "Enter email address!", Toast.LENGTH_SHORT)
                    .show();
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(applicationContext, "Enter password!", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            }



            progressBar.visibility = View.VISIBLE;

            auth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this@MainActivity,
                    OnCompleteListener { task ->
                        val string: String = getString(R.string.btn_back)

                        progressBar.visibility = View.GONE
                        if (!task.isSuccessful) {
                            if (password.length < 6) {
                                inputPassword.error = getString(R.string.minimum_password)
                            } else {
                                Toast.makeText(
                                    applicationContext,
                                    getString(R.string.auth_failed),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Authentication successful.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    })
        }

        /*// Another way to set button click listener
        btnSignUp.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

            }
        })

        // Another way to set button click listener
        btnResetPassword.setOnClickListener({ v ->

        })*/


    }


}

package com.example.disobey

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class login : AppCompatActivity() {
    var loginbtn: SignInButton? = null
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()

        val currentUser: FirebaseUser? = mAuth!!.getCurrentUser()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()


        val mGoogleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(this, gso)
        loginbtn = findViewById<SignInButton>(R.id.login)
        loginbtn?.setOnClickListener(View.OnClickListener {
            val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
            startActivityForResult(signInIntent, 100)
        })
    }
    override fun onStart() {
        super.onStart()
        //        Log.d(TAG, "onStart: called");
        mAuth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = mAuth!!.getCurrentUser()
        if (user != null) {
            loginbtn!!.visibility = View.GONE
            val s = " Welcome ${user.getDisplayName()}"
            displayToast(s)
            Handler().postDelayed({
                val ihome = Intent(this@login, Base::class.java)
                startActivity(ihome)
                finish()
            }, 2000)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            // When request code is equal to 100 initialize task
            val signInAccountTask: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            // check condition
            if (signInAccountTask.isSuccessful) {
                // When google sign in successful initialize string
                val s = "Google sign in successful"
                // Display Toast
                displayToast(s)
                // Initialize sign in account
                try {
                    // Initialize sign in account
                    val googleSignInAccount = signInAccountTask.getResult(
                        ApiException::class.java
                    )
                    // Check condition
                    if (googleSignInAccount != null) {
                        // When sign in account is not equal to null initialize auth credential
                        val authCredential: AuthCredential =
                            GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)
                        // Check credential
                        mAuth?.signInWithCredential(authCredential)?.addOnSuccessListener(
                            OnSuccessListener<Any?> {
                                Toast.makeText(
                                    this@login,
                                    "Sign in successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(this@login, Base::class.java)
                                startActivity(intent)
                                finish()
                            })?.addOnFailureListener(OnFailureListener { e ->
                            Toast.makeText(
                                this@login,
                                e.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        })
                    }
                } catch (e: ApiException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun displayToast(s: String) {
        Toast.makeText(applicationContext, s, Toast.LENGTH_SHORT).show()
    }
}
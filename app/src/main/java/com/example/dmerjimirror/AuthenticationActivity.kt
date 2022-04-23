package com.example.dmerjimirror

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dmerjimirror.databinding.ActivityAuthenticationBinding
import com.example.dmerjimirror.library.controller.UserController
import com.example.dmerjimirror.library.model.request.user.UserToken
import com.example.dmerjimirror.library.utils.KProgressHUDUtility

class AuthenticationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthenticationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        restoreSignIn()
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun restoreSignIn() {
        val hud = KProgressHUDUtility.show(this)
        val sharedPreferences =
            getSharedPreferences(getString(R.string.auth_shared_preferences), Context.MODE_PRIVATE)
        val token = sharedPreferences.getString(
            getString(R.string.auth_shared_preferences_access_token),
            null
        )
        if (token != null) {
            UserController.refreshSignIn(UserToken(token)) { userResponse, throwable ->
                hud.dismiss()
                if (userResponse != null) {
                    val intent = Intent(this, MainActivity::class.java)
                    val bundle = Bundle()
                    bundle.putSerializable("UserResponse", userResponse)
                    intent.putExtras(bundle)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    this.finish()
                }
            }
        } else {
            hud.dismiss()
        }
    }

}
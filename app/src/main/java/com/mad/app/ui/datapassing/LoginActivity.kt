package com.mad.app.ui.datapassing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.mad.app.R
import com.mad.app.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Animate entrance
        val scaleIn = AnimationUtils.loadAnimation(this, R.anim.scale_fade_in)
        binding.ivLoginIcon.startAnimation(scaleIn)

        // Pre-fill from SharedPreferences
        val prefs = getSharedPreferences("mad_prefs", Context.MODE_PRIVATE)
        val savedUsername = prefs.getString("username", "") ?: ""
        if (savedUsername.isNotEmpty()) {
            binding.etUsername.setText(savedUsername)
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            var hasError = false

            if (username.isEmpty()) {
                binding.tilUsername.error = "Username is required"
                hasError = true
            } else {
                binding.tilUsername.error = null
            }

            if (password.isEmpty()) {
                binding.tilPassword.error = "Password is required"
                hasError = true
            } else {
                binding.tilPassword.error = null
            }

            if (hasError) {
                // Shake animation
                val shake = AnimationUtils.loadAnimation(this, R.anim.shake)
                if (username.isEmpty()) binding.tilUsername.startAnimation(shake)
                if (password.isEmpty()) binding.tilPassword.startAnimation(shake)
                return@setOnClickListener
            }

            // Save to SharedPreferences
            prefs.edit()
                .putString("username", username)
                .putBoolean("has_logged_in", true)
                .apply()

            // Launch Dashboard with data
            val intent = Intent(this, DashboardActivity::class.java).apply {
                putExtra("username", username)
                putExtra("login_time", System.currentTimeMillis())
            }
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }
    }
}

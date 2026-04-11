package com.mad.app.ui.datapassing

import android.content.Context
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mad.app.R
import com.mad.app.databinding.ActivityDashboardBinding
import java.text.SimpleDateFormat
import java.util.*

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarDashboard.setNavigationOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        // Get data from intent
        val username = intent.getStringExtra("username") ?: "User"
        val loginTime = intent.getLongExtra("login_time", System.currentTimeMillis())

        // Check if returning user
        val prefs = getSharedPreferences("mad_prefs", Context.MODE_PRIVATE)
        val hasLoggedIn = prefs.getBoolean("has_logged_in", false)

        // Set greeting
        binding.tvGreeting.text = "Hello, $username! 👋"
        binding.tvWelcomeType.text = if (hasLoggedIn) "Welcome back!" else "Welcome!"

        // Format time
        val formattedTime = SimpleDateFormat(
            "HH:mm:ss, dd MMM yyyy",
            Locale.getDefault()
        ).format(Date(loginTime))

        binding.tvUsernameDisplay.text = "👤 Username: $username"
        binding.tvLoginTime.text = "⏰ Login time: $formattedTime"

        // Animate wave emoji
        val waveAnimator = ObjectAnimator.ofPropertyValuesHolder(
            binding.tvWaveEmoji,
            PropertyValuesHolder.ofFloat(View.ROTATION, 0f, 20f, -10f, 15f, -5f, 0f)
        ).apply {
            duration = 1500
            repeatCount = 2
            repeatMode = ObjectAnimator.RESTART
            startDelay = 500
        }
        waveAnimator.start()

        // Animate cards
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up_fade_in)
        val slideUpDelayed = AnimationUtils.loadAnimation(this, R.anim.slide_up_fade_in).apply {
            startOffset = 200
        }
        binding.cardGreeting.startAnimation(slideUp)
        binding.cardDetails.startAnimation(slideUpDelayed)
    }

    @Deprecated("Use OnBackPressedDispatcher")
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}

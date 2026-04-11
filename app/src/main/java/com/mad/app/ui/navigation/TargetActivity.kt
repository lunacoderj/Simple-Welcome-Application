package com.mad.app.ui.navigation

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.mad.app.R
import com.mad.app.databinding.ActivityTargetBinding
import java.text.SimpleDateFormat
import java.util.*

class TargetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTargetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTargetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up toolbar with back navigation
        binding.toolbarTarget.setNavigationOnClickListener {
            finishWithAnimation()
        }

        // Retrieve intent data
        val source = intent.getStringExtra("source") ?: "Unknown"
        val timestamp = intent.getLongExtra("timestamp", 0L)
        val formattedTime = SimpleDateFormat(
            "HH:mm:ss, dd MMM yyyy",
            Locale.getDefault()
        ).format(Date(timestamp))

        binding.tvTimestamp.text = "⏰ Arrived at: $formattedTime"
        binding.tvSource.text = "📍 Source: $source"

        // Animate cards entrance
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up_fade_in)
        val slideUpDelayed = AnimationUtils.loadAnimation(this, R.anim.slide_up_fade_in).apply {
            startOffset = 150
        }
        binding.cardWelcome.startAnimation(slideUp)
        binding.cardInfo.startAnimation(slideUpDelayed)

        binding.btnGoBack.setOnClickListener {
            finishWithAnimation()
        }
    }

    private fun finishWithAnimation() {
        finish()
        overridePendingTransition(
            R.anim.slide_in_left,
            R.anim.slide_out_right
        )
    }

    @Deprecated("Use OnBackPressedDispatcher")
    override fun onBackPressed() {
        finishWithAnimation()
    }
}

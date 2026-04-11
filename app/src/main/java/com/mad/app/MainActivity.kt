package com.mad.app

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.mad.app.databinding.ActivityMainBinding

/**
 * ═══════════════════════════════════════════════════
 * AI & ML ENGINEERING — MAIN ACTIVITY
 * ═══════════════════════════════════════════════════
 *
 * This activity handles the interactive logic for the 
 * futuristic AI-themed welcome screen.
 *
 * FEATURES IMPLEMENTED:
 * 1. Typewriter Animation for the hero title.
 * 2. Auto-cycling messages via TextSwitcher.
 * 3. Motion Morphism: Floating ambient glow circles.
 * 4. Micro-interactions: Pulse rotation for the AI icon.
 * 5. Expandable Glassmorphic Card logic.
 * 6. Dynamic content changes via Button interaction.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val handler = Handler(Looper.getMainLooper())
    
    // Subtitle messages for cycling
    private val messages = listOf(
        "Build Intelligent Systems",
        "Explore Neural Networks",
        "Shape the Future with AI",
        "Master Deep Learning",
        "Innovate with Machine Learning"
    )
    private var currentMessageIndex = 0
    private var isExpanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTextSwitcher()
        setupInteractions()
        startAmbientAnimations()
        runEntranceSequence()
    }

    /**
     * STAGGERED ENTRANCE SEQUENCE
     * Animates UI components sequentially to create a professional feel.
     */
    private fun runEntranceSequence() {
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up_fade_in)
        val popIn = AnimationUtils.loadAnimation(this, R.anim.scale_fade_in)

        // 1. Pop in the AI icon
        binding.aiIcon.visibility = View.VISIBLE
        binding.aiIcon.startAnimation(popIn)
        binding.aiIcon.alpha = 1f

        // 2. Delay title reveal (Typewriter effect start)
        handler.postDelayed({
            binding.welcomePrefix.visibility = View.VISIBLE
            binding.welcomePrefix.animate().alpha(1f).duration = 500
            startTypewriter(getString(R.string.welcome_title))
        }, 600)

        // 3. Staggered reveal for action elements
        val components = listOf(
            binding.subtitleSwitcher,
            binding.dotsContainer,
            binding.infoCard,
            binding.discoverButton,
            binding.topicCardsRow,
            binding.footerText
        )

        components.forEachIndexed { index, view ->
            handler.postDelayed({
                view.visibility = View.VISIBLE
                view.startAnimation(slideUp)
                view.alpha = 1f
                
                // If it's the subtitle switcher, start cycling
                if (view == binding.subtitleSwitcher) {
                    startMessageCycling()
                }
            }, 1200 + (index * 200L))
        }
    }

    /**
     * TYPEWRITER ANIMATION
     * Reveals text character-by-character.
     */
    private fun startTypewriter(text: String) {
        binding.welcomeTitle.visibility = View.VISIBLE
        binding.welcomeTitle.alpha = 1f
        var index = 0
        val typewriterRunnable = object : Runnable {
            override fun run() {
                if (index <= text.length) {
                    binding.welcomeTitle.text = text.substring(0, index++)
                    handler.postDelayed(this, 60)
                }
            }
        }
        handler.post(typewriterRunnable)
    }

    /**
     * TEXT SWITCHER SETUP
     * Configures look and feel of the cycling text.
     */
    private fun setupTextSwitcher() {
        binding.subtitleSwitcher.setFactory {
            TextView(this).apply {
                textSize = 18f
                setTextColor(getColor(R.color.text_secondary))
                gravity = android.view.Gravity.CENTER
                letterSpacing = 0.05f
            }
        }
        binding.subtitleSwitcher.setText(messages[0])
        setupDots()
    }

    private fun setupDots() {
        binding.dotsContainer.removeAllViews()
        messages.forEachIndexed { index, _ ->
            val dot = View(this).apply {
                val size = resources.getDimensionPixelSize(R.dimen.dot_size)
                layoutParams = android.widget.LinearLayout.LayoutParams(size, size).apply {
                    marginEnd = resources.getDimensionPixelSize(R.dimen.dot_spacing)
                }
                setBackgroundResource(if (index == 0) R.drawable.dot_active else R.drawable.dot_inactive)
                tag = index
            }
            binding.dotsContainer.addView(dot)
        }
    }

    private fun startMessageCycling() {
        val cycleRunnable = object : Runnable {
            override fun run() {
                currentMessageIndex = (currentMessageIndex + 1) % messages.size
                updateSubtitle(currentMessageIndex)
                handler.postDelayed(this, 3000)
            }
        }
        handler.postDelayed(cycleRunnable, 3000)
    }

    private fun updateSubtitle(index: Int) {
        binding.subtitleSwitcher.setText(messages[index])
        // Update dots
        binding.dotsContainer.children.forEachIndexed { i, view ->
            view.setBackgroundResource(if (i == index) R.drawable.dot_active else R.drawable.dot_inactive)
        }
    }

    /**
     * AMBIENT ANIMATIONS (Motion Morphism)
     * Makes the background glow circles float gently.
     */
    private fun startAmbientAnimations() {
        // Simple floating movement for glow circles
        fun animateFloat(view: View, duration: Long, distance: Float) {
            ObjectAnimator.ofFloat(view, "translationY", -distance, distance).apply {
                this.duration = duration
                repeatMode = ObjectAnimator.REVERSE
                repeatCount = ObjectAnimator.INFINITE
                interpolator = AccelerateDecelerateInterpolator()
                start()
            }
        }

        animateFloat(binding.glowCyan, 4000, 40f)
        animateFloat(binding.glowPurple, 5000, -30f)
        animateFloat(binding.glowGreen, 4500, 25f)

        // Pulse rotation for AI Icon
        ObjectAnimator.ofPropertyValuesHolder(
            binding.aiIcon,
            PropertyValuesHolder.ofFloat("scaleX", 1f, 1.1f),
            PropertyValuesHolder.ofFloat("scaleY", 1f, 1.1f),
            PropertyValuesHolder.ofFloat("rotation", -5f, 5f)
        ).apply {
            duration = 3000
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = ObjectAnimator.INFINITE
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }

    /**
     * INTERACTION LOGIC
     */
    private fun setupInteractions() {
        // Expand/Collapse Info Card
        binding.infoCard.setOnClickListener {
            isExpanded = !isExpanded
            binding.cardBody.visibility = if (isExpanded) View.VISIBLE else View.GONE
            
            // Rotate chevron
            binding.chevronIcon.animate()
                .rotation(if (isExpanded) 180f else 0f)
                .setDuration(300)
                .start()
        }

        // Discover Button - Interaction
        binding.discoverButton.setOnClickListener {
            // Button click animation
            it.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).withEndAction {
                it.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
            }.start()

            // Change content logic
            currentMessageIndex = (currentMessageIndex + 1) % messages.size
            updateSubtitle(currentMessageIndex)
        }
        
        // Topic Cards click animation
        listOf(binding.cardDL, binding.cardNLP, binding.cardCV).forEach { card ->
            card.setOnClickListener {
                it.animate().scaleX(0.9f).scaleY(0.9f).setDuration(150).withEndAction {
                    it.animate().scaleX(1f).scaleY(1f).setDuration(150).start()
                }.start()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}

package com.mad.app.ui.fragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mad.app.R
import com.mad.app.databinding.ActivityFragmentHostBinding

class FragmentHostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFragmentHostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up toolbar
        binding.toolbarFragments.setNavigationOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        // Load initial fragment
        if (savedInstanceState == null) {
            loadFragment(InfoFragment(), "info")
        }

        // Bottom navigation
        binding.bottomNavFragments.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_info -> {
                    loadFragment(InfoFragment(), "info")
                    true
                }
                R.id.nav_stats -> {
                    loadFragment(StatsFragment(), "stats")
                    true
                }
                R.id.nav_gallery -> {
                    loadFragment(GalleryFragment(), "gallery")
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: androidx.fragment.app.Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_up_fade_in,
                R.anim.slide_down_fade_out,
                R.anim.slide_up_fade_in,
                R.anim.slide_down_fade_out
            )
            .replace(R.id.fragment_container, fragment, tag)
            .commit()
    }

    @Deprecated("Use OnBackPressedDispatcher")
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}

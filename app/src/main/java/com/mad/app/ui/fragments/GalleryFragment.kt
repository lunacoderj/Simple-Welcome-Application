package com.mad.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mad.app.R

class GalleryFragment : Fragment() {

    private val galleryItems = listOf(
        "🤖" to "Android",
        "🎨" to "Design",
        "⚡" to "Performance",
        "🔒" to "Security",
        "📱" to "Mobile",
        "☁️" to "Cloud",
        "🧪" to "Testing",
        "🚀" to "Deploy",
        "📊" to "Analytics",
        "🔔" to "Push",
        "💾" to "Storage",
        "🌐" to "Network"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv = view.findViewById<RecyclerView>(R.id.rv_gallery)
        rv.layoutManager = GridLayoutManager(requireContext(), 3)
        rv.adapter = GalleryAdapter(galleryItems)

        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.scale_fade_in)
        view.startAnimation(anim)
    }
}

class GalleryAdapter(
    private val items: List<Pair<String, String>>
) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val emoji: android.widget.TextView = view.findViewById(R.id.tv_gallery_emoji)
        val title: android.widget.TextView = view.findViewById(R.id.tv_gallery_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gallery, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (emoji, title) = items[position]
        holder.emoji.text = emoji
        holder.title.text = title

        // Staggered animation
        holder.itemView.alpha = 0f
        holder.itemView.translationY = 50f
        holder.itemView.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(400)
            .setStartDelay((position * 60).toLong())
            .start()
    }

    override fun getItemCount() = items.size
}

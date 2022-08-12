package com.example.shape_up_2022.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.shape_up_2022.databinding.MovieVideoItemBinding
import com.google.android.youtube.player.YouTubeStandalonePlayer


class YoutubeViewHolder(val binding: MovieVideoItemBinding): RecyclerView.ViewHolder(binding.root)
class YoutubeAdapter(val context: Context, val datas: ArrayList<com.example.shape_up_2022.data.SearchResult>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int{
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = YoutubeViewHolder(MovieVideoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding=(holder as YoutubeViewHolder).binding
        val model = datas!![position].snippet!!


        binding.dateTextView.text=model.publishedAt
        binding.titleTextView.text=model.title
        binding.contentsTextView.text=model.description

        if(model.thumbnails != null && model.thumbnails.medium != null){
            Glide.with(binding.root)
                .load(model.thumbnails.medium.url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(binding.thumbnailImageView)
        }

        holder.itemView.setOnClickListener{
            val intent = YouTubeStandalonePlayer.createVideoIntent(
                context as Activity,
                "AIzaSyDDN21CCWYkdZBa9D4quDG7-0wAu_AIaFc",
                datas!![position].id!!.videoId.toString(),
                0,
                true,
                true
            )
            context.startActivity(intent)
        }
    }
}
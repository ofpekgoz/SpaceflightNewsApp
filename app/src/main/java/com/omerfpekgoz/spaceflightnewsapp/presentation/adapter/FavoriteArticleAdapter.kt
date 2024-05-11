package com.omerfpekgoz.spaceflightnewsapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omerfpekgoz.spaceflightnewsapp.databinding.ItemArticleBinding
import com.omerfpekgoz.spaceflightnewsapp.domain.model.FavoriteArticle

/**
 * Created by omerfarukpekgoz on 11.05.2024.
 */
class FavoriteArticleAdapter() : RecyclerView.Adapter<FavoriteArticleAdapter.FavoriteArticleViewHolder>() {
    private var favoriteArticleList = arrayListOf<FavoriteArticle>()

    fun submitList(list: List<FavoriteArticle>) {
        this.favoriteArticleList.clear()
        this.favoriteArticleList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FavoriteArticleViewHolder(
        ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = favoriteArticleList.size

    override fun onBindViewHolder(holder: FavoriteArticleViewHolder, position: Int) {
        val article = favoriteArticleList[position]
        with(holder.itemArticleBinding) {
            with(article) {
                txtTitle.text = title
                txtSummary.text = summary
                txtPublishedAt.text = published_at

                Glide.with(holder.itemView.context)
                    .load(image_url)
                    .diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.ALL)
                    .placeholder(com.omerfpekgoz.spaceflightnewsapp.R.drawable.ic_block)
                    .error(com.omerfpekgoz.spaceflightnewsapp.R.drawable.ic_block)
                    .override(300, 400)
                    .centerCrop()
                    .into(imgNews)
            }
        }
    }

    inner class FavoriteArticleViewHolder(val itemArticleBinding: ItemArticleBinding) : RecyclerView.ViewHolder(itemArticleBinding.root)
}
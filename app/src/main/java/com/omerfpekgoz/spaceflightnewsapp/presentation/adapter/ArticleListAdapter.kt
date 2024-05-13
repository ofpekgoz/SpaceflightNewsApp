package com.omerfpekgoz.spaceflightnewsapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.omerfpekgoz.spaceflightnewsapp.R
import com.omerfpekgoz.spaceflightnewsapp.databinding.ItemArticleBinding
import com.omerfpekgoz.spaceflightnewsapp.domain.model.ArticleEntity
import com.omerfpekgoz.spaceflightnewsapp.util.Util

/**
 * Created by omerfarukpekgoz on 11.05.2024.
 */
class ArticleListAdapter(
    var onItemClicked: ((id: Int) -> Unit)? = null,
) : RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder>() {

    private var articleList = arrayListOf<ArticleEntity>()

    fun submitList(list: List<ArticleEntity>) {
        this.articleList.clear()
        this.articleList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ArticleViewHolder(
        ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = articleList.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articleList[position]
        with(holder.itemArticleBinding) {
            with(article) {
                txtTitle.text = title
                txtSummary.text = summary
                txtPublishedAt.text = Util.formatDateTime(published_at)

                Glide.with(holder.itemView.context)
                    .load(image_url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_block)
                    .error(R.drawable.ic_block)
                    .override(300, 400)
                    .centerCrop()
                    .into(imgNews)

                cardArticle.setOnClickListener {
                    onItemClicked?.invoke(id)
                }

            }
        }
    }

    inner class ArticleViewHolder(val itemArticleBinding: ItemArticleBinding) : RecyclerView.ViewHolder(itemArticleBinding.root)

}
package com.omerfpekgoz.spaceflightnewsapp.util

import android.widget.Filter
import com.omerfpekgoz.spaceflightnewsapp.domain.model.ArticleEntity
import com.omerfpekgoz.spaceflightnewsapp.presentation.adapter.ArticleListAdapter
import java.util.Locale

/**
 * Created by omerfarukpekgoz on 13.05.2024.
 */
class CustomFilter(private var filterList: ArrayList<ArticleEntity>, private var adapter: ArticleListAdapter) : Filter() {

    override fun performFiltering(charSequence: CharSequence?): FilterResults {
        var charSequence = charSequence

        charSequence = charSequence!!.toString().lowercase(Locale.ROOT)
        val results = FilterResults()
        if (charSequence.isNotEmpty()) {

            val filteredPlayers = ArrayList<ArticleEntity>()
            filterList.forEach {
                if (it.title.lowercase(Locale.ROOT).contains(charSequence) || it.title.lowercase(Locale.ROOT).contains(charSequence)) {
                    filteredPlayers.add(it)
                }
            }
            results.values = filteredPlayers
        } else {
            results.values = filterList
        }
        return results
    }

    override fun publishResults(constraint: CharSequence, results: FilterResults) {
        adapter.articleList = results.values as ArrayList<ArticleEntity>
        adapter.notifyDataSetChanged()
    }
}
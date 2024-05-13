package com.omerfpekgoz.spaceflightnewsapp.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.omerfpekgoz.spaceflightnewsapp.R
import com.omerfpekgoz.spaceflightnewsapp.databinding.FragmentArticleDetailBinding
import com.omerfpekgoz.spaceflightnewsapp.domain.model.ArticleEntity
import com.omerfpekgoz.spaceflightnewsapp.domain.model.toFavoriteArticleEntity
import com.omerfpekgoz.spaceflightnewsapp.presentation.viewmodel.ArticleDetailViewModel
import com.omerfpekgoz.spaceflightnewsapp.presentation.viewmodel.FavoriteArticleViewModel
import com.omerfpekgoz.spaceflightnewsapp.util.Resource
import com.omerfpekgoz.spaceflightnewsapp.util.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ArticleDetailFragment : Fragment() {

    private lateinit var b: FragmentArticleDetailBinding
    private lateinit var articleDetailViewModel: ArticleDetailViewModel
    private lateinit var favoriteArticleViewModel: FavoriteArticleViewModel
    private var article: ArticleEntity? = null
    private var isArticleFavorite = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentArticleDetailBinding.inflate(layoutInflater)
        articleDetailViewModel = ViewModelProvider(this)[ArticleDetailViewModel::class.java]
        favoriteArticleViewModel = ViewModelProvider(this)[FavoriteArticleViewModel::class.java]
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spaceflightId = arguments?.getInt("id")
        if (spaceflightId != null) {
            lifecycleScope.launch {
                articleDetailViewModel.getArticleById(
                    spaceflightId,
                    Util.hasInternetConnection(requireContext())
                )
            }
        }
        getSpaceflight()
        b.cardFavorite.setOnClickListener {
            article?.let { it1 -> clickCardFavorite(it1) }
        }
    }

    private fun getSpaceflight() {
        lifecycleScope.launch {
            articleDetailViewModel.articleDetailState.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> {
                        b.progressBar2.visibility = View.VISIBLE
                    }

                    is Resource.Success -> {
                        b.progressBar2.visibility = View.GONE
                        article = it.data as ArticleEntity
                        article?.let {
                            setSpaceflight(article!!)
                        }
                    }

                    is Resource.Error -> {
                        b.progressBar2.visibility = View.GONE
                        Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    private fun setSpaceflight(articleEntity: ArticleEntity) {
        b.txtTitle.text = articleEntity.title
        b.txtSummary.text = articleEntity.summary
        b.txtPublishedAt.text = Util.formatDateTime(articleEntity.published_at)
        isArticleInFavorite(articleEntity.id)

        Glide.with(requireContext())
            .load(articleEntity.image_url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_block)
            .error(R.drawable.ic_block)
            .override(300, 400)
            .centerCrop()
            .into(b.imageView)


    }

    private fun isArticleInFavorite(id: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            if (favoriteArticleViewModel.isArticleInFavorites(id) != null) {
                withContext(Dispatchers.Main) {
                    b.imgIsFavorite.setImageResource(R.drawable.ic_added_bookmark)
                    isArticleFavorite = true
                }
            } else {
                withContext(Dispatchers.Main) {
                    b.imgIsFavorite.setImageResource(R.drawable.ic_add_bookmark)
                    isArticleFavorite = false
                }
            }
        }
    }

    private fun clickCardFavorite(article: ArticleEntity) {
        if (isArticleFavorite) {
            isArticleFavorite=false
            deleteFavoriteArticle(article.id)
            b.imgIsFavorite.setImageResource(R.drawable.ic_add_bookmark)
            Toast.makeText(requireContext(), "Article Removed to Favorites", Toast.LENGTH_SHORT).show()
        } else {
            isArticleFavorite=true
            saveFavoriteArticle(article)
            b.imgIsFavorite.setImageResource(R.drawable.ic_added_bookmark)
            Toast.makeText(requireContext(), "Article Added to Favorites", Toast.LENGTH_SHORT).show()
        }
    }


    private fun saveFavoriteArticle(article: ArticleEntity) {
        lifecycleScope.launch(Dispatchers.IO) {
            favoriteArticleViewModel.insertFavoriteArticle(article.toFavoriteArticleEntity())
        }
    }

    private fun deleteFavoriteArticle(articleId: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            favoriteArticleViewModel.deleteFavoriteArticle(articleId)
        }
    }
}
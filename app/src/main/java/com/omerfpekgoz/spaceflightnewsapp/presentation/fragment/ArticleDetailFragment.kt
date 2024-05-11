package com.omerfpekgoz.spaceflightnewsapp.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.omerfpekgoz.spaceflightnewsapp.R
import com.omerfpekgoz.spaceflightnewsapp.databinding.FragmentArticleDetailBinding
import com.omerfpekgoz.spaceflightnewsapp.domain.model.ArticleEntity
import com.omerfpekgoz.spaceflightnewsapp.presentation.viewmodel.ArticleDetailViewModel
import com.omerfpekgoz.spaceflightnewsapp.util.Resource
import com.omerfpekgoz.spaceflightnewsapp.util.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleDetailFragment : Fragment() {

    private lateinit var b: FragmentArticleDetailBinding
    private lateinit var articleDetailViewModel: ArticleDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentArticleDetailBinding.inflate(layoutInflater)
        articleDetailViewModel = ViewModelProvider(this)[ArticleDetailViewModel::class.java]
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spaceflightId = arguments?.getInt("id")
        if (spaceflightId != null) {
            articleDetailViewModel.getArticleList(id, Util.hasInternetConnection(requireContext()))
        }
        getSpaceflight()
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
                        setSpaceflight(it.data as ArticleEntity)
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
        b.txtPublishedAt.text = articleEntity.published_at

        Glide.with(requireContext())
            .load(articleEntity.image_url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_block)
            .error(R.drawable.ic_block)
            .override(300, 400)
            .centerCrop()
            .into(b.imageView)
    }
}
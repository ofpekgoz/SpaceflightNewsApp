package com.omerfpekgoz.spaceflightnewsapp.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.omerfpekgoz.spaceflightnewsapp.R
import com.omerfpekgoz.spaceflightnewsapp.databinding.FragmentFavoriteArticlesBinding
import com.omerfpekgoz.spaceflightnewsapp.domain.model.ArticleEntity
import com.omerfpekgoz.spaceflightnewsapp.domain.model.FavoriteArticle
import com.omerfpekgoz.spaceflightnewsapp.presentation.adapter.ArticleListAdapter
import com.omerfpekgoz.spaceflightnewsapp.presentation.adapter.FavoriteArticleAdapter
import com.omerfpekgoz.spaceflightnewsapp.presentation.viewmodel.FavoriteArticleViewModel
import com.omerfpekgoz.spaceflightnewsapp.util.Resource
import com.omerfpekgoz.spaceflightnewsapp.util.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteArticlesFragment : Fragment() {

    private lateinit var b: FragmentFavoriteArticlesBinding
    private lateinit var favoriteArticleViewModel: FavoriteArticleViewModel
    private lateinit var adapter: FavoriteArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentFavoriteArticlesBinding.inflate(layoutInflater)
        favoriteArticleViewModel = ViewModelProvider(this)[FavoriteArticleViewModel::class.java]
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupObserve()
    }

    private fun setupObserve() {
        lifecycleScope.launch {
            favoriteArticleViewModel.favoriteArticleState.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> {
                        b.progressBar.visibility = View.VISIBLE
                    }

                    is Resource.Success -> {
                        b.progressBar.visibility = View.GONE
                        val favoriteArticleList = it.data as List<FavoriteArticle>
                        if (favoriteArticleList.isNotEmpty()) {
                            adapter.submitList(it.data)
                            b.txtNoFavorite.visibility = View.GONE
                        } else {
                            b.txtNoFavorite.visibility = View.VISIBLE
                        }
                    }

                    is Resource.Error -> {
                        b.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    private fun setupAdapter() {
        adapter = FavoriteArticleAdapter()
        b.recyclerView.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        b.recyclerView.layoutManager = linearLayoutManager
        b.recyclerView.setHasFixedSize(true)
        b.recyclerView.parent.requestLayout()
    }
}
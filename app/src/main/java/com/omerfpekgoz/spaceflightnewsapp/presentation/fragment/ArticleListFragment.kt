package com.omerfpekgoz.spaceflightnewsapp.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.omerfpekgoz.spaceflightnewsapp.R
import com.omerfpekgoz.spaceflightnewsapp.databinding.FragmentArticleListBinding
import com.omerfpekgoz.spaceflightnewsapp.domain.model.ArticleEntity
import com.omerfpekgoz.spaceflightnewsapp.presentation.adapter.ArticleListAdapter
import com.omerfpekgoz.spaceflightnewsapp.presentation.viewmodel.ArticleViewModel
import com.omerfpekgoz.spaceflightnewsapp.util.Resource
import com.omerfpekgoz.spaceflightnewsapp.util.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleListFragment : Fragment() {
    private lateinit var b: FragmentArticleListBinding
    private lateinit var adapter: ArticleListAdapter
    private lateinit var articleViewModel: ArticleViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentArticleListBinding.inflate(layoutInflater)
        articleViewModel = ViewModelProvider(this)[ArticleViewModel::class.java]
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        getArticleList()
        setupObserve()
        goToFavoriteFragment()
        setupSearchView()
    }

    private fun goToFavoriteFragment() {
        b.cardBookmark.setOnClickListener {
            findNavController().navigate(R.id.action_spaceflightNewsListFragment_to_favoriteArticlesFragment)
        }
    }

    private fun getArticleList() {
        articleViewModel.getArticleList(Util.hasInternetConnection(requireContext()))
    }

    private fun setupObserve() {
        lifecycleScope.launch {
            articleViewModel.articleState.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> {
                        b.progressBar.visibility = View.VISIBLE
                    }

                    is Resource.Success -> {
                        b.progressBar.visibility = View.GONE
                        adapter.submitList(it.data as List<ArticleEntity>)
                    }

                    is Resource.Error -> {
                        b.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    private fun setupSearchView() {
        b.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener, androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.getFilter().filter(newText);
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
        })
    }

    private fun setupAdapter() {
        adapter = ArticleListAdapter()
        b.recyclerView.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        b.recyclerView.layoutManager = linearLayoutManager
        b.recyclerView.setHasFixedSize(true)
        b.recyclerView.parent.requestLayout()

        adapter.onItemClicked = {
            val bundle = bundleOf("id" to it)
            findNavController().navigate(R.id.action_spaceflightNewsListFragment_to_spaceflightDetailFragment, bundle)
        }
    }
}
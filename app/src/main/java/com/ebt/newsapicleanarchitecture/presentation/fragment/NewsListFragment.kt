package com.ebt.newsapicleanarchitecture.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ebt.newsapicleanarchitecture.data.util.Result
import com.ebt.newsapicleanarchitecture.databinding.FragmentNewsListBinding
import com.ebt.newsapicleanarchitecture.presentation.adapter.ArticleAdapter
import com.ebt.newsapicleanarchitecture.presentation.viewmodel.NewsListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsListFragment : Fragment() {
    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsListViewModel by viewModels()
    private val articleAdapter by lazy {
        ArticleAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initListeners()
        initUI()
        viewModel.getArticles()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initListeners() {
        articleAdapter.setOnItemClickListener {

        }
    }

    private fun initUI() {
        binding.recyclerView.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initObservers() {
        viewModel.apiResult.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Result.Success -> {
                    hideLoading()
                    val articles = it.data?.articles
                    articles?.let {
                        val dataList = ArrayList(articles)
                        articleAdapter.differ.submitList(dataList)
                    }
                }
                is Result.Error -> {
                    hideLoading()
                }
                is Result.Loading -> {
                    showLoading()
                }
            }
        })
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.INVISIBLE
    }
}
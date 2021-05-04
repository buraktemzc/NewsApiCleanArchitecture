package com.ebt.newsapicleanarchitecture.presentation.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ebt.newsapicleanarchitecture.R
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
    ): View {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initListeners()
        initUI()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filter -> {
                Toast.makeText(requireContext(), "Hello", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initListeners() {
        articleAdapter.setOnItemClickListener {
            findNavController().navigate(
                R.id.action_newsListFragment_to_newsDetailFragment,
                Bundle().apply {
                    putString("article_url", it.url)
                }
            )
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
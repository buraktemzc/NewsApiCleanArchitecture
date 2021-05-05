package com.ebt.newsapicleanarchitecture.presentation.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ebt.newsapicleanarchitecture.R
import com.ebt.newsapicleanarchitecture.common.DateUtil
import com.ebt.newsapicleanarchitecture.common.observeEvent
import com.ebt.newsapicleanarchitecture.data.util.Result
import com.ebt.newsapicleanarchitecture.databinding.FragmentNewsListBinding
import com.ebt.newsapicleanarchitecture.presentation.adapter.ArticleAdapter
import com.ebt.newsapicleanarchitecture.presentation.viewmodel.NewsListViewModel
import com.ebt.newsapicleanarchitecture.presentation.viewmodel.SharedCalendarViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsListFragment : Fragment() {
    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsListViewModel by viewModels()
    private val sharedViewModel: SharedCalendarViewModel by activityViewModels()
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
                findNavController().navigate(
                    NewsListFragmentDirections.actionNewsListFragmentToCalendarDialogFragment()
                )
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
                        updateUIVisibility(it.size)
                    }
                }
                is Result.Error -> {
                    hideLoading()
                    Toast.makeText(requireContext(), "Error: $it", Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    showLoading()
                }
            }
        })

        viewModel.defaultDateInitialized.observeEvent(viewLifecycleOwner) {
            it.let {
                sharedViewModel.sCalendar = it.startDate
                sharedViewModel.eCalendar = it.endDate
            }
        }

        sharedViewModel.calendar.observeEvent(viewLifecycleOwner) {
            viewModel.getArticles(
                DateUtil.getDateInString(
                    dateFormat = DateUtil.API_DATE_FORMAT,
                    calendar = it.startDate
                ),
                DateUtil.getDateInString(
                    dateFormat = DateUtil.API_DATE_FORMAT,
                    calendar = it.endDate
                )
            )
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun updateUIVisibility(listSize: Int) {
        if (listSize > 0) {
            binding.recyclerView.visibility = View.VISIBLE
            binding.emptyMessageTextView.visibility = View.GONE
        } else {
            binding.recyclerView.visibility = View.GONE
            binding.emptyMessageTextView.visibility = View.VISIBLE
        }
    }
}
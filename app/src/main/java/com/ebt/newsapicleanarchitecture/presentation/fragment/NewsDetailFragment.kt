package com.ebt.newsapicleanarchitecture.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ebt.newsapicleanarchitecture.databinding.FragmentNewsDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailFragment : Fragment() {
    private var _binding: FragmentNewsDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: NewsDetailFragmentArgs by navArgs()
        val articleURL = args.articleUrl
        initUI(articleURL)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUI(articleURL: String) {
        binding.webView.settings.apply {
            javaScriptEnabled = true
            loadWithOverviewMode = true
            domStorageEnabled = true
            allowContentAccess = true
            allowFileAccess = true
        }
        binding.webView.loadUrl(articleURL)
    }
}
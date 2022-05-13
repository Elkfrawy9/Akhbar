package com.elkfrawy.akhbar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.elkfrawy.akhbar.databinding.FragmentWebViewBinding


class WebViewFragment : Fragment() {

    lateinit var binding: FragmentWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWebViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val url = WebViewFragmentArgs.fromBundle(requireArguments()).url
        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl(url)


    }

}
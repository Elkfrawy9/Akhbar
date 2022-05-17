package com.elkfrawy.akhbar.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.opengl.Visibility
import android.os.Bundle
import android.text.BoringLayout
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.elkfrawy.akhbar.MainViewModel
import com.elkfrawy.akhbar.R
import com.elkfrawy.akhbar.databinding.FragmentHomeBinding
import com.elkfrawy.akhbar.model.Article
import com.elkfrawy.akhbar.model.News
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject lateinit var glide: RequestManager
    @Inject lateinit var adapter: NewsRecyclerAdapter
    lateinit var binding: FragmentHomeBinding
    lateinit var controller: NavController

    val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getNews()
        controller = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getNewsLiveData().observe(viewLifecycleOwner){
            adapter.getNews(it.articles)
            binding.progressBar.visibility = View.GONE
        }

        adapter.onViewClickedListener(object : NewsRecyclerAdapter.onViewClicked {
            override fun onSaveIconClickListener(article: Article) {
                if (article.saved) {
                    viewModel.insert(article)
                }else{
                    viewModel.delete(article)
                }
            }


            override fun onRootClickListener(url: String) {
                val direction = HomeFragmentDirections.webViewAction(url)
                controller.navigate(direction)
            }

        })



    }



}
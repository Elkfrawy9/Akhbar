package com.elkfrawy.akhbar.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.elkfrawy.akhbar.MainViewModel
import com.elkfrawy.akhbar.R
import com.elkfrawy.akhbar.databinding.FragmentHomeBinding
import com.elkfrawy.akhbar.databinding.FragmentSearchBinding
import com.elkfrawy.akhbar.model.Article
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    @Inject lateinit var glide: RequestManager
    @Inject lateinit var adapter: NewsRecyclerAdapter

    lateinit var binding: FragmentSearchBinding
    lateinit var controller: NavController

    val viewModel by viewModels<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = NewsRecyclerAdapter(requireContext(), glide)
        controller = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchRecyclerView.adapter = adapter
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.liveData.observe(viewLifecycleOwner){
            adapter.getNews(it.articles)
        }

        binding.searchImg.setOnClickListener{
            if (binding.search.text.length > 0)
                viewModel.getSearchResult(binding.search.text.toString())
        }

        adapter.onViewClickedListener(object : NewsRecyclerAdapter.onViewClicked{
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
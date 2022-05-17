package com.elkfrawy.akhbar.ui

import android.media.MediaRouter
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
import com.elkfrawy.akhbar.databinding.FragmentSavedBinding
import com.elkfrawy.akhbar.model.Article
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import javax.inject.Inject

@AndroidEntryPoint
class SavedFragment : Fragment() {

    @Inject lateinit var adapter: NewsRecyclerAdapter
    @Inject lateinit var glid: RequestManager

    lateinit var binding: FragmentSavedBinding
    lateinit var controller: NavController

    val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.savedRecyclerView.adapter = adapter
        binding.savedRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getCachedArticle().observe(viewLifecycleOwner){
            adapter.getNews(it)

            adapter.onViewClickedListener(object : NewsRecyclerAdapter.onViewClicked{
                override fun onSaveIconClickListener(article: Article) {

                }

                override fun onRootClickListener(url: String) {
                    val direction = HomeFragmentDirections.webViewAction(url)
                    controller.navigate(direction)
                }

            })



        }

        deleteSavedArticle()

    }

    fun deleteSavedArticle(){
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT + ItemTouchHelper.RIGHT){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.delete(adapter.getArtcle(viewHolder.adapterPosition))
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

        }).attachToRecyclerView(binding.savedRecyclerView)
    }


}
package com.elkfrawy.akhbar.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.elkfrawy.akhbar.R
import com.elkfrawy.akhbar.databinding.NewsCardBinding
import com.elkfrawy.akhbar.di.AppModule
import com.elkfrawy.akhbar.model.Article
import com.elkfrawy.akhbar.model.News
import dagger.hilt.android.EntryPointAccessors
import java.net.URL
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NewsRecyclerAdapter
    (val context:Context, val glide: RequestManager) : RecyclerView.Adapter<NewsRecyclerAdapter.viewHolder>() {

    lateinit var binding: NewsCardBinding
    lateinit var mListener: onViewClicked

    var news: List<Article> = listOf()

    class viewHolder(val binding: NewsCardBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    fun getArtcle(pos: Int):Article{
        return news.get(pos)
    }


    fun getNews(articles: List<Article>){
        this.news = articles
        notifyDataSetChanged()
    }

    fun onViewClickedListener(listener: onViewClicked){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        binding = NewsCardBinding.inflate(LayoutInflater.from(context), parent, false)
        val holder: viewHolder = viewHolder(binding)
        return holder
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        val n = news.get(position)

        holder.binding.loading.visibility = View.VISIBLE
        holder.binding.desc.setText(n.desc)
        holder.binding.title.setText(n.title)
        holder.binding.source.setText(n.source.name)
        holder.binding.author.setText(n.author)
        if (n.saved)
            holder.binding.saveArticle.visibility = View.GONE
        else
            holder.binding.saveArticle.setImageResource(R.drawable.ic_unsave)

        holder.binding.saveArticle.setOnClickListener{
            if (mListener != null) {
                n.saved = !n.saved
                if (n.saved) {
                    holder.binding.saveArticle.visibility = View.GONE
                    Toast.makeText(context, "Saved ${n.saved}", Toast.LENGTH_LONG).show()
                }
                else
                    holder.binding.saveArticle.setImageResource(R.drawable.ic_unsave)

                mListener.onSaveIconClickListener(n)
            }
        }
        holder.binding.root.setOnClickListener{
            if (mListener != null)
                mListener.onRootClickListener(n.url)
        }

        val sdf = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss'Z'",
            Locale.getDefault())

        val dateFormat = SimpleDateFormat("dd',' MMM yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())


        glide.load(n.imageUrl)
            .into(holder.binding.articleImage)
        holder.binding.loading.visibility = View.GONE

        try {
            val date: Date? = sdf.parse(n.date)
            holder.binding.calender.setText(dateFormat.format(date))
            holder.binding.time.setText(timeFormat.format(date))
        }catch (ex:ParseException){
            ex.printStackTrace()
        }


    }

    override fun getItemCount(): Int {
        return news.size
    }

    interface onViewClicked{
        fun onSaveIconClickListener(article: Article)
        fun onRootClickListener(url: String)
    }
}
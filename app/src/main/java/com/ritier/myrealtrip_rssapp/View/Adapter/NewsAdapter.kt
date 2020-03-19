package com.ritier.myrealtrip_rssapp.View.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ritier.myrealtrip_rssapp.R
import com.ritier.myrealtrip_rssapp.databinding.ActivityMainItemBinding
import com.ritier.myrealtrip_rssapp.model.NewsListItem


//https://codechacha.com/ko/android-jetpack-databinding/
class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var items = listOf<NewsListItem>()

    companion object{
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, url: String) = Glide.with(view.context).load(url).into(view)
    }

    inner class NewsViewHolder(private val binding: ActivityMainItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NewsListItem) {
            with(binding) {
                this.newsListItem = item
                this.executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ActivityMainItemBinding>(
            inflater,
            R.layout.activity_main_item,
            parent,
            false
        )
        return NewsViewHolder(binding)
    }


    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) =
        holder.bind(items[position])

    fun setItems(itemList: List<NewsListItem>) {
        items = itemList
        notifyDataSetChanged()
    }
}
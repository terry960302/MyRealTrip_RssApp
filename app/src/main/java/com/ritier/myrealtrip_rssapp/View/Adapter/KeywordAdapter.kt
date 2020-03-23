package com.ritier.myrealtrip_rssapp.View.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ritier.myrealtrip_rssapp.R
import com.ritier.myrealtrip_rssapp.databinding.KeywordItemBinding

class KeywordAdapter : RecyclerView.Adapter<KeywordAdapter.KeywordViewHolder>(){

    private var keywordList = mutableListOf<String>()

    inner class KeywordViewHolder(private val binding: KeywordItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(keyword: String) {
            with(binding) {
                this.tvKeyword.text = keyword
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<KeywordItemBinding>(
            inflater,
            R.layout.keyword_item,
            parent,
            false
        )
        return KeywordViewHolder(binding)
    }

    override fun getItemCount(): Int = keywordList.size

    override fun onBindViewHolder(holder: KeywordViewHolder, position: Int) = holder.bind(keywordList[position])

    fun setItems(items : MutableList<String>){
        keywordList = items
        notifyDataSetChanged()
    }
}
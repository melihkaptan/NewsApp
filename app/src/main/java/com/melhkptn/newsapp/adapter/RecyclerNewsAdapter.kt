package com.melhkptn.newsapp.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.melhkptn.newsapp.R
import com.melhkptn.newsapp.model.Article
import com.melhkptn.newsapp.util.downloadImage
import kotlinx.android.synthetic.main.recycler_row.view.*

class RecyclerNewsAdapter(private var articleList: ArrayList<Article>) :
    RecyclerView.Adapter<RecyclerNewsAdapter.RecyclerNewsViewHolder>() {

    class RecyclerNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerNewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_row, parent, false)
        return RecyclerNewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: RecyclerNewsViewHolder, position: Int) {
        holder.itemView.textViewSource.text = articleList[position].source?.name
        holder.itemView.textViewTitle.text = articleList[position].title

        articleList[position].urlToImage?.let {
            holder.itemView.imageViewNews.downloadImage(it, holder.itemView.context)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW , Uri.parse(articleList[position].url))
            holder.itemView.context.startActivity(intent)
        }
    }

    fun updateArticleList(list: List<Article>) {
        articleList.clear()
        articleList.addAll(list)
        notifyDataSetChanged()
    }


}
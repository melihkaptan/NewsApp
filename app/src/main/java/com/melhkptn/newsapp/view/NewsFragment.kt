package com.melhkptn.newsapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.melhkptn.newsapp.R
import com.melhkptn.newsapp.adapter.RecyclerNewsAdapter
import com.melhkptn.newsapp.util.hide
import com.melhkptn.newsapp.util.show
import com.melhkptn.newsapp.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_news.*


class NewsFragment : BaseFragment() {

    private var adapter = RecyclerNewsAdapter(arrayListOf())
    private lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun initView() {
        super.initView()

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        viewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        viewModel.getNews()

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            progressBar.show()
            recyclerView.hide()
            viewModel.getDataOnline()
        }

        initObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    private fun initObservers() {

        viewModel.articleList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.updateArticleList(it)
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                progressBar.show()
                recyclerView.hide()
            } else {
                progressBar.hide()
                recyclerView.show()
            }
        })
    }
}
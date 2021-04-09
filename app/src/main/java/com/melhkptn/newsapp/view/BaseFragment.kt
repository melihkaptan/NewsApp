package com.melhkptn.newsapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    open fun initView() {
       // can be overridden
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
}
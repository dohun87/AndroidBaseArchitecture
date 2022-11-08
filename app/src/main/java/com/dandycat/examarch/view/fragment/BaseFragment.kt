package com.dandycat.examarch.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment


abstract class BaseFragment<V : ViewDataBinding>(private val layoutId : Int) : Fragment() {

    abstract fun initFragment()
    abstract fun onBackPressed()

    protected lateinit var binding : V

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,layoutId,container,false)
        activity?.onBackPressedDispatcher?.addCallback {
            onBackPressed()
        }

        initFragment()
        return binding.root
    }
}
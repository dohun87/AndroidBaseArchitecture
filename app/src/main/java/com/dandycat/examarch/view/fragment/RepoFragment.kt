package com.dandycat.examarch.view.fragment

import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.dandycat.examarch.R
import com.dandycat.examarch.databinding.FragmentRepoBinding
import com.dandycat.examarch.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoFragment : BaseFragment<FragmentRepoBinding>(R.layout.fragment_repo) {

    private val vm : SearchViewModel by hiltNavGraphViewModels(R.id.my_graph)

    override fun initFragment() {
        binding.model = vm.getRepoModel()
    }

    override fun onBackPressed() {
        findNavController().popBackStack()
    }
}
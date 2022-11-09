package com.dandycat.examarch.view.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.dandycat.domain.model.RepositoryEntity
import com.dandycat.examarch.BR
import com.dandycat.examarch.R
import com.dandycat.examarch.adapter.RecyclerViewGenericListAdapter
import com.dandycat.examarch.databinding.FragmentSearchBinding
import com.dandycat.examarch.databinding.ListSearchRepoBinding
import com.dandycat.examarch.util.SingleEventObserver
import com.dandycat.examarch.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment() : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    //해당 ViewModel을 이용하게 될 시 Hilt서 Navigation에 연관된 ViewModel을 관리하게 된다.
    private val vm : SearchViewModel by hiltNavGraphViewModels(R.id.my_graph)
    private lateinit var mAdapter : RecyclerViewGenericListAdapter<RepositoryEntity,ListSearchRepoBinding>

    override fun initFragment() {
        binding.vm = vm
        binding.lifecycleOwner = this@SearchFragment
        vm.setRepoModel.observe(this,SingleEventObserver{
            if(it){
                findNavController().navigate(R.id.action_fragment_search_to_fragment_repo)
            }
        })
        vm.searchList.observe(this){
            if(::mAdapter.isInitialized) mAdapter.submitList(it)
        }
        initAdapter()
    }

    private fun initAdapter(){
        mAdapter = RecyclerViewGenericListAdapter(R.layout.list_search_repo,BR.repoEntity,vm)
        binding.rvList.adapter = mAdapter
    }

    override fun onBackPressed() {
        requireActivity().finishAffinity()
    }

}
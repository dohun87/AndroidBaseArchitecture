package com.dandycat.examarch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dandycat.domain.model.RepositoryEntity
import com.dandycat.examarch.databinding.ListSearchRepoBinding
import javax.inject.Inject

class SearchListAdapter : ListAdapter<RepositoryEntity, SearchListAdapter.ViewHolder>(diffUtil) {

    object diffUtil : DiffUtil.ItemCallback<RepositoryEntity>(){
        override fun areItemsTheSame(oldItem: RepositoryEntity, newItem: RepositoryEntity) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: RepositoryEntity, newItem: RepositoryEntity) =
            oldItem.id == newItem.id
    }

    inner class ViewHolder(private val binding : ListSearchRepoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(data :RepositoryEntity){
            binding.entity=data
            //해당 로직을 넣을 경우 즉각적으로 데이터 바인드를 선언한다.
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListSearchRepoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(currentList[position])
    }
}
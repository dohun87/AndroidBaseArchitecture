package com.dandycat.examarch.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dandycat.examarch.listener.widget.ViewModelTransferInterface

/**
 * DiffUtil 기반의 RecyclerView Adapter
 * 해당 동작에 대해 Generic 적용(데이터, 데이터바인딩)하여 여러 RecyclerView서 동일하게 사용할 수 있게 만들었다.
 * @author dohun8832
 * @see
 */
class RecyclerViewGenericListAdapter<ITEM : Any, VB : ViewDataBinding>(
    @LayoutRes private val layoutResId : Int,
    private val bindingVariable : Int? = null,
    private val callback : ViewModelTransferInterface? = null
) : ListAdapter<ITEM,RecyclerViewGenericListAdapter.ViewHolder<VB>>(ListDiffUtil()) {

    /**
     * DiffUtil에 대한 클래스
     * 해당 클래스에서 contents, item을 비교하여 처리 하도록 한다.
     * @author dohun8832
     */
    class ListDiffUtil<ITEM : Any> : DiffUtil.ItemCallback<ITEM>(){
        override fun areItemsTheSame(oldItem: ITEM, newItem: ITEM) =
            oldItem == newItem


        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: ITEM, newItem: ITEM) =
            oldItem.toString() == newItem.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<VB> {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(layoutResId,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder<VB>, position: Int) {
        holder.bindHolder(bindingVariable,currentList[position],callback,position)
    }


    class ViewHolder<VB : ViewDataBinding>(view : View) : RecyclerView.ViewHolder(view) {
        private val binding : VB = DataBindingUtil.bind(view)!!

        fun<ITEM : Any> bindHolder(bindingVariable: Int?, item : ITEM, callback : ViewModelTransferInterface?, position : Int){
            bindingVariable?.let {
                //binding.xxx = item의 결과와 동일하다.
                //차이점으로는 setVariable를 이용하게 될 시 추상적인 타입에 대하여 Binding 대입이 가능하다.
                //https://medium.com/@saqwzx88/databinding-객체에-type을-모른체-값-넣기-724c1cd708b0
                binding.setVariable(it,item)
                binding.root.setOnClickListener {
                    callback?.clickPosition(position)
                    callback?.clickData(item)
                }
                binding.executePendingBindings()
            }
        }
    }
}
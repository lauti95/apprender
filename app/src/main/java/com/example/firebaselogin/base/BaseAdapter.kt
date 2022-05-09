package com.example.firebaselogin.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

private typealias ViewHolderBindingInflater<VB> = (
        inflater: LayoutInflater,
        parent: ViewGroup,
        attachToParent: Boolean,
        ) -> VB
abstract class BaseAdapter<VH:RecyclerView.ViewHolder>:RecyclerView.Adapter<VH>() {

    fun<VB: ViewBinding> ViewGroup.inflateBinding(bindingInflater: ViewHolderBindingInflater<VB>): VB{
        return bindingInflater.invoke(LayoutInflater.from(context), this,false)
    }

    abstract class BaseViewHolder(binding:ViewBinding): RecyclerView.ViewHolder(binding.root)

}
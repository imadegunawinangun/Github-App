package com.rumahgugun.consumerapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rumahgugun.consumerapp.databinding.ItemUserBinding

class UserSearchAdapter : RecyclerView.Adapter<UserSearchAdapter.UserViewHolder>() {

    private val list = ArrayList<UserDetail>()

    private var onItemClickCallback: OnItemClickCallback? = null

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(user: ArrayList<UserDetail>) {
        list.clear()
        list.addAll(user)
        notifyDataSetChanged()

    }

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(user: UserDetail) {

            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }

            Glide.with(itemView)
                .load(user.avatar_url)
                .into(binding.imgItemPhoto)
            binding.tvUsername.text = user.login
            return
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: UserDetail)
    }
}
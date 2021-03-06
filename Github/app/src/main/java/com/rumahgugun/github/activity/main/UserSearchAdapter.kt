package com.rumahgugun.github.activity.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rumahgugun.github.data.UserDetail
import com.rumahgugun.github.databinding.ItemUserBinding

class UserSearchAdapter : RecyclerView.Adapter<UserSearchAdapter.UserViewHolder>() {

    private val list = ArrayList<UserDetail>()

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

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

    fun setList(userDetail: List<UserDetail>?) {
        list.clear()
        if (userDetail != null) {
            for (user in userDetail){
                val userMapped = UserDetail(
                    user.login,
                    user.id,
                    user.node_id,
                    user.avatar_url,
                    user.gravatar_id,
                    user.url,
                    user.html_url,
                    user.followers_url,
                    user.following_url,
                    user.gists_url,
                    user.starred_url,
                    user.subscriptions_url,
                    user.organizations_url,
                    user.repos_url,
                    user.events_url,
                    user.received_events_url,
                    user.type,
                    user.site_admin,
                    user.score,
                    user.name,
                    user.company,
                    user.blog,
                    user.location,
                    user.email,
                    user.hireable,
                    user.bio,
                    user.twitter_username,
                    user.public_repos,
                    user.public_gists,
                    user.followers,
                    user.following
                )
                list.add(userMapped)
                notifyDataSetChanged()
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: UserDetail)
    }
}
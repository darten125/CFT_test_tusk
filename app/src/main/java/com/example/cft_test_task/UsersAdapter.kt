package com.example.cft_test_task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cft_test_task.databinding.ItemUserBinding
import com.example.cft_test_task.model.User

class UsersAdapter(
    private val onItemClick: (User) -> Unit,
): RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {
    private val usersList = mutableListOf<User>()

    fun submitList(list: List<User>){
        usersList.clear()
        usersList.addAll(list)
        notifyDataSetChanged()
    }

    class UsersViewHolder(
        private val binding: ItemUserBinding,
        private val onItemClick: (User) -> Unit
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(user: User){
            binding.nameTextView.text = "${user.name.title} ${user.name.first} ${user.name.last}"
            binding.addressTextView.text = "${user.location.street.number} ${user.location.street.name}"
            binding.phoneNumberTextView.text = user.phone
            Glide.with(binding.imageView.context)
                .load(user.picture.medium)
                .into(binding.imageView)

            itemView.setOnClickListener {
                onItemClick(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UsersViewHolder(binding,onItemClick)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(usersList[position])
    }

    override fun getItemCount(): Int = usersList.size
}
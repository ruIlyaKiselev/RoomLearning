package com.example.roomlearning.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.roomlearning.R
import com.example.roomlearning.model.User
import kotlinx.android.synthetic.main.user_view_holder.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var userList = emptyList<User>()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewForViewHolder = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.user_view_holder, parent, false)

        return ViewHolder(viewForViewHolder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = userList[position]

        holder.itemView.user_vh_id_tv.text = currentItem.id.toString()
        holder.itemView.user_vh_first_name_tv.text = currentItem.firstName
        holder.itemView.user_vh_last_name_tv.text = currentItem.lastName
        holder.itemView.user_vh_age_tv.text = "${currentItem.age} y.o."
        holder.itemView.user_vh_address_tv.text = currentItem.address.streetName + " " + currentItem.address.streetNumber
        holder.itemView.imageView.setImageBitmap(currentItem.profilePhoto)

        holder.itemView.user_view_holder.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setData(userList: List<User>) {
        this.userList = userList
        notifyDataSetChanged()
    }
}
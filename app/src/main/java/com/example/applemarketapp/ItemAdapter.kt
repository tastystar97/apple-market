package com.example.applemarketapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.applemarketapp.databinding.ItemBinding
import java.text.DecimalFormat

class ItemAdapter(private val mItems: MutableList<SaleItem>) :
    RecyclerView.Adapter<ItemAdapter.Holder>() {

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    interface ItemLongClick {
        fun onLongClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null
    var itemLongClick: ItemLongClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }

        holder.itemView.setOnLongClickListener OnLongClickListener@{
            itemLongClick?.onLongClick(it, position)
            return@OnLongClickListener true
        }

        holder.itemImg.setImageResource(mItems[position].Image)
        holder.tvTitle.text = mItems[position].ItemTitle
        holder.tvAddress.text = mItems[position].Address

        val price = mItems[position].Price
        holder.tvPrice.text = DecimalFormat("#,###").format(price) + "원"

        holder.tvChat.text = mItems[position].ChatCnt.toString()
        holder.tvLike.text = mItems[position].LikeCnt.toString()

        if (mItems[position].isLike) {
            holder.ivLike.setImageResource(R.drawable.ic_heart_filled)
        } else {
            holder.ivLike.setImageResource(R.drawable.ic_heart_empty)
        }
    }

    inner class Holder(binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemImg = binding.ivItemImg
        val tvTitle = binding.tvItemTitle
        val tvAddress = binding.tvAddress
        val tvPrice = binding.tvPrice
        val tvChat = binding.tvChat
        val tvLike = binding.tvLike
        val ivLike = binding.ivLike
    }
}
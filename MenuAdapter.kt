package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(
    private val dishList: List<Dish>,
    private val onAddClick: (Dish) -> Unit
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivImage: ImageView = itemView.findViewById(R.id.iv_dish_image)
        val tvName: TextView = itemView.findViewById(R.id.tv_dish_name)
        val tvPrice: TextView = itemView.findViewById(R.id.tv_dish_price)
        val btnAdd: TextView = itemView.findViewById(R.id.btn_add_to_cart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.caidan, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val dish = dishList[position]
        holder.tvName.text = dish.name
        holder.tvPrice.text = "¥${dish.price}"
        holder.btnAdd.setOnClickListener { onAddClick(dish) }
        if (dish.imageResId != 0) {
            holder.ivImage.setImageResource(dish.imageResId)
        }
    }

    override fun getItemCount() = dishList.size
}
package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val cartList = mutableListOf<Dish>()
    private var totalPrice = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.rv_menu_list)
        val tvTotalPrice = findViewById<TextView>(R.id.tv_total_price)
        val btnCheckout = findViewById<Button>(R.id.btn_checkout)

        val menuList = listOf(
            Dish(1, "【川菜馆】宫保鸡丁", 28.0, R.drawable.gongbao),
            Dish(2, "【川菜馆】麻婆豆腐", 18.0, R.drawable.mapo),
            Dish(3, "【快餐店】黄焖鸡米饭", 20.0, R.drawable.huangmenji), // 👈 把这里的 0 改成 huangmenji
            Dish(4, "【奶茶店】珍珠奶茶", 15.0, R.drawable.zhenzhu),
            Dish(5, "【烧烤店】烤羊肉串", 30.0, R.drawable.yangrou)
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = MenuAdapter(menuList) { clickedDish ->
            cartList.add(clickedDish)
            totalPrice += clickedDish.price
            tvTotalPrice.text = "总计: ¥${String.format("%.1f", totalPrice)}"
        }
        recyclerView.adapter = adapter

        // 👇 新增：减号按钮的逻辑 👇
        val btnRemove = findViewById<Button>(R.id.btn_remove)
        btnRemove.setOnClickListener {
            if (cartList.isNotEmpty()) {
                val lastDish = cartList.last() // 拿到最后加进购物车的菜
                cartList.removeAt(cartList.size - 1) // 删掉它
                totalPrice -= lastDish.price // 扣除价格

                if (totalPrice < 0) totalPrice = 0.0 // 防止负数

                tvTotalPrice.text = "总计: ¥${String.format("%.1f", totalPrice)}" // 刷新总价
                Toast.makeText(this, "已退掉一份 ${lastDish.name}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "购物车已经空了哦~", Toast.LENGTH_SHORT).show()
            }
        }

        btnCheckout.setOnClickListener {
            if (cartList.isEmpty()) {
                Toast.makeText(this, "购物车是空的~", Toast.LENGTH_SHORT).show()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("确认订单")
                    .setMessage("共 ${cartList.size} 份菜，需支付：¥${String.format("%.1f", totalPrice)}")
                    .setPositiveButton("确认支付") { dialog, _ ->
                        Toast.makeText(this, "支付成功！", Toast.LENGTH_SHORT).show()
                        cartList.clear()
                        totalPrice = 0.0
                        tvTotalPrice.text = "总计: ¥0.0"
                        dialog.dismiss()
                    }
                    .setNegativeButton("取消", null)
                    .show()
            }
        }
    }
}
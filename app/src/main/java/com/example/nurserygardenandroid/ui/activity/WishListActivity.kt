package com.example.nurserygardenandroid.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nurserygardenandroid.R
import com.example.nurserygardenandroid.model.Question
import com.example.nurserygardenandroid.ui.fragment.home.CustomAdapter
import kotlinx.android.synthetic.main.activity_wish_list.*

class WishListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wish_list)

        recycler_view.layoutManager = GridLayoutManager(this, 2)
        val adapter = CustomAdapter(this, getItemList())
        recycler_view.adapter = adapter
    }

    fun back_onclik(view: android.view.View) {
        finish()
    }

    private fun getItemList(): ArrayList<Question>{
        val list = ArrayList<Question>()
        list.add(Question("PRoduct_1", 589))
        list.add(Question("PRoduct_2", 589))
        list.add(Question("PRoduct_3", 589))
        list.add(Question("PRoduct_4", 589))
        list.add(Question("PRoduct_5", 589))
        list.add(Question("PRoduct_6", 589))
        list.add(Question("PRoduct_6", 589))
        list.add(Question("PRoduct_6", 589))
        list.add(Question("PRoduct_6", 589))
        list.add(Question("PRoduct_6", 589))
        list.add(Question("PRoduct_6", 589))
        list.add(Question("PRoduct_6", 589))
        list.add(Question("PRoduct_6", 589))


        return  list

    }
}
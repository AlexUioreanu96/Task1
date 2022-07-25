package com.example.task1

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.task1.FileUtils.Companion.getList
import com.example.task1.databinding.ActivityCoinsBinding
import com.example.task1.models.CoinModel

const val TAG = "MainActivity"
const val ID = "ID"

class CoinsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val list = getList(this, R.raw.list2)


        if (list.isNotEmpty()) {
            populateView(list)
        }

        binding.card1.setOnClickListener {
            Intent(this, CoinDetailsActivity::class.java).also {
                it.putExtra(ID, list[0].id)
                startActivity(it)
            }
        }
        binding.card2.setOnClickListener {
            Intent(this, CoinDetailsActivity::class.java).also {
                it.putExtra(ID, list[1].id)
                startActivity(it)
            }
        }
        binding.card3.setOnClickListener {
            Intent(this, CoinDetailsActivity::class.java).also {
                it.putExtra(ID, list[2].id)
                startActivity(it)
            }
        }
    }

//    fun activityLauncher(position:CoinModel,extras:String,card:CardView) {
//        binding.cardView.setOnClickListener {
//            Intent(this, CoinDetailsActivity::class.java).also {
//                it.putExtra(extras, position.id)
//                startActivity(it)
//            }
//        }
//    }

    fun populateView(list: List<CoinModel>) {
        binding.apply {
            name1.text = list[0].name
            symbol1.text = list[0].symbol
            if (list[0].is_active) active1.apply {
                text = "active"
                setTextColor(Color.GREEN)
            } else {
                active1.apply {
                    text = "unactive"
                    setTextColor(Color.RED)
                }
            }
            name2.text = list[1].name
            symbol2.text = list[1].symbol
            if (list[1].is_active) active2.apply {
                text = "active"
                setTextColor(Color.GREEN)
            } else {
                active2.apply {
                    text = "unactive"
                    setTextColor(Color.RED)
                }
            }

            name3.text = list[2].name
            symbol3.text = list[2].symbol
            if (list[2].is_active) active3.apply {
                text = "active"
                setTextColor(Color.GREEN)
            } else {
                active3.apply {
                    text = "unactive"
                    setTextColor(Color.RED)
                }
            }
        }
    }
}
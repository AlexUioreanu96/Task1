package com.example.task1

import android.content.Intent
import android.os.Build.ID
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.task1.databinding.ActivityCoinsBinding
import com.example.task1.models.CoinModel

const val TAG = "MainActivity"


class CoinsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinsBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        populateView(list)
//
//        activityLauncherIdExtras(list[0], binding.card1)
//        activityLauncherIdExtras(list[1], binding.card2)
//        activityLauncherIdExtras(list[2], binding.card3)
    }

    private fun activityLauncherIdExtras(position: CoinModel, card: CardView) {
        card.setOnClickListener {
            Intent(this, CoinDetailsActivity::class.java).also {
                it.putExtra(ID, position.id)
                startActivity(it)
            }
        }
    }
//
//    private fun populateView(list: List<CoinModel>?) {
//        list?.let {
//            binding.apply {
//                name1.text = list[0].name
//                symbol1.text = list[0].symbol
//                if (list[0].is_active) active1.apply {
//                    text = "active"
//                    setTextColor(Color.GREEN)
//                } else {
//                    active1.apply {
//                        text = "unactive"
//                        setTextColor(Color.RED)
//                    }
//                }
//                name2.text = list[1].name
//                symbol2.text = list[1].symbol
//                if (list[1].is_active) active2.apply {
//                    text = "active"
//                    setTextColor(Color.GREEN)
//                } else {
//                    active2.apply {
//                        text = "unactive"
//                        setTextColor(Color.RED)
//                    }
//                }
//
//                name3.text = list[2].name
//                symbol3.text = list[2].symbol
//                if (list[2].is_active) active3.apply {
//                    text = "active"
//                    setTextColor(Color.GREEN)
//                } else {
//                    active3.apply {
//                        text = "unactive"
//                        setTextColor(Color.RED)
//                    }
//                }
//            }
//        }
//    }


}
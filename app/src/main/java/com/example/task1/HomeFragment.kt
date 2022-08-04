package com.example.task1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.task1.adapter.ViewPagerAdapter
import com.example.task1.databinding.FragmentHomeBinding
import com.example.task1.models.Images
import com.example.task1.models.Page
import com.example.task1.retrofit.LoginClientRetrofit
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofit = LoginClientRetrofit()

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                var p: Page = retrofit.retriveTrendingMoviesSeries()
                var images: List<Images> = p.results?.map {
                    it?.releaseDate?.let { it1 ->
                        Images(
                            "https://image.tmdb.org/t/p/w500${it.backdropPath}",
                            it1
                        )
                    }
                }?.take(6) as List<Images>
                launch(Dispatchers.Main) {
                    setupViewPager(images)
                    println(images)
                }
            } catch (e: Exception) {
            }


        }

//        setupViewPager()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setupViewPager(list: List<Images>) {
        binding.indicatorView.apply {
            setSliderColor(R.color.normalColor, R.color.checkedColor)
            setSliderWidth(resources.getDimension(R.dimen.dp_10))
            setSliderHeight(resources.getDimension(R.dimen.dp_5))
            setSlideMode(IndicatorSlideMode.WORM)
            setIndicatorStyle(IndicatorStyle.CIRCLE)
            setupWithViewPager(binding.viewpager)
            setPageSize(list.size)
            notifyDataChanged()
        }

        binding.viewpager.adapter = ViewPagerAdapter().apply {
            submitList(list) {
            }
        }
    }
}
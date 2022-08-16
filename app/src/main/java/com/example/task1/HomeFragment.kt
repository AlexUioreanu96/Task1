package com.example.task1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.task1.adapter.CountriesAdapter
import com.example.task1.adapter.MoviesAdapter
import com.example.task1.adapter.PopularPeopleAdapter
import com.example.task1.adapter.ViewPagerAdapter
import com.example.task1.databinding.FragmentHomeBinding
import com.example.task1.db.MovieDBSingelton
import com.example.task1.db.MovieDBSingelton.database
import com.example.task1.db.MoviesDao
import com.example.task1.models.ImagesModel
import com.example.task1.models.MovieEntity
import com.example.task1.models.Star
import com.example.task1.retrofit.LoginClientRetrofit
import com.example.task1.viewModel.HomeViewModel
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


const val HFTAG = "HomeFragment "

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var retrofit: LoginClientRetrofit = LoginClientRetrofit()
    private lateinit var dao: MoviesDao

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDB()

        displayAiring()

        displayViewPager()

        displayStars()

        displayTopRated()

        displayPopularMovies()

        fragmentTransaction()


        lifecycleScope.launch(Dispatchers.IO) {
            val response = apolloClient.query(CountriesQuery()).execute()

            lifecycleScope.launch(Dispatchers.Main) {
                val adapter = CountriesAdapter()

                adapter.list = response.data?.countries ?: emptyList()

                binding.countriesRecycler.adapter = adapter
            }

            Log.d("LaunchList", "Success ${response.data}")
        }
    }

    private fun setupDB() {
        database = MovieDBSingelton.getInstance(requireContext())
        dao = MovieDBSingelton.getInstance(requireContext())?.getMovieDB() as MoviesDao
    }

    private fun fragmentTransaction() {
        binding.btSearch.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace<MovieSearchedFragment>(R.id.fragment_container_view_tag)
                .addToBackStack("searched")
                .commit()
        }
    }


    private fun displayAiring() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val movieEntities: List<MovieEntity> =
                    retrofit.retriveAiringMovies("en-US", 1).results.map {
                        MovieEntity(
                            id = it.id,
                            name = it.title,
                            image = it.posterPath,
                            voteAvg = it.voteAverage,
                            trending = 3
                        )
                    }

                synchronizeList(movieEntities)

                val adapter = MoviesAdapter {
                    lifecycleScope.launch(Dispatchers.IO) {
                        dao.update(it)
                    }
                }
                val list = dao.getAllTrend(3)

                launch(Dispatchers.Main) {
                    binding.airingRecycler.adapter = adapter
                    adapter.list = list
                }

            } catch (e: Exception) {
                Log.w(HFTAG, "It broke's")
            }
        }
    }

    private fun displayPopularMovies() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val movieEntities: List<MovieEntity> =
                    retrofit.retrivePopularMovies("en-US", 1).results.map {
                        MovieEntity(
                            id = it.id,
                            name = it.title,
                            image = it.posterPath,
                            voteAvg = it.voteAverage,
                            trending = 2
                        )
                    }
                synchronizeList(movieEntities)

                val adapter = MoviesAdapter {
                    lifecycleScope.launch(Dispatchers.IO) {
                        dao.update(it)
                    }
                }

                val list = dao.getAllTrend(2)

                launch(Dispatchers.Main) {
                    binding.popularRecycler.adapter = adapter
                    adapter.list = list
                }

            } catch (e: Exception) {
                Log.w(HFTAG, "It broke's")
            }
        }
    }

    private fun displayTopRated() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val movieEntities: List<MovieEntity> =
                    retrofit.retriveTopRatedMovies("en-US", 1).results.map {
                        MovieEntity(
                            id = it.id,
                            name = it.title,
                            image = it.posterPath,
                            voteAvg = it.voteAverage,
                            trending = 1
                        )
                    }
                synchronizeList(movieEntities)

                val adapter = MoviesAdapter {
                    lifecycleScope.launch(Dispatchers.IO) {
                        dao.update(it)
                    }
                }
                val list = dao.getAllTrend(1)

                launch(Dispatchers.Main) {
                    binding.topRatedRecycler.adapter = adapter
                    adapter.list = list
                }
            } catch (e: Exception) {
                Log.w(HFTAG, "It broke's")
            }
        }
    }


    private fun displayViewPager() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val images: List<ImagesModel> = retrofit.retriveTrendingMoviesSeries().results.map {
                    ImagesModel(
                        "https://image.tmdb.org/t/p/w500${it.backdropPath}",
                        it.releaseDate
                    )
                }.take(6)

                launch(Dispatchers.Main) {
                    setupViewPager(images)
                    println(images)
                }
            } catch (e: Exception) {
                Log.w(HFTAG, "It broke's")
            }
        }
    }

    private fun displayStars() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val stars: List<Star> = retrofit.retrivePopularPeople("en-US", 1).results.map {
                    Star(
                        it.name,
                        it.profilePath
                    )
                }

                launch(Dispatchers.Main) {
                    val adapter = PopularPeopleAdapter()
                    adapter.list = stars
                    binding.starsRecycler.adapter = adapter
                }
            } catch (e: Exception) {
                Log.w(HFTAG, "It broke's")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViewPager(list: List<ImagesModel>) {
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

    private suspend fun synchronizeList(list: List<MovieEntity>) {
        list.forEach {
            val movie = it.id?.let { it1 -> dao.queryAfterId(it1) }
            if (movie != null) {
                dao.updateFields(it.id, it.name, it.image, it.voteAvg)
            } else {
                dao.insertOne(it)
            }
        }
    }
}
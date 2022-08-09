package com.example.task1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import com.example.task1.adapter.CountriesAdapter
import com.example.task1.adapter.MoviesAdapter
import com.example.task1.adapter.PopularPeopleAdapter
import com.example.task1.adapter.ViewPagerAdapter
import com.example.task1.databinding.FragmentHomeBinding
import com.example.task1.db.MovieDBSingelton
import com.example.task1.db.MoviesDB
import com.example.task1.db.MoviesDao
import com.example.task1.models.*
import com.example.task1.retrofit.LoginClientRetrofit
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var retrofit: LoginClientRetrofit = LoginClientRetrofit()

    lateinit var database: MoviesDB
    lateinit var dao: MoviesDao


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        retrofit = LoginClientRetrofit()
        database = activity?.let { MovieDBSingelton.getInstance(it.applicationContext) }!!
        dao = MovieDBSingelton.getInstance(requireContext())?.getMovieDB()!!


        lifecycleScope.launch(Dispatchers.IO) {
            val callback: (model: MovieEntity) -> Unit = { model: MovieEntity ->
                launch {
                    dao.insertOne(
                        MovieEntity(
                            id = model.id,
                            image = model.image,
                            voteAvg = model.voteAvg,
                            isFavorite = model.isFavorite
                        )
                    )
                }
            }
        }


        displayAiring()

        displayViewPager()

        displayStars()

        displayTopRated()

        displayPopularMovies()


        binding.btSearch.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace<MovieSearchedFragment>(R.id.fragment_container_view_tag)
                .addToBackStack("searched")
                .commit()
        }

        lifecycleScope.launchWhenResumed {
            val response = apolloClient.query(CountriesQuery()).execute()

            val adapter = CountriesAdapter()
            adapter.list = response.data!!.countries

            binding.countriesRecycler.adapter = adapter

            Log.d("LaunchList", "Success ${response.data}")

        }
    }


    private fun displayAiring() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val page: PageMovieModel = retrofit.retriveAiringMovies("en-US", 1)
                val movieEntities =
                    page.results.map { MovieEntity(it.id, it.posterPath, it.voteAverage) }

                val adapter = MoviesAdapter {
                    println("Petrea:$it")
                    lifecycleScope.launch(Dispatchers.IO) {
                        println(it)
                        dao.update(it)
                    }
                }


                val list = dao.getAll()


                launch(Dispatchers.Main) {
                    binding.airingRecycler.adapter = adapter
                    adapter.list = list
                }

            } catch (e: Exception) {
                println("Alex ${e.message}")
            }
        }
    }

    private fun displayPopularMovies() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val page: PageMovieModel = retrofit.retrivePopularMovies("en-US", 1)
                val movieEntities =
                    page.results.map { MovieEntity(it.id, it.posterPath, it.voteAverage) }

                launch(Dispatchers.Main) {
                    val adapter = MoviesAdapter {

                    }
                    adapter.list = movieEntities
                    binding.popularRecycler.adapter = adapter
                }
            } catch (e: Exception) {
            }
        }
    }

    private fun displayTopRated() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val page: PageMovieModel = retrofit.retriveTopRatedMovies("en-US", 1)
                val movieEntities =
                    page.results.map { MovieEntity(it.id, it.posterPath, it.voteAverage) }

//                for (i in movies) {
//                    dao?.insertOne(i)
//                }

//                val list = dao?.getAll()


                launch(Dispatchers.Main) {
                    val adapter = MoviesAdapter {

                    }
//                    if (list != null) {
//                        adapter.list = list
//                    }
                    binding.topRatedRecycler.adapter = adapter
                }
            } catch (e: Exception) {
            }
        }
    }


    private fun displayViewPager() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val p: PageMovieModel = retrofit.retriveTrendingMoviesSeries()
                val images: List<ImagesModel> = p.results.map {
                    it.releaseDate?.let { it1 ->
                        ImagesModel(
                            "https://image.tmdb.org/t/p/w500${it.backdropPath}",
                            it1
                        )
                    }
                }.take(6) as List<ImagesModel>
                launch(Dispatchers.Main) {
                    setupViewPager(images)
                    println(images)
                }
            } catch (e: Exception) {
            }
        }
    }

    private fun displayStars() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val page: PopularPeople = retrofit.retrivePopularPeople("en-US", 1)
                val stars = page.results.map { Star(it.name, it.profilePath) }
                launch(Dispatchers.Main) {
                    val adapter = PopularPeopleAdapter()
                    adapter.list = stars
                    binding.starsRecycler.adapter = adapter
                }
            } catch (e: Exception) {
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setupViewPager(list: List<ImagesModel>) {
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

    fun setupRecylcer(list: List<Any>, binding: FragmentHomeBinding, recycler: View) {

    }
}
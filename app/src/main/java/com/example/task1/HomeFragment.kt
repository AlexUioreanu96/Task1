package com.example.task1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task1.adapter.CountriesAdapter
import com.example.task1.adapter.MoviesAdapter
import com.example.task1.adapter.PopularPeopleAdapter
import com.example.task1.adapter.ViewPagerAdapter
import com.example.task1.databinding.FragmentHomeBinding
import com.example.task1.db.MovieDBSingelton
import com.example.task1.db.MoviesDB
import com.example.task1.db.MoviesDao
import com.example.task1.models.ImagesModel
import com.example.task1.models.MovieEntity
import com.example.task1.retrofit.LoginRepository
import com.example.task1.viewModel.HomeViewModel
import com.example.task1.viewModel.HomeViewModelFactory
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle


class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var retrofit: LoginRepository = LoginRepository()

    lateinit var database: MoviesDB
    private var dao: MoviesDao? = null

    private lateinit var viewModel: HomeViewModel
    private lateinit var factory: HomeViewModelFactory


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retrofit = LoginRepository()
        dao = MovieDBSingelton.getInstance(requireContext())?.getMovieDB()

        factory = dao?.let { HomeViewModelFactory(retrofit, it) }!!
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

//        val repo = MovieRepository(requireContext())
//        println("Alex" + repo.getAllMovies()?.value.toString())

        displayAiring()

        displayPopularMovies()

        displayStars()

        displayViewPager()

        displayTopRated()

        displayPopularMovies()

        displayCountries()


        binding.btSearch.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace<MovieSearchedFragment>(R.id.fragment_container_view_tag)
                .addToBackStack("searched")
                .commit()
        }
    }


    private fun displayAiring() {
        val adapterObj = MoviesAdapter {
//            viewModel.update()
        }
        binding.airingRecycler.apply {
            adapter = adapterObj
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        viewModel.airingMovies.observe(viewLifecycleOwner, Observer { movies ->
            adapterObj.list = movies
        })
    }


    private fun displayPopularMovies() {
        val adapterObj = MoviesAdapter {
//            viewModel.update()
        }

        binding.popularRecycler.apply {
            adapter = adapterObj
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = adapter
        }

        viewModel.popularMovies.observe(viewLifecycleOwner, Observer { movies ->
            adapterObj.list = movies
        })
    }


    private fun displayTopRated() {
        val adapterObj = MoviesAdapter {}

        binding.topRatedRecycler.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterObj
        }
        viewModel.topRated.observe(viewLifecycleOwner, Observer { movies ->
            binding.topRatedRecycler.also {
                adapterObj.list = movies
            }
        })


    }


    private fun displayViewPager() {
        viewModel.viewPager.observe(viewLifecycleOwner, Observer {
            setupViewPager(it)
        })
    }

    private fun displayStars() {
        val adapterObj = PopularPeopleAdapter()

        binding.starsRecycler.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterObj
        }
        viewModel.starsMovie.observe(viewLifecycleOwner, Observer { movies ->
            adapterObj.list = movies
        })
    }

    private fun displayCountries() {
        val adapterObj = CountriesAdapter()
        binding.countriesRecycler.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterObj
        }

        viewModel.countries.observe(viewLifecycleOwner, Observer { countries ->
            binding.countriesRecycler.also {
                adapterObj.list = countries
            }
        })

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

    private suspend fun synchronized(list: List<MovieEntity>) {
        list.forEach {
            val movie = it.id?.let { it1 -> dao?.queryAfterId(it1) }
            if (movie != null) {
                dao?.updateFields(it.id, it.name, it.image, it.voteAvg)
            } else {
                dao?.insertOne(it)
            }
        }
    }
}
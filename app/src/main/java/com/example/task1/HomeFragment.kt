package com.example.task1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task1.adapter.CountriesAdapter
import com.example.task1.adapter.MoviesAdapter
import com.example.task1.adapter.PopularPeopleAdapter
import com.example.task1.adapter.ViewPagerAdapter
import com.example.task1.databinding.FragmentHomeBinding
import com.example.task1.models.ImagesModel
import com.example.task1.models.MovieEntity
import com.example.task1.viewModel.HomeViewModel
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private val safeArgs: HomeFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.logOut()
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isLogged = safeArgs.isLogged

        if (!viewModel.isLogged) {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
        }

        displayAiring()

        displayPopularMovies()

        displayStars()

        displayViewPager()

        displayTopRated()

        displayPopularMovies()

        displayCountries()

        binding.btSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_movieSearchedFragment)
        }
    }


    private fun displayAiring() {
        val adapterObj = MoviesAdapter(
            { viewModel.update(it) },
            { id -> navDetailsOnClick(id) }
        )
        binding.airingRecycler.apply {
            adapter = adapterObj
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        viewModel.airingMovies.observe(viewLifecycleOwner) { movies ->
            adapterObj.list = movies
        }
    }


    private fun displayPopularMovies() {
        val adapterObj = MoviesAdapter(
            { viewModel.update(it) },
            { id -> navDetailsOnClick(id) }
        )

        binding.popularRecycler.apply {
            adapter = adapterObj
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = adapter
        }

        viewModel.popularMovies.observe(viewLifecycleOwner) { movies ->
            adapterObj.list = movies
        }
    }


    private fun displayTopRated() {
        val adapterObj = MoviesAdapter(
            { viewModel.update(it) },
            { id -> navDetailsOnClick(id) }
        )

        binding.topRatedRecycler.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterObj
        }
        viewModel.topRated.observe(viewLifecycleOwner) { movies ->
            binding.topRatedRecycler.also {
                adapterObj.list = movies
            }
        }
    }


    private fun displayViewPager() {
        viewModel.viewPager.observe(viewLifecycleOwner) {
            setupViewPager(it)
        }
    }

    private fun displayStars() {
        val adapterObj = PopularPeopleAdapter()

        binding.starsRecycler.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterObj
        }
        viewModel.starsMovie.observe(viewLifecycleOwner) { movies ->
            adapterObj.list = movies
        }
    }

    private fun displayCountries() {
        val adapterObj = CountriesAdapter()
        binding.countriesRecycler.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterObj
        }

        viewModel.countries.observe(viewLifecycleOwner) { countries ->
            binding.countriesRecycler.also {
                adapterObj.list = countries
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
//        viewModel.cancelJobs()
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


    private fun navDetailsOnClick(id: Int) {
        findNavController().navigate(HomeFragmentDirections.actionDetails(id))
    }

    private fun toggleFav(movie: MovieEntity) {
        viewModel.update(movie)
    }


}
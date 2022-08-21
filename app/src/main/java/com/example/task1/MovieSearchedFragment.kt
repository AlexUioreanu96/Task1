package com.example.task1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.task1.adapter.MoviesAdapter
import com.example.task1.databinding.FragmentSearchedListBinding
import com.example.task1.db.MovieDBSingelton
import com.example.task1.retrofit.LoginRepository
import com.example.task1.viewModel.SearchViewModel
import com.example.task1.viewModel.SearchViewModelFactory


class MovieSearchedFragment : Fragment(R.layout.fragment_searched_list) {

    private val repo = LoginRepository()

    private var _binding: FragmentSearchedListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SearchViewModel
    private lateinit var factory: SearchViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchedListBinding.inflate(inflater, container, false)

        activity?.actionBar?.show()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = activity?.let { MovieDBSingelton.getInstance(it.applicationContext) }!!
        val dao = MovieDBSingelton.getInstance(requireContext())?.getMovieDB()!!

        factory = SearchViewModelFactory(dao, repo)
        viewModel = ViewModelProvider(this, factory)[SearchViewModel::class.java]

        binding.btSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchQuery(query)
                val adapterObj = MoviesAdapter({ viewModel.update(it) },
                    { id -> navDetailsOnClick(id) }
                )
                binding.list.apply {
                    adapter = adapterObj
                    layoutManager = GridLayoutManager(context, 3)
                }

                viewModel.searched.observe(viewLifecycleOwner) {
                    adapterObj.list = it

                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                viewModel.searchQuery(query)
                val adapterObj = MoviesAdapter(
                    { viewModel.update(it) },
                    { id -> navDetailsOnClick(id) }
                )
                binding.list.apply {
                    adapter = adapterObj
                    layoutManager = GridLayoutManager(context, 3)
                }

                viewModel.searched.observe(viewLifecycleOwner) {
                    adapterObj.list = it

                }
                return true
            }
        })

    }

    private fun navDetailsOnClick(id: Int) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(id))
    }

//    private fun toggleFav(id: Int) {
//        viewModel.update(id)
//    }


}


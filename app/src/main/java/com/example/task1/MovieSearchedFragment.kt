package com.example.task1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.task1.adapter.MoviesAdapter
import com.example.task1.databinding.FragmentSearchedListBinding
import com.example.task1.viewModel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KProperty

@AndroidEntryPoint
class MovieSearchedFragment : Fragment(R.layout.fragment_searched_list) {

    private var _binding: FragmentSearchedListBinding? = null
    private val binding get() = _binding!!

    private var viewModel: SearchViewModel by viewModels()

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
        findNavController().navigate(
            MovieSearchedFragmentDirections.actionMovieSearchedFragmentToDetailsFragment(
                id
            )
        )
    }

}

private operator fun Any.setValue(
    movieSearchedFragment: MovieSearchedFragment,
    property: KProperty<*>,
    searchViewModel: SearchViewModel
) {

}


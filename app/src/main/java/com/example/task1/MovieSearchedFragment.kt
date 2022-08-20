package com.example.task1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.task1.adapter.MoviesAdapter
import com.example.task1.databinding.FragmentSearchedListBinding
import com.example.task1.db.MovieDBSingelton
import com.example.task1.models.MovieEntity
import com.example.task1.retrofit.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MovieSearchedFragment : Fragment(R.layout.fragment_searched_list) {

    private val retrofit = LoginRepository()

    private var _binding: FragmentSearchedListBinding? = null
    private val binding get() = _binding!!


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

        binding.btSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                try {
                    lifecycleScope.launch(Dispatchers.IO) {
                        val fullList = (retrofit.searchMovies(query, "en-US", 1).results +
                                retrofit.searchMovies(
                                    query,
                                    "en-US",
                                    2
                                ).results).filter { it.posterPath.isNotEmpty() }.map {
                            it.title?.let { it1 ->
                                it.voteAverage?.let { it2 ->
                                    MovieEntity(
                                        id = it.id,
                                        name = it1,
                                        image = it.posterPath,
                                        voteAvg = it2
                                    )
                                }
                            }
                        }

                        fullList.forEach { model ->
                            if (model != null) {
                                model.id?.let { it1 -> dao.queryAfterId(it1) }?.let { movieEntity ->
                                    model.isFavorite = movieEntity.isFavorite
                                }
                            }
                        }

                        lifecycleScope.launch(Dispatchers.Main) {
                            val adapter1 = MoviesAdapter(
//                                lifecycleScope.launch(Dispatchers.IO) {
//                                    dao.update(it)
//                                }
                                findNavController()
                            )
                            binding.list.layoutManager = GridLayoutManager(context, 3)
                            binding.list.adapter = adapter1
                            adapter1.list = fullList
                        }

                    }
                    return true
                } catch (e: Exception) {
                    Log.w("search", "error", e)
                    return false
                }
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

    }
}


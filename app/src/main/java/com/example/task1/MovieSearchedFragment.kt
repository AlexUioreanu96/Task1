package com.example.task1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.task1.adapter.MoviesAdapter
import com.example.task1.databinding.FragmentSearchedListBinding
import com.example.task1.db.MovieDBSingelton
import com.example.task1.models.MovieEntity
import com.example.task1.models.PageMovieModel
import com.example.task1.retrofit.LoginClientRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MovieSearchedFragment : Fragment() {

    private val retrofit = LoginClientRetrofit()

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
                    var page1: PageMovieModel
                    var page2: PageMovieModel


                    lifecycleScope.launch(Dispatchers.IO) {
                        page1 = retrofit.searchMovies(query, "en-US", 1)
                        page2 = retrofit.searchMovies(query, "en-US", 2)

                        val movies1 =
                            page1.results.map {
                                MovieEntity(
                                    it.id,
                                    it.title,
                                    it.posterPath,
                                    it.voteAverage
                                )
                            }
                        val movies2 =
                            page2.results.map {
                                MovieEntity(
                                    it.id,
                                    it.title,
                                    it.posterPath,
                                    it.voteAverage
                                )
                            }

                        val fullList: MutableList<MovieEntity> = ArrayList<MovieEntity>()
                        fullList.addAll(movies1)
                        fullList.addAll(movies2)


                        fullList.forEach { model ->
                            model.id?.let { it1 -> dao.queryAfterId(it1) }?.let { movieEntity ->
                                model.isFavorite = movieEntity.isFavorite
                            }
                        }

                        launch(Dispatchers.IO) {
                            val adapter1 = MoviesAdapter {
                                lifecycleScope.launch(Dispatchers.IO) {
                                    dao.update(it)
                                }
                            }

                            adapter1.list = fullList

                            lifecycleScope.launch(Dispatchers.Main) {
                                binding.list.apply {
                                    layoutManager =
                                        GridLayoutManager(context, 3)
                                    adapter = adapter1
                                }
                            }
                        }
                    }
                    return true
                } catch (e: Exception) {
                    return false
                }
            }

            override fun onQueryTextChange(newText: String?): Boolean {
//                try {
//
//
//                    var page1: PageMovieModel = PageMovieModel()
//                    var page2: PageMovieModel = PageMovieModel()
//
//
//                    lifecycleScope.launch(Dispatchers.IO) {
//                        page1 = retrofit.searchMovies(newText, "en-US", 1)
//                        page2 = retrofit.searchMovies(newText, "en-US", 2)
//
//                        val movies1 =
//                            page1.results.map { MovieEntity(it.id, it.posterPath, it.voteAverage) }
//                        val movies2 =
//                            page2.results.map { MovieEntity(it.id, it.posterPath, it.voteAverage) }
//
//                        val fullList: MutableList<MovieEntity> = ArrayList<MovieEntity>()
//                        fullList.addAll(movies1)
//                        fullList.addAll(movies2)
//
//                        launch(Dispatchers.Main) {
//                            val adapter1 = MoviesAdapter {
//
//                            }
//                            adapter1.list = fullList
//
//                            binding.list.apply {
//                                layoutManager =
//                                    GridLayoutManager(context, 3)
//                                adapter = adapter1
//                            }
//                            delay(900)
//                        }
//                    }
//                    return true
//                } catch (e: Exception) {
//                    return false
//                }
                return true
            }
        })

    }
}

//
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.menu, menu)

//        val menuItem: MenuItem = menu.findItem(R.id.action_search)
//        menuItem.isVisible = true
//        val searchView: SearchView = menuItem.actionView as SearchView
//        searchView.queryHint = "Search a movie"

//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//
//                var page1: PageMovieModel = PageMovieModel()
//                var page2: PageMovieModel = PageMovieModel()
//
//
//                lifecycleScope.launch(Dispatchers.IO) {
//                    page1 = retrofit.searchMovies(query, "en-US", 1)
//                    page2 = retrofit.searchMovies(query, "en-US", 2)
//
//                    val movies1 = page1.results.map { Movie(it.id, it.posterPath, it.voteAverage) }
//                    val movies2 = page2.results.map { Movie(it.id, it.posterPath, it.voteAverage) }
//
//                    val fullList: MutableList<Movie> = ArrayList<Movie>()
//                    fullList.addAll(movies1)
//                    fullList.addAll(movies2)
//
//                    launch(Dispatchers.Main) {
//                        val adapter = MoviesAdapter()
//                        adapter.list = fullList
//                        binding.list.adapter = adapter
//                    }
//
//                    parentFragmentManager.beginTransaction()
//                        .replace<MovieSearchedFragment>(R.id.fragment_container_view_tag)
//                        .addToBackStack("searched")
//                        .commit()
//
//
//                }
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return true
//            }
//
//        })
//
//        return super.onCreateOptionsMenu(menu, inflater)
//


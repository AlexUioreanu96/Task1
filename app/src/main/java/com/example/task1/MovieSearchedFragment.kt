package com.example.task1

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.task1.adapter.MoviesAdapter
import com.example.task1.databinding.FragmentSearchedListBinding
import com.example.task1.db.MovieDBSingelton
import com.example.task1.db.MoviesDao
import com.example.task1.models.MovieEntity
import com.example.task1.retrofit.LoginClientRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val HISTORYTAG = "History"
private const val SEARCHTAG = "SEARCH TAG"

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

        binding.btSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.btSearch.setOnEditorActionListener { viewS, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE ||
                keyEvent == null ||
                keyEvent.keyCode == KeyEvent.KEYCODE_ENTER
            ) {

                setupSearchListWithHistory(viewS.text.toString(), dao)
            }
            false
        }

    }

    private fun setupSearchListWithHistory(s: String, dao: MoviesDao) {
        try {
            val sharedPref = activity?.getSharedPreferences(
                HISTORYTAG, Context.MODE_PRIVATE
            ) ?: return


            val history: String? = sharedPref.getString(HISTORYTAG, "")

            with(sharedPref.edit()) {
                putString(HISTORYTAG, s)
                commit()
            }

            val historyList = ArrayList<String>()
            if (history != null) {
                historyList.add(history)
            }

            binding.btSearch.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    historyList.reversed()
                )
            )
//            historySearchAdapter(historyList)


            var fullList: List<MovieEntity>

            lifecycleScope.launch(Dispatchers.IO) {
                fullList = (retrofit.searchMovies(s, "en-US", 1).results + retrofit.searchMovies(
                    s,
                    "en-US",
                    2
                ).results).filter { it.posterPath.isNotEmpty() }.map {
                    MovieEntity(
                        it.id,
                        it.title,
                        it.posterPath,
                        it.voteAverage
                    )
                }


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

                    val fullMovies = dao.getAll()

                    adapter1.list = fullList.map { movie ->
                        if (fullMovies.firstOrNull { it.id == movie.id } != null) {
                            return@map movie.copy(isFavorite = true)
                        }
                        return@map movie
                    }


                    lifecycleScope.launch(Dispatchers.Main) {
                        binding.list.apply {
                            layoutManager =
                                GridLayoutManager(context, 3)
                            adapter = adapter1
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.w(SEARCHTAG, "It it broke")
        }
    }
}

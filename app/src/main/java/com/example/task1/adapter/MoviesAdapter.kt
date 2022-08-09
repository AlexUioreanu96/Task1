package com.example.task1.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task1.R
import com.example.task1.databinding.ItemMoviesBinding
import com.example.task1.models.MovieEntity

class MoviesAdapter(private val callback: (model: MovieEntity) -> Unit) :
    RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    var list = listOf<MovieEntity>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    data class MoviesViewHolder(
        val callback: (model: MovieEntity) -> Unit,
        val binding: ItemMoviesBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movieEntity: MovieEntity) {
            try {
                val photo = "https://image.tmdb.org/t/p/w500${movieEntity.image}"
                Glide.with(binding.root.context)
                    .load(photo)
                    .into(binding.imgTopRated)
                binding.imgFav.visibility = View.GONE
                binding.txtWatched.visibility = View.GONE

                if (movieEntity.voteAvg!! > 8.0) {
                    binding.txtWatched.visibility = View.VISIBLE
                }

                binding.cardMovie.setOnLongClickListener {
                    if (movieEntity.isFavorite != true) {

                        movieEntity.isFavorite = true
                        binding.apply {
                            imgFav.visibility = View.VISIBLE
                            cardMovie.apply {
                                strokeColor = resources.getColor(R.color.cardStrokeColor)
                                strokeWidth = resources.getDimension(R.dimen.dp_3).toInt()
                            }
                        }
                        callback.invoke(movieEntity)
                    } else {
                        movieEntity.isFavorite = false
                        binding.apply {
                            imgFav.visibility = View.GONE
                            cardMovie.apply {
                                strokeWidth = 0
                            }
                        }
                        callback.invoke(movieEntity)
                    }
                    true
                }

                if (movieEntity.isFavorite == true) {
                    binding.apply {
                        imgFav.visibility = View.VISIBLE
                        cardMovie.apply {
                            strokeColor = resources.getColor(R.color.cardStrokeColor)
                            strokeWidth = resources.getDimension(R.dimen.dp_3).toInt()
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("Stuff", e.message.toString())
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesAdapter.MoviesViewHolder {
        return MoviesAdapter.MoviesViewHolder(
            callback,
            ItemMoviesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: MoviesAdapter.MoviesViewHolder, position: Int) {
        holder.bind((list[position]))
    }

    override fun getItemCount(): Int = list.size

}
package com.example.task1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task1.R
import com.example.task1.databinding.ItemMoviesBinding
import com.example.task1.models.Movie

class MoviesAdapter() :
    RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    var list = listOf<Movie>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    data class MoviesViewHolder(val binding: ItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            try {


                val photo = "https://image.tmdb.org/t/p/w500${movie.image}"
                Glide.with(binding.root.context)
                    .load(photo)
                    .into(binding.imgTopRated)
                binding.imgFav.visibility = View.GONE
                binding.txtWatched.visibility = View.GONE

                if (movie.voteAvg!! > 8.0) {
                    binding.txtWatched.visibility = View.VISIBLE
                }

                binding.cardMovie.setOnLongClickListener {
                    if (movie.isFavorite != true) {
                        movie.isFavorite = true
                        binding.apply {
                            imgFav.visibility = View.VISIBLE
                            cardMovie.apply {
                                strokeColor = resources.getColor(R.color.cardStrokeColor)
                                strokeWidth = resources.getDimension(R.dimen.dp_3).toInt()
                            }
                        }
                    } else {
                        movie.isFavorite = false
                        binding.apply {
                            imgFav.visibility = View.GONE
                            cardMovie.apply {
                                strokeWidth = 0
                            }
                        }
                    }
                    true
                }
            } catch (e: Exception) {
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesAdapter.MoviesViewHolder {
        return MoviesAdapter.MoviesViewHolder(
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
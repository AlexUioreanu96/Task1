package com.example.task1.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task1.R
import com.example.task1.databinding.ItemMoviesBinding
import com.example.task1.models.MovieEntity

const val MOVIESADAPTER = "MoviesAdapter"

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
                loadPhoto(binding, movieEntity)

                mustWatchTagLogic(binding, movieEntity)

                ifTapAddFavColorIt(binding, movieEntity, callback)

                ifIsFavoriteColorIt(binding, movieEntity)

            } catch (e: Exception) {
                Log.e(MOVIESADAPTER, e.message.toString())
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesViewHolder {
        return MoviesViewHolder(
            callback,
            ItemMoviesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind((list[position]))
    }

    override fun getItemCount(): Int = list.size

}

fun loadPhoto(binding: ItemMoviesBinding, movieEntity: MovieEntity) {
    val photo = "https://image.tmdb.org/t/p/w500${movieEntity.image}"
    Glide.with(binding.root.context)
        .load(photo)
        .into(binding.imgTopRated)
    binding.imgFav.visibility = View.GONE
    binding.txtWatched.visibility = View.GONE
}

fun mustWatchTagLogic(binding: ItemMoviesBinding, movieEntity: MovieEntity) {
    movieEntity.voteAvg?.let {
        if (movieEntity.voteAvg > 8.0) {
            binding.txtWatched.visibility = View.VISIBLE
        }
    }
}

fun ifIsFavoriteColorIt(binding: ItemMoviesBinding, movieEntity: MovieEntity) {
    if (movieEntity.isFavorite == true) {
        binding.apply {
            imgFav.visibility = View.VISIBLE
            cardMovie.apply {
                strokeColor = ContextCompat.getColor(
                    binding.root.context,
                    R.color.cardStrokeColor
                )
                strokeWidth = resources.getDimension(R.dimen.dp_3).toInt()
            }
        }
    }
}

fun ifTapAddFavColorIt(
    binding: ItemMoviesBinding,
    movieEntity: MovieEntity,
    callback: (model: MovieEntity) -> Unit
) {
    binding.cardMovie.setOnLongClickListener {
        if (movieEntity.isFavorite != true) {

            movieEntity.isFavorite = true
            binding.apply {
                imgFav.visibility = View.VISIBLE
                cardMovie.apply {
                    strokeColor = ContextCompat.getColor(
                        binding.root.context,
                        R.color.cardStrokeColor
                    )
                    strokeWidth = resources.getDimension(R.dimen.dp_3).toInt()
                }
            }
            callback(movieEntity)
        } else {
            movieEntity.isFavorite = false
            binding.apply {
                imgFav.visibility = View.GONE
                cardMovie.apply {
                    strokeWidth = 0
                }
            }
            callback(movieEntity)
        }
        true
    }
}
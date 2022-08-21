package com.example.task1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task1.R
import com.example.task1.databinding.ItemMoviesBinding
import com.example.task1.models.MovieEntity


const val ENTITYID = "ENTITYID"

class MoviesAdapter(
    private val onLongClick: (model: MovieEntity) -> Unit,
    private val onClick: (id: Int) -> Unit
) :
    RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    var list = listOf<MovieEntity>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun modifyOneElement(model: MovieEntity) {
        val position = list.indexOf(model)
        list = list.map {
            if (model.id == it.id) {
                return@map it.copy(isFavorite = !it.isFavorite)
            } else
                it
        }
        notifyItemChanged(position)
    }

    data class MoviesViewHolder(
        val onLongClick: (model: MovieEntity) -> Unit,
        private val onClick: (id: Int) -> Unit,
        val binding: ItemMoviesBinding,
//        private val nav: NavController
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movieEntity: MovieEntity) {
            loadPhoto(binding, movieEntity)
            mustWatchTagLogic(binding, movieEntity)
            setFavorite(movieEntity)


            ifTapAddFavColorIt(binding, movieEntity, onLongClick)

            binding.cardMovie.setOnClickListener {
                onClick(movieEntity.id)
            }
        }

        fun setFavorite(movie: MovieEntity) {
            if (movie.isFavorite) {
                binding.imgFav.visibility = View.VISIBLE
                binding.imgFavBorder.visibility = View.VISIBLE
                binding.cardMovie.apply {
                    strokeColor = ContextCompat.getColor(
                        binding.root.context,
                        R.color.cardStrokeColor
                    )
                    strokeWidth = resources.getDimension(R.dimen.dp_3).toInt()
                }
            } else {
                binding.imgFav.visibility = View.GONE
                binding.imgFavBorder.visibility = View.GONE
                binding.cardMovie.strokeWidth = 0
            }
        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesViewHolder {
        return MoviesViewHolder(
            onLongClick,
            onClick,
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_movies,
                parent,
                false
            ),
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
}

fun mustWatchTagLogic(binding: ItemMoviesBinding, movieEntity: MovieEntity) {
    binding.txtWatched.visibility = View.INVISIBLE
    if (movieEntity.voteAvg > 8.0) {
        binding.txtWatched.visibility = View.VISIBLE
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
    onLongClick: (model: MovieEntity) -> Unit
) {
    binding.cardMovie.setOnLongClickListener {
        if (!movieEntity.isFavorite) {
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
            onLongClick(movieEntity)
        } else {
            movieEntity.isFavorite = false
            binding.apply {
                imgFav.visibility = View.GONE
                cardMovie.apply {
                    strokeWidth = 0
                }
            }
            onLongClick(movieEntity)
        }
        true
    }
}
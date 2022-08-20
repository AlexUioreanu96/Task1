package com.example.task1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.task1.adapter.ENTITYID
import com.example.task1.databinding.FragmentDetailsBinding
import com.example.task1.db.MovieDBSingelton
import com.example.task1.db.MoviesDao
import com.example.task1.viewModel.DetailsViewModel
import com.example.task1.viewModel.DetailsViewModelFactory

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private var bind: FragmentDetailsBinding? = null
    private val binding get() = bind!!

    private var dao: MoviesDao? = null

    private lateinit var viewModel: DetailsViewModel
    private lateinit var factory: DetailsViewModelFactory

    companion object {
        fun newInstance() = DetailsFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dao = MovieDBSingelton.getInstance(requireContext())?.getMovieDB()

        factory = dao?.let { DetailsViewModelFactory(it) }!!
        viewModel = ViewModelProvider(this, factory)[DetailsViewModel::class.java]

        arguments?.let { viewModel.getMovieById(it.getInt(ENTITYID)) }

        viewModel
            .image.observe(viewLifecycleOwner) {
                Glide.with(binding.root.context)
                    .load(it)
                    .into(binding.viewImage)
            }

        viewModel.title.observe(viewLifecycleOwner) {
            binding.viewTitle.text = it
        }

        viewModel.vote.observe(viewLifecycleOwner) {
            binding.viewVote.text = it.toString()
        }
    }
}
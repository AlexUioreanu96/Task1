package com.example.task1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.task1.databinding.FragmentDetailsBinding
import com.example.task1.viewModel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val viewModel: DetailsViewModel by viewModels()

    private var bind: FragmentDetailsBinding? = null
    private val binding get() = bind!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val safeArgs: DetailsFragmentArgs by navArgs()

        viewModel.setMovieId(safeArgs.idMovie)

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
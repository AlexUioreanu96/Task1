package com.example.task1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.task1.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
//    fun initList{
//
//        val listOfViewpagerItems = listOf(
//            ExampleModel(
//                "image 1",
//                0,
//                "https://files.worldwildlife.org/wwfcmsprod/images/HERO_Penguins_Antarctica/story_full_width/9de57cats0_Medium_WW267491.jpg"
//            ),
//            ExampleModel(
//                "image 2",
//                1,
//                "https://files.worldwildlife.org/wwfcmsprod/images/HERO_Penguins_Antarctica/story_full_width/9de57cats0_Medium_WW267491.jpg"
//            ),
//            ExampleModel(
//                "image 3",
//                2,
//                "https://files.worldwildlife.org/wwfcmsprod/images/HERO_Penguins_Antarctica/story_full_width/9de57cats0_Medium_WW267491.jpg"
//            ), ExampleModel(
//                "image 4",
//                3,
//                "https://files.worldwildlife.org/wwfcmsprod/images/HERO_Penguins_Antarctica/story_full_width/9de57cats0_Medium_WW267491.jpg"
//            ),
//            ExampleModel(
//                "image 5",
//                4,
//                "https://files.worldwildlife.org/wwfcmsprod/images/HERO_Penguins_Antarctica/story_full_width/9de57cats0_Medium_WW267491.jpg"
//            )
//        )
//    }
}
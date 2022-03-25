package com.example.nurserygardenandroid.ui.fragment.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nurserygardenandroid.R
import com.example.nurserygardenandroid.adapter.ProductAdapter
import com.example.nurserygardenandroid.databinding.FragmentHomeBinding
import com.example.nurserygardenandroid.model.Question
import com.example.nurserygardenandroid.ui.activity.CartActivity
import com.example.nurserygardenandroid.ui.fragment.BlankFragment
import com.example.nurserygardenandroid.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : BlankFragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null



    val viewModel:ProductViewModel by lazy{
        ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
    }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        showProgressBar("Loading")


        viewModel.refresh()
        viewModel.getProducts.observe(requireActivity()){response->
            dismissProgressBar()
            if(response == null){
                Toast.makeText(requireContext(), "Error while loading", Toast.LENGTH_SHORT).show()
                return@observe
            }



            root.recycler_view.layoutManager = GridLayoutManager(context, 2)



            var adapter = ProductAdapter(activity, response)

            root.recycler_view.adapter = adapter

        }



        return root

    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
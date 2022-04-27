package com.example.nurserygardenandroid.ui.fragment.home

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nurserygardenandroid.adapter.ProductAdapter
import com.example.nurserygardenandroid.databinding.FragmentHomeBinding
import com.example.nurserygardenandroid.model.products.Products
import com.example.nurserygardenandroid.network.NetworkLayer
import com.example.nurserygardenandroid.ui.fragment.BlankFragment
import com.example.nurserygardenandroid.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : BlankFragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    private lateinit var recyclerView:RecyclerView
    private lateinit var searchView:androidx.appcompat.widget.SearchView

    val viewModel:ProductViewModel by lazy{
        ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
    }

    private val binding get() = _binding!!

    private lateinit var adapter:ProductAdapter

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
        recyclerView = root.recycler_view
        searchView = root.searchBar

        var searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager



        searchView.setOnQueryTextListener(object :androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        }
        )

//        searchView.setSearchableInfo(searchManager
//            .getSearchableInfo())


//        try{
//            viewModel.refresh()
//            viewModel.getProducts.observe(requireActivity()){response->
//                dismissProgressBar()
//                if(response == null){
//                    Toast.makeText(requireContext(), "Error while loading", Toast.LENGTH_SHORT).show()
////                    return@observe
//                }else{
//                    root.recycler_view.layoutManager = GridLayoutManager(context, 2)
//                    var adapter = ProductAdapter(activity, response)
//                    root.recycler_view.adapter = adapter
//                }
//
//
//            }
//        }catch (e:Exception){
//            dismissProgressBar()
//            Toast.makeText(context, "Filed to load", Toast.LENGTH_SHORT).show()
//        }




        return root

    }





    override fun onResume() {
        super.onResume()
        NetworkLayer.apiClient.getProducts().enqueue(object: Callback<ArrayList<Products>>{

            override fun onFailure(call: Call<ArrayList<Products>>, t: Throwable) {
                dismissProgressBar()
            }

            override fun onResponse(
                call: Call<ArrayList<Products>>,
                response: Response<ArrayList<Products>>
            ) {if(response.isSuccessful){
                recyclerView.layoutManager = GridLayoutManager(context, 2)
                adapter = ProductAdapter(activity, response.body()!!)
                recyclerView.adapter = adapter
            }else{

            }
                dismissProgressBar()
            }

        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
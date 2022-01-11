package com.example.nurserygardenandroid.ui.fragment.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nurserygardenandroid.databinding.FragmentDashboardBinding
import com.example.nurserygardenandroid.model.Question
import com.example.nurserygardenandroid.ui.fragment.home.CustomAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it

        root.recycler_view.layoutManager = GridLayoutManager(context, 2)

        val adapter = CustomAdapter(activity,getItemList())

        root.recycler_view.adapter = adapter
//        })
        return root
    }


    private fun getItemList(): ArrayList<Question>{
        val list = ArrayList<Question>()
        list.add(Question("PRoduct_1", 589))
        list.add(Question("PRoduct_2", 589))
        list.add(Question("PRoduct_3", 589))
        list.add(Question("PRoduct_4", 589))
        list.add(Question("PRoduct_5", 589))
        list.add(Question("PRoduct_6", 589))
        list.add(Question("PRoduct_6", 589))
        list.add(Question("PRoduct_6", 589))
        list.add(Question("PRoduct_6", 589))
        list.add(Question("PRoduct_6", 589))
        list.add(Question("PRoduct_6", 589))
        list.add(Question("PRoduct_6", 589))
        list.add(Question("PRoduct_6", 589))


        return  list

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
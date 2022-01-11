package com.example.nurserygardenandroid.ui.fragment.cart

import android.content.Intent
import android.net.Network
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.nurserygardenandroid.databinding.ProfileFragmentBinding
import com.example.nurserygardenandroid.model.user.UserAuth
import com.example.nurserygardenandroid.network.NetworkLayer
import com.example.nurserygardenandroid.sharedpreference.SharedPref
import com.example.nurserygardenandroid.ui.activity.*
import com.example.nurserygardenandroid.utils.Constants
import com.example.nurserygardenandroid.utils.ErrorUtils
import kotlinx.android.synthetic.main.profile_fragment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {


    private var _binding: ProfileFragmentBinding? = null

    private val binding get() = _binding!!
    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        root.profile_name.setOnClickListener{
            activity?.intent = Intent(activity, EditProfileActivity::class.java)
            startActivity(activity?.intent)
        }

        root.cart.setOnClickListener{
            activity?.intent = Intent(activity, CartActivity::class.java)
            startActivity(activity?.intent)
        }

        root.orders.setOnClickListener{
            activity?.intent = Intent(activity, OrderActivity::class.java)
            startActivity(activity?.intent)
        }

        root.profile_details.setOnClickListener {
            activity?.intent = Intent(activity, EditProfileActivity::class.java)
            startActivity(activity?.intent)
        }

        root.address.setOnClickListener {
            activity?.intent = Intent(activity, AddressActivity::class.java)
            startActivity(activity?.intent)
        }

        root.wishlist.setOnClickListener {
            activity?.intent = Intent(activity, WishListActivity::class.java)
            startActivity(activity?.intent)
        }



        root.btn_logout.setOnClickListener {

            NetworkLayer.apiClient.logoutUser(
                Constants.BEARER + SharedPref(requireContext())
                    .getAuthToken()
            ).enqueue(object : Callback<UserAuth> {
                override fun onResponse(call: Call<UserAuth>, response: Response<UserAuth>) {
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "Logout Successfully", Toast.LENGTH_SHORT)
                            .show()
                        SharedPref(requireContext()).setAuthToken("")
                        activity?.intent = Intent(activity, MainActivity::class.java)
                        activity?.intent?.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(activity?.intent)
                        activity?.finish()
                    } else {
                        Toast.makeText(requireContext(), "Can't logout", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UserAuth>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }

            })

        }

        return root



    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        _binding?.root

        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()
        _binding?.root?.profile_name?.text = SharedPref(requireContext()).getUserName()
        _binding?.root?.profile_email?.text = SharedPref(requireContext()).getEmail()
        _binding?.root?.profile_phone?.text = SharedPref(requireContext()).getMobile()
    }
}
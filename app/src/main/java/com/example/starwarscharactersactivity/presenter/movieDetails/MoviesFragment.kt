package com.example.starwarscharactersactivity.presenter.movieDetails

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwarscharactersactivity.R
import com.example.starwarscharactersactivity.databinding.FragmentMoviesBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MoviesFragment : Fragment() {
    private lateinit var binding: FragmentMoviesBinding
    private val viewModel by lazy {
        ViewModelProvider(this)[MoviesViewModel::class.java]
    }
    private val number by lazy {
        arguments?.getInt(NUMBER) ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setDataFromFragment(number)
        initMoviesAdapter()
    }

    private fun initMoviesAdapter() {
        val states = viewModel.states
        val recyclerView = binding.rvCharacters
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        viewModel.states.observe(viewLifecycleOwner) {
            if (it.error != null) {
                Toast.makeText(requireContext(), getString(R.string.try_again), Toast.LENGTH_SHORT).show()
                removeCurrentFragment()
            } else {
                recyclerView.adapter = states.value?.let { MoviesAdapter(it) }
            }
        }
    }

    companion object {
        const val NUMBER = "number"
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(requireContext(), ConnectivityManager::class.java)
        val activeNetworkInfo = connectivityManager?.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    private fun removeCurrentFragment() {
        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.remove(this)
        transaction.commit()
    }


}
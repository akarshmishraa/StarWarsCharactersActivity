package com.example.starwarscharactersactivity.presenter.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.starwarscharactersactivity.R
import com.example.starwarscharactersactivity.databinding.FragmentCharactersBinding
import com.example.starwarscharactersactivity.presenter.home.filters.FilterBottomsheet
import com.example.starwarscharactersactivity.presenter.movieDetails.MoviesFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CharactersFragment : Fragment(), ApiCallback{
    private lateinit var binding: FragmentCharactersBinding
    private val viewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }
    private var isFilterApplied = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_characters, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCharactersAdapter()
        initListeners()
    }

    private fun initListeners() {
        binding.btnFilter.setOnClickListener {
            val bottomSheetFragment = FilterBottomsheet()
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }
    }

    private fun initCharactersAdapter() {
        val states = viewModel.states
        val recyclerView = binding.rvCharacters
        val layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.layoutManager = layoutManager
        viewModel.states.observe(viewLifecycleOwner) {
            recyclerView.adapter = states.value?.let { CharacterAdapter(it, isFilterApplied, this) }
        }
    }

    override fun paginationRequired() {
        viewModel.onEvents(HomeScreenEvents.isRefreshing)
    }

    override fun characterClicked(position: Int) {
        openMoviesFragment(position)
    }

    private fun openMoviesFragment(position: Int) {
        val moviesFragment = MoviesFragment()
        val transaction = parentFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putInt(MoviesFragment.NUMBER, position)
        moviesFragment.arguments = bundle
        transaction.replace(R.id.flContainer, moviesFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun filteredOptions(category: String, query: String) {
        isFilterApplied = !(category.isEmpty() && query.isEmpty())
        viewModel.onEvents(HomeScreenEvents.OnFilterApplied(category, query))
    }
}
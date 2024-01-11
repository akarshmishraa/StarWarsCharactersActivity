package com.example.starwarscharactersactivity.presenter.home.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwarscharactersactivity.R
import com.example.starwarscharactersactivity.databinding.FragmentFilterBottomsheetBinding
import com.example.starwarscharactersactivity.domain.utils.Resource
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FilterBottomsheet : BottomSheetDialogFragment() {
    private lateinit var binding : FragmentFilterBottomsheetBinding
    private val viewModel : FilterViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_filter_bottomsheet, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        val recyclerView = binding.rvItemsFilterBottomSheet
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        binding.tvHairColor.setOnClickListener{
            lifecycleScope.launch {
                recyclerView.adapter = FilterItemsAdapter(getHairColor())
            }
        }
        binding.tvGender.setOnClickListener{
            recyclerView.adapter = FilterItemsAdapter(listOf("Male", "Female"))
        }
        binding.tvEyeColor.setOnClickListener{
            lifecycleScope.launch {
                recyclerView.adapter = FilterItemsAdapter(getEyeColor())
            }
        }
    }

    private suspend fun getHairColor() : List<String> {
        return when(viewModel.getHairColors()){
            is Resource.Success -> {
                viewModel.getHairColors().data ?: emptyList()
            }
            else -> {
                emptyList()
            }
        }
    }
    private suspend fun getEyeColor() : List<String> {
        return when(viewModel.getEyeColors()){
            is Resource.Success -> {
                viewModel.getEyeColors().data ?: emptyList()
            }
            else -> {
                emptyList()
            }
        }
    }

}
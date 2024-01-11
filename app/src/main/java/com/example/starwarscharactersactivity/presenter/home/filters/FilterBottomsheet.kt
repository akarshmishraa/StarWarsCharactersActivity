package com.example.starwarscharactersactivity.presenter.home.filters

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwarscharactersactivity.R
import com.example.starwarscharactersactivity.databinding.FragmentFilterBottomsheetBinding
import com.example.starwarscharactersactivity.domain.utils.Resource
import com.example.starwarscharactersactivity.presenter.home.HomeScreenEvents
import com.example.starwarscharactersactivity.presenter.home.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FilterBottomsheet : BottomSheetDialogFragment(), OptionSelected {
    private lateinit var binding: FragmentFilterBottomsheetBinding
    private val viewModel: FilterViewModel by viewModels()
    private lateinit var filterOptions: FilterOptions
    private var category = ""
    private var query = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_filter_bottomsheet,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFilterAdapter()
        initListeners()
    }

    private fun initListeners() {
        binding.btnApply.setOnClickListener {
            filterOptions.filteredOptions(category, query)
            dismiss()
        }
        binding.btnReset.setOnClickListener {
            filterOptions.filteredOptions("", "")
            dismiss()
        }
    }

    private fun initFilterAdapter() {
        val recyclerView = binding.rvItemsFilterBottomSheet
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        binding.tvHairColor.setOnClickListener {
            category = "HAIR_COLOR"
            lifecycleScope.launch {
                recyclerView.adapter = FilterItemsAdapter(getHairColor(), this@FilterBottomsheet)
            }
        }
        binding.tvGender.setOnClickListener {
            category = "GENDER"
            recyclerView.adapter =
                FilterItemsAdapter(listOf("Male", "Female"), this@FilterBottomsheet)
        }
        binding.tvEyeColor.setOnClickListener {
            category = "EYE_COLOR"
            lifecycleScope.launch {
                recyclerView.adapter = FilterItemsAdapter(getEyeColor(), this@FilterBottomsheet)
            }
        }
    }

    private suspend fun getHairColor(): List<String> {
        return when (viewModel.getHairColors()) {
            is Resource.Success -> {
                viewModel.getHairColors().data ?: emptyList()
            }

            else -> {
                emptyList()
            }
        }
    }

    private suspend fun getEyeColor(): List<String> {
        return when (viewModel.getEyeColors()) {
            is Resource.Success -> {
                viewModel.getEyeColors().data ?: emptyList()
            }

            else -> {
                emptyList()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        filterOptions = context as FilterOptions
    }

    override fun optionSelected(query: String) {
        this.query = query
    }

    interface FilterOptions {
        fun filteredOptions(
            category: String,
            query: String,
        )
    }

}
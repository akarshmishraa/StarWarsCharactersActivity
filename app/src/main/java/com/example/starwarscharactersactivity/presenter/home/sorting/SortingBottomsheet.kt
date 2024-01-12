package com.example.starwarscharactersactivity.presenter.home.sorting

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.starwarscharactersactivity.R
import com.example.starwarscharactersactivity.databinding.FragmentSortingBottomsheetBinding
import com.example.starwarscharactersactivity.presenter.home.filters.FilterBottomsheet
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SortingBottomsheet : BottomSheetDialogFragment() {
    lateinit var binding: FragmentSortingBottomsheetBinding
    private lateinit var sortingOptions: SortingOptions
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_sorting_bottomsheet,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        var sortingOptionSelected = ""
        binding.rbSortMassLH.setOnClickListener{
            sortingOptionSelected = "massLH"
        }
        binding.rbSortMassHL.setOnClickListener{
            sortingOptionSelected = "massHL"
        }
        binding.rbSortHeightLH.setOnClickListener{
            sortingOptionSelected = "heightLH"
        }
        binding.rbSortHeightHL.setOnClickListener{
            sortingOptionSelected = "heightHL"
        }
        binding.cvApply.setOnClickListener{
            sortingOptions.sortingOptions(sortingOptionSelected)
            dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sortingOptions = context as SortingOptions
    }
    interface SortingOptions {
        fun sortingOptions(
            query: String,
        )
    }
}
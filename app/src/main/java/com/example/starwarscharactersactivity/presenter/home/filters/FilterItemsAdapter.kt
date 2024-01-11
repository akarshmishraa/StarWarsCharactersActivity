package com.example.starwarscharactersactivity.presenter.home.filters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarscharactersactivity.R
import com.example.starwarscharactersactivity.databinding.ItemsFilterBinding

class FilterItemsAdapter(
    private val list: List<String>,
    private val optionSelected: OptionSelected,
) :
    RecyclerView.Adapter<FilterItemsAdapter.ViewHolder>() {
    class ViewHolder(itemView: ItemsFilterBinding) : RecyclerView.ViewHolder(itemView.root) {
        val textView = itemView.tvItemsRv
        val checkBox = itemView.checkBox
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.items_filter, parent, false)
        return ViewHolder(ItemsFilterBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = list[position]
        holder.checkBox.setOnClickListener {
            if (holder.checkBox.isChecked) {
                optionSelected.optionSelected(holder.textView.text.toString())
            } else {
                optionSelected.optionSelected("")
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

interface OptionSelected {
    fun optionSelected(query: String)
}
package com.example.starwars.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.starwars.R
import com.example.starwars.model.People
import com.example.starwars.model.Results
import com.example.starwars.presentation.view.Detalhes
import com.example.starwars.presentation.view.Inicio
import kotlinx.android.synthetic.main.custom_row_people.view.*
import java.util.*

class PeopleAdapter: RecyclerView.Adapter<PeopleAdapter.MyViewHolder>(), Filterable {

    companion object{
        var nameCompanion: String = ""
        var heightCompanion: String = ""
        var genderCompanion: String = ""
        var massCompanion: String = ""
        var hair_colorCompanion: String = ""
        var skin_colorCompanion: String = ""
        var eye_colorCompanion: String = ""
        var birth_yearCompanion: String = ""

    }

    // Declara uma Lista vazia
    private var myListResults = emptyList<Results>()

    //Lista Auxiliar
    private var listFilter = emptyList<Results>()

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row_people, parent, false))
    }

    override fun getItemCount(): Int {
        return listFilter.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val context = holder.itemView.context
        val currentItem = listFilter[position]
        holder.itemView.textName.text = "Name: "+currentItem.name
        holder.itemView.textHeight.text = "Height: "+currentItem.height
        holder.itemView.textGender.text = "Gender: "+currentItem.gender
        holder.itemView.textMass.text = "Mass: "+currentItem.mass

        holder.itemView.custom_row.setOnClickListener {
            nameCompanion = currentItem.name
            heightCompanion = currentItem.height
            genderCompanion = currentItem.gender
            massCompanion = currentItem.mass
            hair_colorCompanion = currentItem.hair_color
            skin_colorCompanion = currentItem.skin_color
            eye_colorCompanion = currentItem.eye_color
            birth_yearCompanion = currentItem.birth_year

            context.startActivity(Intent(holder.itemView.context, Detalhes::class.java))

        }
    }

    fun setData(newList: List<Results>){
        this.myListResults = newList
        listFilter = myListResults
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charSearch = p0.toString()
                if (charSearch.isEmpty()) {
                    listFilter = myListResults
                } else {
                    val resultList = mutableListOf<Results>()
                    for (row in myListResults) {
                        if (row.name.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    listFilter = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = listFilter
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                listFilter = p1?.values as MutableList<Results>
                notifyDataSetChanged()
            }
        }
    }

}
package com.lalit.devtrackr.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lalit.devtrackr.R
import com.lalit.devtrackr.data.local.Entity.DsaProblem

class DsaAdapter : RecyclerView.Adapter<DsaAdapter.ViewHolder>() {

    private var problemList = listOf<DsaProblem>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.tvTitle)
        val difficulty = itemView.findViewById<TextView>(R.id.tvDifficulty)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_problem, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = problemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val problem = problemList[position]
        holder.title.text = problem.title
        holder.difficulty.text = problem.difficulty
    }

    fun setData(list: List<DsaProblem>) {
        problemList = list
        notifyDataSetChanged()
    }
}

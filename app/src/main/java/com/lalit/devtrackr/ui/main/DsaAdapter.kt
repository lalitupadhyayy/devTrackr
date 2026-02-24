package com.lalit.devtrackr.ui.main

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lalit.devtrackr.R
import com.lalit.devtrackr.data.local.Entity.DsaProblem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DsaAdapter : RecyclerView.Adapter<DsaAdapter.ViewHolder>() {

    private var problemList = listOf<DsaProblem>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.tvTitle)
        val difficulty = itemView.findViewById<TextView>(R.id.tvDifficulty)
        val platform = itemView.findViewById<TextView>(R.id.tvPlatform)
        val complexity = itemView.findViewById<TextView>(R.id.tvComplexity)
        val date = itemView.findViewById<TextView>(R.id.tvDate)
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
        holder.platform.text = problem.platform
        holder.difficulty.text = "Difficulty: ${problem.difficulty}"
        holder.complexity.text = "Complexity: ${problem.timecomplexity}"

        val formattedDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            .format(Date(problem.datesolved))

        holder.date.text = "Solved: $formattedDate"

        when (problem.difficulty) {
            "Easy" -> holder.difficulty.setTextColor(Color.parseColor("#2E7D32"))
            "Medium" -> holder.difficulty.setTextColor(Color.parseColor("#F9A825"))
            "Hard" -> holder.difficulty.setTextColor(Color.parseColor("#C62828"))
        }
    }


    fun setData(list: List<DsaProblem>) {
        problemList = list
        notifyDataSetChanged()
    }

    fun getProblemAt(position: Int): DsaProblem {
        return problemList[position]
    }
}

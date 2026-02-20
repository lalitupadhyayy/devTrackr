package com.lalit.devtrackr.ui.problem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.lalit.devtrackr.R
import com.lalit.devtrackr.data.local.Entity.DsaProblem
import com.lalit.devtrackr.databinding.FragmentProblemBinding
import com.lalit.devtrackr.ui.main.DsaAdapter
import com.lalit.devtrackr.viewmodel.DsaViewModel

class ProblemFragment : Fragment(R.layout.fragment_problem) {

    private var _binding: FragmentProblemBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DsaViewModel
    private lateinit var adapter: DsaAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProblemBinding.bind(view)

        // ViewModel
        viewModel = ViewModelProvider(this)[DsaViewModel::class.java]

        binding.fabAdd.setOnClickListener {
            showAddProblemDialog()
        }
    }

    private fun showAddProblemDialog() {

        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_add_problem, null)

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Add Problem")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                // Later we will extract data
                val name =
                    dialogView.findViewById<EditText>(R.id.etProblemName).text.toString()
                val platform =
                    dialogView.findViewById<EditText>(R.id.etPlatform).text.toString()
                val difficulty =
                    dialogView.findViewById<EditText>(R.id.etDifficulty).text.toString()
                val complexity =
                    dialogView.findViewById<EditText>(R.id.etComplexity).text.toString()

                val problem = DsaProblem(
                    title = name,
                    platform = platform,
                    difficulty = difficulty,
                    timecomplexity = complexity,
                    datesolved = System.currentTimeMillis()
                )

                viewModel.insert(problem)

//                Toast.makeText(, "saved", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

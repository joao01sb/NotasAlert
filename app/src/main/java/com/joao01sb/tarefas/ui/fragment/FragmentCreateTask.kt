package com.joao01sb.tarefas.ui.fragment

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.joao01sb.tarefas.databinding.FragmentCreateTaskBinding
import com.joao01sb.tarefas.ui.dialog.DataPickerDialog

class FragmentCreateTask : Fragment() {

    private lateinit var binding: FragmentCreateTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().actionBar?.setDisplayHomeAsUpEnabled(true)
        binding.dataTarefa.setOnClickListener {
            // criando o dialog
            val dataPickerDialog = DataPickerDialog()
            val supportfragmentManager = requireActivity().supportFragmentManager

            // we have implementation setFragmentResultListiner
            supportfragmentManager.setFragmentResultListener(
                "REQUEST_CODE", viewLifecycleOwner
            ) { requestKey, bundle ->
                if (requestKey == "REQUEST_KEY") {
                    val args = bundle.getString("SELECTED_DATE")
                    binding.dataTarefa.text = args
                }
            }
        }
    }

}
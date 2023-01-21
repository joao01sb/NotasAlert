package com.joao01sb.tarefas.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.joao01sb.tarefas.databinding.FragmentDetailsNoteBinding
import com.joao01sb.tarefas.model.Tarefa

class FragmentDetailsNote : Fragment() {

    private lateinit var binding: FragmentDetailsNoteBinding
    private val argsNote: FragmentDetailsNoteArgs? by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().actionBar?.setDisplayHomeAsUpEnabled(true)
        argsNote?.note?.let { note ->
            configViewNoteDetails(note)
        } ?: Toast.makeText(requireContext(), "Tarefa invalida!", Toast.LENGTH_SHORT).show()
    }

    private fun configViewNoteDetails(note: Tarefa?) {
        binding.apply {
            nomeNoteDetails.text = note?.titulo
            descricaoNoteDetails.text = note?.conteudo
            dataNoteDetails.text = note?.data
        }
    }

    private fun goEditeNote() {
        val direction = FragmentDetailsNoteDirections.goNoteDetailsForNoteEdite(argsNote?.note)
        findNavController().navigate(direction)
    }
}
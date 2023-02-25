package com.joao01sb.tarefas.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.joao01sb.tarefas.databinding.FragmentDetalhesTarefaBinding
import com.joao01sb.tarefas.domain.viewModel.TarefaViewModel
import com.joao01sb.tarefas.extra.Util.formatDate
import com.joao01sb.tarefas.model.Tarefa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetalhesTarefaFragment : Fragment() {

    private lateinit var binding: FragmentDetalhesTarefaBinding
    private val argsNote: DetalhesTarefaFragmentArgs? by navArgs()
    private val tarefaViewModel: TarefaViewModel by viewModel { parametersOf(argsNote?.note) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetalhesTarefaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        argsNote?.note?.let { note ->
            configViewNoteDetails(note)
        } ?: Toast.makeText(requireContext(), "Tarefa invalida!", Toast.LENGTH_SHORT).show()
        binding.iconeApagarDetalhes.setOnClickListener {
            deleteNote()
        }
        binding.iconeVoltarDetalhes.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun configViewNoteDetails(note: Tarefa?) {
        binding.apply {
            nomeNoteDetails.text = note?.titulo
            descricaoNoteDetails.text = note?.conteudo
            dataNoteDetails.text = note?.data?.formatDate()
        }
    }

    private fun deleteNote() {
        lifecycleScope.launch(Dispatchers.IO) {
            tarefaViewModel.deletarTarefa(argsNote?.note)
        }
        findNavController().popBackStack()
    }

}
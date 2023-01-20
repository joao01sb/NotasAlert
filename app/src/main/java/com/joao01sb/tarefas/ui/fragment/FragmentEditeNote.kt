package com.joao01sb.tarefas.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.joao01sb.tarefas.databinding.FragmentEditeNoteBinding
import com.joao01sb.tarefas.domain.viewModel.TarefaViewModel
import com.joao01sb.tarefas.extra.Util.formatDate
import com.joao01sb.tarefas.model.Tarefa
import com.joao01sb.tarefas.ui.dialog.DataPickerDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class FragmentEditeNote : Fragment() {

    private lateinit var binding: FragmentEditeNoteBinding
    private val args: FragmentEditeNoteArgs by navArgs()
    private val tarefaViewModel: TarefaViewModel by viewModel { parametersOf(args.tarefa) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditeNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isEditerOrNewNote()
        binding.dataTarefa.setOnClickListener {
            dialogPickData()
        }
        binding.salvaTarefaButtom.setOnClickListener {
            val novaTarefa = Tarefa(
                id = null,
                titulo = binding.nomeDaTarefaValor.text.toString(),
                conteudo = binding.descricaoDaTarefaValor.text.toString(),
                data = binding.dataTarefa.text.toString().formatDate()
            )
            lifecycleScope.launch(Dispatchers.IO) {
                tarefaViewModel.salvarTarefa(novaTarefa)
            }
        }
    }

    private fun isEditerOrNewNote() {
        val tarefa = tarefaViewModel.tarefa
        if (tarefa != null) {
            binding.descricaoDaTarefaValor.setText(tarefa.conteudo)
            binding.nomeDaTarefaValor.setText(tarefa.titulo)
            binding.dataTarefa.text = tarefa.data
        }
    }

    private fun dialogPickData() {
        val dataPickerDialog = DataPickerDialog()
        val supportfragmentManager = requireActivity().supportFragmentManager
        supportfragmentManager.setFragmentResultListener(
            "REQUEST_CODE", viewLifecycleOwner
        ) { requestKey, bundle ->
            if (requestKey == "REQUEST_CODE") {
                val args = bundle.getString("SELECTED_DATE")
                binding.dataTarefa.text = args?.formatDate()
            }
        }
        dataPickerDialog.show(supportfragmentManager, "DatePickerFragment")
    }

}
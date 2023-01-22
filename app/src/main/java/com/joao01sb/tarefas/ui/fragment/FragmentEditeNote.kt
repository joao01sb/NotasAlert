package com.joao01sb.tarefas.ui.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.joao01sb.tarefas.R
import com.joao01sb.tarefas.databinding.FragmentEditeNoteBinding
import com.joao01sb.tarefas.domain.viewModel.TarefaViewModel
import com.joao01sb.tarefas.extra.Util.formatDate
import com.joao01sb.tarefas.model.Tarefa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.*


class FragmentEditeNote : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private lateinit var binding: FragmentEditeNoteBinding
    private val args: FragmentEditeNoteArgs by navArgs()
    private val tarefaViewModel: TarefaViewModel by viewModel { parametersOf(args.tarefa) }
    private val calendar = Calendar.getInstance()
    private lateinit var dataSelecionada: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditeNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isEditeOrNewNote()
        binding.dataTarefa.setOnClickListener {
            configurarData()
        }
        binding.salvaTarefaButtom.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        if (this::dataSelecionada.isInitialized) {
            if (binding.nomeDaTarefaValor.text.isNullOrBlank()) {
                Toast.makeText(
                    requireContext(),
                    "Informe um titulo para a tarefa",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val newNote = Tarefa(
                    id = null,
                    titulo = binding.nomeDaTarefaValor.text.toString(),
                    conteudo = binding.descricaoDaTarefaValor.text.toString(),
                    data = dataSelecionada
                )
                try {
                    lifecycleScope.launch(Dispatchers.IO) {
                        tarefaViewModel.salvarTarefa(newNote)
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        requireContext(),
                        "Erro ao salvar note verifique todos os campos e tente novamente",
                        Toast.LENGTH_LONG
                    ).show()
                }
                findNavController().popBackStack()
            }
        } else {
            Toast.makeText(
                requireContext(),
                "Informe uma data para a tarefa",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun isEditeOrNewNote() {
        args.tarefa?.let { note ->
            binding.nomeDaTarefaValor.setText(note.titulo)
            binding.descricaoDaTarefaValor.setText(note.conteudo)
            binding.dataTarefa.text = note.data.formatDate()
        }
    }

    fun configurarData() {
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        DatePickerDialog(requireActivity(), this, year, month, day).show()
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        calendar.set(Calendar.DAY_OF_MONTH, p3)
        calendar.set(Calendar.MONTH, p2)
        calendar.set(Calendar.YEAR, p1)

        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minutes = calendar.get(Calendar.MINUTE)
        TimePickerDialog(requireContext(), this, hour, minutes, true).show()
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        calendar.set(Calendar.HOUR_OF_DAY, p1)
        calendar.set(Calendar.MINUTE, p2)
        dataSelecionada =
            SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(calendar.time)
        binding.dataTarefa.text = dataSelecionada.formatDate()
    }

}
package com.joao01sb.tarefas.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.joao01sb.tarefas.R
import com.joao01sb.tarefas.databinding.FragmentDetailsNoteBinding
import com.joao01sb.tarefas.domain.viewModel.TarefaViewModel
import com.joao01sb.tarefas.extra.Util.formatDate
import com.joao01sb.tarefas.model.Tarefa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FragmentDetailsNote : Fragment() {

    private lateinit var binding: FragmentDetailsNoteBinding
    private val argsNote: FragmentDetailsNoteArgs? by navArgs()
    private val tarefaViewModel: TarefaViewModel by viewModel { parametersOf(argsNote?.note) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().actionBar?.setDisplayHomeAsUpEnabled(true)
        requireActivity().actionBar?.title = " Detalhes da Nota"
        argsNote?.note?.let { note ->
            configViewNoteDetails(note)
        } ?: Toast.makeText(requireContext(), "Tarefa invalida!", Toast.LENGTH_SHORT).show()
        setupMenu()
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

    private fun goEditeNote() {
        val direction = FragmentDetailsNoteDirections.goNoteDetailsForNoteEdite(argsNote?.note)
        findNavController().navigate(direction)
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_details_note, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.menu_detalhes_produto_altera -> goEditeNote()
                    R.id.menu_detalhes_produto_remove -> deleteNote()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


}
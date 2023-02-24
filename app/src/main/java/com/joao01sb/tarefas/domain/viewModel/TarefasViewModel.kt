package com.joao01sb.tarefas.domain.viewModel

import androidx.lifecycle.ViewModel
import com.joao01sb.tarefas.domain.repository.TarefaRepository
import com.joao01sb.tarefas.model.Tarefa

class TarefasViewModel(
    val repository: TarefaRepository
) : ViewModel() {

    suspend fun tarefaslista() : List<Tarefa>?  = repository.buscarTarefas()

    suspend fun salvarTarefa(tarefa: Tarefa?) {
        if (tarefa != null) {
            repository.salvarTarefa(tarefa)
            return
        }
        throw java.lang.Exception("Tarefa não selecionada")
    }
}
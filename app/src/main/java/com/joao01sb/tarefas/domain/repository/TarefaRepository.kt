package com.joao01sb.tarefas.domain.repository

import androidx.lifecycle.LiveData
import com.joao01sb.tarefas.data.dao.TarefaDAO
import com.joao01sb.tarefas.model.Tarefa

class TarefaRepository(
    private val dao: TarefaDAO
) {
    suspend fun salvarTarefa (tarefa: Tarefa) {
        dao.salva(tarefa)
    }

    suspend fun buscarTarefas () : List<Tarefa> {
        return dao.buscarTodas()
    }

    suspend fun deletarTarefa(tarefa: Tarefa) {
        dao.remove(tarefa)
    }
}
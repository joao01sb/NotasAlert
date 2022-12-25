package com.joao01sb.tarefas.domain.repository

import androidx.lifecycle.LiveData
import com.joao01sb.tarefas.data.dao.TarefaDAO
import com.joao01sb.tarefas.model.Tarefa

class TarefaRepository(
    val dao: TarefaDAO
) {
    suspend fun salvarTarefa (tarefa: Tarefa) {
        dao.salva(tarefa)
    }

    suspend fun buscarTarefas () : LiveData<List<Tarefa>> {
        return dao.buscarTodas()
    }

    suspend fun buscarTerafaId(id: Long) : Tarefa? {
        return dao.buscaPorId(id).value
    }

    suspend fun deletarTarefa(tarefa: Tarefa) {
        dao.remove(tarefa)
    }

    suspend fun editarTarefa(id: Long, tarefa: Tarefa) {
        dao.editarTarefa(id, tarefa)
    }
}
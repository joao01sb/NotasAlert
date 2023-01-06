package com.joao01sb.tarefas.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.joao01sb.tarefas.model.Tarefa

@Dao
interface TarefaDAO {

    @Query("SELECT * FROM Tarefa")
     fun buscarTodas(): LiveData<List<Tarefa>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun salva(tarefa: Tarefa)

    @Delete
     fun remove(tarefa: Tarefa)

    @Query("SELECT * FROM Tarefa WHERE id = :id")
    fun buscaPorId(id: Long): LiveData<Tarefa?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun salva(tarefas: List<Tarefa>)

    @Update()
     fun editarTarefa(tarefa: Tarefa)

}
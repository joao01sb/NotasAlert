package com.joao01sb.tarefas.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.joao01sb.tarefas.model.Tarefa

@Dao
interface TarefaDAO {

    @Query("SELECT * FROM Tarefa ORDER BY id DESC")
    suspend fun buscarTodas(): LiveData<List<Tarefa>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salva(tarefa: Tarefa)

    @Delete
    suspend fun remove(tarefa: Tarefa)

    @Query("SELECT * FROM Tarefa WHERE id = :id")
    suspend fun buscaPorId(id: Long): LiveData<Tarefa?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salva(tarefas: List<Tarefa>)

    @Update()
    suspend fun editarTarefa(id: Long, tarefa: Tarefa)

}
package com.joao01sb.tarefas.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tarefa (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val titulo: String,
    val conteudo: String
    )
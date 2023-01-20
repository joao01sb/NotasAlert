package com.joao01sb.tarefas.extra

object Util {

    fun String.formatDate() : String {
        return this.replace("-", "/", true)
    }
}
package hu.bme.spacedumpling.worktimemanager.logic.models

import hu.bme.spacedumpling.worktimemanager.domain.dto.TaskDto

data class TasksByProject (
    val id: Int,
    val name: String?,
    val tasks: List<Task>?
        ){
    override fun toString(): String{
        return name ?: "id: $id"
    }
}
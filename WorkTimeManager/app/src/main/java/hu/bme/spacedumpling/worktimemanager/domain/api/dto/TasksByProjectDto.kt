package hu.bme.spacedumpling.worktimemanager.domain.api.dto

import hu.bme.spacedumpling.worktimemanager.logic.models.TasksByProject

data class TasksByProjectDto (
   val id: Int,
   val name: String?,
   val tasks: List<TaskDto>?
)

fun TasksByProjectDto.mapToModelTasksByProject(): TasksByProject {
    return TasksByProject(
        id = this.id, name = this.name, tasks = this.tasks?.map { it.toModelTask() }
    )
}
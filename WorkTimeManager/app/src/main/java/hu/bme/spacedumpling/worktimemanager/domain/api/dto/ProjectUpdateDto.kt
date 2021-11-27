package hu.bme.spacedumpling.worktimemanager.domain.api.dto

import hu.bme.spacedumpling.worktimemanager.logic.models.Project
import hu.bme.spacedumpling.worktimemanager.logic.models.Task
import hu.bme.spacedumpling.worktimemanager.logic.models.User

data class ProjectUpdateDto(
    val id: Int,
    val description: String?,
    val leaders: List<SimpleUserDto>?,
    val tasks: List<TaskDto>?,
)

fun Project.toDtoProjectUpdateDto(): ProjectUpdateDto {
    return ProjectUpdateDto(
        id = this.id,
        description = this.description,
        leaders = this.leaders?.map { it.toDtoUserDto() },
        tasks = this.tasks?.map { it.toDtoTaskDto() }

    )
}

fun Task.toDtoTaskDto(): TaskDto{
    return TaskDto(
        id = this.id, title = this.title, description = this.description
    )
}

fun User.toDtoUserDto(): SimpleUserDto{
    return SimpleUserDto(
        name = this.name, id = this.id
    )
}
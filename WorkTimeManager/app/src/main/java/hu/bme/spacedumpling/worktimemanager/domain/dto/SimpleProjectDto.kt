package hu.bme.spacedumpling.worktimemanager.domain.dto

import hu.bme.spacedumpling.worktimemanager.logic.models.Project

data class SimpleProjectDto (
    val id: Int,
    val name: String?,
    val description: String?,
    val sumTasks: Int?,
    val leaders: List<String>?
)

fun SimpleProjectDto.toModelProject() :Project{
    return Project(
        id = this.id,
        title = this.name,
        description = this.description,
        sumTasks = this.sumTasks,
        leaders = this.leaders,
    )
}
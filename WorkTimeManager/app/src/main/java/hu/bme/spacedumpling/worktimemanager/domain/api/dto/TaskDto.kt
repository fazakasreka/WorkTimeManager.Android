package hu.bme.spacedumpling.worktimemanager.domain.api.dto

import com.squareup.moshi.Json
import hu.bme.spacedumpling.worktimemanager.logic.models.Task

class TaskDto (
    val id: Int,
    @Json(name = "name")
    val title: String?,
    val description: String?
)

fun TaskDto.toModelTask(): Task{
    return Task(
        title = this.title,
        id = this.id,
        description = this.description
    )
}
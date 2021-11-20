package hu.bme.spacedumpling.worktimemanager.logic.models


data class Project (
    val id: Int,
    val title: String?,
    val description: String?,
    val sumTasks:Int?,
    val leaders: List<User>?,
//for details
    val allUsers: List<User>? = null,
    val tasks: List<Task>? = null,
)
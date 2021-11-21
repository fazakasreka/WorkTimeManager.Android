package hu.bme.spacedumpling.worktimemanager.logic.models


data class Project (
    val id: Int,
    val title: String? = null,
    val description: String? = null,
    val sumTasks:Int? = null,
    val leaders: List<User>? = null,
//for details
    val allUsers: List<User>? = null,
    val tasks: List<Task>? = null,
)
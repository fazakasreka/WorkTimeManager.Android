package hu.bme.spacedumpling.worktimemanager.logic.models

data class TasksByProject (
    val id: Int,
    val name: String?,
    val tasks: List<Task>?
        ){
    override fun toString(): String{
        return name ?: "id: $id"
    }
}
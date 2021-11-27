package hu.bme.spacedumpling.worktimemanager.logic.models

data class Task(
    val title: String?,
    val id: Int,
    val description: String?
){
    override fun toString(): String {
        return title ?: "id: $id"
    }
}
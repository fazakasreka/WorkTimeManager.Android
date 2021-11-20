package hu.bme.spacedumpling.worktimemanager.logic.models

import java.io.FileDescriptor

data class Project (
    val id: Int,
    val title: String?,
    val description: String?,
    val sumTasks:Int?,
    val leaders: List<String>?
)
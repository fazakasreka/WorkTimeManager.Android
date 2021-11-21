package hu.bme.spacedumpling.worktimemanager.domain.dto

import com.squareup.moshi.Json
import hu.bme.spacedumpling.worktimemanager.logic.models.Project
import hu.bme.spacedumpling.worktimemanager.logic.models.Task
import hu.bme.spacedumpling.worktimemanager.logic.models.User

data class DetailedProjectDto (
    val id: Int,
    val name: String?,
    val description: String?,
    val sumTasks: Int?,
    val leaders: List<SimpleUserDto>?,
    val allUsers: List<SimpleUserDto>?,
    val tasks: List<TaskDto>?,
)

fun DetailedProjectDto.toModelProject(): Project {
    return Project(
        id = this.id,
        title = this.name,
        description = this.description,
        sumTasks = this.sumTasks,
        leaders = this.leaders?.map { it.toModelUser()},
        allUsers = this.allUsers?.map { it.toModelUser()},
        tasks = this.tasks?.map { it.toModelTask()}
    )
}


//fun DetailedProjectDto.toModelProject(): Project {
//    return Project(
//        id = 1,
//        title = "MyProject",
//        description = "Coca-Cola, or Coke, is a carbonated soft drink manufactured by The Coca-Cola Company. Originally marketed as a temperance drink and intended as a patent medicine, it was invented in the late 19th century by John Stith Pemberton and was bought out by businessman Asa Griggs Candler, whose marketing tactics led Coca-Cola to its dominance of the world soft-drink market throughout the 20th century.[1] ",
//        sumTasks = 3,
//        leaders = listOf(
//            User("Berni", 1),
//            User("Reka", 2)
//        ),
//        allUsers = listOf(
//            User("Berni", 1),
//            User("Reka", 2),
//            User("Banczik", 3)
//        ),
//        tasks = listOf(
//            Task("Do shit 1 sdnsdfghjyesfd sdfbnhmj", 1, "Lol lol lol lol lol"),
//            Task("Do shit 2 dghmj,k.lhfd ht n", 2,"Lol lol lol lol lol"),
//            Task("Do shit 3   dfhghmj,", 4,"Lol lol lol lol lol"),
//        ),
//    )
//}
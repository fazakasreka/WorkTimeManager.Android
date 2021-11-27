package hu.bme.spacedumpling.worktimemanager.domain.api.dto

import hu.bme.spacedumpling.worktimemanager.logic.models.Project

data class SimpleProjectDto (
    val id: Int,
    val name: String?,
    val description: String?,
    val sumHours: String?,
    val leaders: List<SimpleUserDto>?
)


fun SimpleProjectDto.toModelProject() :Project{
    return Project(
        id = this.id,
        title = this.name,
        description = this.description,
        sumHours = this.sumHours,
        leaders = this.leaders?.map {it.toModelUser()},
    )
}

//fun SimpleProjectDto.toModelProject() :Project{
//    return Project(
//        id = 1,
//        title = "MyProject",
//        description = "Coca-Cola, or Coke, is a carbonated soft drink manufactured by The Coca-Cola Company. Originally marketed as a temperance drink and intended as a patent medicine, it was invented in the late 19th century by John Stith Pemberton and was bought out by businessman Asa Griggs Candler, whose marketing tactics led Coca-Cola to its dominance of the world soft-drink market throughout the 20th century.[1] ",
//        sumTasks = 3,
//        leaders = listOf(
//            User("Berni", 1),
//            User("Reka", 2)
//        )
//    )
//}
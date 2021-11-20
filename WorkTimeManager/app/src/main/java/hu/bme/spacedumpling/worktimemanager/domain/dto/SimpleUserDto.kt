package hu.bme.spacedumpling.worktimemanager.domain.dto

import hu.bme.spacedumpling.worktimemanager.logic.models.User

data class SimpleUserDto (
    val name: String,
    val id: Int
)

fun SimpleUserDto.toModelUser() : User {
    return User(
        name = this.name,
        id = this.id
    )

}
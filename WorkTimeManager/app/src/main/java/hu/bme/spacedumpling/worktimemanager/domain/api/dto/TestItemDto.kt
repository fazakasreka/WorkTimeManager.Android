package hu.bme.spacedumpling.worktimemanager.domain.api.dto

import hu.bme.spacedumpling.worktimemanager.logic.models.TestItem

data class TestItemDto (
    val title: String
)

fun TestItemDto.mapToModelTestItem() : TestItem{
    return TestItem(this.title)
}
package hu.bme.spacedumpling.worktimemanager.domain.api

import hu.bme.spacedumpling.worktimemanager.domain.dto.SimpleProjectDto
import hu.bme.spacedumpling.worktimemanager.domain.dto.TestItemDto
import retrofit2.http.GET

interface WorkTimeManagerAPIService {
    @GET("projects/echo")
    suspend fun test() : TestItemDto

    @GET("projects")
    suspend fun getListOfProjects() : List<SimpleProjectDto>
}
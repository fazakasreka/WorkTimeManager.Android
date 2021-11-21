package hu.bme.spacedumpling.worktimemanager.domain.api

import hu.bme.spacedumpling.worktimemanager.domain.dto.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface APIService {
    @GET("projects/echo")
    suspend fun test() : TestItemDto

    @GET("projects")
    suspend fun getListOfProjects() : List<SimpleProjectDto>

    @GET("projects/{projectId}")
    suspend fun getProjectDetails(@Path("projectId") projectId: Int) : DetailedProjectDto

    @PUT("projects")
    suspend fun updateProject(@Body body: ProjectUpdateDto)

    @GET("projects/tasks")
    suspend fun getTasksByProjects(): List<TasksByProjectDto>
}
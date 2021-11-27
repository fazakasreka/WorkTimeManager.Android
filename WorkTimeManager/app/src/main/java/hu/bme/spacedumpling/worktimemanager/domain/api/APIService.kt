package hu.bme.spacedumpling.worktimemanager.domain.api

import hu.bme.spacedumpling.worktimemanager.domain.api.dto.*
import retrofit2.http.*

interface APIService {
    @GET("projects/echo")
    suspend fun test() : TestItemDto

    @GET("projects/myprojects")
    suspend fun getListOfProjects() : List<SimpleProjectDto>

    @GET("projects/{projectId}")
    suspend fun getProjectDetails(@Path("projectId") projectId: Int) : DetailedProjectDto

    @PUT("projects")
    suspend fun updateProject(@Body body: ProjectUpdateDto)

    @GET("projects/tasks")
    suspend fun getTasksByProjects(): List<TasksByProjectDto>

    @GET("statistics")
    suspend fun getStatistics(): UserStatisticsDto

    @GET("home/myitems")
    suspend fun getHomePage(): HomePageDto

    @DELETE("timeIntervalItems/{timeIntervalId}")
    suspend fun deleteTimeInterval(@Path("timeIntervalId") timeIntervalId: Int)

    @POST("login")
    suspend fun login(@Body body: LoginDto) : Boolean

    @POST("home/myitems")
    suspend fun saveTimeInterval(@Body timeIntervalDto: TimeIntervalDto)
}
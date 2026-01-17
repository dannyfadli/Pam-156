package com.example.genshin_app.data.repository.build


import com.example.genshin_app.data.remote.dto.BuildDto
import com.example.genshin_app.data.remote.dto.BuildRequest


interface BuildRepository {
    suspend fun getBuilds(slug: String): Result<List<BuildDto>>
    suspend fun createBuild(slug: String, req: BuildRequest): Result<Int?>
    suspend fun updateBuild(id: Int, req: BuildRequest): Result<Unit>
    suspend fun deleteBuild(id: Int): Result<Unit>
}
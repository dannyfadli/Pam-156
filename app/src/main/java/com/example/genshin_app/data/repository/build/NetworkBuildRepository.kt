package com.example.genshin_app.data.repository.build


import com.example.genshin_app.data.remote.api.BuildApi
import com.example.genshin_app.data.remote.dto.BuildRequest


class NetworkBuildRepository(private val api: BuildApi) : BuildRepository {
    override suspend fun getBuilds(slug: String) = try {
        Result.success(api.getBuilds(slug))
    } catch (e: Exception) {
        Result.failure(e)
    }


    override suspend fun createBuild(slug: String, req: BuildRequest) = try {
        val res = api.createBuild(slug, req)
        Result.success(res.id)
    } catch (e: Exception) {
        Result.failure(e)
    }


    override suspend fun updateBuild(id: Int, req: BuildRequest) = try {
        api.updateBuild(id, req)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }


    override suspend fun deleteBuild(id: Int) = try {
        api.deleteBuild(id)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
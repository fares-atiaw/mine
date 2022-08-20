package com.example.android.devbyteviewer.work

import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.android.devbyteviewer.database.getDatabase
import com.example.android.devbyteviewer.repository.VideosRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters): CoroutineWorker(appContext, params)
{
    companion object {
        const val WORK_NAME = "RefreshDataWorker"   // That's the name of a certain task/work
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = VideosRepository(database)

        return try {
            repository.refreshVideos()
            Result.success()
        } catch (e: HttpException){
            Result.retry()
        }
    }

}
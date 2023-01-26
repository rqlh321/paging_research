package com.example.paging_reserch.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.paging_reserch.MainViewModel
import com.example.paging_reserch.adapter.Message
import com.example.paging_reserch.network.MessagesServiceApi

class MessagesPageSource(
    private val serviceApi: MessagesServiceApi
) : PagingSource<Int, Message>() {

    override fun getRefreshKey(state: PagingState<Int, Message>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Message> {
        return try {
            val currentPage = params.key ?: 0
            val response = serviceApi.messages(
                borderPosition = currentPage,
                limit = MainViewModel.PAGE_SIZE
            )
            LoadResult.Page(
                data = response.map { Message(it.id) },
                prevKey = currentPage.minus(1),
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            Log.w(this::class.java.simpleName, e)
            LoadResult.Error(e)
        }
    }

}
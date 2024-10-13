package ru.gubatenko.common.repo

import ru.gubatenko.common.CreateChatBody
import ru.gubatenko.common.database.ChatDao
import ru.gubatenko.common.database.ChatDatabaseEntity
import ru.gubatenko.server_api.ChatApi

class ChatRepo(
    private val dao: ChatDao
) {
    private val api: ChatApi = ChatApi()

    suspend fun create(name: String) {
        val chatBody = CreateChatBody(name)
        val chat = api.create(chatBody)
        val entity = ChatDatabaseEntity(id = chat.id.value)
        dao.upsert(entity)
    }

    suspend fun update() {
        val chats = api.chats()
        val entities = chats.map { ChatDatabaseEntity(id = it.id.value) }
        dao.upsert(entities)
    }

    fun chats() = dao.getAllChatsFlow()
}
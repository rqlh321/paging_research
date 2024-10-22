package ru.gubatenko.server.domain

import ru.gubatenko.common.CreateMessageBody
import ru.gubatenko.common.Response
import ru.gubatenko.server.data.DataStore
import ru.gubatenko.server.session.UserWebSocketSessionController

class CreateMessageUseCase(
    private val dataStore: DataStore,
    private val sessionController: UserWebSocketSessionController,
) {
    suspend fun run(body: CreateMessageBody): Response {
        val message = dataStore.createMessage(body)
        sessionController.send(message.senderId, message)
        return message
    }
}
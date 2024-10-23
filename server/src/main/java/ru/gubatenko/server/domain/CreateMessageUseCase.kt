package ru.gubatenko.server.domain

import ru.gubatenko.common.CreateMessageBody
import ru.gubatenko.common.Response
import ru.gubatenko.server.data.DataStore
import ru.gubatenko.server.session.UserWebSocketSessionController

class CreateMessageUseCase(
    private val dataStore: DataStore,
    private val sessionController: UserWebSocketSessionController,
) : UseCase<CreateMessageBody>() {
    override suspend fun run(args: CreateMessageBody): Response {
        val message = dataStore.createMessage(args)
        sessionController.send(message.senderId, message)
        return message
    }
}
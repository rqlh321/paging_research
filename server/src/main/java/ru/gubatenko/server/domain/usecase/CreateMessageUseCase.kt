package ru.gubatenko.server.domain.usecase

import ru.gubatenko.common.CreateMessageBody
import ru.gubatenko.common.Response
import ru.gubatenko.common.UserId
import ru.gubatenko.server.data.UserWebSocketSessionController
import ru.gubatenko.server.domain.UseCase
import ru.gubatenko.server.domain.repo.MessageRepository

class CreateMessageUseCase(
    private val dataStore: MessageRepository,
    private val sessionController: UserWebSocketSessionController,
) : UseCase<CreateMessageUseCaseArgs>() {
    override suspend fun run(args: CreateMessageUseCaseArgs): Response {
        val message = dataStore.createMessage(args.userId, args.body)
        sessionController.send(message.senderId, message)
        return message
    }
}

data class CreateMessageUseCaseArgs(
    val userId: UserId,
    val body: CreateMessageBody,
)

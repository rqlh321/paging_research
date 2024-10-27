package ru.gubatenko.server.data

import io.ktor.server.websocket.WebSocketServerSession
import io.ktor.server.websocket.sendSerialized
import io.ktor.websocket.close
import ru.gubatenko.common.Response
import ru.gubatenko.common.UserId
import java.util.concurrent.ConcurrentHashMap

class UserWebSocketSessionController {
    private val sessions = ConcurrentHashMap<UserId, WebSocketServerSession>()

    suspend fun checkInSession(userId: UserId, newSession: WebSocketServerSession) {
        sessions[userId]?.close()
        sessions[userId] = newSession
    }

    suspend fun send(userId: UserId, response: Response) {
        sessions[userId]?.sendSerialized(response)
    }
}
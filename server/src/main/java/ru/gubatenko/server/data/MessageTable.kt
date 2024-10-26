package ru.gubatenko.server.data

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.UUID

object MessageTable : UUIDTable("message") {
    val chatId = uuid("chat_id")
    val senderId = uuid("sender_id")
    val text = varchar("text", 50)
    val timestamp = long("timestamp")
}

class MessageDAO(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<MessageDAO>(MessageTable)

    var chatId by MessageTable.chatId
    var senderId by MessageTable.senderId
    var text by MessageTable.text
    var timestamp by MessageTable.timestamp
}
package ru.gubatenko.server.data

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.UUID

object ChatTable : UUIDTable("chat") {
    val ownerId = uuid("owner_id")
    val name = varchar("name", 50)
}

class ChatDAO(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ChatDAO>(ChatTable)

    var name by ChatTable.name
    var ownerId by ChatTable.ownerId
}
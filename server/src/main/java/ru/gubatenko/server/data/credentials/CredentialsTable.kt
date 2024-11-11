package ru.gubatenko.server.data.credentials

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.UUID

object CredentialsTable : UUIDTable("credential") {
    val token = varchar("token", 50)
    val userId = uuid("user_id")
}

class CredentialsDAO(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<CredentialsDAO>(CredentialsTable)

    var token by CredentialsTable.token
    var userId by CredentialsTable.userId
}
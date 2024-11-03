package ru.gubatenko.server.data.credentials

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.UUID

object CredentialsTable : UUIDTable("user") {
    val accessToken = varchar("access_token", 50)
    val refreshToken = varchar("refresh_token", 50)
}

class CredentialsDAO(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<CredentialsDAO>(CredentialsTable)

    var accessToken by CredentialsTable.accessToken
    var refreshToken by CredentialsTable.refreshToken
}
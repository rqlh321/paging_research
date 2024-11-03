package ru.gubatenko.server.data.user

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.UUID

object UserTable : UUIDTable("user") {
    val displayName = varchar("display_name", 50)
    val username = varchar("username", 50)
    val password = varchar("password", 50)
}

class UserDAO(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserDAO>(UserTable)

    var displayName by UserTable.displayName
    var username by UserTable.username
    var password by UserTable.password
}
package ru.gubatenko.server.data

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import ru.gubatenko.server.data.chat.ChatDAO
import ru.gubatenko.server.data.chat.ChatTable
import ru.gubatenko.server.data.credentials.CredentialsDAO
import ru.gubatenko.server.data.credentials.CredentialsTable
import ru.gubatenko.server.data.message.MessageDAO
import ru.gubatenko.server.data.message.MessageTable
import ru.gubatenko.server.data.user.UserDAO
import ru.gubatenko.server.data.user.UserTable

class DaoStore(
    database: Database
) {
    init {
        transaction(database) {
            SchemaUtils.create(UserTable)
            SchemaUtils.create(CredentialsTable)
            SchemaUtils.create(ChatTable)
            SchemaUtils.create(MessageTable)
        }
    }

    fun userDao() = UserDAO
    fun credentialDao() = CredentialsDAO
    fun chatDao() = ChatDAO
    fun messageDao() = MessageDAO
}
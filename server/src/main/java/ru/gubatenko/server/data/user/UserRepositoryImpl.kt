package ru.gubatenko.server.data.user

import ru.gubatenko.common.Password
import ru.gubatenko.common.Username
import ru.gubatenko.server.data.DaoStore
import ru.gubatenko.server.data.suspendTransaction
import ru.gubatenko.server.domain.repo.UserRepository
import java.util.UUID

class UserRepositoryImpl(
    private val daoStore: DaoStore
) : UserRepository {
    override suspend fun isNotExist(username: Username): Boolean = suspendTransaction {
        daoStore.userDao()
            .find { (UserTable.username eq username.value) }
            .empty()
    }

    override suspend fun create(username: Username, password: Password) {
        suspendTransaction {
            daoStore.userDao().new {
                this.username = username.value
                this.password = password.value
                this.displayName = UUID.randomUUID().toString()
            }
        }
    }
}
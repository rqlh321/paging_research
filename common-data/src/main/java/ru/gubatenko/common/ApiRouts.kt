package ru.gubatenko.common

object ApiRouts {
    const val AUTH = "/auth"
    const val LOGIN = "$AUTH/login"
    const val CREATE = "$AUTH/create"

    const val USER = "/user"
    const val DELETE_USER = "$USER/delete"
    const val LOGOUT_USER = "$USER/logout"

    const val CHATS = "/chats"
    const val MESSAGES = "/messages"
}
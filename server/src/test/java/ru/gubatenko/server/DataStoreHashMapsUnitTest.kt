package ru.gubatenko.server

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import ru.gubatenko.common.CreateChatBody
import ru.gubatenko.common.CreateMessageBody
import ru.gubatenko.common.Message
import ru.gubatenko.common.MessagesRout
import ru.gubatenko.server.data.DataStoreHashMaps
import kotlin.test.Test

class DataStoreHashMapsUnitTest {
    @Test
    fun messagesLoad() {
        runBlocking {
            val store = DataStoreHashMaps()
            val chatBody = CreateChatBody("test")
            val chat = store.createChat(chatBody)

            val createdMessages = ArrayList<Message>()
            (0..(LIMIT * 2)).forEach {
                delay(10)
                createdMessages.add(store.createMessage(CreateMessageBody(chat.id, it.toString())))
            }
            val messages = store.messages(
                MessagesRout(
                    chatId = chat.id,
                    messageId = createdMessages[15].id,
                    isDirectionToLatest = true,
                    limit = LIMIT
                )
            )
            println(messages)
        }
    }

    companion object {
        const val LIMIT = 10L
    }
}
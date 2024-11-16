package com.example.paging_reserch

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

class LearnCoroutine {

    @Test
    fun first() {
        runBlocking { // this: CoroutineScope
            doWorld()
            println("Hello 1")
        }
    }

    // Concurrently executes both sections
    suspend fun doWorld() = coroutineScope { // this: CoroutineScope
        launch {
            delay(2000L)
            println("World 2")
        }
        launch {
            delay(1000L)
            println("World 1")
        }
        println("Hello 2")
    }


    @Test
    fun second() {
        runBlocking {
            val job = launch { // launch a new coroutine and keep a reference to its Job
                delay(1000L)
                println("World!")
            }
            println("Hello")
            job.join() // wait until child coroutine completes
            println("Done")
        }
    }

    @Test
    fun third() {
        runBlocking {
            val deferred: Deferred<Int> = async {
                loadData()
            }
            println("waiting...")
            println(deferred.await())
        }
    }

    suspend fun loadData(): Int {
        println("loading...")
        delay(1000L)
        println("loaded!")
        return 42
    }

    @Test
    fun main() = runBlocking<Unit> {
        val channel = Channel<String>()
        launch {
            channel.send("A1")
            channel.send("A2")
            log("A done")
        }
        launch {
            channel.send("B1")
            log("B done")
        }
        launch {
            repeat(3) {
                val x = channel.receive()
                log(x)
            }
        }
    }

    fun log(message: Any?) {
        println("[${Thread.currentThread().name}] $message")
    }
}
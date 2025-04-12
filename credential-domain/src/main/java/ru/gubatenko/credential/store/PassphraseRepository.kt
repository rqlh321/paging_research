package ru.gubatenko.credential.store

abstract class PassphraseRepository {
    abstract fun getPassphrase(): ByteArray
}
package ru.gubatenko.app.core.android

import android.content.Context
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import ru.gubatenko.credential.store.PassphraseRepository
import java.io.File
import java.security.SecureRandom

private const val PASSPHRASE_LENGTH = 32
private const val CHILD = "passphrase.bin"

class PassphraseRepositoryImpl(
    private val context: Context
) : PassphraseRepository() {
    override fun getPassphrase(): ByteArray {
        val file = File(context.filesDir, CHILD)
        val encryptedFile = EncryptedFile.Builder(
            file,
            context,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        return if (file.exists()) {
            encryptedFile.openFileInput().use { it.readBytes() }
        } else {
            generatePassphrase().also { passphrase ->
                encryptedFile.openFileOutput().use { it.write(passphrase) }
            }
        }
    }

    private fun generatePassphrase(): ByteArray {
        val random = SecureRandom.getInstanceStrong()
        val result = ByteArray(PASSPHRASE_LENGTH)

        random.nextBytes(result)

        // filter out zero byte values, as SQLCipher does not like them
        while (result.contains(0)) {
            random.nextBytes(result)
        }

        return result
    }
}
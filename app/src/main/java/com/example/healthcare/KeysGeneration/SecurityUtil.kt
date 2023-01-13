package com.example.healthcare.KeysGeneration
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.annotation.RequiresApi
import java.security.*
import java.util.*

object SecurityUtil {

    private const val KEYSTORE_ALIAS =
        " com.example.healthcare.Registration"


    @RequiresApi(Build.VERSION_CODES.M)
    fun getKeyPair(): KeyPair? {
        val ks: KeyStore = KeyStore.getInstance("AndroidKeyStore").apply {
            load(null)
        }
        val aliases: Enumeration<String> = ks.aliases()
        val keyPair: KeyPair?


        if (aliases.toList().firstOrNull { it == KEYSTORE_ALIAS } == null) {
            // If it doesn't exist, generate new keypair
            val kpg: KeyPairGenerator = KeyPairGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_EC,
                "AndroidKeyStore"
            )
            val parameterSpec: KeyGenParameterSpec = KeyGenParameterSpec.Builder(
                KEYSTORE_ALIAS,
                KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY
            ).run {
                setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                build()
            }
            kpg.initialize(parameterSpec)

            keyPair = kpg.generateKeyPair()
        } else {
            // If it exists, load the existing keypair
            val entry = ks.getEntry(KEYSTORE_ALIAS, null) as? KeyStore.PrivateKeyEntry
            keyPair = KeyPair(entry?.certificate?.publicKey, entry?.privateKey)
        }
        return keyPair
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun getPublicKey(): String? {
        val keyPair = getKeyPair()
        val publicKey = keyPair?.public ?: return null
        return String(Base64.encode(publicKey.encoded, Base64.DEFAULT))
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun getPrivateKey(): String? {
        val keyPair = getKeyPair()
        val privateKey = keyPair?.private ?:return null
        return String(Base64.encode(privateKey.toString().encodeToByteArray(), Base64.DEFAULT))
    }
}

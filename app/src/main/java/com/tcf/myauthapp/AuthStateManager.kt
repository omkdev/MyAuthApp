package com.tcf.myauthapp
import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import net.openid.appauth.AuthState

// This class saves and loads the user's login state.
// AuthState holds everything: tokens, expiry, config.
class AuthStateManager(context: Context) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs = EncryptedSharedPreferences.create(
        context,
        "auth_encrypted",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun read(): AuthState {
        val json = prefs.getString("state", null) ?: return AuthState()
        return AuthState.jsonDeserialize(json)
    }

    fun write(state: AuthState) {
        prefs.edit().putString("state", state.jsonSerializeString()).apply()
    }

    fun clear() {
        prefs.edit().remove("state").apply()
    }
}
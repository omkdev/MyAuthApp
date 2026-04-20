package com.tcf.myauthapp
import android.content.Context
import net.openid.appauth.AuthState

// This class saves and loads the user's login state.
// AuthState holds everything: tokens, expiry, config.
class AuthStateManager(context: Context) {

    private val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)

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
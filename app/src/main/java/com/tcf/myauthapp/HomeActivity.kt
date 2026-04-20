package com.tcf.myauthapp



import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import net.openid.appauth.*

class HomeActivity : AppCompatActivity() {

    private lateinit var authService: AuthorizationService
    private lateinit var stateManager: AuthStateManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        authService = AuthorizationService(this)
        stateManager = AuthStateManager(this)

        val state = stateManager.read()

        // Show the first 80 characters of the access token so you can see it worked
        val token = state.accessToken ?: "No token found"
        findViewById<TextView>(R.id.tvToken).text = "${token.take(80)}..."

        // Logout button
        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        val state = stateManager.read()
        val config = state.authorizationServiceConfiguration

        if (config != null && config.endSessionEndpoint != null) {
            // Tell Keycloak to end the session on the server side too
            val endSessionRequest = EndSessionRequest.Builder(config)
                .setIdTokenHint(state.idToken)
                .setPostLogoutRedirectUri(Constants.LOGOUT_REDIRECT_URI)
                .build()

            val completionIntent = Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            stateManager.clear() // Clear local tokens first

            authService.performEndSessionRequest(
                endSessionRequest,
                android.app.PendingIntent.getActivity(
                    this, 0, completionIntent,
                    android.app.PendingIntent.FLAG_UPDATE_CURRENT or
                            android.app.PendingIntent.FLAG_IMMUTABLE
                )
            )
        } else {
            // Fallback: just clear local state
            stateManager.clear()
            startActivity(Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            })
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        authService.dispose()
    }
}
package com.tcf.myauthapp

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import net.openid.appauth.*

class LoginActivity : AppCompatActivity() {

    private lateinit var authService: AuthorizationService
    private lateinit var stateManager: AuthStateManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // ✅ Allow HTTP issuer (needed for local Keycloak)
        val appAuthConfig = AppAuthConfiguration.Builder()
            .setConnectionBuilder(AppAuthConnectionBuilder)
            .setSkipIssuerHttpsCheck(true)
            .build()

        authService = AuthorizationService(this, appAuthConfig)
        stateManager = AuthStateManager(this)

        // Handle redirect back from browser after login
        intent?.let { handleResponseIfPresent(it) }

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            startLogin()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleResponseIfPresent(intent)
    }

    private fun startLogin() {
        // ✅ Use fetchFromIssuer() so ALL endpoints are discovered
        // including end_session_endpoint (needed for proper logout)
        AuthorizationServiceConfiguration.fetchFromIssuer(
            Uri.parse(Constants.ISSUER),
            { config, ex ->
                if (config == null) {
                    Log.e("LoginActivity", "Failed to fetch config: ${ex?.message}")
                    runOnUiThread {
                        findViewById<TextView>(R.id.tvError).apply {
                            text = "Keycloak unreachable: ${ex?.message}"
                            visibility = View.VISIBLE
                        }
                    }
                    return@fetchFromIssuer
                }

                val authRequest = AuthorizationRequest.Builder(
                    config,
                    Constants.CLIENT_ID,
                    ResponseTypeValues.CODE,
                    Constants.REDIRECT_URI
                )
                    .setScope(Constants.SCOPE)
                    .build()

                val completionIntent = Intent(this, LoginActivity::class.java)

                authService.performAuthorizationRequest(
                    authRequest,
                    PendingIntent.getActivity(
                        this,
                        1001,
                        completionIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
                    )
                )
            },
            AppAuthConnectionBuilder  // ✅ Allow HTTP for discovery call
        )
    }

    private fun handleResponseIfPresent(intent: Intent) {
        val response = AuthorizationResponse.fromIntent(intent)
        val ex = AuthorizationException.fromIntent(intent)

        // Nothing to handle
        if (response == null && ex == null) return
        if (response == null) return

        // ✅ Reuse config from response (has all discovered endpoints)
        val authState = AuthState(response.request.configuration)
        authState.update(response, ex)
        stateManager.write(authState)

        authService.performTokenRequest(response.createTokenExchangeRequest()) { tokenResponse, tokenEx ->

            // ✅ Log warning but don't block on ID token validation errors
            // These are common with local HTTP Keycloak setups
            if (tokenEx != null) {
                Log.w("LoginActivity", "Token warning: ${tokenEx.message}")
            }

            val updatedState = stateManager.read()
            updatedState.update(tokenResponse, tokenEx)
            stateManager.write(updatedState)

            // ✅ Proceed as long as we have an access token
            if (tokenResponse?.accessToken != null) {
                runOnUiThread {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }
            } else {
                Log.e("LoginActivity", "No access token received. Error: ${tokenEx?.message}")
                runOnUiThread {
                    findViewById<TextView>(R.id.tvError).apply {
                        text = "Login failed: ${tokenEx?.message}"
                        visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        authService.dispose()
    }
}
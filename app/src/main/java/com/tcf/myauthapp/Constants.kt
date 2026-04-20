package com.tcf.myauthapp

import android.net.Uri

object Constants {
    // 🔧 Change this to your Keycloak server URL
    // For local development: http://10.0.2.2:8080/realms/YOUR_REALM
    // For production: https://your-keycloak-server.com/realms/YOUR_REALM
    const val ISSUER = "http://10.0.2.2:8080/realms/myrealm"

    // Your Keycloak client ID
    const val CLIENT_ID = "android-app"

    val REDIRECT_URI: Uri =
        Uri.parse("com.tcf.myauthapp://oauth2redirect")

    val LOGOUT_REDIRECT_URI: Uri =
        Uri.parse("com.tcf.myauthapp://oauth2redirect/logout")

    const val SCOPE = "openid email profile"
}
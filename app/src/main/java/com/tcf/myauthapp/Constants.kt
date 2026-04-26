package com.tcf.myauthapp

import android.net.Uri

object Constants {
    // 🔧 Change this to your Keycloak server URL
    // For local development: http://10.0.2.2:8080/realms/YOUR_REALM
    // For production: https://your-keycloak-server.com/realms/YOUR_REALM
    const val ISSUER = "http://10.0.2.2:8080/realms/myrealm"

    // Your Keycloak client ID
    const val CLIENT_ID = "android-app"

    const val REDIRECT_URI = "com.tcf.myauthapp://oauth2redirect"

    const val LOGOUT_REDIRECT_URI = "com.tcf.myauthapp://oauth2redirect/logout"

    const val SCOPE = "openid email profile"
}
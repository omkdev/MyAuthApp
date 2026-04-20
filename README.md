# MyAuthApp 🔐

An Android app demonstrating secure user authentication using
**OAuth2 Authorization Code Flow + PKCE** with **Keycloak** as
the identity provider.

Built as a learning project to understand industry-standard
authentication practices used by Google, Facebook, and every
major app.

---

## 📱 Screenshots

> Login Screen → Keycloak Login → Home Screen with Token

---

## ✨ Features

- ✅ OAuth2 Authorization Code Flow with PKCE
- ✅ OpenID Connect (OIDC) for identity
- ✅ Chrome Custom Tab for secure login (no WebView)
- ✅ Auto-discovery of Keycloak endpoints via fetchFromIssuer()
- ✅ Token storage using SharedPreferences
- ✅ Full logout with server-side session termination
- ✅ Keycloak running locally via Docker

---

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Kotlin | Android development |
| AppAuth (0.11.1) | OAuth2 / OIDC library |
| Keycloak 24.0.1 | Identity & Access Management server |
| Docker | Running Keycloak locally |
| Chrome Custom Tab | Secure browser for login |
| SharedPreferences | Token storage |

---

## 🏗️ Architecture
LoginActivity → fetchFromIssuer() → Chrome Custom Tab
↓
Keycloak Login Page (user enters credentials)
↓
Redirect back with auth code
↓
Token exchange (code + PKCE verifier)
↓
Tokens saved → HomeActivity

---

## 🚀 How to Run This Project

### Prerequisites
- Android Studio
- Docker Desktop
- Android Emulator (API 24+)

### Step 1 — Start Keycloak
```bash
docker run -p 8080:8080 \
  -e KEYCLOAK_ADMIN=admin \
  -e KEYCLOAK_ADMIN_PASSWORD=admin \
  quay.io/keycloak/keycloak:24.0.1 start-dev
```

### Step 2 — Configure Keycloak
1. Open `http://localhost:8080` → login with `admin/admin`
2. Create realm: `myrealm`
3. Create client: `android-app` (public client)
4. Set redirect URI: `com.tcf.myauthapp://*`
5. Create a test user with email verified ON

### Step 3 — Run the App
1. Open project in Android Studio
2. Update `Constants.kt` with your Keycloak URL
3. Run on emulator

---

## 📂 Project Structure
com.tcf.myauthapp/
├── Constants.kt                 → All config values
├── AppAuthConnectionBuilder.kt  → HTTP support for local dev
├── AuthStateManager.kt          → Token storage
├── LoginActivity.kt             → Login flow
└── HomeActivity.kt              → Post-login screen

---

## 🧠 What I Learned

- OAuth2 Authorization Code + PKCE flow
- Why Chrome Custom Tab is required (not WebView)
- How JWT tokens work (access, ID, refresh)
- Keycloak realm, client and user setup
- AppAuth library for Android
- Token lifecycle and storage

---

## ⚠️ Note

`AppAuthConnectionBuilder.kt` is for **local development only**
(allows HTTP). Remove it in production when using HTTPS.

---

## 📄 License

MIT License — feel free to use this as a reference for your own projects.

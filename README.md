# MyAuthApp

Secure Android authentication demo using **OAuth2 Authorization Code Flow + PKCE** with **Keycloak** as the Identity Provider.

Built to replicate **real-world authentication flows** used in modern applications while focusing on **security, token management, and clean architecture**.

---

## 🚀 Key Highlights

- 🔐 **Secure OAuth2 + PKCE Flow** (prevents authorization code interception)
- 🌐 **Browser-based login via Chrome Custom Tabs** (no WebView vulnerabilities)
- 🧠 **OIDC-compliant identity handling**
- 🔄 **Token lifecycle management** (access, refresh, ID tokens)
- 🐳 **Locally hosted Keycloak using Docker**
- 🏗️ **Modular architecture separating auth, storage, and UI layers**

---

## 📱 Screenshots

> Login → Keycloak → Redirect → Authenticated Home
> 


---

## 🛠️ Tech Stack

| Technology | Purpose |
| --- | --- |
| Kotlin | Android development |
| AppAuth | OAuth2 / OIDC implementation |
| Keycloak | Identity & Access Management |
| Docker | Local Keycloak setup |
| Chrome Custom Tabs | Secure authentication UI |
| SharedPreferences | Token persistence (demo) |

---

## 🏗️ Architecture Flow

```
LoginActivity
   ↓
fetchFromIssuer()  (OIDC discovery)
   ↓
Chrome Custom Tab (Auth Request)
   ↓
Keycloak Login
   ↓
Redirect with Authorization Code
   ↓
Token Exchange (Code + PKCE Verifier)
   ↓
AuthStateManager (store tokens)
   ↓
HomeActivity
```

---

## 🔐 Security Considerations

- ✅ PKCE used to mitigate **authorization code interception**
- ✅ Chrome Custom Tabs used instead of WebView (prevents credential leakage)
- ⚠️ Token storage currently uses SharedPreferences (for demo)

### 🔥 Production Improvements

- Replace with **EncryptedSharedPreferences + Android KeyStore**
- Enforce **HTTPS only** (remove custom connection builder)
- Add **certificate pinning**
- Implement **secure logout + token revocation**

---

## 🚀 Getting Started

### Prerequisites

- Android Studio
- Docker
- Android Emulator (API 24+)

---

### 1️⃣ Start Keycloak

```
docker run-p8080:8080 \
-eKEYCLOAK_ADMIN=admin \
-eKEYCLOAK_ADMIN_PASSWORD=admin \
  quay.io/keycloak/keycloak:24.0.1 start-dev
```

---

### 2️⃣ Configure Keycloak

- Open: `http://localhost:8080`
- Login: `admin / admin`

Create:

- Realm → `myrealm`
- Client → `android-app` (public)
- Redirect URI → `com.tcf.myauthapp://*`
- Test user (email verified)

---

### 3️⃣ Run the App

- Update `Constants.kt`
- Run on emulator
- Click Login → authenticate → redirected back

---

## 📂 Project Structure

```
com.tcf.myauthapp/
├── Constants.kt                # Config values
├── AppAuthConnectionBuilder   # HTTP support (dev only)
├── AuthStateManager           # Token handling
├── LoginActivity              # Auth entry point
└── HomeActivity               # Post-login UI
```

---

## 🧠 Learnings

- OAuth2 Authorization Code Flow with PKCE (end-to-end)
- OIDC identity layer on top of OAuth2
- Secure mobile authentication patterns
- Token lifecycle (access, refresh, ID tokens)
- Keycloak setup (realm, client, users)
- AppAuth integration in Android

---

## ⚠️ Important Note

`AppAuthConnectionBuilder.kt` is used **only for local HTTP testing**.

**Do NOT use in production.**

---

## 📈 Future Enhancements

- 🔐 Encrypted token storage (Jetpack Security + KeyStore)
- 🌍 Multi-environment support (dev/staging/prod)
- 📡 Backend API integration using access tokens
- 🔄 Silent token refresh handling
- 🧪 Unit + integration testing for auth flows

---

## 📄 License

MIT License

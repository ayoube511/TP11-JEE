# 🔐 Spring Security JPA

> Authentification depuis **MySQL** avec **BCrypt** et **UserDetailsService** personnalisé — Spring Boot 3 + Spring Security + JPA + Thymeleaf

![Java](https://img.shields.io/badge/Java-17+-orange?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3-brightgreen?style=flat-square&logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6.x-darkgreen?style=flat-square&logo=springsecurity)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=flat-square&logo=mysql)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.x-green?style=flat-square)

---

## 📋 Description

Ce TP fait évoluer l'authentification en mémoire (TP1 & TP2) vers une **authentification persistante depuis MySQL**.  
Les utilisateurs et leurs rôles sont stockés en base de données, et les mots de passe sont protégés avec **BCrypt**.

---

## Resultat et Apperçu 
<img width="1919" height="926" alt="Capture d&#39;écran 2026-03-22 193415" src="https://github.com/user-attachments/assets/152f09ce-67ba-4da5-aa31-4227f5b382dd" />
<img width="1919" height="872" alt="Capture d&#39;écran 2026-03-22 193451" src="https://github.com/user-attachments/assets/fc236956-71c6-4bce-a186-5e1df026f014" />

---

## ✨ Fonctionnalités

- 🗄️ Utilisateurs stockés en **MySQL**
- 🔒 Mots de passe encodés avec **BCrypt**
- 👤 Rôle **USER** → `/user/dashboard`
- 🔑 Rôle **ADMIN** → `/admin/dashboard`
- 🚫 Page **403** pour les accès refusés
- 🚪 Déconnexion propre avec message de confirmation
- ⚡ Initialisation automatique des données au démarrage

---

## 🏗️ Architecture du projet
```
src/main/java/ma/fstg/security/
├── config/
│   ├── SecurityConfig.java              # Règles de sécurité + BCrypt + DaoAuthProvider
│   └── DatabaseInitializer.java         # Insertion automatique des données
├── entities/
│   ├── User.java                        # Entité JPA → table users
│   └── Role.java                        # Entité JPA → table roles
├── repositories/
│   ├── UserRepository.java              # Accès base utilisateurs
│   └── RoleRepository.java              # Accès base rôles
├── services/
│   └── CustomUserDetailsService.java    # Charge les users depuis MySQL
├── web/
│   └── AuthController.java              # Contrôleur des vues
└── SpringSecurityJpaApplication.java

src/main/resources/templates/
├── login.html
├── home.html
├── user-dashboard.html
├── admin-dashboard.html
└── access-denied.html
```

---

## 🛠️ Technologies

| Technologie | Rôle |
|---|---|
| Spring Boot 3.3 | Framework principal |
| Spring Security 6 | Authentification & autorisation |
| Spring Data JPA | Persistance des données |
| MySQL 8 | Base de données |
| BCrypt | Encodage des mots de passe |
| Thymeleaf | Templates HTML |
| Lombok | Réduction du code boilerplate |

---

## ⚙️ Prérequis

- ✅ JDK 17+
- ✅ MySQL 8.x installé et démarré
- ✅ Maven 3.x
- ✅ IntelliJ IDEA

---

## 🚀 Installation & Lancement

### 1. Cloner le projet
```bash
git clone https://github.com/ton-username/spring-security-jpa.git
cd spring-security-jpa
```

### 2. Créer la base MySQL
```sql
CREATE DATABASE security_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. Configurer application.properties
```properties
spring.datasource.username=root
spring.datasource.password=ton_mot_de_passe
```

### 4. Lancer l'application
```bash
mvn spring-boot:run
```

### 5. Accéder à l'application
```
http://localhost:8080/login
```

---

## 🗄️ Tables créées automatiquement

Hibernate génère automatiquement au démarrage :

| Table | Contenu |
|---|---|
| `users` | id, username, password (BCrypt), active |
| `roles` | id, name (ROLE_USER, ROLE_ADMIN) |
| `user_roles` | user_id, role_id (relation ManyToMany) |

---

## 🧪 Comptes de test

| Utilisateur | Mot de passe | Rôle | Accès |
|---|---|---|---|
| `user` | `1111` | ROLE_USER | `/home`, `/user/dashboard` |
| `admin` | `1234` | ROLE_ADMIN + ROLE_USER | Toutes les pages |

> Les mots de passe sont stockés sous forme BCrypt : `$2a$10$...`

---

## 🔑 Points clés
```java
// CustomUserDetailsService — charge l'utilisateur depuis MySQL
User user = userRepository.findByUsername(username)
    .orElseThrow(() -> new UsernameNotFoundException("..."));

// SecurityConfig — BCrypt + DaoAuthenticationProvider
provider.setUserDetailsService(userDetailsService);
provider.setPasswordEncoder(new BCryptPasswordEncoder());

// DatabaseInitializer — encodage au démarrage
encoder.encode("1234") // → $2a$10$...
```

---

## 📡 Pages disponibles

| URL | Accès |
|---|---|
| `/login` | Public |
| `/home` | Connecté |
| `/user/dashboard` | ROLE_USER + ROLE_ADMIN |
| `/admin/dashboard` | ROLE_ADMIN uniquement |
| `/access-denied` | Page 403 |
| `/logout` | Déconnexion |

---

## 👨‍💻 Auteur

**Ayoub Moubssite**  
TP11 Spring Security — Authentification MySQL + BCrypt + JPA

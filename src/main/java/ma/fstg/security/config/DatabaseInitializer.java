package ma.fstg.security.config;

import ma.fstg.security.entities.Role;
import ma.fstg.security.entities.User;
import ma.fstg.security.repositories.RoleRepository;
import ma.fstg.security.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseInitializer {

    /**
     * Insère des données initiales au démarrage de l'application.
     * Vérifie que les données n'existent pas déjà pour éviter les doublons
     * lors des redémarrages successifs.
     */
    @Bean
    CommandLineRunner init(RoleRepository roleRepo,
                           UserRepository userRepo,
                           BCryptPasswordEncoder encoder) {
        return args -> {

            // Création des rôles si inexistants
            if (!roleRepo.existsByName("ROLE_ADMIN")) {
                roleRepo.save(new Role(null, "ROLE_ADMIN"));
            }
            if (!roleRepo.existsByName("ROLE_USER")) {
                roleRepo.save(new Role(null, "ROLE_USER"));
            }

            Role adminRole = roleRepo.findByName("ROLE_ADMIN");
            Role userRole  = roleRepo.findByName("ROLE_USER");

            // Création de l'utilisateur admin si inexistant
            if (!userRepo.existsByUsername("admin")) {
                User admin = new User(
                        null,
                        "admin",
                        encoder.encode("1234"),
                        true,
                        List.of(adminRole, userRole)
                );
                userRepo.save(admin);
                System.out.println("✅ Utilisateur 'admin' créé (mot de passe encodé avec BCrypt)");
            }

            // Création de l'utilisateur user si inexistant
            if (!userRepo.existsByUsername("user")) {
                User user = new User(
                        null,
                        "user",
                        encoder.encode("1111"),
                        true,
                        List.of(userRole)
                );
                userRepo.save(user);
                System.out.println("✅ Utilisateur 'user' créé (mot de passe encodé avec BCrypt)");
            }

            System.out.println("✅ Base de données initialisée avec succès !");
        };
    }
}

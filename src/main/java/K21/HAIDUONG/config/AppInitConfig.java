package K21.HAIDUONG.config;

import K21.HAIDUONG.model.User;
import K21.HAIDUONG.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//@AllArgsConstructor
@RequiredArgsConstructor
@Configuration
public class AppInitConfig implements CommandLineRunner {
     UserRepository userRepository;
     PasswordEncoder passwordEncoder;

    @Value("${app.default-admin.username}")
    String defaultUsername;

    @Value("${app.default-admin.password}")
    String defaultPassword;

    @Override
    public void run(String... args) throws Exception {
        log.info("Application started!");
        if (!userRepository.existsByUsername("admin")) {
            userRepository.save(User.builder()
                    .username(defaultUsername)
                    .password(passwordEncoder.encode(defaultPassword))
                    .build());
            log.info("Created default admin user with username: admin and password: admin");
        }
    }
}

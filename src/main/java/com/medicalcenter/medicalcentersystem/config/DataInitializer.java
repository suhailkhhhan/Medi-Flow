package com.medicalcenter.medicalcentersystem.config;


import com.medicalcenter.medicalcentersystem.dao.UserRepository;
import com.medicalcenter.medicalcentersystem.model.Role;
import com.medicalcenter.medicalcentersystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create ADMIN user if not exists
        if (userRepository.findByUsername("admin").isEmpty()) {
    User admin = new User();
    admin.setUsername("admin");
    admin.setPassword(passwordEncoder.encode("password")); // <-- This line is key
    admin.setRoles(Set.of(Role.ROLE_ADMIN));
    userRepository.save(admin);
}

        // Create regular USER if not exists
        if (userRepository.findByUsername("user").isEmpty()) {
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("password"));
            user.setRoles(Set.of(Role.ROLE_USER));
            userRepository.save(user);
        }
    }
}

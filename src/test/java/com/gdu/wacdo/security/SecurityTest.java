package com.gdu.wacdo.security;

import com.gdu.wacdo.entities.Collaborateur;
import com.gdu.wacdo.repositories.CollaborateurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CollaborateurRepository collaborateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        collaborateurRepository.deleteAll(); // clean table before each test

        Collaborateur admin = new Collaborateur();
        admin.setNom("admin");
        admin.setPrenom("test");
        admin.setEmail("admin@gmail.com");
        admin.setDateEmbauche(LocalDate.now());
        admin.setAdministrateur(true);
        admin.setPassWord(passwordEncoder.encode("password"));

        collaborateurRepository.save(admin);
    }

    @Test
    void shouldRedirectToLoginWhenNotAuthenticated() throws Exception {
        mockMvc.perform(get("/restaurants"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    void shouldAuthenticateAndAccessRestaurantsPage() throws Exception {
        mockMvc.perform(formLogin("/login")
                        .user("admin@gmail.com")
                        .password("password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/restaurants"));

        mockMvc.perform(get("/collaborateurs")
                        .with(user("admin@gmail.com").roles("ADMIN")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFailAuthenticationWithBadPassword() throws Exception {
        mockMvc.perform(formLogin("/login")
                        .user("admin@gmail.com")
                        .password("wrongpassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));
    }
}
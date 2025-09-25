package com.gdu.wacdo.config;

import com.gdu.wacdo.entities.Collaborateur;
import com.gdu.wacdo.repositories.CollaborateurRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CollaborateurRepository collaborateurRepository;

    public CustomUserDetailsService(CollaborateurRepository collaborateurRepository) {
        this.collaborateurRepository = collaborateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Collaborateur admin = collaborateurRepository.findByEmailAndAdministrateurTrue(email)
                .orElseThrow(() -> {
                    log.warn("Pas d'admin trouvé avec l'email : {}", email);
                    return new UsernameNotFoundException("Pas d'admin trouvé avec l'email : " + email);
                });
        return User.withUsername(email)
                .password(admin.getPassWord())
                .roles("ADMIN")
                .build();
    }
}

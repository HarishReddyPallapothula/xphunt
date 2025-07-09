package studio.contrarian.xphunt.auth.service;

import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import studio.contrarian.xphunt.app.model.Hunter;
import studio.contrarian.xphunt.app.repo.HunterRepository;
import studio.contrarian.xphunt.auth.model.CustomUserDetails;

@Service

public class CustomUserDetailsService implements UserDetailsService {

    private final HunterRepository hunterRepository;

    public CustomUserDetailsService(HunterRepository hunterRepository) {
        this.hunterRepository = hunterRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Hunter hunter = hunterRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with name: " + username));
        
        return new CustomUserDetails(hunter);
    }

    @Transactional // Good practice for DB calls
    public UserDetails loadUserById(Long id) {
        Hunter hunter = hunterRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
        return new CustomUserDetails(hunter);
    }
}
package studio.contrarian.xphunt.auth.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import studio.contrarian.xphunt.app.model.Hunter;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final Hunter hunter;

    public CustomUserDetails(Hunter hunter) {
        this.hunter = hunter;
    }

    // --- Custom method to easily get the ID ---
    public Long getId() {
        return hunter.getId();
    }

    // --- UserDetails interface methods ---
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // For now, we return an empty list as we don't have roles.
        // In a real app, you would map roles/permissions here.
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return hunter.getPassword();
    }

    @Override
    public String getUsername() {
        // We use the hunter's name as the username
        return hunter.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
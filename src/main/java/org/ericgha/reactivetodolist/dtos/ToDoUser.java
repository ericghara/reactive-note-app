package org.ericgha.reactivetodolist.security;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RequiredArgsConstructor
@Builder
public class ToDoUser implements UserDetails {

    private final String userId;
    private final  String username;
    private final String email;
    private final Boolean emailVerified;
    private final String fullName;
    private final String firstName;
    private final String lastName;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public boolean isAccountNonLocked() {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public boolean isCredentialsNonExpired() {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public boolean isEnabled() {
        throw new UnsupportedOperationException("Not Implemented");
    }
}

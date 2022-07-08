package org.ericgha.reactivetodolist.dtos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RequiredArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@Getter
public class ToDoUser implements UserDetails {

    @NonNull
    private final String userId;
    @NonNull
    private final  String username;
    private final String email;
    private final Boolean emailVerified;
    private final String fullName;
    private final String firstName;
    private final String lastName;
    @NonNull
    private final String realm;


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
        return username;
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

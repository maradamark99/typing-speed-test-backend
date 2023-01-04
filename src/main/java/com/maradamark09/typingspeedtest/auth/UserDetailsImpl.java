package com.maradamark09.typingspeedtest.auth;

import com.maradamark09.typingspeedtest.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@AllArgsConstructor
@Getter
public class UserDetailsImpl implements UserDetails {

    private String username;
    private String password;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
    private Set<GrantedAuthority> grantedAuthorities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }



    public UserDetailsImpl(String username, String password, Set<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.grantedAuthorities = authorities;
        this.isAccountNonExpired = true;
        this.isCredentialsNonExpired = true;
        this.isEnabled = true;
        this.isAccountNonLocked = true;
    }

    public static UserDetailsImpl build(User user) {
        var role = new SimpleGrantedAuthority(
            user.getRole()
                    .toString()
        );

        return new UserDetailsImpl(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(role)
            );
    }

}

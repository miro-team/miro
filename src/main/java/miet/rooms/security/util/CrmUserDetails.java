package miet.rooms.security.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import miet.rooms.security.jpa.entity.User;
import miet.rooms.security.jpa.entity.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class CrmUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;
    private Collection<? extends GrantedAuthority> authorities;
    private String password;
    private String username;

    public CrmUserDetails(User user) {
        this.username = user.getUserLogin();
        this.password = user.getPassword();
        this.authorities = translate(Arrays.asList(user.getRole()));
    }

    private Collection<? extends GrantedAuthority> translate(List<UserRole> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (UserRole role : roles) {
            String name = role.getName().toUpperCase();
            if (!name.startsWith("ROLE_")) {
                name = "ROLE_" + name;
            }
            authorities.add(new SimpleGrantedAuthority(name));
        }
        return authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

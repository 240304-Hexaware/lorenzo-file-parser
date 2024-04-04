package com.revature.entity;
import com.revature.enums.UserRole;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
@Document(collection = "users")
public class User implements UserDetails {
    @Id
    private String id;
    private String username;
    private String email;
    private String password;
    private UserRole role;
    private boolean isAccountDisabled;

    public User(){
    }

    public User(String id, String username, String email, String password, UserRole role, boolean isAccountDisabled) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isAccountDisabled = isAccountDisabled;
    }


    public String getId() {
        return id;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


    @Override
    public String getPassword() {
        return password;
    }


    public String getEmail() {
        return email;
    }


    public UserRole getRole() {
        return role;
    }

    public boolean isAccountDisabled() {
        return isAccountDisabled;
    }


    public void setPassword(String password) {
        this.password = password;
    }

}

package com.revature.authentication;


import com.revature.entity.User;
import com.revature.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String username;
    private Boolean isAccountDisabled;
    private String email;
    private String password;
    private UserRole role;
}
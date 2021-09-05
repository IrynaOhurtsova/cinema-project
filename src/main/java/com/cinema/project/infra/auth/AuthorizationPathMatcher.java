package com.cinema.project.infra.auth;

import com.cinema.project.user.User;
import com.cinema.project.user.UserRole;
import lombok.Value;

import java.util.Arrays;
import java.util.List;

@Value
public class AuthorizationPathMatcher {

    String pathRegex;
    List<UserRole> accessRoles;

    public AuthorizationPathMatcher(String pathRegex, UserRole... roles) {
        this.pathRegex = pathRegex;
        this.accessRoles = Arrays.asList(roles);
    }

    public boolean pathMatch(String path) {
        return path.matches(pathRegex);
    }

    public boolean hasRole(User user) {
        return user != null && accessRoles.contains(user.getRole());
    }
}

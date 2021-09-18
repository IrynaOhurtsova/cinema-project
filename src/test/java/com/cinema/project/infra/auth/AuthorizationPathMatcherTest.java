package com.cinema.project.infra.auth;

import com.cinema.project.user.User;
import com.cinema.project.user.UserRole;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class AuthorizationPathMatcherTest {

    private final AuthorizationPathMatcher authorizationPathMatcher = new AuthorizationPathMatcher("/app", UserRole.CLIENT);

    private final User client = new User(1L, "", "", UserRole.CLIENT);
    private final User admin = new User(2L, "", "", UserRole.ADMIN);

    @Test
    public void pathMatchSuccessful() {
        assertTrue(authorizationPathMatcher.pathMatch("/app"));
    }

    @Test
    public void pathMatchNotSuccessful() {
        assertFalse(authorizationPathMatcher.pathMatch("/"));
    }

    @Test
    public void hasRoleSuccessful() {
        assertTrue(authorizationPathMatcher.hasRole(client));
    }

    @Test
    public void hasRoleNotSuccessful() {
        assertFalse(authorizationPathMatcher.hasRole(admin));
    }

    @Test
    public void hasRoleNotSuccessfulWithEmptyUser() {
        assertFalse(authorizationPathMatcher.hasRole(null));
    }
}
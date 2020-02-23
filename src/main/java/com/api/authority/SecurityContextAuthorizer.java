package com.api.authority;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

/**
 * 运用于角色权限管理
 * TODO : 需要完善
 *
 * @author 李星源
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
public class SecurityContextAuthorizer implements SecurityContext {
    private Principal principal;
    private String role;

    @Override
    public Principal getUserPrincipal() {
        return this.principal;
    }

    @Override
    public boolean isUserInRole(String s) {
        return true;
    }

    @Override
    public boolean isSecure() {
        return true;
    }

    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.DIGEST_AUTH;
    }
}

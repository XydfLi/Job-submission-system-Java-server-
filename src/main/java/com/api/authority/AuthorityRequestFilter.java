package com.api.authority;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * 角色管理，权限过滤
 * TODO : 需要完善
 *
 * @author 李星源
 * @version 1.0
 */
//@Provider
public class AuthorityRequestFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        containerRequestContext.setSecurityContext(new SecurityContextAuthorizer(null,"Administrator"));
    }
}

package com.epam.training.order.clientsecurity;

import com.epam.training.order.security.SecurityUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;

public class JwtTokenRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        String jwtToken = SecurityUtils.getAuthenticatedUserDetails().getJwtToken();
        template.header("Authorization", "Bearer " + jwtToken);
    }
}

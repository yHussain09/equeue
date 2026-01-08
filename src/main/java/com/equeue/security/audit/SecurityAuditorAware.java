package com.equeue.security.audit;

import com.equeue.security.jwt.UserPrincipal;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityAuditorAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof UserPrincipal p)) {
            return Optional.empty();
        }

        return Optional.of(p.getId());
    }
}
